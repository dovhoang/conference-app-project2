package controller;

import DAO.UserDAO;
import global.UserSession;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import pojo.User;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    @FXML
    private AnchorPane paneSignIn;

    @FXML
    private Pane paneWelcome;

    @FXML
    private Button btnSignUp;

    @FXML
    private Pane paneFormSignIn;

    @FXML
    private TextField signInUsername;

    @FXML
    private PasswordField signInPassword;

    @FXML
    private Button btnSignInForm;

    @FXML
    private Text signInError;

    StackPane stackPane;

    Text helloUser;

    Label titleName;

    List<Button> btnMenuList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signInView();
    }

    void signInView() {
        //titleName.setText(Utils.convertUTF8IntoString("ĐĂNG NHẬP"));
        signInUsername.clear();
        signInPassword.clear();
        btnSignInForm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = signInUsername.getText();
                String passwrod = Utils.md5(signInPassword.getText());
                User user = UserDAO.getUser(username, passwrod);
                if (user != null) {
                    if (user.isActive()) {
                        UserSession.getInstace(user);
                        //
                        try {
                            stackPane.getChildren().add(FXMLLoader.load(getClass().getResource("/scene/cfr_list.fxml")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        UserSession.getInstace(user);
                        Utils.loggedMenuAdmin(btnMenuList);
                        helloUser.setText(Utils.convertUTF8IntoString("Chào, ")+ user.getName());
                        titleName.setText(Utils.convertUTF8IntoString("DANH SÁCH HỘI NGHỊ"));
                        Utils.getButtonById("btnList",btnMenuList).requestFocus();
                    } else {
                        signInError.setVisible(true);
                        signInError.setText(Utils.convertUTF8IntoString("Tài khoản của bạn đã bị chặn"));
                    }
                } else {
                    signInError.setVisible(true);
                    signInError.setText(Utils.convertUTF8IntoString("Tên tài khoản hoặc mật khẩu không đúng"));

                }
            }
        });

        btnSignUp.setOnAction(event -> {
            try {
                addScreen("/scene/sign_up.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public void addScreen(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stackPane.getChildren().add(loader.load());
        SignUpController controller = loader.getController();
        controller.getRoot(stackPane,helloUser,titleName, btnMenuList);
    }

    public void getRoot(StackPane sp, Text helloUser,Label titleName, List<Button> btnMenuList){
        stackPane = sp;
        this.helloUser = helloUser;
        this.btnMenuList = btnMenuList;
        this.titleName = titleName;
    }
}