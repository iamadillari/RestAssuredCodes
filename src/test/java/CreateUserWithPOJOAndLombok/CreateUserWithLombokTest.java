package CreateUserWithPOJOAndLombok;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * This class contains test cases for creating and fetching users using the GoRest API.
 * It demonstrates the use of Lombok for POJO creation and RestAssured for API testing.
 */
public class CreateUserWithLombokTest {

    /**
     * Generates a random email ID for testing purposes.
     *
     * @return A unique email ID string.
     */
    public String getRandomEmailId() {
        return "apiTest" + System.currentTimeMillis() + "@gmail.com";
    }

    /**
     * Test case to create a user and fetch the same user using the GoRest API.
     * This test uses a POJO with Lombok annotations for serialization.
     */
    @Test
    public void createAndFetchUserTest() {
        RestAssured.baseURI = "https://gorest.co.in/";

        // Create a user object using Lombok
        UserLombok user = new UserLombok("Adil", getRandomEmailId(), "Male", "Active");

        // POST request to create a user
        Integer userId = given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("public/v2/users")
                .then().log().all()
                .statusCode(201)
                .extract()
                .path("id");
        System.out.println("User ID: " + userId);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<User Created>>>>>>>>>>>>>>>>>>>>>>>>>>");

        // GET request to fetch the created user
        given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .when()
                .get("public/v2/users/" + userId)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", equalTo(userId));
        System.out.println("<<<<<<<<<<<<<<<<<<<<<User Fetched Successfully>>>>>>>>>>>>>>>>>>>>>");
    }

    /**
     * Test case to create a user using the Builder pattern and fetch the same user.
     * This test demonstrates the use of Lombok's Builder pattern for object creation.
     */
    @Test
    public void createAndFetchUserUsingBuilderPatternTest() {
        RestAssured.baseURI = "https://gorest.co.in/";

        // Create a user object using Lombok's Builder pattern
        UserLombok user = new UserLombok.UserLombokBuilder()
                .name("Tom")
                .email(getRandomEmailId())
                .status("Active")
                .gender("Male")
                .build();

        // POST request to create a user
        Integer userId = given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("public/v2/users")
                .then().log().all()
                .statusCode(201)
                .extract()
                .path("id");
        System.out.println("User ID: " + userId);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<User Created>>>>>>>>>>>>>>>>>>>>>>>>>>");

        // GET request to fetch the created user
        given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .when()
                .get("public/v2/users/" + userId)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", equalTo(userId));
        System.out.println("<<<<<<<<<<<<<<<<<<<<<User Fetched Successfully>>>>>>>>>>>>>>>>>>>>>");
    }

    /**
     * Test case to create a user using the Builder pattern and validate the user's name.
     * This test demonstrates the use of Lombok's Builder pattern and getter methods.
     */
    @Test
    public void createAndFetchUserUsingBuilderPatternWithGetterTest() {
        RestAssured.baseURI = "https://gorest.co.in/";

        // Create a user object using Lombok's Builder pattern
        UserLombok user = new UserLombok.UserLombokBuilder()
                .name("Nikita")
                .email(getRandomEmailId())
                .status("active")
                .gender("female")
                .build();

        // POST request to create a user and extract the name from the response
        Response response = given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("public/v2/users");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<User Created>>>>>>>>>>>>>>>>>>>>>>>>>>");
        JsonPath js = response.jsonPath();
        Assert.assertEquals(js.get("name"), user.getName());
        Assert.assertEquals(js.get("status"), user.getStatus());
        Assert.assertEquals(js.get("email"), user.getEmail());
        Assert.assertEquals(js.get("gender"), user.getGender());
        Assert.assertNotNull(js.get("id"));
    }
}