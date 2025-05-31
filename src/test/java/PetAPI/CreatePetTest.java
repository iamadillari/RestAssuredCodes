package PetAPI;

import PetAPI.Pet.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CreatePetTest {

    @Test
    public void createPetWithoutBuilderTest() throws JsonProcessingException {

        RestAssured.baseURI = "https://petstore3.swagger.io";

        Pet.Category category = new Pet.Category(1, "Cat");
        List<String> photourls = Arrays.asList("https://catimage1.jpg", "https://catimage2.jpg"); //since java 1.2, mutable (means we can modify the elements)
        //List<String> photourls = List.of("https://catimage1.jpg", "https://catimage2.jpg"); //since java 9 (immutable)

        Pet.Tag tag1 = new Pet.Tag(11, "red");
        Pet.Tag tag2 = new Pet.Tag(21, "blue");

        List<Tag> tags = Arrays.asList(tag1, tag2); //since java 1.2, mutable (means we can modify the elements)
        //List<Pet.Tag> tags = List.of(tag1, tag2); //since java 9 (immutable)

        Pet pet = new Pet(101, "Roomy", "Available", category, photourls, tags);

        Response response = given().log().all().contentType(ContentType.JSON).body(pet).when().log().all().post("/api/v3/pet");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>");
        response.prettyPrint();

        //Deserialization JSON to POJO

        ObjectMapper mapper = new ObjectMapper();

        Pet petRes = mapper.readValue(response.getBody().asString(), Pet.class);
        System.out.println(petRes.getCategory().getId());
        System.out.println(petRes.getCategory().getName());
        System.out.println(petRes.getId());
        System.out.println(petRes.getTags());
        System.out.println(petRes.getName());
        System.out.println(petRes.getPhotoUrls());
    }

    @Test
    public void createPetUsingBuilderTest() throws JsonProcessingException {

        RestAssured.baseURI = "https://petstore3.swagger.io";

        Pet.Category category = new Pet.Category.CategoryBuilder().id(5).name("Dog").build();
        Pet.Tag tag1 = new Tag.TagBuilder().id(104).name("Blue").build();
        Pet.Tag tag2 = new Tag.TagBuilder().id(106).name("Red").build();
        List<Tag> tag = Arrays.asList(tag1, tag2);
        List<String> photoUrls = Arrays.asList("Dog1Image", "Dog2Image");
        Pet pet = new Pet.PetBuilder().id(1011).name("Tommy").status("Active").category(category).tags(tag).photoUrls(photoUrls).build();
        Response response = given().log().all().contentType(ContentType.JSON).body(pet).when().log().all().post("/api/v3/pet");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>");
        response.prettyPrint();

        //Deserialization JSON to POJO

        ObjectMapper mapper = new ObjectMapper();

        Pet petRes = mapper.readValue(response.getBody().asString(), Pet.class);
        System.out.println(petRes.getCategory().getId());
        System.out.println(petRes.getCategory().getName());
        System.out.println(petRes.getId());
        System.out.println(petRes.getTags());
        System.out.println(petRes.getName());
        System.out.println(petRes.getPhotoUrls());

    }

}