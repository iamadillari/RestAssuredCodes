package GetAPITest_BDD;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * This class, GoRestApiTest, contains test cases to interact with the GoRest API.
 * It validates the functionalities of fetching single and multiple user details
 * from the
 * GoRest API endpoints using RestAssured.
 */
public class GoRestApiTest {

	/**
	 * Test to Fetch a Single User Details from the goRest API
	 */
	@Test
	public void getASingleUserTest() {

		RestAssured.baseURI = "https://gorest.co.in";
		Response response = given().log().all()
				.header("Authentication", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
				.when().get("public/v2/users/7840243");

		System.out.println("Status Code: " + response.statusCode());
		System.out.println("Status Line: " + response.statusLine());
		Assert.assertEquals(response.statusCode(), 200, "validating the status code");
		Assert.assertTrue(response.statusLine().contains("200 OK"), "validating the status line");

		response.prettyPrint();

		// Fetch the JSON response Body
		JsonPath js = response.jsonPath();

		int userID = js.getInt("id");
		System.out.println(userID);
		Assert.assertEquals(userID, 7840243, "validating the userID");

		String userName = js.getString("name");
		System.out.println(userName);
		Assert.assertEquals(userName, "Gauranga Nambeesan", "validating the userName");

	}

	/**
	 * Test to Fetch multiple Users' Details from the goRest API
	 */
	@Test
	public void getAllUsersTest() {

		RestAssured.baseURI = "https://gorest.co.in";
		Response response = given().log().all()
				.header("Authentication", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
				.when().get("public/v2/users");

		System.out.println("Status Code: " + response.statusCode());
		System.out.println("Status Line: " + response.statusLine());
		Assert.assertEquals(response.statusCode(), 200, "validating the status code");
		Assert.assertTrue(response.statusLine().contains("200 OK"), "validating the status line");

		response.prettyPrint();

		// Fetch the JSON response Body
		JsonPath js = response.jsonPath();

		List<Integer> listOfIds = js.getList("id");
		System.out.println("List of Ids of all users :" + listOfIds);
		List<String> listOfNames = js.getList("name");
		System.out.print("List of Names of all users :" + listOfNames);

	}

}
