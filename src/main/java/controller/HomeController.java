package controller;

import DAO.*;
import DTO.ApprovalDTO;
import DTO.ConferenceDetailDTO;
import DTO.MyConferencesDTO;
import DTO.UserDTO;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import global.UserSession;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;
import pojo.*;
import utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private AnchorPane window;

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
    private Button btnProfile;

    @FXML
    private Button btnList;

    @FXML
    private Button btnMyCfr;

    @FXML
    private Button btnSetting;

    @FXML
    private Button btnSignOut;

    @FXML
    private Button btnSignIn;

    @FXML
    private Button btnSignUp;

    @FXML
    private Label title_name;

    @FXML
    private Label cfr_id;

    @FXML
    private Text cfr_time;

    @FXML
    private Text cfr_address;

    @FXML
    private Text cfr_place;

    @FXML
    private Text cfr_general;

    @FXML
    private Text cfr_detail;

    @FXML
    private VBox cfr_picture;

    @FXML
    private GridPane paneList;

    @FXML
    private GridPane paneCfr;

    @FXML
    private AnchorPane paneSignIn;

    @FXML
    private GridPane paneProfile;

    @FXML
    private Pane paneTitle;

    @FXML
    private TextField signInUsername;

    @FXML
    private PasswordField signInPassword;

    @FXML
    private Button btnSignInForm;

    @FXML
    private Text signInError;

    @FXML
    private Text helloUser;

    @FXML
    private Label user_name;

    @FXML
    private Text user_id;

    @FXML
    private Text user_username;

    @FXML
    private Text user_email;

    @FXML
    private Text user_type;

    @FXML
    private Text user_active;

    @FXML
    private Button btnRegisterCfr;

    @FXML
    private GridPane paneMyCfr;

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
    private Pane paneFormSignUp;

    @FXML
    private Pane paneFormSignIn;

    @FXML
    private Pane paneWelcome;

    @FXML
    private TextField signUpUsername;

    @FXML
    private TextField signUpName;

    @FXML
    private Button btnSignUpForm;

    @FXML
    private TextField signUpEmail;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private PasswordField signUpPasswordRepeat;

    @FXML
    private Text signUpError;

    @FXML
    private Pane paneSignUpError;

    @FXML
    private Button btnAddCfr;

    @FXML
    private Button btnUserManager;

    @FXML
    private AnchorPane paneAddCfr;

    @FXML
    private TextArea addCfr_name;

    @FXML
    private Button btnAddCfrForm;

    @FXML
    private DatePicker addCfr_date;

    @FXML
    private ComboBox<LocalTime> addCfr_time = new ComboBox<>();

    @FXML
    private ComboBox<Place> addCfr_place = new ComboBox<>();

    @FXML
    private ComboBox<Room> addCfr_room = new ComboBox<Room>();

    @FXML
    private TextArea addCfr_generalDesc;

    @FXML
    private TextArea addCfr_detiailDesc;

    @FXML
    private Button addCfr_picture;

    @FXML
    private TextField addCfr_numberAttendees;

    @FXML
    private Pane paneAddCfrError;

    @FXML
    private Text addCfr_error;

    @FXML
    private ProgressBar cfr_pgNumAttendees;

    @FXML
    private Text cfr_numAttendees;

    @FXML
    private GridPane paneUserManager;

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
    private GridPane paneApproval;

    @FXML
    private Button btnAppvoral;

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

    @FXML
    private Button btnEditCfr;

    @FXML
    private Text numPicture;

    @FXML
    private StackPane stackPane;

    boolean flag = true;//Add button approval to the table only once

    boolean flag2 = true;//Add button accept to the table only once

    boolean flag3 = true;//Init combobox to the table only once

    List<File> fileList = new ArrayList<>();

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        //addCfrView();// Initial ComboBox Date, Time, Place, Room
        listConferenceView();
    }

    TableCell<ConferenceDetailDTO, String> getTableCellCustom() {
        return new TableCell<ConferenceDetailDTO, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    super.setText(null);
                    super.setGraphic(null);
                } else {
                    super.setText(null);
                    Label l = new Label(item);
                    l.setWrapText(true);
                    VBox box = new VBox(l);
                    box.setPrefHeight(80);
                    box.setAlignment(Pos.CENTER_LEFT);
                    super.setGraphic(box);
                }
            }
        };
    }

    //Click button event
    public void handClick(ActionEvent event) throws IOException, URISyntaxException {
        if (event.getSource() == btnProfile) {
            profileView();
        } else if (event.getSource() == btnList) {
            listConferenceView();
        } else if (event.getSource() == btnMyCfr) {
            myCfrView(UserSession.getInstance().getUser());
        } else if (event.getSource() == btnSetting) {
        } else if (event.getSource() == btnSignOut) {
            UserSession.cleanUserSession();
            notLoggedMenu();
        } else if (event.getSource() == btnSignIn) {
            signInView();
            signUpView();
        } else if (event.getSource() == btnAddCfr) {
            addCfrView();
        } else if (event.getSource() == btnAppvoral) {
            approvalView();
        } else if (event.getSource() == btnUserManager) {
            userManagerView();
        }
    }

    public void chooseCfrPicture() {
        final boolean t  = true;
        addCfr_picture.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
            }
        });
    }

    public void fileSaved(List<File> fileList, int id) throws IOException {
        String spath = "src/main/resources/picture/" + id + "/";
        File file = new File(spath);
        Path path = Paths.get(file.getAbsolutePath());
        Files.createDirectories(path);
        int i = 1;
        for (File f : fileList) {
            if (f != null) {
                try{
                    BufferedImage originalImage = ImageIO.read(f);
                    BufferedImage resizeImageJpg = Utils.createResizedCopy(originalImage, 220, 180,true);
                    File newFile = new File(path.toString() + "/" + i + ".jpg");
                    ImageIO.write(resizeImageJpg, "jpg", newFile);

                } catch(IOException e) {
                    System.out.println("error resize: "+e.getMessage());
                }
            }
            i++;
        }
    }

    boolean checkErrorFormSignUp() {
        boolean flag = true;
        String error = Utils.convertUTF8IntoString("Lỗi:");
        String username = signUpUsername.getText();
        String name = signUpName.getText();
        String email = signUpEmail.getText();
        String pass = signUpPassword.getText();
        String rPass = signUpPasswordRepeat.getText();
        if (name.equals("") || email.equals("") || username.equals("")
                || pass.equals("") || rPass.equals("")) {
            error = error.concat(Utils.convertUTF8IntoString("\nCác trường không được để trống"));
            signUpError.setText(error);
            return false;
        }
        if (!username.matches("^([A-Za-z])([A-Za-z0-9_.]){7,31}$")) {
            flag = false;
            error = error.concat(Utils.convertUTF8IntoString("\nTên đăng nhập từ 8 -32 kí tự có thể chứa chữ cái, số, \"_\" , \".\"  và bắt đầu từ chữ cái"));
        }
        if (!email.matches("^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$")) {
            flag = false;
            error = error.concat(Utils.convertUTF8IntoString("\nEmail không đúng định dạng"));
        }
        if (!pass.matches("[\\w]{6,16}")) {
            flag = false;
            error = error.concat(Utils.convertUTF8IntoString("\nMật khẩu từ 6-16 kí tự gồm chữ cái và số"));// password error

        }
        if (!pass.equals(rPass)) {
            flag = false;
            error = error.concat(Utils.convertUTF8IntoString("\nNhập lại khẩu không trùng khớp"));// password error// password repeat error
        }
        signUpError.setText(error);
        return flag;
    }

    void listConferenceView() {
        //Define comumn of table
        table_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        table_place.setCellValueFactory(new PropertyValueFactory<>("place"));
        table_info.setCellValueFactory(new PropertyValueFactory<>("generalDesc"));


        //Homepage is List Cfr view
        paneList.toFront();
        btnList.requestFocus();
        cfr_id.setText("");
        title_name.setText(Utils.convertUTF8IntoString("DANH SÁCH HỘI NGHỊ"));

        //Set cell listener to show Cfr detail
        table_name.setCellFactory(tc -> {
            TableCell<ConferenceDetailDTO, String> cell = getTableCellCustom();
            cell.setOnMouseClicked(event -> {
                ConferenceDetailDTO conference = cell.getTableRow().getItem();
                CfrDetailView(conference);
            });
            return cell;
        });
        table_place.setCellFactory(tc -> getTableCellCustom());
        table_info.setCellFactory(tc -> getTableCellCustom());

        table.setItems(ConferenceDAO.getConferencesDetail());
        //Show left menu:  logged or not logged view
        showMenu();
    }

    void signUpView() {
        signUpName.clear();
        signUpUsername.clear();
        signUpEmail.clear();
        signUpPassword.clear();
        signUpPasswordRepeat.clear();
        btnSignUpForm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id = UserDAO.getMaxIUser();
                String name = signUpName.getText();
                String username = signUpUsername.getText();
                String email = signUpEmail.getText();
                String password = signUpPassword.getText();
                if (checkErrorFormSignUp()) {
                    password = Utils.md5(password);
                    User user = new User(id, name, username, password, email,
                            new UserType(1, "User"), true);
                    if (!UserDAO.checkExistsUsernameAndEmail(user)) {
                        UserDAO.insertUser(user);
                        UserSession.getInstace(user);
                        helloUser.setText(Utils.convertUTF8IntoString("Chào, ") +
                                UserSession.getInstance().getUser().getName());
                        listConferenceView();
                    } else {
                        signUpError.setText("Tên đăng nhập hoặc email đã trùng");
                        paneSignUpError.setVisible(true);
                    }
                } else {
                    paneSignUpError.setVisible(true);
                }
            }
        });
    }

    void signInView() {
        paneFormSignIn.setVisible(true);
        paneWelcome.setVisible(true);
        paneFormSignUp.setVisible(false);
        paneSignUpError.setVisible(false);
        title_name.setText(Utils.convertUTF8IntoString("ĐĂNG NHẬP"));
        paneSignIn.toFront();

        signInUsername.clear();
        signInPassword.clear();

        btnSignInForm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = signInUsername.getText();
                String passwrod = Utils.md5(signInPassword.getText());
                User user = UserDAO.getUser(username, passwrod);
                if (user != null) {
                    if (user.isActive()) {
                        UserSession.getInstace(user);
                        listConferenceView();
                        helloUser.setText(Utils.convertUTF8IntoString("Chào, ") +
                                UserSession.getInstance().getUser().getName());
                    } else {
                        signInError.setVisible(true);
                        signInError.setText(Utils.convertUTF8IntoString("Tài khoản của bạn đã bị chặn"));
                    }
                } else {
                    signInError.setVisible(true);
                    signInError.setText(Utils.convertUTF8IntoString("Tên tài khoản hoặc mật khẩu không đúng"));

                }
            }
        });

        btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                title_name.setText(Utils.convertUTF8IntoString("ĐĂNG KÝ"));
                paneWelcome.setVisible(false);
                paneFormSignIn.setVisible(false);
                paneFormSignUp.setVisible(true);
                paneSignUpError.setVisible(false);
            }
        });
    }

    void profileView() {
        User cur = UserSession.getInstance().getUser();
        paneProfile.toFront();
        title_name.setText(Utils.convertUTF8IntoString("HỒ SƠ"));
        user_id.setText(Integer.toString(cur.getId()));
        user_name.setText(cur.getName());
        user_username.setText(cur.getUsername());
        user_email.setText(cur.getEmail());
        user_type.setText(cur.getType().getName());
        user_active.setText(cur.isActive() ? Utils.convertUTF8IntoString("Đang hoạt động")
                : Utils.convertUTF8IntoString("Đã bị chặn"));
    }

    void myCfrView(User user) {
        paneMyCfr.toFront();
        myCfr_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        myCfr_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        myCfr_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        myCfr_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        myCfr_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        myCfr_table.setItems(ConferenceDAO.getMyConferencesByUser(user));

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
                    CfrDetailView(ConferenceDAO.getConferenceDetailById(myConference.getId()));
                }
            });
            return cell;
        });
    }

    void CfrDetailView(ConferenceDetailDTO cfr) {
        Conference conference = ConferenceDAO.getConferenceById(cfr.getId());
        long attendees = AttendsDAO.getNumberAttendeesForConference(conference);
        paneCfr.toFront();
        cfr_id.setText(Utils.convertUTF8IntoString("Danh sách hội nghị / ") + Integer.toString(cfr.getId()));
        if (cfr.getName().length() > 50)
            title_name.setFont(Font.font("verdana", FontWeight.BOLD, 28.0 / cfr.getName().length() * 50));

        title_name.setText(cfr.getName());
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
            if (checkAttends == 1) {
                btnRegisterCfr.setText("Đã đăng kí");
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
                    showAlertConfirmConference(conference);
                } else {
                    showAlertWarnSignIn();
                }
            }
        });

        btnEditCfr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateCfrView(cfr);
            }
        });


    }

    void approvalView() {
        paneApproval.toFront();
        title_name.setText(Utils.convertUTF8IntoString("DUYỆT THAM GIA"));
        approval_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        approval_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        approval_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        approval_cfr.setCellValueFactory(new PropertyValueFactory<>("conference"));
        table_approval.setItems(AttendsDAO.getAttendsListNotApprovalDetail());

        if (flag) {
            flag = addButtonAppvoral();
        }
        ;
    }

    public boolean addCfrView() {
        paneAddCfr.toFront();
        title_name.setText(Utils.convertUTF8IntoString("THÊM HỘI NGHỊ"));

        addCfr_name.clear();
        addCfr_generalDesc.clear();
        addCfr_detiailDesc.clear();
        addCfr_numberAttendees.clear();
        numPicture.setText(Utils.convertUTF8IntoString("Đã chọn: 0 file"));

        int id = ConferenceDAO.getMaxId() + 1;
        final Room[] room = new Room[1];
        final LocalDate[] date = new LocalDate[1];
        final LocalTime[] time = new LocalTime[1];

        //get date
        addCfr_date.setOnAction(new EventHandler() {
            public void handle(Event t) {
                date[0] = addCfr_date.getValue();
            }
        });

        //get time
        ObservableList<LocalTime> timeOptions = FXCollections.observableArrayList();
        int i = 14;
        while (i / 2 < 21) {
            timeOptions.add(LocalTime.of(i / 2, i % 2 * 30));
            i++;
        }
        addCfr_time.setItems(timeOptions);
        addCfr_time.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalTime object) {
                return object.toString();
            }

            @Override
            public LocalTime fromString(String string) {
                return addCfr_time.getItems().stream().filter(ap ->
                        ap.toString().equals(string)).findFirst().orElse(null);
            }
        });

        addCfr_time.valueProperty().addListener(new ChangeListener<LocalTime>() {
            @Override
            public void changed(ObservableValue<? extends LocalTime> observable, LocalTime oldValue, LocalTime newValue) {
                time[0] = newValue;
            }
        });

        //get room and place
        ObservableList<Place> placeOptions = PlaceDAO.getPlacesList();
        addCfr_place.setItems(placeOptions);
        addCfr_place.setConverter(new StringConverter<Place>() {
            @Override
            public String toString(Place object) {
                return object.getName();
            }

            @Override
            public Place fromString(String string) {
                return addCfr_place.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });

        addCfr_place.valueProperty().addListener(new ChangeListener<Place>() {
            @Override
            public void changed(ObservableValue<? extends Place> observable, Place oldValue, Place newValue) {
                ObservableList<Room> roomOptions = RoomDAO.getRoomsListByPlace(newValue);
                addCfr_room.setItems(roomOptions);
                addCfr_room.setConverter(new StringConverter<Room>() {
                    @Override
                    public String toString(Room object) {
                        return object.getName() + " - " + object.getCapacity();
                    }

                    @Override
                    public Room fromString(String string) {
                        return addCfr_room.getItems().stream().filter(ap ->
                                ap.getName().equals(string)).findFirst().orElse(null);
                    }
                });
                addCfr_room.valueProperty().addListener(new ChangeListener<Room>() {
                    @Override
                    public void changed(ObservableValue<? extends Room> observable, Room oldValue, Room newValue) {
                        room[0] = newValue;
                    }
                });
            }
        });

        chooseCfrPicture();

        // submit form
        btnAddCfrForm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String error = Utils.convertUTF8IntoString("Lỗi:");
                boolean flag = true;
                int numberAttendees = 0;
                Timestamp datetime = null;
                String name = addCfr_name.getText();
                String generalDesc = addCfr_generalDesc.getText();
                String detailDesc = addCfr_detiailDesc.getText();
                if (date[0] != null && time[0] != null && room[0] != null) {
                    datetime = Timestamp.valueOf(LocalDateTime.of(date[0], time[0]));
                    try {
                        numberAttendees = Integer.parseInt(addCfr_numberAttendees.getText());
                        if (numberAttendees < 10) {
                            error = error.concat(Utils.convertUTF8IntoString("\nSố người tham gia tối tiêu là 10"));
                            flag = false;
                        }
                        if (numberAttendees > room[0].getCapacity()) {
                            error = error.concat(Utils.convertUTF8IntoString("\nSố người tham gia tối đa là ")
                                    + room[0].getCapacity());
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

                } else {
                    error = error.concat(Utils.convertUTF8IntoString("\nCác trường không được để trống"));
                    flag = false;
                }
                addCfr_error.setText(error);
                if (flag) {
                    Conference cfr = new Conference(id, name, room[0], generalDesc, detailDesc, datetime, numberAttendees);
                    ConferenceDAO.insertConference(cfr);
                    try {
                        fileSaved(fileList, id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    paneAddCfrError.setVisible(false);
                    btnList.requestFocus();
                    listConferenceView();
                } else paneAddCfrError.setVisible(true);
            }
        });
        return false;
    }

    public void updateCfrView(ConferenceDetailDTO cfr) {
        if(flag3) flag3 = addCfrView();
        paneAddCfr.toFront();
        Conference conference = ConferenceDAO.getConferenceById(cfr.getId());
        title_name.setText(Utils.convertUTF8IntoString("CẬP NHẬT HỘI NGHỊ"));
        addCfr_name.setText(cfr.getName());
        addCfr_date.setValue(conference.getTime().toLocalDateTime().toLocalDate());
        addCfr_time.setValue(conference.getTime().toLocalDateTime().toLocalTime());
        addCfr_place.setValue(conference.getRoom().getPlace());
        addCfr_room.setValue(conference.getRoom());
        addCfr_numberAttendees.setText(Integer.toString(cfr.getMaxNumberAttendees()));
        addCfr_generalDesc.setText(cfr.getGeneralDesc());
        addCfr_detiailDesc.setText(cfr.getDetailDesc());

        String spath = "src/main/resources/picture/" + cfr.getId() + "/";
        File file = new File(spath);
        numPicture.setText(Utils.convertUTF8IntoString("Đã chọn: " +
                file.listFiles().length) + " file");
        chooseCfrPicture();
        btnAddCfrForm.setText(Utils.convertUTF8IntoString("Cập nhật"));
        btnAddCfrForm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String error = Utils.convertUTF8IntoString("Lỗi:");
                boolean flag = true;
                int numberAttendees = 0;
                Timestamp datetime = null;
                String name = addCfr_name.getText();
                String generalDesc = addCfr_generalDesc.getText();
                String detailDesc = addCfr_detiailDesc.getText();
                LocalDate date = addCfr_date.getValue();
                LocalTime time = addCfr_time.getValue();
                Room room = addCfr_room.getValue();
                Place place = addCfr_place.getValue();
                datetime = Timestamp.valueOf(LocalDateTime.of(date, time));
                try {
                    numberAttendees = Integer.parseInt(addCfr_numberAttendees.getText());
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
                addCfr_error.setText(error);
                if (flag) {
                    Conference newcfr = new Conference(cfr.getId(), name, room, generalDesc, detailDesc, datetime, numberAttendees);
                    ConferenceDAO.updateConference(newcfr);
                    CfrDetailView(cfr);
                    try {
                        fileSaved(fileList, cfr.getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    paneAddCfrError.setVisible(false);
                    btnList.requestFocus();
                    listConferenceView();
                } else paneAddCfrError.setVisible(true);
            }
        });
    }

    public void userManagerView() {
        paneUserManager.toFront();
        title_name.setText(Utils.convertUTF8IntoString("QUẢN LÍ NGƯỜI DÙNG"));
        userManager_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        userManager_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        userManager_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        userManager_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        userManager_status.setCellValueFactory(new PropertyValueFactory<>("active"));
        table_userManager.setItems(UserDAO.getUserList());
        if (flag2) flag2 = addButtonActiveUser();

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
                                btnAppvoral.requestFocus();
                                approvalView();
                            }
                        });
                    }

                    {
                        btn2.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #FFFFFF;");
                        btn2.setOnAction((ActionEvent event) -> {
                            ApprovalDTO data = getTableView().getItems().get(getIndex());
                            if (showAlertConfirmAppvoral(data, 2)) {
                                btnAppvoral.requestFocus();
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
                            userManagerView();
                            btnUserManager.requestFocus();
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

    //show menu left
    void showMenu() {
        if (UserSession.isLogin()) {
            if (UserSession.getInstance().getUser().getType().getName().equals("User")) {
                loggedMenuUser();
            } else {
                loggedMenuAdmin();
            }

        } else {
            notLoggedMenu();
        }
    }

    //logged menu admin
    void loggedMenuAdmin() {
        btnAddCfr.setVisible(true);
        btnAddCfr.setManaged(true);

        btnUserManager.setVisible(true);
        btnUserManager.setManaged(true);

        btnSignOut.setVisible(true);
        btnSignOut.setManaged(true);

        btnSignIn.setVisible(false);
        btnSignIn.setManaged(false);

        btnAppvoral.setVisible(true);
        btnAppvoral.setManaged(true);

        btnEditCfr.setVisible(true);
        btnEditCfr.setManaged(true);

        btnList.setText(Utils.convertUTF8IntoString("Quản lí hội nghị"));
        btnList.requestFocus();
    }

    //logged menu view
    void loggedMenuUser() {
        btnProfile.setVisible(true);
        btnProfile.setManaged(true);
        btnMyCfr.setVisible(true);
        btnMyCfr.setManaged(true);
        btnSignOut.setVisible(true);
        btnSignOut.setManaged(true);

        btnSignIn.setVisible(false);
        btnSignIn.setManaged(false);

        btnEditCfr.setVisible(false);
        btnEditCfr.setManaged(false);

        btnList.requestFocus();
        paneList.toFront();
    }

    //not logged menu view
    void notLoggedMenu() {

        btnProfile.setVisible(false);
        btnProfile.setManaged(false);

        btnMyCfr.setVisible(false);
        btnMyCfr.setManaged(false);

        btnSignOut.setVisible(false);
        btnSignOut.setManaged(false);

        btnAddCfr.setVisible(false);
        btnAddCfr.setManaged(false);

        btnUserManager.setVisible(false);
        btnUserManager.setManaged(false);

        btnSignIn.setVisible(true);
        btnSignIn.setManaged(true);

        btnAppvoral.setVisible(false);
        btnAppvoral.setManaged(false);

        btnEditCfr.setVisible(false);
        btnEditCfr.setManaged(false);

        helloUser.setText("");

        btnList.requestFocus();
        paneList.toFront();
    }

    private void showAlertConfirmConference(Conference conference) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng kí tham gia hội nghị");
        alert.setHeaderText("Xác nhận tham gia hội nghị: " + conference.getName());
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            User user = UserSession.getInstance().getUser();
            AttendsDAO.insertAttends(user, conference);
            btnRegisterCfr.setDisable(true);
            btnRegisterCfr.setText(Utils.convertUTF8IntoString("Đã đăng ký"));
        }
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
            btnRegisterCfr.setDisable(true);
            btnRegisterCfr.setText(Utils.convertUTF8IntoString("Đã đăng ký"));
            return true;
        }
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

    private void showAlertWarnSignIn() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng kí tham gia hội nghị");
        alert.setHeaderText("Bạn phải đăng nhập để thực hiện");
        alert.showAndWait();
    }

}