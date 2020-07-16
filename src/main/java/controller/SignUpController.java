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

public class SignUpController implements Initializable {

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

    StackPane stackPane;

    Text helloUser;

    Label titleName;

    List<Button> btnMenuList = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signUpView();
    }

    void signUpView() {
        signUpError.setVisible(false);
        signUpError.setManaged(false);
        signUpName.clear();
        signUpUsername.clear();
        signUpEmail.clear();
        signUpPassword.clear();
        signUpPasswordRepeat.clear();
        btnSignUpForm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id = UserDAO.getMaxIUser();
                String name = signUpName.getText();
                String username = signUpUsername.getText();
                String email = signUpEmail.getText();
                String password = signUpPassword.getText();
                if (checkErrorFormSignUp()) {
                    password = Utils.md5(password);
                    User user = new User(id, name, username, password, email,
                            new UserType(1, "User"), true);
                    if (!UserDAO.checkExistsUsernameAndEmail(user)) {
                        try {
                            stackPane.getChildren().add(FXMLLoader.load(getClass().getResource("/scene/cfr_list.fxml")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        UserSession.getInstace(user);
                        Utils.loggedMenuAdmin(btnMenuList);
                        titleName.setText(Utils.convertUTF8IntoString("Quản lí hội nghị"));
                        helloUser.setText(Utils.convertUTF8IntoString("Chào, ")+ user.getName());
                        Utils.getButtonById("btnList",btnMenuList).requestFocus();
                    } else {
                        signUpError.setText("Tên đăng nhập hoặc email đã trùng");
                        paneSignUpError.setVisible(true);
                    }
                } else {
                    paneSignUpError.setVisible(true);
                }
            }
        });
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
        if (!email.matches("^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$")) {
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

    public void getRoot(StackPane sp, Text helloUser, Label titleName, List<Button> btnMenuList){
        stackPane = sp;
        this.helloUser = helloUser;
        this.btnMenuList = btnMenuList;
        this.titleName = titleName;
    }


}
