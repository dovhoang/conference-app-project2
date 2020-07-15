package DTO;

import DAO.AttendsDAO;
import DAO.ConferenceDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pojo.Attends;
import pojo.Conference;
import pojo.User;

import java.util.ArrayList;
import java.util.List;

public class MyConferencesDTO {
    private int id;
    private String name;
    private String time;
    private String address;
    private String status;

    public MyConferencesDTO(){};

    public MyConferencesDTO(int id, String name, String time, String address, String status) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.address = address;
        this.status = status;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
