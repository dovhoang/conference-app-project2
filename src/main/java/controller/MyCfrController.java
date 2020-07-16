package controller;

import DAO.ConferenceDAO;
import DTO.MyConferencesDTO;
import global.UserSession;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pojo.User;

import java.net.URL;
import java.util.ResourceBundle;

public class MyCfrController implements Initializable {

    @FXML
    private TableView<MyConferencesDTO> myCfr_table = new TableView<>();

    @FXML
    private TableColumn<MyConferencesDTO, Integer> myCfr_id = new TableColumn<>();

    @FXML
    private TableColumn<MyConferencesDTO, String> myCfr_name = new TableColumn<>();

    @FXML
    private TableColumn<MyConferencesDTO, String> myCfr_time = new TableColumn<>();

    @FXML
    private TableColumn<MyConferencesDTO, String> myCfr_address = new TableColumn<>();

    @FXML
    private TableColumn<MyConferencesDTO, String> myCfr_status = new TableColumn<>();


    void myCfrView() {
        myCfr_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        myCfr_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        myCfr_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        myCfr_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        myCfr_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        myCfr_table.setItems(ConferenceDAO.getMyConferencesByUser(UserSession.getInstance().getUser()));

        //Set cell listener to show Cfr detail
        myCfr_name.setCellFactory(tc -> {
            TableCell<MyConferencesDTO, String> cell = new TableCell<MyConferencesDTO, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                }
            };
            cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    MyConferencesDTO myConference = (MyConferencesDTO) cell.getTableRow().getItem();
                    //CfrDetailView(ConferenceDAO.getConferenceDetailById(myConference.getId()));
                }
            });
            return cell;
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myCfrView();
    }
}
