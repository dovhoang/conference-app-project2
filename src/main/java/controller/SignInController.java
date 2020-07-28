package controller;

import DAO.UserDAO;
import global.UserSession;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import pojo.User;
import utils.Utils;

import java.io.IOException;

public class SignInController extends Controller{

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

    public void addScreen(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stackPane.getChildren().add(loader.load());
        Controller controller = loader.getController();
        controller.getRoot(stackPane,helloUser,titleName, btnMenuList);
        controller.loadView();
    }

    @Override
    public void loadView() {
        signInUsername.clear();
        signInPassword.clear();
        signInPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                signIn();
            }
        });

        btnSignInForm.setOnAction(event -> {
            signIn();
        });

        btnSignUp.setOnAction(event -> {
            titleName.setText(Utils.convertUTF8IntoString("ĐĂNG KÝ"));
            try {
                addScreen("/scene/sign_up.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    void signIn(){
        String username = signInUsername.getText();
        String password =signInPassword.getText();
        String hash =  Utils.hashPassword(password);
        User user = UserDAO.getUserByUsername(username);
        if (user != null && Utils.checkPassword(password,hash)) {
            if (user.isActive()) {
                UserSession.getInstace(user);
                //
                try {
                    addScreen("/scene/cfr_list.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (user.getType().getName().equals("Admin")){
                    Utils.loggedMenuAdmin(btnMenuList);
                }else  Utils.loggedMenuUser(btnMenuList);

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

}
