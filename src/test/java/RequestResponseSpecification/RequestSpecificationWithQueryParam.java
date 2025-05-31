package RequestResponseSpecification;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RequestSpecificationWithQueryParam {

    RequestSpecification requestSpecification;

    /**
     * Sets up the request specification for the tests with a query parameter.
     *
     * Request Specification:
     * - Logs all request details.
     * - Sets the base URI to "https://gorest.co.in".
     * - Adds an Authorization header with a Bearer token.
     * - Sets the Content-Type to JSON.
     * - Adds a query parameter "status" with the value "active".
     */
    @BeforeMethod
    public void reqSpecificationForGoRestWithQueryParam() {
        requestSpecification = RestAssured.given().log().all()
                .baseUri("https://gorest.co.in")
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .contentType(ContentType.JSON)
                .queryParam("status", "active");
        System.out.println("-------<<Request Specification Setup Done>>-------");
    }

    /**
     * Test to fetch all active users.
     *
     * Steps:
     * 1. Sends a GET request to the "/public/v2/users" endpoint with the query parameter "status=active".
     * 2. Validates that the response status code is 200 (OK).
     */
    @Test
    public void getAllActiveUsers() {
        requestSpecification.when()
                .get("/public/v2/users")
                .then().log().all()
                .statusCode(200);
    }
}