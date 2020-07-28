package controller;

import DAO.ConferenceDAO;
import DTO.ApprovalDTO;
import DTO.ConferenceDetailDTO;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import global.UserSession;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import pojo.User;
import utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Optional;
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
    @FXML
    private Button btnSearch;
    @FXML
    private TextField searchField;

    @FXML
    private Text numSearchResult;

    boolean flag = true;

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
                if (conference.getName().length() > 40) {
                    titleName.setFont(Font.font("verdana", FontWeight.BOLD, 24));
                }
            });
            return cell;
        });
        table_place.setCellFactory(tc -> getTableCellCustom());
        table_info.setCellFactory(tc -> getTableCellCustom());
        table.setItems(ConferenceDAO.getConferencesDetail());
        UserSession userSession = UserSession.getInstance();
        if (userSession!=null){
            if (userSession.getUser().getType().getName().equals("Admin")){
                flag = addButtonEditConference();
            }else{
                table.getColumns().remove(getTableColumnByName(table," "));
                flag = true;
            }
        }
        numSearchResult.setVisible(false);
        numSearchResult.setManaged(false);
        btnSearch.setOnAction(event -> {
            searchConference();
        });

        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                searchConference();
            }
        });



    }

    private TableColumn<DTO.ConferenceDetailDTO, ?> getTableColumnByName(TableView<DTO.ConferenceDetailDTO> tableView, String name) {
        for (TableColumn<DTO.ConferenceDetailDTO, ?> col : tableView.getColumns())
            if (col.getText().equals(name)) return col ;
        return null ;
    }

    private boolean addButtonEditConference() {
        TableColumn<ConferenceDetailDTO, Void> colBtn = new TableColumn(" ");

        Callback<TableColumn<ConferenceDetailDTO, Void>, TableCell<ConferenceDetailDTO, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ConferenceDetailDTO, Void> call(final TableColumn<ConferenceDetailDTO, Void> param) {
                final TableCell<ConferenceDetailDTO, Void> cell = new TableCell<>() {

                    private final Button btn = new Button();
                    {
                        btn.setStyle("-fx-background-color: #FF0000;-fx-fill:#FFFFFF");
                        btn.setGraphic(GlyphsDude.createIcon(FontAwesomeIcons.EDIT));
                        btn.setOnAction((ActionEvent event) -> {
                            ConferenceDetailDTO data = getTableView().getItems().get(getIndex());
                            if(ConferenceDAO.getConferenceById(data.getId()).getTime()
                                    .before(new Timestamp(System.currentTimeMillis()))){
                                showAlertNotUpdate();
                            }else{
                                try {
                                    titleName.setText(Utils.convertUTF8IntoString("CẬP NHẬT HỘI NGHỊ"));
                                    addScreen("/scene/update_cfr.fxml",data);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox box = new HBox(btn);
                            box.setSpacing(5);
                            setGraphic(box);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        table.getColumns().add(colBtn);
        return false;
    }


    void searchConference(){
        numSearchResult.setVisible(true);
        numSearchResult.setManaged(true);
        ObservableList<ConferenceDetailDTO> listSearch = ConferenceDAO.searchConference(searchField.getText());
        numSearchResult.setText(Utils.convertUTF8IntoString("Tìm thấy "+ listSearch.size()+ " kết quả"));
        table.setItems(listSearch);
    }

    public void addScreen(String path, ConferenceDetailDTO cfr) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stackPane.getChildren().add(loader.load());
        Controller controller = loader.getController();
        controller.getRoot(stackPane, cfr,titleName);
        controller.loadView(cfr);
    }

    private void showAlertNotUpdate(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Không thể cập nhật");
        alert.setHeaderText("Hội nghị đã diễn ra!");
        alert.showAndWait();
    }
}
