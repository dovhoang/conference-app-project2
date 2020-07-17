package controller;

import DAO.ConferenceDAO;
import DTO.ConferenceDetailDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utils.Utils.getTableCellCustom;

public class CfrListController extends Controller{

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

    @Override
    public void loadView() {
        //Define comumn of table
        table_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        table_place.setCellValueFactory(new PropertyValueFactory<>("place"));
        table_info.setCellValueFactory(new PropertyValueFactory<>("generalDesc"));

        //Set cell listener to show Cfr detail
        table_name.setCellFactory(tc -> {
            TableCell<ConferenceDetailDTO, String> cell = getTableCellCustom();
            cell.setOnMouseClicked(event -> {
                ConferenceDetailDTO conference = cell.getTableRow().getItem();
                titleName.setText(conference.getName());
                try {
                    addScreen("/scene/cfr_detail.fxml", conference);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (conference.getName().length() > 50) {
                    titleName.setFont(Font.font("verdana", FontWeight.BOLD, 28.0 / conference.getName().length() * 50));
                }
            });
            return cell;
        });
        table_place.setCellFactory(tc -> getTableCellCustom());
        table_info.setCellFactory(tc -> getTableCellCustom());

        table.setItems(ConferenceDAO.getConferencesDetail());
    }

    public void addScreen(String path, ConferenceDetailDTO cfr) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stackPane.getChildren().add(loader.load());
        CfrDetailController controller = loader.getController();
        controller.getRoot(stackPane, cfr);
        controller.loadView(cfr);
    }
}
