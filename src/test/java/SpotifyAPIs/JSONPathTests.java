package SpotifyAPIs;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JSONPathTests {

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
     * Test to fetch album details using JSONPath.
     *
     * Steps:
     * 1. Sets the base URI for the Spotify API.
     * 2. Configures the request with the OAuth 2.0 token in the Authorization header.
     * 3. Sends a GET request to the `/v1/albums/{id}` endpoint to retrieve album details.
     * 4. Parses the JSON response using JayWay JSONPath.
     * 5. Extracts and prints image URLs and their widths for images with a height of 300.
     *
     * JSONPath Explanation:
     * - `$`: Refers to the root of the JSON.
     * - `images.[?(@.height==300)]`: Filters the `images` array for objects where the `height` equals 300.
     * - `['url','width']`: Extracts the `url` and `width` fields from the filtered results.
     */
    @Test
    public void getAlbumDetailsUsingJsonPathTest() {
        RestAssured.baseURI = "https://api.spotify.com";
        System.out.println("<<<<<<<<<<<<<<<<<<<<Starting getAlbumDetailsUsingJsonPathTest>>>>>>>>>>>>>>>>>>>>");
        Response response = given().log().all()
                .header("Authorization", "Bearer " + accessToken) // Sets the Authorization header
                .when()
                .get("/v1/albums/4aawyAB9vmqN3uQ7FjRGTy"); // API endpoint to get album details
        ReadContext readContext = JsonPath.parse(response.asString());
        List<Map<String, Object>> urlList = readContext.read("$.images.[?(@.height==300)].['url','width']");
        for (Map<String, Object> e : urlList){
            System.out.println(e);
        }
        System.out.println("<<<<<<<<<<<<<<<<<<<<Ending getAlbumDetailsUsingJsonPathTest>>>>>>>>>>>>>>>>>>>>");
    }

}
