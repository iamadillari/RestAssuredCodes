package BookingAPITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

//TODO: use as reference below class for ContactsAPI validation as below

public class BookingApiTest {

    // Base URI for the API
    private static final String BASE_URI = "https://restful-booker.herokuapp.com";
    String tokenID;
    int newBookingId;

    /**
     * Generates an authentication token for API requests.
     * <p>
     * Steps:
     * 1. Set the base URI for the API.
     * 2. Send a POST request with the basic authentication credentials.
     * 3. Extract and store the generated token for use in subsequent requests.
     */
    @BeforeTest
    public void getToken() {
        RestAssured.baseURI = BASE_URI;
        // Create a BasicAuthCredentials object with the predefined username and password
        BasicAuthCredentials basicAuthCredentials = new BasicAuthCredentials("admin", "password123");
        System.out.println("<<<<<<<<<<<<Generating Token>>>>>>>>>>>>");
        // Generate the authentication token
        tokenID = given().log().all()
                .contentType(ContentType.JSON)
                .body(basicAuthCredentials)
                .post("/auth")
                .then()
                .extract()
                .path("token");
        System.out.println("Token ID: " + tokenID);
        System.out.println("<<<<<<<<<<<<Token Generated Successfully>>>>>>>>>>>>");
    }

    /**
     * Sets up a new booking before each test.
     * <p>
     * Steps:
     * 1. Create a new booking using the `createBooking` method.
     * 2. Store the booking ID for use in subsequent tests.
     */
    @BeforeMethod
    public void setUp() {
        newBookingId = createBooking();
    }

    /**
     * Test to fetch and validate booking details.
     * <p>
     * Steps:
     * 1. Fetch the booking details using a GET request.
     * 2. Validate the response status code and booking details.
     */
    @Test
    public void getBookingTest() {
        System.out.println("<<<<<<<<<<<Starting getBookingTest Test>>>>>>>>>>>");
        given().log().all()
                .pathParams("bookingId", newBookingId)
                .when()
                .get("/booking/{bookingId}")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("firstname", equalTo("Jim"))
                .body("bookingdates.checkout", equalTo("2019-01-01"));
        System.out.println("<<<<<<<<<<<Ending getBookingTest Test>>>>>>>>>>>");
    }

    /**
     * Test to create a new booking and validate the booking ID.
     * <p>
     * Steps:
     * 1. Validate that the booking ID is not null.
     */
    @Test
    public void createBookingTest() {
        System.out.println("<<<<<<<<<<<Starting createBookingTest Test>>>>>>>>>>>");
        Assert.assertNotNull(newBookingId);
        System.out.println("<<<<<<<<<<<Ending createBookingTest Test>>>>>>>>>>>");
    }

    /**
     * Test to update an existing booking and validate the updated details.
     * <p>
     * Steps:
     * 1. Update the booking details using a PUT request.
     * 2. Validate the response status code and updated booking details.
     */
    @Test
    public void updateBookingTest() {
        System.out.println("<<<<<<<<<<<Starting updateBookingTest Test>>>>>>>>>>>");
        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .pathParams("BookingId", newBookingId)
                .cookie("token", tokenID)
                .body("{\n" +
                        "    \"firstname\" : \"James\",\n" +
                        "    \"lastname\" : \"Brown\",\n" +
                        "    \"totalprice\" : 111,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}")
                .when().put("/booking/{BookingId}");
        response.prettyPrint();
        JsonPath js = response.jsonPath();
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(js.get("firstname"), "James");
        System.out.println("<<<<<<<<<<<Ending updateBookingTest Test>>>>>>>>>>>");
    }

    /**
     * Test to partially update an existing booking and validate the updated details.
     * <p>
     * Steps:
     * 1. Partially update the booking details using a PATCH request.
     * 2. Validate the response status code and updated booking details.
     */
    @Test
    public void partialUpdateBookingTest() {
        System.out.println("<<<<<<<<<<<Starting partialUpdateBookingTest Test>>>>>>>>>>>");
        System.out.println("<<<<<<<<<<<<<<Partial Update of existing Booking>>>>>>>>>>>>>>");
        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .pathParams("BookingId", newBookingId)
                .cookie("token", tokenID)
                .body("{\n" +
                        "    \"firstname\" : \"Manish\",\n" +
                        "    \"lastname\" : \"Malhotra\"\n" +
                        "}")
                .when().patch("/booking/{BookingId}");
        response.prettyPrint();
        JsonPath js = response.jsonPath();
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(js.get("firstname"), "Manish");
        Assert.assertEquals(js.get("lastname"), "Malhotra");
        System.out.println("<<<<<<<<<<<<<<Partial Booking Update and Validations done>>>>>>>>>>>>>>");
        System.out.println("<<<<<<<<<<<Ending partialUpdateBookingTest Test>>>>>>>>>>>");
    }

    /**
     * Test to delete an existing booking and validate the deletion.
     * <p>
     * Steps:
     * 1. Delete the booking using a DELETE request.
     * 2. Validate the response status code for successful deletion.
     */
    @Test
    public void deleteBookingTest() {
        System.out.println("<<<<<<<<<<<Starting deleteBookingTest Test>>>>>>>>>>>");
        Response deleteRes = given().log().all()
                .contentType(ContentType.JSON)
                .pathParams("BookingId", newBookingId)
                .cookie("token", tokenID)
                .when().delete("/booking/{BookingId}");
        Assert.assertEquals(deleteRes.statusCode(), 201);
        System.out.println("<<<<<<<<<<<Ending deleteBookingTest Test>>>>>>>>>>>");
        System.out.println("<<<<<<Verifying the Deleted booking using GET call>>>>>>");
        Response response = given().contentType(ContentType.JSON)
                .pathParams("BookingId", newBookingId)
                .when().get("/booking/{BookingId}");
        response.prettyPrint();
        int statusCode = response.statusCode();
        System.out.println("Status Code is: "+statusCode);
        System.out.println("<<<<<Get call validation after Deleting the existing booking is Done>>>>>");
    }

    /**
     * Creates a new booking and returns the booking ID.
     * <p>
     * Steps:
     * 1. Set the base URI for the API.
     * 2. Send a POST request with the booking details.
     * 3. Extract and return the booking ID from the response.
     *
     * @return The ID of the newly created booking.
     */
    public int createBooking() {
        System.out.println("<<<<<<<<<Creating a Booking>>>>>>>>>");
        RestAssured.baseURI = BASE_URI;
        Integer bookingID = given().log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"firstname\" : \"Jim\",\n" +
                        "    \"lastname\" : \"Brown\",\n" +
                        "    \"totalprice\" : 111,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}")
                .when()
                .post("/booking")
                .then().log().all()
                .extract().path("bookingid");
        System.out.println("<<<<<<<<<Your new Booking is done and the Booking ID is: " + bookingID + ">>>>>>>>>");
        return bookingID;
    }
}