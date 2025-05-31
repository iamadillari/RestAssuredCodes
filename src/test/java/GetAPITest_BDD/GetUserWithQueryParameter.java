package GetAPITest_BDD;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * The GetUserWithQueryParameter class is designed to handle test cases for fetching user data
 * from the GoRest API based on query parameters. This includes both direct query parameter passing
 * and utilizing a HashMap to pass query parameters.
 */
public class GetUserWithQueryParameter {

	/**
	 * Get a single user using query parameters without Hashmap
	 */
	@Test
	public void getSingleUserTest() {

		RestAssured.baseURI = "https://gorest.co.in";
		System.out.println("<<<<<<<<----------------------------Starting Test---------------------->>>>>>>>>");
		given().log().all()
				.header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
				.queryParam("name", "naveen").queryParam("status", "inactive").when().get("public/v2/users").then()
				.log().all().assertThat().statusCode(200).and().contentType(ContentType.JSON);
		System.out.println("<<<<<<<<----------------------------Ending Test------------------------>>>>>>>>>");
	}

	/**
	 * Get users using query parameter as Hashmap
	 */
	@Test
	public void getUsersTest() {

		HashMap<String, String> userQueryMap = new HashMap<String, String>();
		userQueryMap.put("name", "naveen");
		userQueryMap.put("status", "inactive");
		userQueryMap.put("gender", "female");

		RestAssured.baseURI = "https://gorest.co.in";
		System.out.println("<<<<<<<<----------------------------Starting Test---------------------->>>>>>>>>");
		given().log().all()
				.header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
				.queryParams(userQueryMap).when().get("public/v2/users").then().log().all().assertThat().statusCode(200)
				.and().contentType(ContentType.JSON);
		System.out.println("<<<<<<<<----------------------------Ending Test------------------------>>>>>>>>>");
	}

}
