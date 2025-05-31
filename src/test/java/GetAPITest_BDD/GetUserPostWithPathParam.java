package GetAPITest_BDD;

import io.restassured.RestAssured;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

/**
 * The GetUserPostWithPathParam class contains test cases to interact with APIs
 * using RestAssured library. This class demonstrates the usage of different
 * techniques for passing parameters to API endpoints, including data providers
 * for parameterized tests and path parameters.
 */
public class GetUserPostWithPathParam {

    /**
     * using data provider to check below test with multiple set of data and this
     * dataprovider will come from TestNg
     */
    @DataProvider
    public Object[][] getUserData() {
        return new Object[][]{{7840235, "Odio sperno acer libero deorsum."}, {7839067, "Ambitus allatus votum demoror talis."}, {7839071, "Tam solum quisquam vesper viriliter illum."}};
    }

    @Test(dataProvider = "getUserData")
    public void getUserPostWithPathParamTest(int userId, String title) {
        RestAssured.baseURI = "https://gorest.co.in";
        System.out.println("<<<<<<<<----------------------------Starting Test---------------------->>>>>>>>>");
        given().log().all().header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72").pathParam("userId", userId).when().log().all().get("public/v2/users/{userId}/posts").then().log().all().assertThat().statusCode(200).and().body("title", hasItem(title));
        System.out.println("<<<<<<<<----------------------------Ending Test------------------------>>>>>>>>>");
    }

    /**
     * Here we are using Path param as hashmap in below test
     */
    @Test
    public void getUserUsingHashMapPathParamTest() {
        RestAssured.baseURI = "https://reqres.in";

        HashMap<String, String> pathParamMap = new HashMap<String, String>();
        pathParamMap.put("firstPathParam", "api");
        pathParamMap.put("secondPathParam", "users");

        System.out.println("<<<<<<<<----------------------------Starting Test----------------------->>>>>>>>>");
        given().log().all().pathParams(pathParamMap).queryParam("page", 2).when().log().all().get("{firstPathParam}/{secondPathParam}").then().log().all().assertThat().statusCode(200);
        System.out.println("<<<<<<<<-----------------------------Ending Test------------------------>>>>>>>>>");
    }

}