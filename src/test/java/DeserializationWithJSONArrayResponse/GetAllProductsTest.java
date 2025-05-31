package DeserializationWithJSONArrayResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetAllProductsTest {

    @Test
    public void getAllProductsTes() throws JsonProcessingException {

        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = given().log().all()
                .when()
                .get("/products");
        response.prettyPrint();

        //Deserialization JSON Array to POJO

        ObjectMapper mapper = new ObjectMapper();
        Product[] product = mapper.readValue(response.getBody().asString(), Product[].class);
        for(Product p : product)
        {
            System.out.println("<<<<<<<<<<Product details are>>>>>>>>>>");
            System.out.println("Product id: "+ p.getId());
            System.out.println("Product title: "+ p.getTitle());
            System.out.println("Product price: "+ p.getPrice());
            System.out.println("Product description: "+ p.getDescription());
            System.out.println("Product category: "+ p.getCategory());
            System.out.println("Product image link: "+ p.getImage());
            System.out.println("Product rate is: "+p.getRating().getRate());
            System.out.println("Product count is: "+p.getRating().getCount());
            System.out.println("========================================");
        }
    }

}