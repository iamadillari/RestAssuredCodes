package XMLApis;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetAllUsersXMLApiTest {

    @Test
    public void getAllUserTest() {
        RestAssured.baseURI = "https://gorest.co.in";
        Response response =
                given().log().all()
                        .when()
                        .get("/public/v2/users.xml");
        String responseBody = response.getBody().asString();

        //create the object of XMLPath
        XmlPath xmlPath = new XmlPath(responseBody);

        //fetching the data
        String obtType = xmlPath.getString("objects.@type");
        System.out.println("Object type is: " + obtType);
        Assert.assertEquals(obtType, "array");
        System.out.println("------------------------");
        List<String> objectIdTypeList = xmlPath.getList("objects.object.id.@type");
        for (String e : objectIdTypeList) {
            System.out.println(e);
            Assert.assertEquals(e, "integer");
        }
        System.out.println("------------------------");

        //fetch all the id values/text
        List<Integer> objectIdTypeValueList = xmlPath.getList("objects.object.id", Integer.class);
        for (Integer e : objectIdTypeValueList) {
            System.out.println(e);
            Assert.assertNotNull(e);
        }
        System.out.println("------------------------");
        //fetch all the names
        List<String> objectNameList = xmlPath.getList("objects.object.name");

        for (String e : objectNameList) {
            System.out.println(e);
            Assert.assertNotNull(e);
        }
        System.out.println("------------------------");
    }

    @Test
    public void getAllUserTestTestWithXml_Deserialization(){

        RestAssured.baseURI = "https://gorest.co.in";
        Response response =
                given().log().all()
                        .when()
                        .get("/public/v2/users.xml");
        String responseBody = response.getBody().asString();

        //TODO : Need to revisit Naveen's 8th Video for XML Path

    }

}
