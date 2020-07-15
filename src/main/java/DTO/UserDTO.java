package DTO;

public class UserDTO {
    private int id;
    private String name;
    private String username;
    private String email;
    private String active;

    public UserDTO(){};

    public UserDTO(int id, String name, String username, String email, String active) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
