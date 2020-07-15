package controller;

import DAO.ConferenceDAO;
import DTO.ConferenceDetailDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.Utils;

import java.net.URL;
import java.util.ResourceBundle;

import static utils.Utils.getTableCellCustom;

public class CfrListController implements Initializable {

    @FXML
    TableView<ConferenceDetailDTO> table = new TableView<>();
    @FXML
    TableColumn<ConferenceDetailDTO, Integer> table_id = new TableColumn<>("Conference Id");
    @FXML
    TableColumn<ConferenceDetailDTO, String> table_name = new TableColumn<>("Conference Name");
    @FXML
    TableColumn<ConferenceDetailDTO, String> table_time = new TableColumn<>("Conference Time");
    @FXML
    TableColumn<ConferenceDetailDTO, String> table_place = new TableColumn<>("Conference Address");
    @FXML
    TableColumn<ConferenceDetailDTO, String> table_info = new TableColumn<>("Conference Info");


        void listConferenceView() {
        //Define comumn of table
        table_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        table_place.setCellValueFactory(new PropertyValueFactory<>("place"));
        table_info.setCellValueFactory(new PropertyValueFactory<>("generalDesc"));

        //btnList.requestFocus();
        //cfr_id.setText("");
        //title_name.setText(Utils.convertUTF8IntoString("DANH SÁCH HỘI NGHỊ"));

        //Set cell listener to show Cfr detail
        table_name.setCellFactory(tc -> {
            TableCell<ConferenceDetailDTO, String> cell = getTableCellCustom();
            cell.setOnMouseClicked(event -> {
                ConferenceDetailDTO conference = cell.getTableRow().getItem();
                //CfrDetailView(conference);
            });
            return cell;
        });
        table_place.setCellFactory(tc -> getTableCellCustom());
        table_info.setCellFactory(tc -> getTableCellCustom());

        table.setItems(ConferenceDAO.getConferencesDetail());
        //Show left menu:  logged or not logged view
        //showMenu();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listConferenceView();
    }
}
