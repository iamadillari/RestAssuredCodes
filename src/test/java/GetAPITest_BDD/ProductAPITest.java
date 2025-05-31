package GetAPITest_BDD;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ProductAPITest {

	/**
	 * This test method validates the 'Get Products' API functionality of the fakestore API.
	 * It fetches and prints various details about the products and reviews if the response data is consistent.
	 *
	 * Key Operations Performed:
	 * - Sends a GET request to the '/products' endpoint of the fakestore API.
	 * - Logs the request and response details.
	 * - Extracts and validates the JSON response body, fetching the following:
	 *    - List of product IDs.
	 *    - List of product categories.
	 *    - List of product ratings.
	 *    - List of rating counts.
	 * - Iterates through each product to print its individual details: ID, category, rating, and count.
	 */
	@Test
	public void getProductsTest() {

		RestAssured.baseURI = "https://fakestoreapi.com";
		System.out.println("<<<<<<<<----------------------------Starting Test------------------------>>>>>>>>>");
		Response response = given().when().log().all().get("/products");
		System.out.println("Status code:- " + response.statusCode());
		System.out.println("Status Line:- " + response.statusLine());
		response.prettyPrint();

		JsonPath js = response.jsonPath();

		/**
		 * to fetch the list of 'id' from the response for each products.
		 */
		List<Integer> listOfAllIds = js.getList("id");
		System.out.println("List of All Ids of all Products:- " + listOfAllIds);

		/**
		 * to fetch the list of 'category' of all the products from the response
		 */
		List<String> listOfCategory = js.getList("category");
		System.out.println("List of all Categories of all products:- " + listOfCategory);

		/**
		 * to fetch the list of 'rate' from the 'rating' of each product for all the
		 * products
		 */
		List<Object> listOfRate = js.getList("rating.rate");
		System.out.println("List of rate of all products:- " + listOfRate);

		/**
		 * to fetch the list of 'count' from the 'rating' of each product for all the
		 * products
		 */
		List<Integer> listOfCount = js.getList("rating.count");
		System.out.println("List of count of all products:- " + listOfCount);

		/**
		 * To fetch Individual value of each product,here we will check id, category,
		 * rate and count of each product based on their ID
		 */
		for (int i = 0; i < listOfAllIds.size(); i++) {
			int id = listOfAllIds.get(i);
			String category = listOfCategory.get(i);
			Object rate = listOfRate.get(i);
			int count = listOfCount.get(i);
			System.out.println("ID is:" + id + " Category is: " + category + " Rate of product is: " + rate
					+ " Count of Product is: " + count);
		}
		System.out.println("<<<<<<<<----------------------------Ending Test------------------------>>>>>>>>>");

	}

	/**
	 * here we will use this test case to check the count of products using below
	 * API and, here we'll be using '$' sign, and this sign is use to reach on
	 * root(always root) of the JSON response.
	 */
	@Test
	public void getProductCountTest() {
		RestAssured.baseURI = "https://fakestoreapi.com";
		System.out.println("<<<<<<<<----------------------------Starting Test------------------------>>>>>>>>>");
		given().when().log().all().get("/products").then().log().all().assertThat().statusCode(200).and()
				.body("$.size()", equalTo(20));
		System.out.println("<<<<<<<<----------------------------Ending Test------------------------>>>>>>>>>");
	}

}