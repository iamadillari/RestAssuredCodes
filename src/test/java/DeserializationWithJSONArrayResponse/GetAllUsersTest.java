package DeserializationWithJSONArrayResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetAllUsersTest {

    @Test
    public void getAllUsersTest() throws JsonProcessingException {
        RestAssured.baseURI = "https://gorest.co.in/";
        Response response = given().log().all()
                .header("Authorization", "Bearer eab5554f90d4c5e213aea6bdfed3465ed6250b3a43048f6b128d9a5a0f9c8c72")
                .contentType(ContentType.JSON)
                .when()
                .get("public/v2/users");
        response.prettyPrint();

        //Deserialization: JSON array to User Array

        ObjectMapper mapper = new ObjectMapper();
        User[] usersRes = mapper.readValue(response.getBody().asString(), User[].class);
        for (User user : usersRes)
        {
            System.out.println("<<<<<<<<<<User's detail>>>>>>>>>>");
            System.out.println("user id: "+user.getId());
            System.out.println("user email: "+user.getEmail());
            System.out.println("user name: "+user.getName());
            System.out.println("user gender: "+user.getGender());
            System.out.println("user status: "+user.getStatus());
            System.out.println("<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>");
        }

    }
}
