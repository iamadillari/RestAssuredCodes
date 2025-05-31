package POSTAPITest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


/**
 * The CreateUserAPITest_Advance class provides a refactored implementation of API testing
 * to validate the Create User functionality.
 * It uses the RestAssured library to perform HTTP requests and verify API responses.
 * This class improves code structure by utilizing reusable components and reducing code duplication.
 */
public class CreateUserAPITest_Advance {
    private static final String BASE_URI = "https://gorest.co.in";
    private static final String AUTH_TOKEN = "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72";
    private static final String USERS_ENDPOINT = "/public/v2/users";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    /**
     * Tests the creation of a new user through the API.
     * Verifies that the API returns a 201 (Created) status code.
     */
    @Test
    public void createNewUser() {
        String requestBody = createUserRequestBody(
                "Surajsingh",
                "female",
                "surajsingh12@gmail.com",
                "active"
        );

        given()
                .log().all()
                .header("Authorization", AUTH_TOKEN)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(USERS_ENDPOINT)
                .then()
                .log().all()
                .assertThat()
                .statusCode(201);
    }

    /**
     * Creates a JSON request body for user creation.
     *
     * @param name   User's name
     * @param gender User's gender
     * @param email  User's email
     * @param status User's status
     * @return JSON string representing the user data
     */
    private String createUserRequestBody(String name, String gender, String email, String status) {
        return String.format("""
                {
                    "name": "%s",
                    "gender": "%s",
                    "email": "%s",
                    "status": "%s"
                }""", name, gender, email, status);
    }
}