package controller;

import DAO.AttendsDAO;
import DTO.ApprovalDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import utils.Utils;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ApprovalController extends Controller implements Initializable {

    @FXML
    private TableView<ApprovalDTO> table_approval;

    @FXML
    private TableColumn<ApprovalDTO, Integer> approval_id;

    @FXML
    private TableColumn<ApprovalDTO, String> approval_username;

    @FXML
    private TableColumn<ApprovalDTO, String> approval_name;

    @FXML
    private TableColumn<ApprovalDTO, String> approval_cfr;

    boolean flag = true;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        approvalView();
    }

    void approvalView() {
        approval_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        approval_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        approval_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        approval_cfr.setCellValueFactory(new PropertyValueFactory<>("conference"));
        table_approval.setItems(AttendsDAO.getAttendsListNotApprovalDetail());

        if (flag) {
            flag = addButtonAppvoral();
        }
    }

    private boolean addButtonAppvoral() {
        TableColumn<ApprovalDTO, Void> colBtn = new TableColumn(Utils.convertUTF8IntoString("Hành động"));

        Callback<TableColumn<ApprovalDTO, Void>, TableCell<ApprovalDTO, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ApprovalDTO, Void> call(final TableColumn<ApprovalDTO, Void> param) {
                final TableCell<ApprovalDTO, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Duyệt");
                    private final Button btn2 = new Button("Từ tối");

                    {
                        btn.setStyle("-fx-background-color: #04B431;-fx-text-fill: #FFFFFF;");
                        btn.setOnAction((ActionEvent event) -> {
                            ApprovalDTO data = getTableView().getItems().get(getIndex());
                            if (showAlertConfirmAppvoral(data, 1)) {
                                approvalView();
                            }
                        });
                    }

                    {
                        btn2.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #FFFFFF;");
                        btn2.setOnAction((ActionEvent event) -> {
                            ApprovalDTO data = getTableView().getItems().get(getIndex());
                            if (showAlertConfirmAppvoral(data, 2)) {
                                approvalView();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox box = new HBox(btn, btn2);
                            box.setSpacing(5);
                            setGraphic(box);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        table_approval.getColumns().add(colBtn);
        return false;
    }

    private boolean showAlertConfirmAppvoral(ApprovalDTO approvalDTO, int approval) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (approval == 1) {
            alert.setTitle("Duyệt tham gia hội nghị");
            alert.setHeaderText("Xác nhận duyệt \"" + approvalDTO.getUsername() + "\" tham gia: " +
                    approvalDTO.getName());
        } else {
            alert.setTitle("Từ chối tham gia hội nghị");
            alert.setHeaderText("Xác từ chối \"" + approvalDTO.getUsername() + "\" tham gia: " +
                    approvalDTO.getName());
        }
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            AttendsDAO.updateAppvoralAttends(approvalDTO.getId(), approval);
            //btnRegisterCfr.setDisable(true);
            //btnRegisterCfr.setText(Utils.convertUTF8IntoString("Đã đăng ký"));
            return true;
        }
        return false;
    }

}
