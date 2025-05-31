package ReqResSpecificationBuilder;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ReqSpecBuilderTest {

    /**
     * Creates a reusable RequestSpecification for user-related API requests.
     *
     * Request Specification:
     * - Base URI: "https://gorest.co.in"
     * - Content-Type: JSON
     * - Authorization header with a Bearer token
     *
     * @return A RequestSpecification object with the predefined settings.
     */
    public static RequestSpecification userReqSpec(){
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://gorest.co.in")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .build();
        return requestSpecification;
    }

    /**
     * Test to fetch all users.
     *
     * Steps:
     * 1. Uses the predefined RequestSpecification for the request.
     * 2. Sends a GET request to the "/public/v2/users" endpoint.
     * 3. Validates that the response status code is 200 (OK).
     */
    @Test
    public void getUsersTest(){
        given().log().all().spec(userReqSpec())
                .when()
                .get("/public/v2/users")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    /**
     * Test to fetch all active users with a query parameter.
     *
     * Steps:
     * 1. Uses the predefined RequestSpecification for the request.
     * 2. Adds a query parameter "status" with the value "active".
     * 3. Sends a GET request to the "/public/v2/users" endpoint.
     * 4. Validates that the response status code is 200 (OK).
     */
    @Test
    public void getUsersWithQueryParamTest(){
        given().log().all().spec(userReqSpec())
                .queryParam("status","active")
                .when()
                .get("/public/v2/users")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

}