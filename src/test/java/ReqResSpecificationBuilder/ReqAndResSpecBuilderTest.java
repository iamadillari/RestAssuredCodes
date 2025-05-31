package ReqResSpecificationBuilder;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

/**
 * This class demonstrates the use of RequestSpecBuilder and ResponseSpecBuilder
 * to define reusable request and response specifications for API testing.
 */
public class ReqAndResSpecBuilderTest {

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
     * Creates a reusable ResponseSpecification for validating API responses.
     *
     * Response Specification:
     * - Expects Content-Type to be JSON
     * - Expects Status Code to be 200
     * - Expects the "server" header to be "cloudflare"
     *
     * @return A ResponseSpecification object with the predefined expectations.
     */
    public static ResponseSpecification responseSpec(){
        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectHeader("server", "cloudflare")
                .build();
        return responseSpecification;
    }

    /**
     * Test to fetch all users.
     *
     * Steps:
     * 1. Uses the predefined RequestSpecification for the request.
     * 2. Sends a GET request to the "/public/v2/users" endpoint.
     * 3. Validates the response using the predefined ResponseSpecification.
     */
    @Test
    public void getUsersTest(){
        given().log().all().spec(userReqSpec())
                .when().get("/public/v2/users")
                .then().log().all().assertThat().spec(responseSpec());
    }

}