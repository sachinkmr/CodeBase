/**
 *
 */
package sachin.codejam;

/**
 * @author Sachin
 *
 */
public class EntryPoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SearchFlight search=new SearchFlight();
		search.setUp();
		search.flightDetails();
		search.validationCheck1();
		search.validationCheck2();
		search.validationCheck3();
		search.validationCheck4();
		search.tearDown();
	}

}
