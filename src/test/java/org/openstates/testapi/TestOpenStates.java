package org.openstates.testapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.openstates.api.ArgMap;
import org.openstates.api.MethodMap;
import org.openstates.api.OpenStatesException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

/**
 * TestOpenStates. 
 * 
 * This class will load from a zip file in the (test) resources directory
 * and unmarshall the XML documents inside. It is used with 
 * {@link TestOpenStatesBase}
 *
 */
public class TestOpenStates {
    private char[] buffer;
    private TreeMap<String, ZipEntry> mapEntries;
    private ZipFile zipFile;
	private ObjectMapper mapper;
	SimpleDateFormat dateFormat;

	public TestOpenStates(ResourceBundle bundle) throws OpenStatesException {
		try {
			mapEntries = new TreeMap<String, ZipEntry>();

			zipFile = new ZipFile( TestOpenStates.class.getResource("/AllThingsTexas.zip").getFile() );

			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while ( entries.hasMoreElements() ) {
				ZipEntry entry = entries.nextElement();
				mapEntries.put(entry.getName(), entry);
			}
	
			mapper = new ObjectMapper();
			mapper.addHandler(new MyDeserializationProblemHandler() );
			dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			mapper.setDateFormat( dateFormat );
				
			// arbitrary large value
			buffer = new char[ 262144 ];
		} catch (IOException e) {
			throw new OpenStatesException(e);
		}
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
	 * Queries the API server for the information requested
	 * 
	 * @param method
	 *            The method to be called on the API Server
	 * @param args
	 *            An IdentityHashMap<String, String> of the arguments for the
	 *            method
	 * @return The XML document as a XOM Document.
	 * 
	 * @throws OpenStatesException 
	 * @throws OpenStatesErrorException 
	 */
	public <T> T query(MethodMap methodMap, ArgMap argMap, Class<T> responseType) throws OpenStatesException {

		try {
			Reader reader;
			StringBuilder JSONBuilder = new StringBuilder();
			String JSONStr = null;
			BufferedReader breader;
			StringBuilder filename = new StringBuilder();
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
			// save this for later
			filename.append(".json" );

			ZipEntry entry = mapEntries.get(filename.toString());
			
			breader = new BufferedReader(new InputStreamReader( zipFile.getInputStream(entry), Charset.forName("UTF-8") ) );
			
			int read;
				while ( (read = breader.read(buffer)) != -1 ) {
					JSONBuilder.append(buffer, 0, read);
				}
			breader.close();
			JSONStr = JSONBuilder.toString();
			reader = new StringReader(JSONStr);
			
        	return mapper.readValue(reader, responseType);
		} catch (IOException e) {
			throw new OpenStatesException(e);
		}
	}
	
	public void destroy() {
		try {
			zipFile.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
	}
}
