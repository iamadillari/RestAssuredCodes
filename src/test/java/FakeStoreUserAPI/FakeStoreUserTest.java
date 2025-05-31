package FakeStoreUserAPI;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class FakeStoreUserTest {

    @Test
    public void createAFakeUserWithoutBuilderTest() {

        RestAssured.baseURI = "https://fakestoreapi.com";
        FakeUser.Address.GeoLocation geoLocation = new FakeUser.Address.GeoLocation("-37.3159", "81.1496");
        FakeUser.Address address = new FakeUser.Address(geoLocation, "Pune", "Grant Road", 97252 - 10083, "411014");
        FakeUser.Name name = new FakeUser.Name("Manoj", "Singh");
        FakeUser fakeUser = new FakeUser("manojsingh@gmail.com", "manoj123", "manoj@123", "+91-1234234212", name, address);
        Response responseBody = given().log().all().contentType(ContentType.JSON).body(fakeUser).when().post("/users");
        System.out.println("<<<<<<<<<<<<Test Ended and User is below>>>>>>>>>>>>");
        responseBody.prettyPrint();
    }

    @Test
    public void createAFakeUserUsingSeparatePOJOTest() {

        RestAssured.baseURI = "https://fakestoreapi.com";
        Name name = new Name("Aditya", "Tiwari");
        Geolocation geolocation = new Geolocation("12342", "32133");
        Address address = new Address("411014", 912323, "Pune", "Grant Road", geolocation);
        User user = new User("12312", address, "123123132", name, "aditya@gmail.com", "aditya");

        Response responseBody = given().log().all().contentType(ContentType.JSON).body(user).when().post("/users");
        System.out.println("<<<<<<<<<<<<Test Ended and User is below>>>>>>>>>>>>");
        responseBody.prettyPrint();
    }

    @Test
    public void createAFakeUserUsingBuilderTest() {

        RestAssured.baseURI = "https://fakestoreapi.com";


        System.out.println("<<<<<<<<<<<<<<<<<<<<<Test Started>>>>>>>>>>>>>>>>>>>>>");
        FakeUser.Address.GeoLocation geolocation = new FakeUser.Address.GeoLocation.GeoLocationBuilder().lat("1223.32").longitude("32343.54").build();
        FakeUser.Address address = new FakeUser.Address.AddressBuilder().city("Pune").street("Grant Road").number(23223).geoLocation(geolocation).zipcode("411014").build();
        FakeUser.Name name = new FakeUser.Name.NameBuilder().firstname("Manshi").lastname("Sharma").build();
        FakeUser fakeUser = new FakeUser.FakeUserBuilder().email("manshi123@gmail.com").phone("+91-8763625212").password("Manshi@123").username("manshi123").name(name).address(address).build();
        Response responseBody = given().log().all().contentType(ContentType.JSON).body(fakeUser).when().post("/users");

        System.out.println("<<<<<<<<<<<<Test Ended and User is below>>>>>>>>>>>>");
        responseBody.prettyPrint();
    }

}
