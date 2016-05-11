package sachin.codejam;

import java.util.Map;

/**
 *
 * @author Sachin
 */
public class Config {
	public static final String BROWSER_TYPE;
	public static final int TIMEOUT;
	public static final String URL;
	public static final String DOMASTIC_ORIGIN;
	public static final String DOMASTIC_DESTINATION ;
	public static final int ADULTS;
	public static final int CHILDREN;
	public static final int INFANTS;
//	public static String step;
	static {
		Map<String, String> map=new ExcelManager().readConfigData();
		BROWSER_TYPE=map.get("Browser");
		TIMEOUT=Integer.parseInt(map.get("Timeout"));
		URL=map.get("url");
		DOMASTIC_ORIGIN=map.get("Domestic Origin");
		DOMASTIC_DESTINATION=map.get("Domestic Destination");
		ADULTS=Integer.parseInt(map.get("Adults"));
		CHILDREN=Integer.parseInt(map.get("Children"));
		INFANTS=Integer.parseInt(map.get("Infants"));
	}
}
