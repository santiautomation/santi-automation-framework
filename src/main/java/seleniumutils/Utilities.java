package seleniumutils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.netty.handler.codec.http.HttpMethod;

public class Utilities {

	/**
	 * Given an URL (In String representation) returns true if it's accessible (response codes 200-300), false otherwise.
	 * 
	 * @param url
	 * @return True is the link is valid and returns a 200-300 response code, false otherwise.
	 * @throws MalformedURLException
	 */
	public static boolean getUrlResponse(String url) throws MalformedURLException {
		URL myURL = new URL(url);

		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) myURL.openConnection();
			conn.setRequestMethod(HttpMethod.HEAD.name());

			return conn.getResponseCode() < 400;

		} catch (IOException e) {
			return false;
		} finally {
			if (null != conn) {
				conn.disconnect();
			}
		}
	}

}
