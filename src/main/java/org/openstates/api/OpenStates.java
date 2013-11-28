package org.openstates.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

/**
 * <pre>
 * OpenStates API. This is a fully static class so that
 * it gets initialized once and then any API class 
 * will not have to initialize it again. This
 * class is not thread-safe and is generally not for public
 * consumption. Acceptable methods are
 * 
 * getCache()
 * setCache()
 * suspendCache()
 * 
 * See below.
 *</pre>
 *
 */
public class OpenStates implements OpenStatesAPI {
	private static final Logger logger = Logger.getLogger(OpenStates.class.getName());
	public static final String apikeyKey = "apikey";
	public static final String cacheKey = "cache";
	public static final String apiServer = "openstates.org/api/v1";
	private static String apiKey;
	private static String cache;
    private static boolean checkCache;
    private static boolean suspendCache;
	private static ObjectMapper mapper;
	public static SimpleDateFormat dateFormat;

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
		if ( bundle.containsKey(cacheKey)) {
			cache = bundle.getString(cacheKey);
			if ( cache.lastIndexOf('/') != (cache.length()-1)) cache = cache+"/";
			File cacheFile = new File(cache);
			logger.config("cache directory:" + cacheFile.toString());
			if ( !cacheFile.exists() ) {
				logger.config("Creating directories for cache:" + cacheFile.toString());
				cacheFile.mkdirs();
			}
		}
		checkCache = true;
		suspendCache = false;
		mapper = new ObjectMapper();
		mapper.addHandler(new MyDeserializationProblemHandler() );
		dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		mapper.setDateFormat( dateFormat );
		// arbitrary large size (2^20)
	}

	private static class MyDeserializationProblemHandler extends DeserializationProblemHandler {
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
	public static void setCache(boolean checkCache) {
		if ( OpenStates.checkCache != checkCache ) logger.fine("Changing checkCache setting to:" + checkCache );
		OpenStates.checkCache = checkCache;
	}

	/**
	 * Get the current state of the caching flag
	 * 
	 * @return the current state of the caching flag
	 */
	public static boolean getCache() {
		return OpenStates.checkCache;
	}
	
	/**
	 * Disable caching for one call, and one call only.
	 * 
	 * This call disables caching for the next call 
	 * regardless of the current state of caching. It 
	 * does not disable the current state of caching.  
	 * 
	 */
	public static void suspendCache() {
		suspendCache = true;
	}

	/**
	 * Handles the actual API calls and caching.
	 * 
	 *  This is part of a static class and therefore is not thread-safe. This method 
	 *  is not for public consumption.
	 *   
	 */
	public <T> T query(MethodMap methodMap, ArgMap argMap, Class<T> responseType) throws OpenStatesException {
		BufferedReader reader = null;
		HttpURLConnection conn = null;
		String charSet = "utf-8";
		try {
			if ( isCaching(methodMap, argMap) ) {
				File file = getCacheFile(methodMap, argMap);

				long fileLength = file.length(); 
				logger.fine("Length of File in cache:" + fileLength + ": " + file.getName());
				if ( fileLength == 0L ) {
					OpenStates.cacheFileFromAPI(methodMap, argMap, file);
				}
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charSet));
			} else {
				conn = OpenStates.getConnectionFromAPI(methodMap, argMap);
				charSet = getCharset(conn);
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charSet));
			}
			
        	return mapper.readValue( reader, responseType );
		} catch (JsonParseException e) {
			throw new OpenStatesException(e);
		} catch (JsonMappingException e) {
			throw new OpenStatesException(e);
		} catch (URISyntaxException e) {
			throw new OpenStatesException(e);
		} catch (IOException e) {
			throw new OpenStatesException(e);
		} finally {
			suspendCache = false;
			if ( conn != null ) conn.disconnect();
			if ( reader != null ) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new OpenStatesException(e);
				}
			}
		}
	}
	
	private boolean isCaching(MethodMap methodMap, ArgMap argMap) {
		if ( OpenStates.cache == null ) return false;
		if ( OpenStates.suspendCache == true ) return false;
		if ( OpenStates.checkCache == false ) return false;
		return true;
	}
	
	private static File getCacheFile(MethodMap methodMap, ArgMap argMap) {
		StringBuilder filename = new StringBuilder( OpenStates.cache );
		// There is always a method
		for (String key: methodMap )
		{
			filename.append( key );
			filename.append('.');
		}
		// trim off last char
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
		filename.append(".json" );

		// return a file object
		return new File(filename.toString());
	}
	
	private static void cacheFileFromAPI(MethodMap methodMap, ArgMap argMap, File file) throws URISyntaxException, IOException, OpenStatesException {
		BufferedReader breader = null;
		BufferedWriter bwriter = null;
		HttpURLConnection conn = null;
		try {
		    char[] buffer = new char[262144];
			conn = getConnectionFromAPI(methodMap, argMap);
			String charSet = getCharset(conn);
			breader = new BufferedReader(new InputStreamReader( conn.getInputStream(), charSet ) );
			bwriter = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(file), Charset.forName("utf-8")) );
			int read;
			while ( (read = breader.read(buffer)) != -1 ) {
				bwriter.write(buffer, 0, read);
			}
		} finally {
			if ( conn != null ) conn.disconnect();
			if ( bwriter != null ) bwriter.close();
			if ( breader != null ) breader.close();
		}
	}
	
	private static HttpURLConnection getConnectionFromAPI(MethodMap methodMap, ArgMap argMap) throws URISyntaxException, IOException, OpenStatesException {
	    HttpURLConnection con = null;
		// There is always a method
		StringBuilder method = new StringBuilder("/");
		for (String key: methodMap )
		{
			method.append(key);
			method.append('/');
		}
		// trim off last char
//		method.deleteCharAt(method.length()-1);

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
				terms.append( URLEncoder.encode(value, "utf-8" ) );
			}
		}
			
		// construct the URI ..
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
	    con.setRequestProperty("Accept", "text/json, application/json");
	    con.connect();
	    return con;
	}
	
	private static String getCharset(HttpURLConnection con) throws OpenStatesException, IOException {
	    // better check it first
	    if (con.getResponseCode() / 100 != 2) {
	    	String msg = con.getResponseMessage();
	    	con.disconnect();
	    	throw new OpenStatesException(msg);
	    }
	    String contentType = con.getHeaderField("Content-Type");
	    String charset = null;
	    for (String param : contentType.replace(" ", "").split(";")) {
	        if (param.startsWith("charset=")) {
	            charset = param.split("=", 2)[1];
	            break;
	        }
	    }
	    if ( charset == null ) {
	    	logger.fine("Defaulting to utf-8 charset");
	    	charset = "utf-8";
	    }
	    return charset;
	}
}