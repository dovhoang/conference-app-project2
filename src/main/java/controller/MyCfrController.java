package controller;

import DAO.AttendsDAO;
import DAO.ConferenceDAO;
import DTO.ConferenceDetailDTO;
import DTO.MyConferencesDTO;
import global.UserSession;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import pojo.User;
import utils.Utils;

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

    @FXML
    private Button btnSearch;
    @FXML
    private TextField searchField;

    @FXML
    private Text numSearchResult;

    public void loadView() {
        myCfr_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        myCfr_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        myCfr_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        myCfr_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        myCfr_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        myCfr_table.setItems(ConferenceDAO.getMyConferencesByUser());

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
                    ConferenceDetailDTO conferenceDetailDTO =
                            ConferenceDAO.getConferenceDetailById(myConference.getConferenceId());
                    try {
                        addScreen("/scene/cfr_detail.fxml",conferenceDetailDTO);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    titleName.setText(conferenceDetailDTO.getName());
                    if (conferenceDetailDTO.getName().length() > 50) {
                        titleName.setFont(Font.font("verdana", FontWeight.BOLD, 28.0 / conferenceDetailDTO.getName().length() * 50));
                    }
                }
            });
            return cell;
        });

        numSearchResult.setVisible(false);
        numSearchResult.setManaged(false);
        btnSearch.setOnAction(event -> {
            searchMyConference();
        });

        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                searchMyConference();
            }
        });
    }


    void searchMyConference(){
        numSearchResult.setVisible(true);
        numSearchResult.setManaged(true);
        ObservableList<MyConferencesDTO> listSearch = ConferenceDAO.searchMyConference(searchField.getText());
        numSearchResult.setText(Utils.convertUTF8IntoString("Tìm thấy "+ listSearch.size()+ " kết quả"));
        myCfr_table.setItems(listSearch);
    }

    public void addScreen(String path, ConferenceDetailDTO cfrDetail) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stackPane.getChildren().add(loader.load());
        Controller controller = loader.getController();
        controller.getRoot(stackPane,cfrDetail,titleName);
        controller.loadView(cfrDetail);
    }

}
