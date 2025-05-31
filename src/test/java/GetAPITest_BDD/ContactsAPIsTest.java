package GetAPITest_BDD;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * This class contains test cases to validate the Contacts API functionality.
 * It uses RestAssured with the BDD approach (Given-When-Then) for writing tests
 * and follows the AAA (Arrange, Act, Assert) testing pattern.
 */
public class ContactsAPIsTest {

	/**
	 * we are using @BeforeMethod Annotation for all codes which we need to execute
	 * before each test
	 */
	@BeforeMethod
	public void setup() {
		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
	}

	/**
	 * Using here BDD format using GIVEN WHEN AND THEN for writing the test cases
	 * and also using AAA (Arrange, Act, Assertions) will be used here.
	 */
	@Test
	public void getContactsAPITest() {

		given().log().all().header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2ZkZGE2ODQwNzEwYzAwMTUyNjRlZmEiLCJpYXQiOjE3NDUwODAzMjN9.DBmos6vuVALNqdsFHd144BYK4W-mRrwIsCIoQyr9Pbs")
				.when().get("/contacts").then().log().all().assertThat().statusCode(200).and()
				.contentType(ContentType.JSON);
	}

	@Test
	public void getContactsAPIAuthErrorTest() {

		given().log().all().header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5pXVCJ9.eyJfaWQiOiI2N2ZkZGEzEwYzAwMTUyNjRlZmEiLCYXQiOjE3NDUwODAzMjN9.Dos6vuVALNqdsFHd144BYK4W-mRrwIsCIoQyr9Pbs")
				.when().get("/contacts").then().log().all().assertThat().statusCode(401).and()
				.contentType(ContentType.JSON);
	}

	@Test
	public void getContactsAPIInvalidTokenTest() {

		String errorMessage = given().log().all().header("Authorization",
				"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IXVCJ9.eyJfaWQiOiI2N2ZkZGE2ODQwNzEwYzAwMTUyNjRlZmEiLCJpYXQiOjE3NDUwODAzMjN9.DBmos6vuVALNqdsFHd144BYK4W-mRrwIsCIoQyr9Pbs")
				.when().get("/contacts").then().log().all().assertThat().statusCode(401).extract().path("error");
		System.out.println(errorMessage);
		Assert.assertEquals(errorMessage, "Please authenticate.");

	}

}