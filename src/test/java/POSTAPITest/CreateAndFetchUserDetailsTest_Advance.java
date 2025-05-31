package POSTAPITest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The CreateAndFetchUserDetailsTest_Advance class is designed to demonstrate
 * an end-to-end workflow for creating and verifying user details in an API
 * using RestAssured. This test suite employs dynamic data generation, constructs
 * JSON payloads dynamically and from files, and validates API responses.
 *
 * The primary functionality showcased includes:
 *
 * 1. Generation of dynamic email addresses for unique user creation.
 * 2. Construction of JSON payloads for API requests using string formatting
 *    and file-based templates with runtime data replacement.
 * 3. Sending HTTP POST requests to create new users.
 * 4. Sending HTTP GET requests to fetch and verify user details.
 * 5. Assertions to validate API responses, ensuring the expected behavior.
 *
 * Utility methods are defined to assist in generating test data, building
 * request payloads, and encapsulating reusable logic for API operations.
 *
 * The test cases validate the following:
 * - User creation via JSON string payloads.
 * - User creation via file-based JSON payloads with dynamic data replacement.
 * - Verification of user details post-creation using unique user IDs.
 * - Multiple approaches for handling and reusing request specifications and data.
 */
public class CreateAndFetchUserDetailsTest_Advance {
    private static final String BASE_URI = "https://gorest.co.in";
    private static final String AUTH_TOKEN = "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72";
    private static final String USER_JSON_PATH = "./src/test/resources/jsons/user.json";
    private RequestSpecification baseRequest;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URI;
        baseRequest = given()
                .header("Authorization", AUTH_TOKEN)
                .contentType(ContentType.JSON)
                .log().all();
    }

    private String getRandomEmailId() {
        return "testapi" + System.currentTimeMillis() + "@gmail.com";
    }

    private String createUserJsonString(String name, String gender, String email, String status) {
        return String.format("""
                {
                    "name": "%s",
                    "gender": "%s",
                    "email": "%s",
                    "status": "%s"
                }""", name, gender, email, status);
    }

    private Response createUser(String requestBody) {
        return baseRequest
                .body(requestBody)
                .when()
                .post("/public/v2/users");
    }

    private Response getUserDetails(int userId) {
        return baseRequest
                .when()
                .get("/public/v2/users/" + userId);
    }

    private String getJsonFromFile(String email) throws IOException {
        String rawJson = new String(Files.readAllBytes(Paths.get(USER_JSON_PATH)));
        return rawJson.replace("{{email}}", email);
    }

    /**
     * End-to-end test demonstrating different approaches to create and verify users
     */
    @Test
    public void userManagementEndToEndTest() throws IOException {
        System.out.println("=== Starting End-to-End User Management Test ===");

        // 1. Create user with JSON string
        String emailId1 = getRandomEmailId();
        String jsonBody = createUserJsonString("Aman", "male", emailId1, "active");
        
        System.out.println("1. Creating user with JSON string");
        Response response1 = createUser(jsonBody);
        response1.then()
                .assertThat()
                .statusCode(201);
        int userId1 = response1.path("id");
        System.out.println("Created User ID: " + userId1);

        // 2. Verify created user
        System.out.println("\n2. Verifying created user details");
        getUserDetails(userId1)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(userId1))
                .body("email", equalTo(emailId1));

        // 3. Create another user with JSON file
        String emailId2 = getRandomEmailId();
        String fileJson = getJsonFromFile(emailId2);
        
        System.out.println("\n3. Creating user with JSON file");
        Response response2 = createUser(fileJson);
        response2.then()
                .assertThat()
                .statusCode(201);
        int userId2 = response2.path("id");
        System.out.println("Created User ID: " + userId2);

        // 4. Verify second user
        System.out.println("\n4. Verifying second user details");
        getUserDetails(userId2)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(userId2))
                .body("email", equalTo(emailId2));

        System.out.println("=== End-to-End User Management Test Completed ===");
    }
}