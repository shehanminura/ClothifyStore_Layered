package controller;

import com.jfoenix.controls.JFXTextField;
import dto.Admin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;
import org.jasypt.util.text.BasicTextEncryptor;
import service.ServiceFactory;
import service.custom.AdminService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPassword;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private AnchorPane loadContentAdmin;

    @FXML
    private TableView<Admin> tblAdmin;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtid;

    AdminService service = ServiceFactory.getInstance().getServiceType(ServiceType.ADMIN);
    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
        loadTable();
    }

    private void loadTable() throws SQLException {
        ObservableList<Admin>adminObservableList = FXCollections.observableArrayList();
        adminObservableList.addAll(service.getAll());
        tblAdmin.setItems(adminObservableList);
    }

    @FXML
    void btnAddAdOnAction(ActionEvent event) throws SQLException {
        String id = txtid.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String password =txtPassword.getText();

        String key = "12345";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);

        String encrypt = basicTextEncryptor.encrypt(password);

        Admin admin = new Admin(id, name, email, encrypt);
        boolean isAdd = service.addAdmin(admin);
        if (isAdd){
            new Alert(Alert.AlertType.CONFIRMATION,"Admin Add is Successfully").show();
            clearTxt();
        }else {
            new Alert(Alert.AlertType.ERROR,"Added is Fail").show();
        }
        loadTable();
    }
    @FXML
    void btnDeleteAdOnAction(ActionEvent event) {

        String id = txtid.getText();

        try {
            boolean isDelete = service.deleteAdmin(id);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Delete is Successfully").show();
                clearTxt();
            }
            loadTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSeartchAdOnAction(ActionEvent event) throws SQLException {
        String id = txtid.getText();
        Admin admin = service.searchAdmin(id);
        if (admin != null) {
            new Alert(Alert.AlertType.INFORMATION, "Admin is Founded").show();
            txtid.setText(admin.getUserID());
            txtName.setText(admin.getName());
            txtEmail.setText(admin.getEmail());
            txtPassword.setText(admin.getPassword());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Admin is not Founded").show();
        }
    }

    @FXML
    void btnUpdateAdOnAction(ActionEvent event) {
        String id = txtid.getText();
        String name = txtName.getText();
        String email=txtEmail.getText();
        String password = txtPassword.getText();

        Admin admin = new Admin(id, name, email, password);
        try {
            boolean isUpdate = service.updateAdmin(admin);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Update is Successfully").show();
                clearTxt();
            }else {
                new Alert(Alert.AlertType.ERROR,"Update is not Successfully").show();
            }
            loadTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void clearTxt(){
        txtid.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }
}
