package PostAPIWithDifferentBodyTypes;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

/**
 * The PostApiWithDifferentBody class contains test cases to demonstrate
 * sending POST requests with various types of request bodies using RestAssured.
 * This includes sending plain text, JavaScript, HTML, XML, multipart body,
 * and binary files as payloads in HTTP POST requests.
 * <p>
 * Each test case validates the server's response status code to ensure the
 * request executes successfully, by asserting an expected 200 OK response.
 * <p>
 * Test cases:
 * - bodyWithPlainTextTest: Sends a plain text payload.
 * - bodyWithJavaScriptTest: Sends a JavaScript payload.
 * - bodyWithHTMLTest: Sends an HTML payload.
 * - bodyWithXMLTest: Sends an XML payload.
 * - bodyWithMultipPartTest: Sends a multipart payload with file and additional fields.
 * - bodyWithPDFTest: Sends a binary PDF file payload.
 */
public class PostApiWithDifferentBody {

    //Test cases with Plain Text body
    @Test
    public void bodyWithPlainTextTest() {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<Starting Test>>>>>>>>>>>>>>>>>>>>>>");
        RestAssured.baseURI = "https://postman-echo.com";
        String payload = "This is a plain text body";
        given().log().all().contentType(ContentType.TEXT)
                .body(payload)
                .when().post("/post")
                .then().log().all()
                .assertThat().statusCode(200);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<Ending Test>>>>>>>>>>>>>>>>>>>>>>>");
    }

    //Test cases with Javascript body
    @Test
    public void bodyWithJavaScriptTest() {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<Starting Test>>>>>>>>>>>>>>>>>>>>>>");
        RestAssured.baseURI = "https://postman-echo.com";
        String payload = "<script>\n" +
                "document.getElementById(\"demo\").innerHTML = 10.50;\n" +
                "</script>";

        given().log().all().contentType("application/javascript;charset=UTF-8")
                .body(payload)
                .when().post("/post")
                .then().log().all()
                .assertThat().statusCode(200);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<Ending Test>>>>>>>>>>>>>>>>>>>>>>>");
    }

    //Test case with HTML body
    @Test
    public void bodyWithHTMLTest() {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<Starting Test>>>>>>>>>>>>>>>>>>>>>>");
        RestAssured.baseURI = "https://postman-echo.com";
        String payload = "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h1>This is heading 1</h1>\n" +
                "<h2>This is heading 2</h2>\n" +
                "<h3>This is heading 3</h3>\n" +
                "<h4>This is heading 4</h4>\n" +
                "<h5>This is heading 5</h5>\n" +
                "<h6>This is heading 6</h6>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        given().log().all().contentType(ContentType.HTML)
                .body(payload)
                .when().post("/post")
                .then().log().all()
                .assertThat().statusCode(200);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<Ending Test>>>>>>>>>>>>>>>>>>>>>>>");
    }

    //Test case with XML body
    @Test
    public void bodyWithXMLTest() {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<Starting Test>>>>>>>>>>>>>>>>>>>>>>");
        RestAssured.baseURI = "https://postman-echo.com";
        String payload = "<note>\n" +
                "<to>Tove</to>\n" +
                "<from>Jani</from>\n" +
                "<heading>Reminder</heading>\n" +
                "<body>Don't forget me this weekend!</body>\n" +
                "</note>";
        given().log().all().contentType("application/xml;charset=UTF-8")
                .body(payload)
                .when().post("/post")
                .then().log().all()
                .assertThat().statusCode(200);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<Ending Test>>>>>>>>>>>>>>>>>>>>>>>");
    }

    //Test case with Multipart body
    @Test
    public void bodyWithMultipPartTest() {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<Starting Test>>>>>>>>>>>>>>>>>>>>>>");
        RestAssured.baseURI = "https://postman-echo.com";
        given().log().all()
                .contentType(ContentType.MULTIPART)
                .multiPart("Resume", "/Users/adillari/Downloads/1743311329915.pdf")
                .multiPart("Name", "Adil")
                .when().post("/post").then().log().all().assertThat().statusCode(200);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<Ending Test>>>>>>>>>>>>>>>>>>>>>>>");
    }

    //Test case with PDF file in body as Binary File
    @Test
    public void bodyWithPDFTest() {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<Starting Test>>>>>>>>>>>>>>>>>>>>>>");
        RestAssured.baseURI = "https://postman-echo.com";
        given().log().all()
                .contentType("application/pdf;charset=UTF-8")
                .body(new File("/Users/adillari/Downloads/ChromeDownload/Mastercard NEW HIRE PAYROLL CHECKLIST (FORMS)/GratuityFormFnomination.PDF"))
                .when().post("/post").then().log().all().assertThat().statusCode(200);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<Ending Test>>>>>>>>>>>>>>>>>>>>>>>");
    }

}