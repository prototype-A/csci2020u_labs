package csci2020u.lab09;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;


public class JsonDownloader {


	/**
	 * Parses and downloads the data at url as a JsonObject
	 *
	 * @param url The url to the json data
	 */
	public static JsonObject download(String url) {
		return getJson(url);
	}

	/**
	 * Reads the entire contents of the reader into a string
	 *
	 * @param reader The reader to read the contents of
	 * @para throws IOException if an I/O error occurs while reading
	 */
	public static String readAll(Reader reader) throws IOException {

		StringBuilder builder = new StringBuilder();

		int ch;
		while ((ch = reader.read()) != -1){
			builder.append((char)ch);
		}

		return builder.toString();
    }

	/**
	 * Retrieves the Json from a specified url
	 *
	 * @param url The url string to obtain the Json from
	 * @throws IOException if an I/O error occurs 
	 * @throws JsonParseException if an error occurs while parsing the Json
	 */
	private static JsonObject getJson(String url) {

		InputStream inStream = null;
		JsonObject json = null;

		try {

			// Open an input stream to the url
			inStream = new URL(url).openStream();

			// Get json in string format
			String jsonString = readAll(new InputStreamReader(inStream));

			// Convert json string to JsonObject
			json = new JsonParser().parse(jsonString).getAsJsonObject();
		} catch (IOException e) {
			System.err.println("Could not retrieve Json data");
			e.printStackTrace();
		} catch (JsonParseException e) {
			System.err.println("Could not parse Json");
			e.printStackTrace();
		} finally {
			try {
				inStream.close();
			} catch (IOException e) {
				System.err.println("Could not close connection");
				e.printStackTrace();
			}
		}

		return json;
	}

}
