package AuthAPIs;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * OAuth2ApiTest is a Test class that demonstrates the use of OAuth 2.0 authentication
 * for making HTTP requests to an API. It includes methods to generate an access token
 * and retrieve flight details using that token.
 *
 * The API requests are performed using the RestAssured library, and the class includes
 * tests showcasing two different ways of utilizing the generated OAuth 2.0 token:
 * 1. Using the token directly in the Authorization header.
 * 2. Using the OAuth2 authentication method provided by RestAssured.
 *
 * Test methods include assertions to validate the response status codes.
 */
public class OAuth2ApiTest {

    // Variable to store the generated OAuth 2.0 access token
    private String accessToken;

    /**
     * Generates an OAuth 2.0 access token using client credentials.
     * This method is executed before each test method.
     *
     * Steps:
     * 1. Sets the base URI for the API.
     * 2. Sends a POST request with client credentials to retrieve the access token.
     * 3. Extracts and stores the access token from the response.
     */
    @BeforeMethod
    public void getAccessToken() {
        RestAssured.baseURI = "https://test.api.amadeus.com";
        System.out.println("<<<<<<<<<<Starting Access Token Generation Test>>>>>>>>>>");
        Response response = given().log().all()
                .contentType(ContentType.URLENC) // Sets content type to URL-encoded form
                .formParam("grant_type", "client_credentials") // Specifies the grant type
                .formParam("client_id", "1MGrMEBehFYbqJ1w9tVXVeRR7GJmj28A") // Client ID
                .formParam("client_secret", "wiD438rBOkIvktKT") // Client secret
                .when()
                .post("/v1/security/oauth2/token"); // Endpoint to generate the token
        accessToken = response.jsonPath().get("access_token"); // Extracts the access token
        System.out.println("<<<<<<<<<<Ending Access Token Generation Test>>>>>>>>>>");
    }

    /**
     * Test to retrieve flight details using the OAuth 2.0 token in the Authorization header.
     *
     * Steps:
     * 1. Appends the token with "Bearer" and sets it in the Authorization header.
     * 2. Sends a GET request to retrieve flight details.
     * 3. Validates that the response status code is 200.
     */
    @Test
    public void getFlightDetailsTest(){
        System.out.println("<<<<<<<<<<Starting Test>>>>>>>>>>");
        given().log().all()
                .header("Authorization","Bearer "+accessToken) // Sets the Authorization header
                .get("/v1/shopping/flight-destinations?origin=PAR&maxPrice=200") // API endpoint
                .then().log().all()
                .assertThat()
                .statusCode(200); // Asserts that the response status code is 200
        System.out.println("<<<<<<<<<<Starting Test>>>>>>>>>>");
    }

    /**
     * Test to retrieve flight details using the OAuth2 authentication method provided by RestAssured.
     *
     * Steps:
     * 1. Uses the `oauth2` method to set the token directly.
     * 2. Sends a GET request to retrieve flight details.
     * 3. Validates that the response status code is 200.
     */
    @Test
    public void getFlightDetailsUsingOAuth2Test(){
        System.out.println("<<<<<<<<<<Starting Oauth 2.0 Test>>>>>>>>>>");
        given().log().all()
                .auth().oauth2(accessToken) // Uses RestAssured's OAuth2 method
                .get("/v1/shopping/flight-destinations?origin=PAR&maxPrice=200") // API endpoint
                .then().log().all()
                .assertThat()
                .statusCode(200); // Asserts that the response status code is 200
        System.out.println("<<<<<<<<<<Ending Oauth 2.0 Test>>>>>>>>>>");
    }

}