package utils;

public class Constants {

	public static final String PROPERTIES_NAME = "blaze.properties";
	public static final int MAX_RETRY_COUNT = 2;
	
	private static PropertyReader props = new PropertyReader();
	
	public static String getContextUrl() {
		return props.getString("url");
	}
}
