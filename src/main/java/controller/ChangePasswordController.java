package controller;

import DAO.UserDAO;
import global.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import pojo.User;
import utils.Utils;

import java.io.IOException;

public class ChangePasswordController extends Controller {

    @FXML
    private PasswordField changePasswordOld;

    @FXML
    private PasswordField changePasswordNew;

    @FXML
    private PasswordField changePasswordRepeat;

    @FXML
    private Button btnUpdate;

    @FXML
    private Pane paneEditProfileError;

    @FXML
    private Text editProfileError;

    String error = "";
    String oldPassword = UserSession.getInstance().getUser().getPassword();

    @Override
    public void loadView() {
        User  user = UserSession.getInstance().getUser();
        paneEditProfileError.setVisible(false);
        paneEditProfileError.setManaged(false);
        btnUpdate.setOnAction(event -> {
            if (checkPassword()) {
                user.setPassword(Utils.hashPassword(changePasswordNew.getText()));
                UserDAO.updateProfile(user);
                try {
                    addScreen("/scene/user_profile.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                paneEditProfileError.setVisible(true);
                paneEditProfileError.setManaged(true);
                editProfileError.setText(error);
            }

        });
    }

    public void addScreen(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stackPane.getChildren().add(loader.load());
        Controller controller = loader.getController();
        controller.getRoot(stackPane, titleName);
        controller.loadView();
    }

    public boolean checkPassword() {
        if (!changePasswordNew.getText().matches("[\\w]{6,16}")) {
            error = error.concat(Utils.convertUTF8IntoString("\nMật khẩu từ 6-16 kí tự gồm chữ cái và số"));// password error
            return false;
        } else if (!changePasswordNew.getText().equals(changePasswordRepeat.getText())) {
            error = error.concat(Utils.convertUTF8IntoString("\nMật khẩu từ 6-16 kí tự gồm chữ cái và số"));// password error
            return false;
        } else if (!Utils.checkPassword(changePasswordOld.getText(),oldPassword)) {
            error = error.concat(Utils.convertUTF8IntoString("\nMật khẩu cũ không đúng"));// password error
            return false;
        }
        return true;
    }
}
