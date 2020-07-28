package controller;

import global.UserSession;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {

    @FXML
    private Button btnProfile;

    @FXML
    private Button btnList;

    @FXML
    private Button btnSetting;

    @FXML
    private Button btnMyCfr;

    @FXML
    private Button btnSignOut;

    @FXML
    private Button btnSignIn;

    @FXML
    private Label titleName;

    @FXML
    private Label subTitleName;

    @FXML
    private AnchorPane window;

    @FXML
    private Pane paneTitle;

    @FXML
    private Text helloUser;

    @FXML
    private Button btnAddCfr;

    @FXML
    private Button btnUserManager;

    @FXML
    private Button btnAppvoral;

    @FXML
    private StackPane stackPane;

    List<Button> listButtonMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            addScreen("/scene/cfr_list.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        buttonMenuInit();
        notLoggedMenu();
    }


    public void addScreen(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stackPane.getChildren().add(loader.load());
        Controller controller = loader.getController();
        controller.getRoot(stackPane, helloUser, titleName, listButtonMenu);
        controller.loadView();
        titleName.setFont(Font.font("verdana",FontWeight.BOLD,28));
    }


    //Click button event
    public void buttonMenuInit() {
        btnList.setOnAction(even -> {
            titleName.setText(Utils.convertUTF8IntoString("DANH SÁCH HỘI NGHỊ"));
            try {
                addScreen("/scene/cfr_list.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        btnProfile.setOnAction(even -> {
            titleName.setText(Utils.convertUTF8IntoString("HỒ SƠ"));
            try {
                addScreen("/scene/user_profile.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        btnMyCfr.setOnAction(even -> {
            titleName.setText(Utils.convertUTF8IntoString("HỘI NGHỊ CỦA TÔI"));
            try {
                addScreen("/scene/my_cfr.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        btnSignIn.setOnAction(even -> {
            titleName.setText(Utils.convertUTF8IntoString("ĐĂNG NHẬP"));
            try {
                addScreen("/scene/sign_in.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        btnAddCfr.setOnAction(even -> {
            titleName.setText(Utils.convertUTF8IntoString("THÊM HỘI NGHỊ"));
            try {
                addScreen("/scene/add_cfr.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnAppvoral.setOnAction(even -> {
            titleName.setText(Utils.convertUTF8IntoString("DUYỆT HỘI NGHỊ"));
            try {
                addScreen("/scene/approval_attends.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnUserManager.setOnAction(even -> {
            titleName.setText(Utils.convertUTF8IntoString("QUẢN LÍ NGƯỜI DÙNG"));
            try {
                addScreen("/scene/user_manager.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }


        });

        btnSignOut.setOnAction(even -> {
            UserSession.cleanUserSession();
            notLoggedMenu();
            try {
                addScreen("/scene/cfr_list.fxml");
                titleName.setText(Utils.convertUTF8IntoString("DANH SÁCH HỘI NGHỊ"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        listButtonMenu = Arrays.asList(btnList, btnProfile, btnMyCfr, btnAddCfr, btnUserManager,
                btnAddCfr, btnAppvoral, btnSignIn, btnSignOut);
    }

    //not logged menu view
    void notLoggedMenu() {

        btnProfile.setVisible(false);
        btnProfile.setManaged(false);

        btnMyCfr.setVisible(false);
        btnMyCfr.setManaged(false);

        btnSignOut.setVisible(false);
        btnSignOut.setManaged(false);

        btnAddCfr.setVisible(false);
        btnAddCfr.setManaged(false);

        btnUserManager.setVisible(false);
        btnUserManager.setManaged(false);

        btnSignIn.setVisible(true);
        btnSignIn.setManaged(true);

        btnAppvoral.setVisible(false);
        btnAppvoral.setManaged(false);

        helloUser.setText("");

        btnList.requestFocus();
    }
}