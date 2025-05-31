package AuthAPIs;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AuthApisTest {

    /**
     * 1.Basic
     * 2.Digest
     * 3.API Key
     * 4.OAuth1
     * 5.OAuth2
     * 6.JWT
     * 7.Bearer Token
     */

    @Test
    public void basicAuthApiTest(){
        RestAssured.baseURI= "https://the-internet.herokuapp.com";
        given().auth().basic("admin","admin")
                .when()
                .get("/basic_auth")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void digestAuthApiTest(){
        RestAssured.baseURI= "https://postman-echo.com";
        given().auth().digest("postman","password")
                .when()
                .get("/digest-auth")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void preemptiveAuthApiTest(){
        RestAssured.baseURI= "https://the-internet.herokuapp.com";
        given().auth().preemptive().basic("admin","admin")
                .when()
                .get("/basic_auth")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

}