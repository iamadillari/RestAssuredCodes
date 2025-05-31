package CreateUserWithPOJOAndLombok;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CreateUserTestWithPOJO {

    // We will use this method to generate random email id for out testing purpose.
    public String getRandomEmailId() {
        return "apiTest" + System.currentTimeMillis() + "@gmail.com";
    }

    @Test
    public void createAndFetchUserTest() {
        RestAssured.baseURI = "https://gorest.co.in/";
        UserPOJO user = new UserPOJO("Ram", "Male", "Active", getRandomEmailId());
		//1. POST call : Here we are creating a user using POJO to JSON (Serialization) using Jackson (databind) lib
        Integer userId = given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .contentType(ContentType.JSON).body(user).when().post("public/v2/users").then().log().all()
                .statusCode(201).extract().path("id");
        System.out.println("User ID: " + userId);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<User Created>>>>>>>>>>>>>>>>>>>>>>>>>>");
		//2. GET call : Here we are fetching the above user
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
}