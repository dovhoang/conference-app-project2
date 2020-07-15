package DTO;

import java.awt.*;

public class ApprovalDTO {
    private int id;
    private String username;
    private String name;
    private String conference;

    public ApprovalDTO(){};

    public ApprovalDTO(int id, String username, String name, String conference) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.conference = conference;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

}
