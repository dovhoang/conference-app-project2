package controller;

import DAO.ConferenceDAO;
import DTO.ConferenceDetailDTO;
import DTO.MyConferencesDTO;
import global.UserSession;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pojo.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyCfrController extends Controller{

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


    public void loadView() {
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
                    MyConferencesDTO myConference = cell.getTableRow().getItem();
                    try {
                        addScreen("/scene/cfr_detail.fxml",myConference);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return cell;
        });
    }

    public void addScreen(String path, MyConferencesDTO myCfr) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stackPane.getChildren().add(loader.load());
        Controller controller = loader.getController();
        controller.getRoot(stackPane,myCfr);
        controller.loadView();
    }

}
