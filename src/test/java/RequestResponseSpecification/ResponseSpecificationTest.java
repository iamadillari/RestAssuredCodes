package RequestResponseSpecification;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ResponseSpecificationTest {

    /**
     * Test to validate the response specification for fetching active users.
     *
     * Steps:
     * 1. Creates a ResponseSpecification with the following expectations:
     *    - Status code: 200 (OK)
     *    - Content-Type: JSON
     *    - Header "server": "cloudflare"
     * 2. Sends a GET request to the "/public/v2/users" endpoint with the query parameter "status=active".
     * 3. Validates the response using the predefined ResponseSpecification.
     */
    @Test
    public void responseSpecificationTest(){
        // Define the response specification with expected status code, content type, and header.
        ResponseSpecification responseSpecification =
                RestAssured.expect().statusCode(200)
                        .contentType(ContentType.JSON).header("server","cloudflare");

        // Send a GET request with the specified base URI, headers, and query parameter.
        given()
                .baseUri("https://gorest.co.in")
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .contentType(ContentType.JSON)
                .queryParam("status","active")
                .get("/public/v2/users")
                .then()
                .spec(responseSpecification); // Validate the response using the response specification.
    }

}