package controller;

import DTO.ConferenceDetailDTO;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    StackPane stackPane;

    Text helloUser;

    Label titleName;

    List<Button> btnMenuList = new ArrayList<>();

    ConferenceDetailDTO cfr;

    Button pressed;
    public void getRoot(StackPane stackPane, Text helloUser, Label titleName, List<Button> btnMenuList){
        this.stackPane = stackPane;
        this.helloUser = helloUser;
        this.btnMenuList = btnMenuList;
        this.titleName = titleName;
    }

    public void getRoot(StackPane stackPane,ConferenceDetailDTO cfr){
        this.stackPane = stackPane;
        this.cfr = cfr;
    }

    public void getRoot(StackPane stackPane){
        this.stackPane = stackPane;
    }

    public void loadView(){

    }
    public void loadView(ConferenceDetailDTO cfr){

    }
}
