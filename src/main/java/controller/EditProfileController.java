package controller;

import DAO.UserDAO;
import global.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import pojo.User;
import utils.Utils;

import java.io.IOException;


public class EditProfileController extends Controller {
    @FXML
    private TextField editProfileEmail;

    @FXML
    private TextField editProfileName;

    @FXML
    private Button btnUpdate;

    @FXML
    private Pane paneEditProfileError;

    @FXML
    private Text editProfileError;

    String error= "";

    @Override
    public void loadView() {
        User user = UserSession.getInstance().getUser();
        editProfileName.setText(user.getName());
        editProfileEmail.setText(user.getEmail());
        paneEditProfileError.setVisible(false);
        paneEditProfileError.setManaged(false);
        btnUpdate.setOnAction(event -> {
            if (checkFormEditProfile()) {
                user.setName(editProfileName.getText());
                user.setEmail(editProfileEmail.getText());
                UserDAO.updateProfile(user);
                try {
                    addScreen("/scene/user_profile.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
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

    public boolean checkFormEditProfile() {
        boolean flag = true;
        if (editProfileName.getText().equals("")) {
            flag = false;
            error = error.concat(Utils.convertUTF8IntoString("\nTên không được để trống"));
        }
        if (!editProfileEmail.getText().matches(RegexEmail)) {
            flag = false;
            error = error.concat(Utils.convertUTF8IntoString("\nEmail không đúng định dạng"));
        }
        return flag;
    }
}

