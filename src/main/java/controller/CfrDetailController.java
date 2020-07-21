package controller;

import DAO.AttendsDAO;
import DAO.ConferenceDAO;
import DAO.UserDAO;
import DTO.ConferenceDetailDTO;
import DTO.UserDTO;
import DTO.UserInfoInConferenceDTO;
import global.UserSession;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import pojo.Conference;
import pojo.User;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CfrDetailController extends Controller {

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

    @FXML
    private TableView<UserInfoInConferenceDTO> table_attends = new TableView<>();

    @FXML
    private TableColumn<UserInfoInConferenceDTO, Integer> table_id = new TableColumn<>();

    @FXML
    private TableColumn<UserInfoInConferenceDTO, String> table_username = new TableColumn<>();

    @FXML
    private TableColumn<UserInfoInConferenceDTO, String> table_name = new TableColumn<>();


    @FXML
    public void loadView(ConferenceDetailDTO cfr) {
        Conference conference = ConferenceDAO.getConferenceById(cfr.getId());
        long attendees = AttendsDAO.getNumberAttendeesForConference(conference);
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
            if(checkAttends==0){
                btnRegisterCfr.setText("Đang chờ duyệt");
            }else if (checkAttends == 1) {
                btnRegisterCfr.setText("Đã duyệt");
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
                    showAlertConfirmConference(conference, btnRegisterCfr.getText().equals("Đăng ký"));
                } else {
                    try {
                        showAlertWarnSignIn();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnEditCfr.setVisible(false);
        btnEditCfr.setManaged(false);
        if (UserSession.getInstance() != null) {
            if (UserSession.getInstance().getUser().getType().getName().equals("Admin")) {
                btnEditCfr.setVisible(true);
                btnEditCfr.setManaged(true);
            }
        }
        btnEditCfr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    addScreen("/scene/update_cfr.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        table_id.setCellFactory(indexCellFactory());
        table_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        table_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_attends.setItems(AttendsDAO.getAttendsByConference(conference));
    }

    public static <T> Callback<TableColumn<UserInfoInConferenceDTO, Integer>, TableCell<UserInfoInConferenceDTO, Integer>> indexCellFactory() {
        return t -> new TableCell<>() {

            @Override
            public void updateIndex(int i) {
                super.updateIndex(i);
                setText(isEmpty() ? "" : Integer.toString(i));
            }

        };
    }

    public void addScreen(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stackPane.getChildren().add(loader.load());
        Controller controller = loader.getController();
        controller.getRoot(stackPane, cfr,titleName);
        controller.loadView(cfr);
    }

    public void addScreenSignIn(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stackPane.getChildren().add(loader.load());
        Controller controller = loader.getController();
        controller.getRoot(stackPane,titleName);
        controller.loadView();
    }

    private void showAlertConfirmConference(Conference conference, boolean register) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (register){
            alert.setTitle("Đăng kí tham gia hội nghị");
            alert.setHeaderText("Xác nhận tham gia hội nghị: " + conference.getName());
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                User user = UserSession.getInstance().getUser();
                AttendsDAO.insertAttends(user, conference);
                btnRegisterCfr.setText(Utils.convertUTF8IntoString("Đang chờ duyệt"));
            }
        }else{
            alert.setTitle("Hủy tham gia hội nghị");
            alert.setHeaderText("Xác hủy tham gia hội nghị: " + conference.getName());
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                User user = UserSession.getInstance().getUser();
                AttendsDAO.deleteAttends(user, conference);
                btnRegisterCfr.setText(Utils.convertUTF8IntoString("Đăng ký"));
            }
        }

    }

    private void showAlertWarnSignIn() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng kí tham gia hội nghị");
        alert.setHeaderText("Bạn phải đăng nhập để thực hiện");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK){
            addScreenSignIn("/scene/sign_in.fxml");
            titleName.setText(Utils.convertUTF8IntoString("ĐĂNG NHẬP"));
        }
    }

}
