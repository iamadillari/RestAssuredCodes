package SpotifyAPIs;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * Oauth2WithSpotifyAPI is a test class that demonstrates the use of OAuth 2.0 authentication
 * for interacting with the Spotify API. It includes methods to generate an access token
 * and retrieve album details using that token.
 *
 * The API requests are performed using the RestAssured library, and the class includes
 * tests showcasing two different ways of utilizing the generated OAuth 2.0 token:
 * 1. Using the token directly in the Authorization header.
 * 2. Using the OAuth2 authentication method provided by RestAssured.
 *
 * Test methods include assertions and response printing for validation.
 */
public class Oauth2WithSpotifyAPI {

    // Variable to store the generated OAuth 2.0 access token
    private String accessToken;

    /**
     * Generates an OAuth 2.0 access token using client credentials.
     * This method is executed before each test method.
     *
     * Steps:
     * 1. Sets the base URI for the Spotify Accounts API.
     * 2. Sends a POST request with client credentials to retrieve the access token.
     * 3. Extracts and stores the access token from the response.
     */
    @BeforeMethod
    public void generateAccessToken() {
        RestAssured.baseURI = "https://accounts.spotify.com";
        System.out.println("<<<<<<<<<<<<<<<<<<<<Starting Token Generation>>>>>>>>>>>>>>>>>>>>");
        accessToken = given().log().all()
                .contentType(ContentType.URLENC) // Sets content type to URL-encoded form
                .formParam("grant_type", "client_credentials") // Specifies the grant type
                .formParam("client_id", "3c8bda10d1c04a6aac28e34de151b5b5") // Client ID
                .formParam("client_secret", "39304b7619a64b4f875b04903825f174") // Client secret
                .when()
                .post("/api/token") // Endpoint to generate the token
                .then()
                .extract().path("access_token"); // Extracts the access token
        System.out.println("<<<<<<<<<<<<<<<<<<<<Starting Token Generation>>>>>>>>>>>>>>>>>>>>");
    }

    /**
     * Test to retrieve album details using the OAuth 2.0 token in the Authorization header.
     *
     * Steps:
     * 1. Appends the token with "Bearer" and sets it in the Authorization header.
     * 2. Sends a GET request to retrieve album details.
     * 3. Prints the response for validation.
     */
    @Test
    public void getAlbumTest() {
        RestAssured.baseURI = "https://api.spotify.com";
        System.out.println("<<<<<<<<<<<<<<<<<<<<Starting Get Album Test>>>>>>>>>>>>>>>>>>>>");
        Response response = given().log().all()
                .header("Authorization", "Bearer " + accessToken) // Sets the Authorization header
                .when()
                .get("/v1/albums/4aawyAB9vmqN3uQ7FjRGTy"); // API endpoint to get album details
        response.prettyPrint(); // Prints the response
        System.out.println("<<<<<<<<<<<<<<<<<<<<Ending Get Album Test>>>>>>>>>>>>>>>>>>>>");
    }

    /**
     * Test to retrieve album details using the OAuth2 authentication method provided by RestAssured.
     *
     * Steps:
     * 1. Uses the `oauth2` method to set the token directly.
     * 2. Sends a GET request to retrieve album details.
     * 3. Prints the response for validation.
     */
    @Test
    public void getAlbumWithOAuth2Test() {
        RestAssured.baseURI = "https://api.spotify.com";
        System.out.println("<<<<<<<<<<<<<<<<<<<<Starting Get Album Test>>>>>>>>>>>>>>>>>>>>");
        Response response = given().log().all()
                .auth().oauth2(accessToken) // Uses RestAssured's OAuth2 method
                .when()
                .get("/v1/albums/4aawyAB9vmqN3uQ7FjRGTy"); // API endpoint to get album details
        response.prettyPrint(); // Prints the response
        System.out.println("<<<<<<<<<<<<<<<<<<<<Ending Get Album Test>>>>>>>>>>>>>>>>>>>>");
    }

}