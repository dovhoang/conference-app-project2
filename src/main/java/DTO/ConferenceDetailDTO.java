package DTO;

import DAO.ConferenceDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pojo.Conference;

import java.util.ArrayList;
import java.util.List;

public class ConferenceDetailDTO {
    private int id;
    private String name;
    private String place;
    private String address;
    private String generalDesc;
    private String detailDesc;
    private String time;
    private int maxNumberAttendees;

    public ConferenceDetailDTO(){};

    public ConferenceDetailDTO(int id, String name, String place, String address,
                               String generalDesc, String detailDesc,
                               String time, int maxNumberAttendees) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.address = address;
        this.generalDesc = generalDesc;
        this.detailDesc = detailDesc;
        this.time = time;
        this.maxNumberAttendees = maxNumberAttendees;
    }

    public int getId() {
        return id;
    }

    public String getPlace() {
        return place;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getGeneralDesc() {
        return generalDesc;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public String getTime() {
        return time;
    }

    public int getMaxNumberAttendees() {
        return maxNumberAttendees;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGeneralDesc(String generalDesc) {
        this.generalDesc = generalDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setMaxNumberAttendees(int maxNumberAttendees) {
        this.maxNumberAttendees = maxNumberAttendees;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}
