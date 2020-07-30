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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import pojo.User;
import pojo.UserType;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SignUpController extends Controller{

    @FXML
    private Pane paneFormSignUp;

    @FXML
    private TextField signUpUsername;

    @FXML
    private TextField signUpName;

    @FXML
    private Button btnSignUpForm;

    @FXML
    private TextField signUpEmail;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private PasswordField signUpPasswordRepeat;

    @FXML
    private Pane paneSignUpError;

    @FXML
    private Text signUpError;

    public void loadView() {
//        signUpError.setVisible(false);
//        signUpError.setManaged(false);
        signUpName.clear();
        signUpUsername.clear();
        signUpEmail.clear();
        signUpPassword.clear();
        signUpPasswordRepeat.clear();
        signUpPasswordRepeat.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                signUp();
            }
        });
        btnSignUpForm.setOnAction(event -> {
            signUp();
        });
    }

    public void signUp(){
        int id = UserDAO.getMaxIUser();
        String name = signUpName.getText();
        String username = signUpUsername.getText();
        String email = signUpEmail.getText();
        String password = signUpPassword.getText();
        if (checkErrorFormSignUp()) {
            password = Utils.hashPassword(password);
            User user = new User(id, name, username, password, email,
                    new UserType(1, "User"), true);
            if (!UserDAO.checkExistsUsernameAndEmail(user)) {
                try {
                    addScreen("/scene/cfr_list.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UserSession.getInstace(user);
                UserDAO.insertUser(user);
                Utils.loggedMenuUser(btnMenuList);
                titleName.setText(Utils.convertUTF8IntoString("Quản lý hội nghị"));
                helloUser.setText(Utils.convertUTF8IntoString("Chào, ")+ user.getName());
                Utils.getButtonById("btnList",btnMenuList).requestFocus();
            } else {
                signUpError.setText("Tên đăng nhập hoặc email đã trùng");
                paneSignUpError.setVisible(true);
                paneSignUpError.setManaged(true);
            }
        } else {
            paneSignUpError.setVisible(true);
            paneSignUpError.setManaged(true);
        }
    }

    public void addScreen(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stackPane.getChildren().add(loader.load());
        Controller controller = loader.getController();
        controller.loadView();
    }

    boolean checkErrorFormSignUp() {
        boolean flag = true;
        String error = Utils.convertUTF8IntoString("Lỗi:");
        String username = signUpUsername.getText();
        String name = signUpName.getText();
        String email = signUpEmail.getText();
        String pass = signUpPassword.getText();
        String rPass = signUpPasswordRepeat.getText();
        if (name.equals("") || email.equals("") || username.equals("")
                || pass.equals("") || rPass.equals("")) {
            error = error.concat(Utils.convertUTF8IntoString("\nCác trường không được để trống"));
            signUpError.setText(error);
            return false;
        }
        if (!username.matches("^([A-Za-z])([A-Za-z0-9_.]){7,31}$")) {
            flag = false;
            error = error.concat(Utils.convertUTF8IntoString("\nTên đăng nhập từ 8 -32 kí tự có thể chứa chữ cái, số, \"_\" , \".\"  và bắt đầu từ chữ cái"));
        }
        if (!email.matches(RegexEmail)) {
            flag = false;
            error = error.concat(Utils.convertUTF8IntoString("\nEmail không đúng định dạng"));
        }
        if (!pass.matches("[\\w]{6,16}")) {
            flag = false;
            error = error.concat(Utils.convertUTF8IntoString("\nMật khẩu từ 6-16 kí tự gồm chữ cái và số"));// password error

        }
        if (!pass.equals(rPass)) {
            flag = false;
            error = error.concat(Utils.convertUTF8IntoString("\nNhập lại khẩu không trùng khớp"));// password error// password repeat error
        }
        signUpError.setText(error);
        return flag;
    }
}
