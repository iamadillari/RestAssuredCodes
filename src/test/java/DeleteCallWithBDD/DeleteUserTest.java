package DeleteCallWithBDD;

import CreateUserWithPOJOAndLombok.UserPOJO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteUserTest {

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
     * Test to create, verify, update, and delete a user using REST API calls.
     *
     * Steps:
     * 1. Create a user using a POST request and verify the user with a GET request.
     * 2. Update the user details using a PUT request and verify the update with a GET request.
     * 3. Delete the user using a DELETE request and validate the deletion with a GET request.
     */
    @Test
    public void deleteUserTest() {

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

        // 3. DELETE call: Delete the created user
        Response deleteRes = given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .when()
                .delete("public/v2/users/" + userId);
        System.out.println("<<<<<<<<<<<<<<<User After DELETE call>>>>>>>>>>>>>>>");
        deleteRes.prettyPrint();
        Assert.assertEquals(deleteRes.statusCode(), 204); // Validate status code for deletion

        // 4. GET call: Validate that the user has been deleted
        given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .when()
                .get("public/v2/users/" + userId)
                .then().log().all()
                .assertThat()
                .statusCode(404) // Validate status code for resource not found
                .and()
                .body("message", equalTo("Resource not found")); // Validate error message
    }

}