package GetCallWithDeserialization;

public class User {

    private Integer id;
    private String name;
    private String gender;
    private String status;
    private String email;

    public User() {
    }

    public User(Integer id, String name, String gender, String status, String email) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.status = status;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", gender='" + gender + '\'' + ", status='" + status + '\'' + ", email='" + email + '\'' + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}