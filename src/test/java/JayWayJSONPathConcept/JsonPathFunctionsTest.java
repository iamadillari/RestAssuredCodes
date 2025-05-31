package JayWayJSONPathConcept;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class JsonPathFunctionsTest {

    @Test
    public void jsonPathFunctionsTest(){

        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = given().log().all().when().get("/products");
        String jsonResponse = response.getBody().asString();
        ReadContext readContext = JsonPath.parse(jsonResponse);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<jsonPathFunctionsTest Started>>>>>>>>>>>>>>>>>>>>>");

        //max($[*].price) for maximum price check
        //min($[*].price) for minimum price check
        //avg($[*].price) for average price check

        Double maxPrice = readContext.read("max($[*].price)");
        System.out.println("Maximum Price is: "+maxPrice);
        Double minPrice = readContext.read("min($[*].price)");
        System.out.println("Minimum Price is: "+minPrice);
        Double avgPrice = readContext.read("avg($[*].price)");
        System.out.println("Average Price is: "+avgPrice);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<jsonPathFunctionsTest Ended>>>>>>>>>>>>>>>>>>>>>");

    }

}
