package controller;

import DAO.ConferenceDAO;
import DAO.PlaceDAO;
import DAO.RoomDAO;
import DTO.ConferenceDetailDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import pojo.Conference;
import pojo.Place;
import pojo.Room;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static controller.AddCfrController.fileSaved;

public class UpdateCfrController extends Controller {
    @FXML
    private GridPane panUpdateCfr;

    @FXML
    private TextArea updateCfr_name;

    @FXML
    private DatePicker updateCfr_date;

    @FXML
    private ComboBox<LocalTime> updateCfr_time;

    @FXML
    private ComboBox<Place> updateCfr_place;

    @FXML
    private ComboBox<Room> updateCfr_room;

    @FXML
    private TextField updateCfr_numberAttendees;

    @FXML
    private Button updateCfr_picture;

    @FXML
    private TextArea updateCfr_generalDesc;

    @FXML
    private Text numPicture;

    @FXML
    private TextArea updateCfr_detailDesc;

    @FXML
    private Button btnUpdateCfrForm;

    List<File> fileList = new ArrayList<>();

    LocalDate date;
    LocalTime time;
    Room room;

    ConferenceDetailDTO cfrDetailDto ;

    @Override
    public void loadView(ConferenceDetailDTO cfr) {
        Conference conference = ConferenceDAO.getConferenceById(cfr.getId());
        updateCfr_name.setText(conference.getName());
        updateCfr_numberAttendees.setText(Integer.toString(conference.getMaxNumberAttendees()));
        updateCfr_generalDesc.setText(conference.getGeneralDesc());
        updateCfr_detailDesc.setText(conference.getDetailDesc());
        String spath = "src/main/resources/picture/" + conference.getId() + "/";
        File file = new File(spath);
        numPicture.setText(Utils.convertUTF8IntoString("Đã chọn: " +
                file.listFiles().length) + " file");
        int id = conference.getId();
        //get date
        updateCfr_date.setValue(conference.getTime().toLocalDateTime().toLocalDate());
        date = updateCfr_date.getValue();
        updateCfr_date.setOnAction(new EventHandler() {
            public void handle(Event t) {
                date = updateCfr_date.getValue();
            }
        });
        //get time
        ObservableList<LocalTime> timeOptions = FXCollections.observableArrayList();
        int i = 14;
        while (i / 2 < 21) {
            timeOptions.add(LocalTime.of(i / 2, i % 2 * 30));
            i++;
        }
        updateCfr_time.setItems(timeOptions);
        updateCfr_time.setConverter(new StringConverter<LocalTime>() {
            @Override
            public String toString(LocalTime object) {
                return object.toString();
            }

            @Override
            public LocalTime fromString(String string) {
                return updateCfr_time.getItems().stream().filter(ap ->
                        ap.toString().equals(string)).findFirst().orElse(null);
            }
        });
        updateCfr_time.valueProperty().addListener((observable, oldValue, newValue) -> time = newValue);
        updateCfr_time.setValue(conference.getTime().toLocalDateTime().toLocalTime());


        //get room and place
        ObservableList<Place> placeOptions = PlaceDAO.getPlacesList();
        updateCfr_place.setItems(placeOptions);
        updateCfr_place.setConverter(new StringConverter<Place>() {
            @Override
            public String toString(Place object) {
                return object.getName();
            }

            @Override
            public Place fromString(String string) {
                return updateCfr_place.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });

        updateCfr_place.valueProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Room> roomOptions = RoomDAO.getRoomsListByPlace(newValue);
            updateCfr_room.setItems(roomOptions);

            updateCfr_room.setConverter(new StringConverter<Room>() {
                @Override
                public String toString(Room object) {
                    return object.getName() + " - " + object.getCapacity();
                }

                @Override
                public Room fromString(String string) {
                    return updateCfr_room.getItems().stream().filter(ap ->
                            ap.getName().equals(string)).findFirst().orElse(null);
                }
            });
            updateCfr_room.valueProperty().addListener((observable1, oldValue1, newValue1) -> room= newValue1);
            updateCfr_room.setValue(conference.getRoom());
        });
        updateCfr_place.setValue(conference.getRoom().getPlace());
        chooseCfrPicture();

        // submit form
        btnUpdateCfrForm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String error = Utils.convertUTF8IntoString("Lỗi:");
                boolean flag = true;
                int numberAttendees = 0;
                Timestamp datetime = null;
                String name = updateCfr_name.getText();
                titleName.setText(name);
                String generalDesc = updateCfr_generalDesc.getText();
                String detailDesc = updateCfr_detailDesc.getText();
                System.out.println(date);
                System.out.println(time);
                datetime = Timestamp.valueOf(LocalDateTime.of(date, time));
                try {
                    numberAttendees = Integer.parseInt(updateCfr_numberAttendees.getText());
                    if (numberAttendees < 10) {
                        error = error.concat(Utils.convertUTF8IntoString("\nSố người tham gia tối tiêu là 10"));
                        flag = false;
                    }
                    if (numberAttendees > room.getCapacity()) {
                        error = error.concat(Utils.convertUTF8IntoString("\nSố người tham gia tối đa là ")
                                + room.getCapacity());
                        flag = false;
                    }
                } catch (NumberFormatException e) {
                    error = error.concat(Utils.convertUTF8IntoString("\nSố người tham dự nhập sai định dạng"));
                    flag = false;
                }
                if (name.equals("") || generalDesc.equals("") ||
                        detailDesc.equals("")) {
                    error = error.concat(Utils.convertUTF8IntoString("\nCác trường không được để trống"));
                    flag = false;
                }

                //updateCfr_error.setText(error);
                if (flag) {
                    Conference cfr = new Conference(id, name, room, generalDesc, detailDesc, datetime, numberAttendees);
                    cfrDetailDto = cfr.getConferenceDetail();
                    ConferenceDAO.updateConference(cfr);
                    try {
                        fileSaved(fileList, id);
                        addScreen("/scene/cfr_detail.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {//paneUpdateCfrError.setVisible(true);
                }
            }
        });
    }

    public void chooseCfrPicture() {
        final boolean t = true;
        updateCfr_picture.setOnAction(event -> {
            FileChooser file = new FileChooser();
            List<String> lstFile = new ArrayList<>();
            lstFile.add("*.png");
            lstFile.add("*.jpg");
            file.getExtensionFilters().add(new FileChooser.ExtensionFilter("picture file", lstFile));
            List<File> fl = file.showOpenMultipleDialog(null);
            while (fl.size() > 5) {
                fl.remove(fl.size() - 1);
            }
            numPicture.setText(Utils.convertUTF8IntoString("Đã chọn: ")
                    + fl.size() + " file");
            fileList = fl;
        });
    }

    public void addScreen(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stackPane.getChildren().add(loader.load());
        Controller controller = loader.getController();
        cfr = cfrDetailDto;
        controller.getRoot(stackPane, cfr,titleName);
        controller.loadView(cfr);
    }
}
