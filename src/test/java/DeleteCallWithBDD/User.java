package DeleteCallWithBDD;

/**
 * The UserPOJO class is designed to demonstrate and test the usage of
 * Plain Old Java Objects (POJOs) as data holders.
 * <p>
 * POJOs are simple Java objects that are used primarily for storing data without including
 * any business logic or complex functionality. Key characteristics of POJOs include:
 * <p>
 * - They may contain only fields (data members) and accessor methods (getter/setter).
 * - Fields can be of primitive or reference data types.
 * - Fields can be private, public, static, final, transient, or volatile.
 * - POJOs do not depend on any specific framework or library.
 * <p>
 * This class is intended for testing purposes to showcase the implementation and behavior
 * of POJO classes.
 */
public class User {

    private String name;
    private String gender;
    private String status;
    private String email;

    public User(String name, String gender, String status, String email) {
        this.name = name;
        this.gender = gender;
        this.status = status;
        this.email = email;
    }

    public User() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //We are overriding toString() from the Object class to utilize toString()
    //as per our needs in the test cases or to trace in the logs.
    @Override
    public String toString() {
        return "UserPOJO{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}