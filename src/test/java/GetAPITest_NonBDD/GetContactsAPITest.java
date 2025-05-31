package GetAPITest_NonBDD;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.List;


/**
 * The GetContactsAPITest class contains test cases to validate the Get Contacts API endpoint.
 * This class uses the RestAssured library to perform API testing and log response details.
 * It is designed to ensure the proper functioning of the API by validating the response
 * status and headers.
 */
public class GetContactsAPITest {

    /**
     * Below test case is Get contacts list using below URI and it's NON-BDD format
     */
    @Test
    public void getContactsTest() {
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        System.out.println("<<<<<<<<<<<<<<<<<-----------------Starting Test----------------->>>>>>>>>>>>>>>>>>>");

        RequestSpecification request = RestAssured.given().log().all();
        request.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2ZkZGE2ODQwNzEwYzAwMTUyNjRlZmEiLCJpYXQiOjE3NDQ2ODk3Njh9.cMit9iLyEnuhpvXJ5Fa2FNwevhv6sW81p4ZSSjQ35cw");
        Response response = request.get("/contacts");
        System.out.println("Status code is: " + response.statusCode());
        System.out.println("Status line is: " + response.statusLine());
        response.prettyPrint();
        String contentType = response.header("Content-type");
        System.out.println("Content-Type in response is: " + contentType);
        Headers headers = response.headers();
        List<Header> headerList = headers.asList();
        System.out.println("Number of Headers is: " + headerList.size());
        for (Header e : headerList) {
            String name = e.getName();
            String value = e.getValue();
            System.out.println("Header Name: " + name + " & Header Value: " + value);
        }

        System.out.println("<<<<<<<<<<<<<<<<<-----------------Ending Test----------------->>>>>>>>>>>>>>>>>>>");
    }
}