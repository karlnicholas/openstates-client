package org.openstates.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

/**
 * OpenStates API. This is a fully static class so that
 * it gets initialized once and then any API class 
 * will not have to initialize it again.
 *
 */
public class OpenStates implements OpenStatesAPI {
	private static final Logger logger = Logger.getLogger(OpenStates.class.getName());
	public static final String apikeyKey = "apikey";
	public static final String cacheKey = "cache";
	private String apiKey;
	private String apiServer;
	private String cache;
	private ObjectMapper mapper;
	SimpleDateFormat dateFormat;
    private char[] buffer;
    private static boolean checkCache;
    private static boolean suspendCache;

    /**
     * Initialize the API. Called instead of a constructor.
     *
     * @param bundle the bundle
     */
	public OpenStates(ResourceBundle bundle) throws OpenStatesException {
		// API not needed for testing
		if ( !bundle.containsKey(apikeyKey)) throw new OpenStatesException("No apikey found in openstates.properties");
		apiKey = bundle.getString(apikeyKey);
		if ( apiKey == null ) throw new OpenStatesException("apikey not set in openstates.properties");
		if ( !bundle.containsKey(cacheKey)) throw new OpenStatesException("No apikey found in openstates.properties");
		cache = bundle.getString(cacheKey);
		if ( cache.lastIndexOf('/') != (cache.length()-1)) cache = cache+"/";
		File cacheFile = new File(cache);
		if ( !cacheFile.exists() ) {
			logger.config("Creating directories for cache:" + cacheFile.toString());
			cacheFile.mkdirs();
		}
		apiServer="openstates.org/api/v1";
		checkCache = true;
		suspendCache = false;
		mapper = new ObjectMapper();
		mapper.addHandler(new MyDeserializationProblemHandler() );
		dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		mapper.setDateFormat( dateFormat );
		// arbitrary large size (2^20)
		buffer = new char[262144];
	}

	public static class MyDeserializationProblemHandler extends DeserializationProblemHandler {
		public boolean handleUnknownProperty(
			DeserializationContext ctxt,
	        JsonParser jp,
	        JsonDeserializer<?> deserializer,
	        Object beanOrClass,
	        String propertyName) throws IOException, JsonProcessingException 
	    {
			if ( propertyName.charAt(0) == '+' ) {
				ctxt.getParser().skipChildren();
				return true;
			}
			else return false;
	    }
	}
	
	/**
	 * Modify whether or not the cache is first checked for files.
	 * Note that any JSON read will always be written to the
	 * cache.
	 *
	 * @param checkCache the check cache
	 */
	public static void checkCacheFirst(boolean checkCache) {
		if ( OpenStates.checkCache != checkCache ) logger.fine("Changing checkCache setting to:" + checkCache );
		OpenStates.checkCache = checkCache;
	}
	
	public static boolean getCacheFirst() {
		return OpenStates.checkCache;
	}
	
	public static void suspendCaching() {
		suspendCache = true;
	}

	public <T> T query(MethodMap methodMap, ArgMap argMap, Class<T> responseType) throws OpenStatesException {
		try {
			Reader reader;
			StringBuilder JSONBuilder = new StringBuilder();
			String JSONStr = null;
		    HttpURLConnection con = null;
			BufferedReader breader;
			StringBuilder filename = new StringBuilder( cache );
			StringBuilder method = new StringBuilder("/");
			for (String key: methodMap )
			{
				filename.append( key );
				filename.append('.');
				method.append(key);
				method.append('/');
			}
			// trim off last char
			method.deleteCharAt(method.length()-1);
			filename.deleteCharAt(filename.length()-1);
			// do args if any
			if ( argMap!= null ) {
				for (String key: argMap.keySet() )
				{
					String value = argMap.get(key);
					if ( value == null ) continue;
					filename.append('.');
					filename.append( key );
					filename.append('.');
					filename.append( value );
				}
			} 
			// save this for later
			//filename.append(".json" );

			File file = new File(filename.toString() + ".json");

			long fileLength = file.length(); 
			logger.fine("Length of File in cache:" + fileLength + ": " + filename.toString());
			if ( fileLength == 0L || OpenStates.checkCache == false || suspendCache == true) {
				StringBuilder terms = new StringBuilder();
				// Iterate through the keys and their values, both are important
				if ( argMap != null ) {
					for (String key: argMap.keySet() )
					{
						String value = argMap.get(key);
						if ( value == null ) continue;
						terms.append( '&' );
						terms.append( key );
						terms.append('=' );
						terms.append( URLEncoder.encode(value, "UTF-8" ) );
					}
				}
				
				// construct the url ..
				URI uri = new URI(
					"http", 
					null,
					apiServer, 
					-1,
					method.toString(), 
					"apikey=" + apiKey + terms.toString(), 
					null
				);
				logger.fine(uri.toString());
				
				con = (HttpURLConnection) uri.toURL().openConnection();
			    con.setRequestMethod("GET");
			    con.addRequestProperty("Accept", "text/json, application/json");
			    con.connect();
				breader = new BufferedReader(new InputStreamReader( con.getInputStream(), Charset.forName("UTF-8") ) );
			} else {
				breader = new BufferedReader(new InputStreamReader( new FileInputStream(file), Charset.forName("UTF-8") ) );
			}
			
			int read;
			while ( (read = breader.read(buffer)) != -1 ) {
				JSONBuilder.append(buffer, 0, read);
			}
			breader.close();
			JSONStr = JSONBuilder.toString();
			reader = new StringReader(JSONStr);
			
        	if ( fileLength == 0L || OpenStates.checkCache == false || suspendCache == true ) {
        		con.disconnect();
        		if ( suspendCache != true ) {
					BufferedWriter bwriter = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(file), Charset.forName("UTF-8")) );
					bwriter.write(JSONStr);
					bwriter.close();
        		}
        	}
        	return mapper.readValue(reader, responseType);
		} catch ( UnsupportedEncodingException e) {
			throw new OpenStatesException(e);
		} catch (IOException e) {
			throw new OpenStatesException(e);
		} catch (URISyntaxException e) {
			throw new OpenStatesException(e);
		} finally {
			if ( suspendCache == true ) suspendCache = false;
		}
	}
}
