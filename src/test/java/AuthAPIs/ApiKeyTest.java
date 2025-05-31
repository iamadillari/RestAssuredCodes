package AuthAPIs;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * ApiKeyTest is a test class that demonstrates the use of an API key
 * for making HTTP requests to the Generative Language API.
 *
 * The class includes a test method to send a POST request with a JSON payload
 * and query parameters to generate content using the specified model.
 */
public class ApiKeyTest {

    // API key used for authentication with the Generative Language API
    private static final String geminiKey = "AIzaSyDlwNcNuyR-HnUctFz8Bn2uS0ienqJz2zc";

    /**
     * Test to set up and use an API key for making a POST request to the Generative Language API.
     *
     * Steps:
     * 1. Sets the base URI for the API.
     * 2. Configures the request with the API key as a query parameter.
     * 3. Sends a POST request with a JSON body to generate content.
     * 4. Prints the response for validation.
     *
     * The `urlEncodingEnabled(false)` method is used to prevent URL encoding of the query parameters.
     */
    @Test
    public void generateTextUsingGeminiLLMWithApiKeyTest() {
        RestAssured.baseURI = "https://generativelanguage.googleapis.com";
        Response response = given().log().all()
                .contentType(ContentType.JSON) // Sets the content type to JSON
                .queryParam("key", geminiKey) // Adds the API key as a query parameter
                .urlEncodingEnabled(false) // Prevents URL encoding of the query parameters
                .body("{\n" +
                        "    \"contents\": [\n" +
                        "        {\n" +
                        "            \"parts\": [\n" +
                        "                {\n" +
                        "                    \"text\": \"Explain how Rest Assured works with Java for testing\"\n" +
                        "                }\n" +
                        "            ]\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}") // Sets the JSON payload for the request
                .when()
                .post("/v1beta/models/gemini-2.0-flash:generateContent"); // Sends a POST request to the specified endpoint
        response.prettyPrint(); // Prints the response for validation
    }
}