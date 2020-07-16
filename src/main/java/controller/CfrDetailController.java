package controller;

import DAO.AttendsDAO;
import DAO.ConferenceDAO;
import DTO.ConferenceDetailDTO;
import global.UserSession;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import pojo.Conference;
import pojo.User;
import utils.Utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CfrDetailController implements Initializable {

    @FXML
    private GridPane paneCfr;

    @FXML
    private Text cfr_time;

    @FXML
    private Text cfr_place;

    @FXML
    private Text cfr_address;

    @FXML
    private Text cfr_numAttendees;

    @FXML
    private ProgressBar cfr_pgNumAttendees;

    @FXML
    private Button btnRegisterCfr;

    @FXML
    private Button btnEditCfr;

    @FXML
    private Text cfr_general;

    @FXML
    private Text cfr_detail;

    @FXML
    private VBox cfr_picture;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //CfrDetailView();
    }
    void CfrDetailView(ConferenceDetailDTO cfr) {
        Conference conference = ConferenceDAO.getConferenceById(cfr.getId());
        long attendees = AttendsDAO.getNumberAttendeesForConference(conference);
        //cfr_id.setText(Utils.convertUTF8IntoString("Danh sách hội nghị / ") + Integer.toString(cfr.getId()));
        if (cfr.getName().length() > 50)
            //title_name.setFont(Font.font("verdana", FontWeight.BOLD, 28.0 / cfr.getName().length() * 50));

        cfr_general.setText(cfr.getGeneralDesc());
        cfr_detail.setText(cfr.getDetailDesc());
        cfr_time.setText(cfr.getTime());
        cfr_place.setText(cfr.getPlace());
        cfr_address.setText(cfr.getAddress());
        cfr_numAttendees.setText(attendees + "/" + cfr.getMaxNumberAttendees());
        cfr_pgNumAttendees.setProgress(1.0 * attendees / cfr.getMaxNumberAttendees());
        String spath = "src/main/resources/picture/" + cfr.getId() + "/";
        File file = new File(spath);
        cfr_picture.getChildren().clear();
        cfr_picture.setSpacing(10);
        if (file.listFiles().length != 0) {
            for (File f : file.listFiles()) {
                final ImageView selectedImage = new ImageView();
                Image image = null;
                try {
                    image = new Image(String.valueOf(f.toURI().toURL()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                selectedImage.setImage(image);
                cfr_picture.getChildren().add(selectedImage);
            }
        }
        btnRegisterCfr.setText("Đăng kí");
        btnRegisterCfr.setDisable(false);
        if (attendees >= cfr.getMaxNumberAttendees()) {
            btnRegisterCfr.setText("Hết chỗ");
            btnRegisterCfr.setDisable(true);
        }
        int checkAttends;
        if (UserSession.isLogin()) {
            checkAttends = AttendsDAO.checkAttend(UserSession.getInstance().getUser(), conference);
            if (checkAttends == 1) {
                btnRegisterCfr.setText("Đã đăng kí");
                btnRegisterCfr.setDisable(true);
            } else if (checkAttends == 2) {
                btnRegisterCfr.setText("Đã từ chối");
                btnRegisterCfr.setDisable(true);
            }
        }
        btnRegisterCfr.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (UserSession.isLogin()) {
                    showAlertConfirmConference(conference);
                } else {
                    showAlertWarnSignIn();
                }
            }
        });

        btnEditCfr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //updateCfrView(cfr);
            }
        });


    }

    private void showAlertConfirmConference(Conference conference) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng kí tham gia hội nghị");
        alert.setHeaderText("Xác nhận tham gia hội nghị: " + conference.getName());
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            User user = UserSession.getInstance().getUser();
            AttendsDAO.insertAttends(user, conference);
            btnRegisterCfr.setDisable(true);
            btnRegisterCfr.setText(Utils.convertUTF8IntoString("Đã đăng ký"));
        }
    }

    private void showAlertWarnSignIn() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng kí tham gia hội nghị");
        alert.setHeaderText("Bạn phải đăng nhập để thực hiện");
        alert.showAndWait();
    }

}