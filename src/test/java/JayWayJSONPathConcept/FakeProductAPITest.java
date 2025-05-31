package JayWayJSONPathConcept;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * FakeProductAPITest is a test class that demonstrates the use of RestAssured and JayWay JSONPath
 * for testing a fake product API. It includes methods to fetch and validate product details
 * using JSONPath expressions.
 */
public class FakeProductAPITest {

    /**
     * Test to fetch and validate product details from the API response.
     * <p>
     * Steps:
     * 1. Sends a GET request to the `/products` endpoint.
     * 2. Parses the JSON response using JayWay JSONPath.
     * 3. Extracts and validates individual attributes like IDs and images.
     * 4. Fetches multiple attributes and prints them.
     * 5. Extracts and prints detailed product information including ratings.
     */
    @Test
    public void getProductsAPIJsonTest() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = given().log().all().when().get("/products");
        String jsonResponse = response.getBody().asString();
        ReadContext readContext = JsonPath.parse(jsonResponse);

        System.out.println("<<<<<<<<<<getProductsAPIJsonTest Started>>>>>>>>>>");
        // Fetching individual attributes
        List<Integer> ids = readContext.read("$.[*].id");
        System.out.println("<<<<<<<<<<<<IDs are>>>>>>>>>>>>");
        for (Integer e : ids) {
            System.out.println(e);
            Assert.assertNotNull(e); // Validates that the ID is not null
        }
        System.out.println("Total no of Ids are: " + ids.size());
        System.out.println("<<<<<<<<<<<<>>>>>>>>>>>>");
        List<String> images = readContext.read("$.[*].image");
        System.out.println("<<<<<<<<<<<<Images are>>>>>>>>>>>>");
        for (String e : images) {
            System.out.println(e);
            Assert.assertNotNull(e); // Validates that the image URL is not null
        }
        System.out.println("Total no of Images are: " + images.size());
        System.out.println("<<<<<<<<<<<<>>>>>>>>>>>>");

        // Fetching multiple attributes
        List<Map<String, Object>> attributes = readContext.read("$.[*].['id', 'title']");
        System.out.println(attributes.size());
        for (Map<String, Object> e : attributes) {
            System.out.println(e.get("id"));
            System.out.println(e.get("title"));
        }

        // Fetching ratings details
        System.out.println("<<<<<<<<<<<<<<<<<Ratings Detail Started>>>>>>>>>>>>>>>>>");
        List<Map<Float, Integer>> ratingDetails = readContext.read("$.[*].rating");
        for (Map<Float, Integer> e : ratingDetails) {
            System.out.println(e.get("rate")); // Prints the rating's rate
        }
        System.out.println("<<<<<<<<<<<<<<<<<Ratings Detail Ended>>>>>>>>>>>>>>>>>");

        // Fetching selected attributes
        System.out.println("<<<<<<<<<<<<<<<<<ID-Title-Ratings Detail Started>>>>>>>>>>>>>>>>>");
        List<Map<String, Object>> idTitleRatingDetails = readContext.read("$.*].['id','title','price','image','rating']");
        for (Map<String, Object> e : idTitleRatingDetails) {
            System.out.println("Product ID-> " + e.get("id"));
            System.out.println("Product Title-> " + e.get("title"));
            System.out.println("Product Price-> " + e.get("price"));
            System.out.println("Product Image Link-> " + e.get("image"));
            Map<String, Object> rating = (Map<String, Object>) e.get("rating");
            System.out.println("Rating's Rate-> " + rating.get("rate"));
            System.out.println("Rating's Count-> " + rating.get("count"));
            System.out.println("------------------------------------");
        }
        System.out.println("<<<<<<<<<<<<<<<<<ID-Title-Ratings Detail Ended>>>>>>>>>>>>>>>");

        System.out.println("<<<<<<<<<<getProductsAPIJsonTest Ended>>>>>>>>>>");
    }

    /**
     * Test to fetch product details with a specific condition.
     * <p>
     * Steps:
     * 1. Sends a GET request to the `/products` endpoint.
     * 2. Parses the JSON response using JayWay JSONPath.
     * 3. Extracts and prints product details for the "jewelery" category.
     */
    @Test
    public void getProductsDetailsWithConditionTest() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = given().log().all().when().get("/products");
        String jsonResponse = response.getBody().asString();
        ReadContext readContext = JsonPath.parse(jsonResponse);

        System.out.println("<<<<<<<<<<getProductsDetailsWithConditionTest Started>>>>>>>>>>");

        // Fetching product titles for the "jewelery" category
        List<String> jeweleryProductsTitle = readContext.read("$.[?(@.category=='jewelery')].title");
        for (String e : jeweleryProductsTitle) {
            System.out.println("Jewelery Products Title: " + e);
        }

        // Fetching product details for the "jewelery" category
        List<Map<String, Object>> jeweleryProductDetails = readContext.read("$.[?(@.category=='jewelery')].['id','title', 'rating']");
        System.out.println("------<<<Jewelery related products details are>>>------");
        for (Map<String, Object> e : jeweleryProductDetails) {
            System.out.println("ID: " + e.get("id"));
            System.out.println("TITLE-> " + e.get("title"));
            Map<String, Object> rating = (Map<String, Object>) e.get("rating");
            System.out.println("RATING's RATE-> " + rating.get("rate"));
            System.out.println("RATING's COUNT-> " + rating.get("count"));
        }

        System.out.println("<<<<<<<<<<getProductsDetailsWithConditionTest Ended>>>>>>>>>>");
    }

    /**
     * Test to fetch product details with multiple conditions.
     * <p>
     * Steps:
     * 1. Sends a GET request to the `/products` endpoint.
     * 2. Parses the JSON response using JayWay JSONPath.
     * 3. Extracts and prints product details for the "jewelery" category with a price less than 300.
     */
    @Test
    public void getProductsDetailsWithMultipleConditionsTest() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = given().log().all().when().get("/products");
        String jsonResponse = response.getBody().asString();
        ReadContext readContext = JsonPath.parse(jsonResponse);
        System.out.println("<<<<<<<<<<getProductsDetailsWithMultipleConditionsTest Started>>>>>>>>>>");

        // Fetching IDs for products in the "jewelery" category with price < 300
        List<Integer> idList = readContext.read("$.[?((@.category=='jewelery') && (@.price < 300))].id");
        System.out.println("---Printing all possible IDs list as below for given condition---");
        for (Integer e : idList) {
            System.out.println("ID: " + e);
        }

        // Fetching IDs and titles for products in the "jewelery" category with price < 300
        List<Map<String, Object>> mapOfIdAndTitle = readContext.read("$.[?((@.category=='jewelery') && (@.price < 300))].['id','title']");
        System.out.println("---Printing all possible IDs and Titles as below for given condition---");
        for (Map<String, Object> e : mapOfIdAndTitle) {
            System.out.println("Id: " + e.get("id"));
            System.out.println("Title " + e.get("title"));
        }

        System.out.println("<<<<<<<<<<getProductsDetailsWithMultipleConditionsTest Ended>>>>>>>>>>");
    }


    /**
     * Test to fetch product details with special conditions using JSONPath.
     * <p>
     * Steps:
     * 1. Sends a GET request to the `/products` endpoint.
     * 2. Parses the JSON response using JayWay JSONPath.
     * 3. Filters products based on a regex pattern in the `title` field.
     * 4. Extracts and prints the `id` of products whose `title` contains "Backpack" (case-insensitive).
     * <p>
     * JSONPath Explanation is below
     */

    // $[?(@.title =~/.*Backpack.*/i)].id
    // $ Refers to the root of the JSON.
    // ?(@.title =~ /.*Backpack.*/i) → This filters elements where the title matches the regex pattern:
    // @.title accesses the title field of each object.
    // =~ means "matches regex".
    // /.*Backpack.*/i is a regular expression that matches any title containing "Backpack", regardless of case.
    // .* allows any characters before or after "Backpack".
    // i make the match case-insensitive ("backpack", "BackPack", "BACKPACK", etc.).
    //.id Extracts the id field from the filtered results.
    @Test
    public void getProductsDetailsWithContainsConditionTest() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = given().log().all().when().get("/products");
        String jsonResponse = response.getBody().asString();
        ReadContext readContext = JsonPath.parse(jsonResponse);
        System.out.println("<<<<<<<<<<getProductsDetailsWithContainsConditionTest Started>>>>>>>>>>");

        // Extract the IDs of products whose title contains "Backpack" (case-insensitive)
        List<Integer> idList = readContext.read("$[?(@.title =~/.*Backpack.*/i)].id");
        for (Integer e : idList) {
            System.out.println("Id: " + e);
        }

        System.out.println("<<<<<<<<<<getProductsDetailsWithContainsConditionTest Ended>>>>>>>>>>");
    }


    /**
     * Test to fetch product IDs whose titles start with "Mens" (case-insensitive).
     * <p>
     * Steps:
     * 1. Sends a GET request to the `/products` endpoint.
     * 2. Parses the JSON response using JayWay JSONPath.
     * 3. Extracts and prints the IDs of products whose `title` starts with "Mens".
     */
    @Test
    public void getProductsDetailsStartingWithConditionTest() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = given().log().all().when().get("/products");
        String jsonResponse = response.getBody().asString();
        ReadContext readContext = JsonPath.parse(jsonResponse);
        System.out.println("<<<<<<<<<<getProductsDetailsStartingWithConditionTest Started>>>>>>>>>>");

        // Extract the IDs of products whose title starts with "Mens"
        List<Integer> idList = readContext.read("$[?(@.title =~/^Mens.*/i)].id");
        for (Integer e : idList) {
            System.out.println("Id: " + e);
        }
        System.out.println("<<<<<<<<<<getProductsDetailsStartingWithConditionTest Ended>>>>>>>>>>");
    }


    /**
     * Test to fetch product IDs whose titles end with "Laptops" (case-insensitive).
     * <p>
     * Steps:
     * 1. Sends a GET request to the `/products` endpoint.
     * 2. Parses the JSON response using JayWay JSONPath.
     * 3. Extracts and prints the IDs of products whose `title` ends with "Laptops".
     */
    @Test
    public void getProductsDetailsEndingWithConditionTest() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = given().log().all().when().get("/products");
        String jsonResponse = response.getBody().asString();
        ReadContext readContext = JsonPath.parse(jsonResponse);
        System.out.println("<<<<<<<<<<getProductsDetailsEndingWithConditionTest Started>>>>>>>>>>");

        // Extract the IDs of products whose title ends with "Laptops"
        List<Integer> idList = readContext.read("$[?(@.title =~/.*Laptops$/i)].id");
        for (Integer e : idList) {
            System.out.println("Id: " + e);
        }
        System.out.println("<<<<<<<<<<getProductsDetailsEndingWithConditionTest Ended>>>>>>>>>>");
    }

}