package POSTAPITest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;


/**
 * The CreateAndFetchUserDetailsTest class contains a test case for creating a user and
 * fetching user details from the API. This test case validates the end-to-end
 * workflow of user creation and retrieval using the "/public/v2/users" API endpoint.
 * The test dynamically generates a random email ID, replaces the email placeholder
 * in a JSON file, and sends the updated JSON data via HTTP POST request.
 */
public class CreateAndFetchUserDetailsTest {

    //This method will generate a random email id for the user.
    public String getRandomEmailId() {
        return "testapi" + System.currentTimeMillis() + "@hotmail.com";
    }

    /**
     * Here in this below test case we are creating a User in below API using JSON file with String Replacement
     */
    @Test
    public void createUserAndFetchUserDetailsTest() throws IOException {

        RestAssured.baseURI = "https://gorest.co.in";

        //generate a random email id for the user.
        String emailId = getRandomEmailId();

        //convert the JSON file content to string and replace the email id dynamically.
        String rawJson = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/user.json")));
        String updatedJson = rawJson.replace("{{email}}", emailId);

        //create the user with updated JSON string and here email id is generated dynamically,
        // and get the user id to use this id in other test cases to verify the user creation and to perform other operations on the user (E2E workflow)
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<Starting Test>>>>>>>>>>>>>>>>>>>>>>>>>>");
        int userId = given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .contentType(ContentType.JSON)
                .body(updatedJson)
                .when().post("/public/v2/users").then().log().all().assertThat().statusCode(201).extract().path("id");
        System.out.println("User Id: " + userId);

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<User Created>>>>>>>>>>>>>>>>>>>>>>>>>>");

        //Now we will use userId to get the user details from the API using GET method.
        System.out.println("<<<<<<<<<<<<<<<<<Fetching User Details using GET Method>>>>>>>>>>>>>>>>>");
        given().log().all()
                .header("Authorization","Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .when().get("/public/v2/users/"+userId)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("id",equalTo(userId));
        System.out.println("<<<<<<<<<<<<<<<<<Fetching User Details using GET Method Completed>>>>>>>>>>>>>>>>>");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<Ending Test>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }


}