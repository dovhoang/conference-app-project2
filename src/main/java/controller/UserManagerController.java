package controller;

import DAO.ConferenceDAO;
import DAO.UserDAO;
import DTO.ConferenceDetailDTO;
import DTO.UserDTO;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import utils.Utils;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserManagerController extends Controller {

    @FXML
    private TableView<UserDTO> table_userManager;

    @FXML
    private TableColumn<UserDTO, Integer> userManager_id;

    @FXML
    private TableColumn<UserDTO, String> userManager_username;

    @FXML
    private TableColumn<UserDTO, String> userManager_name;

    @FXML
    private TableColumn<UserDTO, String> userManager_email;

    @FXML
    private TableColumn<UserDTO, String> userManager_status;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField searchField;

    @FXML
    private Text numSearchResult;

    boolean flag = true;

    @Override
    public void loadView() {
        userManager_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        userManager_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        userManager_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        userManager_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        userManager_status.setCellValueFactory(new PropertyValueFactory<>("active"));
        table_userManager.setItems(UserDAO.getUserList());
        if (flag) flag = addButtonActiveUser();
        numSearchResult.setVisible(false);
        btnSearch.setOnAction(event -> {
            searchUser();
        });

        searchField.setOnKeyPressed(event -> {
            if (event.getCode()== KeyCode.ENTER){
                searchUser();
            }
        });

    }

    public void searchUser(){
        numSearchResult.setVisible(true);
        numSearchResult.setManaged(true);
        ObservableList<UserDTO> listSearch = UserDAO.searchUser(searchField.getText());
        numSearchResult.setText(Utils.convertUTF8IntoString("Tìm thấy "+ listSearch.size()+ " kết quả"));
        table_userManager.setItems(listSearch);
    }

    private boolean addButtonActiveUser() {
        TableColumn<UserDTO, Void> colBtn = new TableColumn(Utils.convertUTF8IntoString("Hành động"));

        Callback<TableColumn<UserDTO, Void>, TableCell<UserDTO, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<UserDTO, Void> call(final TableColumn<UserDTO, Void> param) {
                final TableCell<UserDTO, Void> cell = new TableCell<>() {
                    private final Button btn = new Button();

                    {

                        btn.setGraphic(GlyphsDude.createIcon(FontAwesomeIcons.HAND_ALT_UP));
                        btn.setStyle("-fx-background-color: #04B431;-fx-text-fill: #2D75E8;");
                        btn.setOnAction((ActionEvent event) -> {
                            UserDTO data = getTableView().getItems().get(getIndex());
                            showAlertConfirmActiveOrBanUser(data);
                            loadView();
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
        table_userManager.getColumns().add(colBtn);
        return false;
    }

    private void showAlertConfirmActiveOrBanUser(UserDTO user) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (!user.getActive().equals("Đã kích hoạt")) {
            alert.setTitle("Kích họa tài khoản");
            alert.setHeaderText("Xác nhận kích hoạt tài khoản \"" + user.getUsername() + "\"");
        } else {
            alert.setTitle("Chặn tài khoản");
            alert.setHeaderText("Xác từ chặn tài khoản  \"" + user.getUsername() + "\"");
        }
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            UserDAO.updateUser(user.getId(), !user.getActive().equals("Đã kích hoạt"));
        }

    }
}
