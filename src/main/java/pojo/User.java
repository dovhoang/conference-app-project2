package pojo;

import DTO.UserDTO;
import utils.Utils;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private UserType type;
    private boolean active;

    public User(){};

    public User(int id, String name, String username, String password, String email, UserType type, boolean active) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserDTO getUserDetail(){
        return new UserDTO(id,name,username,email,(active? Utils.convertUTF8IntoString("Đã kích hoạt"):
                Utils.convertUTF8IntoString("Đã chặn")));
    }
}
