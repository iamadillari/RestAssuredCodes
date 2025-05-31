package PUTCallWithBDD;

import CreateUserWithPOJOAndLombok.UserPOJO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdateUserTest {

    // Create a UserPOJO object with predefined user details
    UserPOJO user = new UserPOJO("Ramesh", "Male", "active", getRandomEmailId());

    /**
     * Generates a random email ID for testing purposes.
     *
     * @return A unique email ID string.
     */
    public String getRandomEmailId() {
        return "Aditya" + System.currentTimeMillis() + "@gmail.com";
    }

    /**
     * Test to create, verify, update, and validate a user using REST API calls.
     *
     * Steps:
     * 1. Create a user using a POST request and verify the user with a GET request.
     * 2. Update the user details using a PUT request and verify the update with a GET request.
     * 3. Validate the updated user details using assertions.
     */
    @Test
    public void updateUserTest() {

        // Set the base URI for the API
        RestAssured.baseURI = "https://gorest.co.in/";

        // 1. POST call: Create a user using POJO to JSON serialization
        Response response = given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("public/v2/users");
        JsonPath jsonPath = response.jsonPath();
        int userId = jsonPath.get("id"); // Extract the user ID from the response
        System.out.println("User Id is: " + userId);
        System.out.println("<<<<<<<<<<<<<<<User After POST call>>>>>>>>>>>>>>>");
        response.prettyPrint();
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<User Created>>>>>>>>>>>>>>>>>>>>>>>>>>");

        // 2. GET call: Fetch the created user and validate the response
        Response getRes = given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .when()
                .get("public/v2/users/" + userId);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<User Fetched Successfully>>>>>>>>>>>>>>>>>>>>>");
        JsonPath getJsonPath = getRes.jsonPath();
        Assert.assertEquals(getRes.statusCode(), 200); // Validate status code
        Assert.assertEquals(getJsonPath.get("status"), "active"); // Validate user status
        Assert.assertNotNull(getJsonPath.get("id")); // Validate user ID is not null
        System.out.println("<<<<<<<<<<<<<<<<<<<<<GET call validation done>>>>>>>>>>>>>>>>>>>>>");

        // 3. PUT call: Update some fields' values for the user
        user.setStatus("inactive");
        user.setName("Suresh");
        Response putRes = given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put("public/v2/users/" + userId);
        System.out.println("<<<<<<<<<<<<<<<User After PUT call>>>>>>>>>>>>>>>");
        putRes.prettyPrint();
        JsonPath js = putRes.jsonPath();
        Assert.assertEquals(js.get("status"), "inactive"); // Validate updated status

        // 4. GET call: Validate the updated user details
        given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .when()
                .get("public/v2/users/" + userId)
                .then().log().all()
                .assertThat()
                .statusCode(200) // Validate status code
                .and()
                .body("id", equalTo(userId)) // Validate user ID
                .body("name", equalTo("Suresh")) // Validate updated name
                .body("status", equalTo("inactive")); // Validate updated status
    }
}