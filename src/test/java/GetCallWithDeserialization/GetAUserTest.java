package GetCallWithDeserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetAUserTest {

    /**
     * Test to fetch a single user using a POJO for deserialization.
     * This test sends a GET request to the GoRest API, retrieves the user details,
     * and deserializes the response into a `User` object.
     *
     * @throws JsonProcessingException if there is an error during JSON processing.
     */
    @Test
    public void getASingleUserUsingPOJOTest() throws JsonProcessingException {

        // Set the base URI for the API
        RestAssured.baseURI = "https://gorest.co.in";

        // Send a GET request to fetch user details
        Response response =
                given().log().all()
                        .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                        .contentType(ContentType.JSON)
                        .when()
                        .get("public/v2/users/7866978");

        // Print the response for debugging purposes
        response.prettyPrint();

        // Deserialize the response body into a `User` object
        String responseBody = response.asString();
        ObjectMapper mapper = new ObjectMapper();
        User userRes = mapper.readValue(responseBody, User.class);

        // Assert that the user ID in the response matches the expected value
        Assert.assertEquals(userRes.getId(), 7866978);
    }

    /**
     * Test to fetch a single user using a Lombok-based POJO for deserialization.
     * This test sends a GET request to the GoRest API, retrieves the user details,
     * and deserializes the response into a `UserLombok` object.
     *
     * @throws JsonProcessingException if there is an error during JSON processing.
     */
    @Test
    public void getASingleUserUsingLombokTest() throws JsonProcessingException {

        // Set the base URI for the API
        RestAssured.baseURI = "https://gorest.co.in";

        // Send a GET request to fetch user details
        Response response =
                given().log().all()
                        .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                        .contentType(ContentType.JSON)
                        .when()
                        .get("public/v2/users/7866984");

        // Print the response for debugging purposes
        response.prettyPrint();

        // Deserialize the response body into a `UserLombok` object
        String responseBody = response.asString();
        ObjectMapper mapper = new ObjectMapper();
        UserLombok userLombok = mapper.readValue(responseBody, UserLombok.class);

        // Assert that the user ID in the response matches the expected value
        Assert.assertEquals(userLombok.getId(), 7866984);

    }

}