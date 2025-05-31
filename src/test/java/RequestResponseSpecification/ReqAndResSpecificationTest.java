package RequestResponseSpecification;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqAndResSpecificationTest {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    /**
     * Generates a random email ID for the user.
     *
     * @return A unique email ID in the format "testapi<timestamp>@gmail.com".
     */
    public String getRandomEmailId() {
        return "testapi" + System.currentTimeMillis() + "@gmail.com";
    }

    /**
     * Sets up the request and response specifications for the tests.
     *
     * Request Specification:
     * - Logs all request details.
     * - Sets the base URI to "https://gorest.co.in".
     * - Adds an Authorization header with a Bearer token.
     * - Sets the Content-Type to JSON.
     *
     * Response Specification:
     * - Logs all response details.
     * - Expects the status code to be 200 or 201.
     * - Expects the Content-Type to be JSON.
     * - Expects the "server" header to be "cloudflare".
     * - Expects the response time to be less than 5000 milliseconds.
     */
    @BeforeTest
    public void setup() {
        requestSpecification = given().log().all()
                .baseUri("https://gorest.co.in")
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .contentType(ContentType.JSON);

        responseSpecification = expect().log().all()
                .statusCode(anyOf(equalTo(200), equalTo(201)))
                .contentType(ContentType.JSON)
                .header("server", "cloudflare")
                .time(lessThan(5000L));
    }

    /**
     * Test to fetch all users.
     *
     * Steps:
     * 1. Sends a GET request to the "/public/v2/users" endpoint.
     * 2. Validates the response using the predefined response specification.
     */
    @Test
    public void getUsersTest() {
        requestSpecification.when().get("/public/v2/users")
                .then().log().all().spec(responseSpecification);
    }

    /**
     * Test to fetch users with a query parameter.
     *
     * Steps:
     * 1. Adds a query parameter "status" with the value "active".
     * 2. Sends a GET request to the "/public/v2/users" endpoint.
     * 3. Validates the response using the predefined response specification.
     */
    @Test
    public void getUsersWithQueryParamTest() {
        requestSpecification.queryParam("status", "active")
                .when().get("/public/v2/users")
                .then().log().all().spec(responseSpecification);
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
     * 3. Validates the response using the predefined response specification.
     */
    @Test
    public void createAUserTest() {
        String randomEmailId = getRandomEmailId();
        requestSpecification.when().log().all()
                .body("{\n" + "\"name\": \"Aman\",\n" + "\"gender\": \"male\",\n"
                        + "\"email\": \"" + randomEmailId + "\",\n" + "\"status\": \"active\"\n" + "}")
                .post("/public/v2/users")
                .then().log().all().spec(responseSpecification);
    }

}