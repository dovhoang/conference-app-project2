package controller;

import DTO.ConferenceDetailDTO;
import DTO.MyConferencesDTO;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import pojo.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    StackPane stackPane;

    Text helloUser;

    Label titleName;

    List<Button> btnMenuList = new ArrayList<>();

    ConferenceDetailDTO cfr;

    User user;

    protected static final String RegexEmail = "^[\\w]+[\\w_.]{5,31}+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,}+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,}\\.[\\w]{2,})$";

    public void getRoot(StackPane stackPane, Text helloUser, Label titleName, List<Button> btnMenuList){
        this.stackPane = stackPane;
        this.helloUser = helloUser;
        this.btnMenuList = btnMenuList;
        this.titleName = titleName;
    }

    public void getRoot(StackPane stackPane,ConferenceDetailDTO cfr,Label titleName){
        this.stackPane = stackPane;
        this.cfr = cfr;
        this.titleName = titleName;
    }
    public void getRoot(StackPane stackPane, Label titleName){
        this.stackPane = stackPane;
        this.titleName = titleName;
    }

    public void getRoot(StackPane stackPane){
        this.stackPane = stackPane;
    }

    public void loadView(){

    }
    public void loadView(ConferenceDetailDTO cfr){

    }
    public void loadView(User user){

    }

}
