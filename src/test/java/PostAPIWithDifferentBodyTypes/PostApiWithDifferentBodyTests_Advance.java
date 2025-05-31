package PostAPIWithDifferentBodyTypes;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

/**
 * Tests for validating POST API requests with various types of request bodies and payloads.
 * The tests are executed against the Postman Echo API for verifying payload compatibility
 * and status code responses.
 * <p>
 * This class includes tests for posting plain text, JavaScript, HTML, XML bodies,
 * multipart bodies, and file uploads in different content types. The base URI for the
 * API is defined as a constant, and setup is done prior to tests execution.
 * <p>
 * Methods in this class leverage validation mechanisms to ensure successful POST requests
 * with appropriate content types and payloads. User status codes are checked to
 * confirm behavior of the service under test.
 * <p>
 * Testing is divided into the following:
 * - Validating POST requests with plain text payloads.
 * - Validating POST requests with JavaScript payloads.
 * - Validating POST requests with HTML payloads.
 * - Validating POST requests with XML payloads.
 * - Validating POST requests with multipart body content.
 * - Validating POST requests with a file payload.
 * <p>
 * Utility methods like logging and result verification are used to ensure code reusability
 * and consistency in assertions.
 * <p>
 * Setup and validation:
 * - The base URI for tests is set using RestAssured configurations.
 * - Payloads are predefined constants of different content types.
 * - Verification includes ensuring the status code is 200 (HTTP OK).
 */
public class PostApiWithDifferentBodyTests_Advance {
    private static final String BASE_URI = "https://postman-echo.com";
    private static final String POST_ENDPOINT = "/post";
    private static final String TEST_RESOURCES_PATH = "/Users/adillari/Downloads";

    private static final String PLAIN_TEXT_PAYLOAD = "This is a plain text body";
    private static final String JS_PAYLOAD =
            "<script>\ndocument.getElementById(\"demo\").innerHTML = 10.50;\n</script>";
    private static final String HTML_PAYLOAD = """
            <html>
            <body>
            <h1>This is heading 1</h1>
            <h2>This is heading 2</h2>
            <h3>This is heading 3</h3>
            <h4>This is heading 4</h4>
            <h5>This is heading 5</h5>
            <h6>This is heading 6</h6>
            </body>
            </html>""";
    private static final String XML_PAYLOAD = """
            <note>
            <to>Tove</to>
            <from>Jani</from>
            <heading>Reminder</heading>
            <body>Don't forget me this weekend!</body>
            </note>""";

    @BeforeMethod
    public void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    private void logTestBoundary(String message) {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<" + message + ">>>>>>>>>>>>>>>>>>>>>>");
    }

    private void verifySuccessfulPost(ContentType contentType, Object payload) {
        logTestBoundary("Starting Test");

        given()
                .log().all()
                .contentType(contentType)
                .body(payload)
                .when()
                .post(POST_ENDPOINT)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);

        logTestBoundary("Ending Test");
    }

    /**
     * Tests sending a POST request with a plain text body.
     * Verifies that the server accepts and processes a simple text payload
     * with Content-Type: text/plain correctly, expecting a 200 OK response.
     */
    @Test
    public void shouldPostPlainTextBody() {
        verifySuccessfulPost(ContentType.TEXT, PLAIN_TEXT_PAYLOAD);
    }

    /**
     * Tests sending a POST request with a JavaScript code snippet as the body.
     * Verifies that the server handles JavaScript content with proper content type
     * (application/javascript) and UTF-8 encoding, expecting a 200 OK response.
     */
    @Test
    public void shouldPostJavaScriptBody() {
        verifySuccessfulPost(
                ContentType.valueOf("application/javascript;charset=UTF-8"),
                JS_PAYLOAD
        );
    }

    /**
     * Tests sending a POST request with HTML content in the body.
     * Verifies that the server correctly processes HTML markup with appropriate
     * Content-Type: text/html, expecting a 200 OK response.
     */
    @Test
    public void shouldPostHtmlBody() {
        verifySuccessfulPost(ContentType.HTML, HTML_PAYLOAD);
    }

    /**
     * Tests sending a POST request with XML data in the body.
     * Verifies that the server handles XML content with proper content type
     * (application/xml) and UTF-8 encoding, expecting a 200 OK response.
     */
    @Test
    public void shouldPostXmlBody() {
        verifySuccessfulPost(
                ContentType.valueOf("application/xml;charset=UTF-8"),
                XML_PAYLOAD
        );
    }

    /**
     * Tests sending a POST request with multipart form data.
     * Validates that the server can handle multiple parts in the request body:
     * - A PDF file attachment named "Resume"
     * - A text field named "Name" with value "Adil"
     * Expects a 200 OK response from the server.
     */
    @Test
    public void shouldPostMultipartBody() {
        logTestBoundary("Starting Test");

        given()
                .log().all()
                .contentType(ContentType.MULTIPART)
                .multiPart("Resume", new File(TEST_RESOURCES_PATH + "/1743311329915.pdf"))
                .multiPart("Name", "Adil")
                .when()
                .post(POST_ENDPOINT)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);

        logTestBoundary("Ending Test");
    }

    /**
     * Tests sending a POST request with a PDF file as the request body.
     * Verifies that the server can handle binary PDF content with proper content type
     * (application/pdf) and UTF-8 encoding, expecting a 200 OK response.
     */
    @Test
    public void shouldPostPdfFile() {
        String pdfPath = TEST_RESOURCES_PATH +
                "/ChromeDownload/Mastercard NEW HIRE PAYROLL CHECKLIST (FORMS)/GratuityFormFnomination.PDF";

        verifySuccessfulPost(
                ContentType.valueOf("application/pdf;charset=UTF-8"),
                new File(pdfPath)
        );
    }

}