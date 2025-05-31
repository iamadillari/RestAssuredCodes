package POSTAPITest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

/**
 * The CreateUserAPITest class contains test cases for creating a user
 * by sending HTTP POST requests to the "/public/v2/users" API endpoint.
 * It utilizes the RestAssured library for API testing and covers scenarios
 * such as creating users with JSON strings, JSON files, and JSON with
 * string replacement for dynamic data.
 */
public class CreateUserAPITest {

    //This method will generate a random email id for the user.
    public String getRandomEmailId() {
        return "testapi" + System.currentTimeMillis() + "@gmail.com";
    }

    /**
     * Here in this below test case we are creating a User in below API using JSON string
     */
    @Test
    public void createUserWithJsonStringTest() {

        RestAssured.baseURI = "https://gorest.co.in";

        //generate a random email id for the user.
        String emailId = getRandomEmailId();
        given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .contentType(ContentType.JSON)
                .body("{\n" + "\"name\": \"Aman\",\n" + "\"gender\": \"male\",\n"
                        + "\"email\": \"" + emailId + "\",\n" + "\"status\": \"active\"\n" + "}")
                .when().post("/public/v2/users").then().log().all().assertThat().statusCode(201);


    }

    /**
     * Here in this below test case we are creating a User in below API using JSON file
     */
    @Test
    public void createUserWithJsonFileTest() {

        RestAssured.baseURI = "https://gorest.co.in";

        given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .contentType(ContentType.JSON)
                .body(new File("./src/test/resources/jsons/user.json"))
                .when().post("/public/v2/users").then().log().all().assertThat().statusCode(201);

    }

    /**
     * Here in this below test case we are creating a User in below API using JSON file with String Replacement
     */
    @Test
    public void createUserWithJsonFileWithStringReplacementTest() throws IOException {

        RestAssured.baseURI = "https://gorest.co.in";

        //generate a random email id for the user.
        String emailId = getRandomEmailId();

        //convert the JSON file content to string and replace the email id dynamically.
        String rawJson = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/user.json")));
        String updatedJson = rawJson.replace("{{email}}", emailId);

        //create the user with updated JSON string and here email id is generated dynamically,
        // and get the user id to use this id in other test cases to verify the user creation and to perform other operations on the user (E2E workflow)
        int userId = given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .contentType(ContentType.JSON)
                .body(updatedJson)
                .when().post("/public/v2/users").then().log().all().assertThat().statusCode(201).extract().path("id");
        System.out.println("User Id: " + userId);

    }

}
