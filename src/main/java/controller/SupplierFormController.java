package controller;

import com.jfoenix.controls.JFXTextField;
import dto.Products;
import dto.Supplier;
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
import service.ServiceFactory;
import service.custom.SupplierService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colCompany;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane loadContentEmployee;

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    private JFXTextField txtCompany;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    SupplierService service = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("SupplierID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("Company"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        loadTableSupplier();
    }

    private void loadTableSupplier() throws SQLException {
        ObservableList<Supplier> observableList = FXCollections.observableArrayList();
        observableList.addAll(service.getAll());
        tblSupplier.setItems(observableList);
    }

    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException {
        String id = txtId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String company = txtCompany.getText();
        Supplier supplier = new Supplier(id, name, company,email);
        boolean isAdd = service.addSupplier(supplier);
        if (isAdd){
            new Alert(Alert.AlertType.CONFIRMATION,"Supplier Add is Successfully").show();
            loadTableSupplier();
            cleanText();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION,"Supplier Add is not Successfully").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String id = txtId.getText();
        boolean deleted = service.deleteSupplier(id);
        if (deleted){
            new Alert(Alert.AlertType.CONFIRMATION,"Delete is Successfully").show();
            cleanText();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION,"Delete is Successfully").show();
        }
        loadTableSupplier();
    }

    @FXML
    void btnSeartchOnAction(ActionEvent event) throws SQLException {
        String id = txtId.getText();
        Supplier supplier = service.serchSupplier(id);
        if (supplier != null){
            txtId.setText(supplier.getSupplierID());
            txtName.setText(supplier.getName());
            txtCompany.setText(supplier.getCompany());
            txtEmail.setText(supplier.getEmail());
            new Alert(Alert.AlertType.CONFIRMATION,"Supplier is Found").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Supplier is not Found").show();
        }


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String id = txtId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String company = txtCompany.getText();
        Supplier supplier = new Supplier(id, name, company,email);
        boolean isUpdate = service.updateSupplier(supplier);
        if (isUpdate){
            new Alert(Alert.AlertType.CONFIRMATION,"Supplier is Updated").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Supplier is not Updated").show();
        }
        loadTableSupplier();

    }
    void cleanText(){
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtCompany.setText("");
    }
}
