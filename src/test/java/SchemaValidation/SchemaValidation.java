package SchemaValidation;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

public class SchemaValidation {


    public RequestSpecification requestSpecificationSetup() {
        RequestSpecification requestSpecification =
                given().log().all()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                        .baseUri("https://gorest.co.in");
        return requestSpecification;
    }

    public ResponseSpecification responseSpecificationSetup() {
        ResponseSpecification responseSpecification = expect().statusCode(anyOf(equalTo(200),equalTo(201)));
        return responseSpecification;
    }

    @Test
    public void getUsersSchemaValidationTest() {
        System.out.println("-----<<<<<<<getUsersSchemaValidationTest Started>>>>>>>-----");
        requestSpecificationSetup().when().get("/public/v2/users")
                .then().log().all().spec(responseSpecificationSetup())
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getUsersSchema_GoRest.json"));
        System.out.println("-----<<<<<<<getUsersSchemaValidationTest Ended>>>>>>>-----");
    }

    @Test
    public void createAUserSchemaValidation() {
        System.out.println("-----<<<<<<<createAUserSchemaValidation Started>>>>>>>-----");
        requestSpecificationSetup()
                .body(new File("src/test/resources/jsons/createNewUser.json"))
                .when()
                .post("/public/v2/users")
                .then().log().all().spec(responseSpecificationSetup())
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("createAUserSchema_GoRest.json"));
        System.out.println("-----<<<<<<<createAUserSchemaValidation Ended>>>>>>>-----");
    }

}