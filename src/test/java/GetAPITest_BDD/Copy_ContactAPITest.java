package GetAPITest_BDD;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Copy_ContactAPITest {

	private static final String BASE_URI = "https://thinking-tester-contact-list.herokuapp.com";
	private static final String VALID_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2ZkZGE2ODQwNzEwYzAwMTUyNjRlZmEiLCJpYXQiOjE3NDUwODAzMjN9.DBmos6vuVALNqdsFHd144BYK4W-mRrwIsCIoQyr9Pbs";
	private static final String INVALID_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5pXVCJ9."
			+ "Dos6vuVALNqdsFHd144BYK4W-mRrwIsCIoQyr9Pbs";

	@BeforeClass
	public void setup() {
		baseURI = BASE_URI;
	}

	/**
	 * Prepares a reusable request spec with Authorization header
	 *
	 * @param token JWT token for Authorization
	 * @return configured RequestSpecification
	 */
	private RequestSpecification getRequestWithAuth(String token) {
		return given().log().all().header("Authorization", token);
	}

	/**
	 * Validates successful retrieval of contacts using a valid token. Expects HTTP
	 * 200 with JSON content.
	 */
	@Test
	public void getContactsAPITest() {
		getRequestWithAuth(VALID_TOKEN).when().get("/contacts").then().log().all().assertThat().statusCode(200)
				.contentType(ContentType.JSON);
	}

	/**
	 * Validates 401 unauthorized error when using an invalid token.
	 */
	@Test
	public void getContactsAPIAuthErrorTest() {
		getRequestWithAuth(INVALID_TOKEN).when().get("/contacts").then().log().all().assertThat().statusCode(401)
				.contentType(ContentType.JSON);
	}
}
