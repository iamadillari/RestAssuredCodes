package RequestResponseSpecification;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReqSpecificationTest {

    RequestSpecification requestSpecification;

    /**
     * Generates a random email ID for the user.
     *
     * @return A unique email ID in the format "testapi<timestamp>@gmail.com".
     */
    public String getRandomEmailId() {
        return "testapi" + System.currentTimeMillis() + "@gmail.com";
    }

    /**
     * Sets up the request specification for the tests.
     *
     * Request Specification:
     * - Logs all request details.
     * - Sets the base URI to "https://gorest.co.in".
     * - Adds an Authorization header with a Bearer token.
     * - Sets the Content-Type to JSON.
     */
    @BeforeMethod
    public void reqSpecificationSetUp() {
        requestSpecification = RestAssured.given().log().all()
                .baseUri("https://gorest.co.in")
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .contentType(ContentType.JSON);
        System.out.println("-------<<Request Specification Setup Done>>-------");
    }

    /**
     * Test to create a new user.
     *
     * Steps:
     * 1. Generates a random email ID for the new user.
     * 2. Sends a POST request to the "/public/v2/users" endpoint with the user details in the request body:
     *    - Name: "Aman"
     *    - Gender: "male"
     *    - Email: Randomly generated email ID
     *    - Status: "active"
     * 3. Validates that the response status code is 201 (Created).
     */
    @Test
    public void createANewUserTest() {
        System.out.println("<<<<<<<<<<<Starting createANewUserTest>>>>>>>>>>>");
        // Generate a random email ID for the user.
        String emailId = getRandomEmailId();
        requestSpecification.when()
                .body("{\n" + "\"name\": \"Aman\",\n" + "\"gender\": \"male\",\n"
                        + "\"email\": \"" + emailId + "\",\n" + "\"status\": \"active\"\n" + "}")
                .when().post("/public/v2/users").then().log().all().assertThat().statusCode(201);
        System.out.println("<<<<<<<<<<<Ending createANewUserTest>>>>>>>>>>>");
    }

    /**
     * Test to fetch all users.
     *
     * Steps:
     * 1. Sends a GET request to the "/public/v2/users" endpoint.
     * 2. Validates that the response status code is 200 (OK).
     */
    @Test
    public void getAllUserTest() {
        System.out.println("<<<<<<<<<<<Starting getAllUserTest>>>>>>>>>>>");
        requestSpecification
                .when()
                .get("/public/v2/users")
                .then().log().all()
                .statusCode(200);
        System.out.println("<<<<<<<<<<<Ending getAllUserTest>>>>>>>>>>>");
    }

}