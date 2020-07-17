package controller;

import global.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import pojo.User;
import utils.Utils;

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
    }
}
