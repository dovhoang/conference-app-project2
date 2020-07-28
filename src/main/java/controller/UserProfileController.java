package controller;

import global.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import pojo.User;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserProfileController extends Controller {

    @FXML
    private GridPane paneProfile;

    @FXML
    private Text user_id;

    @FXML
    private Text user_username;

    @FXML
    private Text user_email;

    @FXML
    private Text user_type;

    @FXML
    private Text user_active;

    @FXML
    private Label user_name;

    @FXML
    private Button btnEditProfile;

    @FXML
    private Button btnChangePassword;


    @Override
    public void loadView() {
        User cur = UserSession.getInstance().getUser();
        user_id.setText(Integer.toString(cur.getId()));
        user_name.setText(cur.getName());
        user_username.setText(cur.getUsername());
        user_email.setText(cur.getEmail());
        user_type.setText(cur.getType().getName());
        user_active.setText(cur.isActive() ? Utils.convertUTF8IntoString("Đang hoạt động")
                : Utils.convertUTF8IntoString("Đã bị chặn"));
        btnEditProfile.setOnAction(event ->{
            try {
                addScreen("/scene/edit_profile.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnChangePassword.setOnAction(event -> {
            try {
                addScreen("/scene/change_password.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void addScreen(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stackPane.getChildren().add(loader.load());
        Controller controller = loader.getController();
        controller.getRoot(stackPane,titleName);
        controller.loadView();
    }
}
