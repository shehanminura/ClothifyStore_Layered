package controller;

import com.jfoenix.controls.JFXTextField;
import dto.Employee;
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
import service.custom.EmployeeService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colAdminid;

    @FXML
    private TableColumn<?, ?> colCompany;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPassword;

    @FXML
    private TableColumn<?, ?> coliEmpd;

    @FXML
    private AnchorPane loadContentAdmin;

    @FXML
    private TableView tblEmp;

    @FXML
    private JFXTextField txtCompany;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtadminid;

    @FXML
    private JFXTextField txtempid;

    @FXML
    private JFXTextField txtname;

    EmployeeService service = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);


    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coliEmpd.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
        colAdminid.setCellValueFactory(new PropertyValueFactory<>("userid"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("Company"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        loadEmpTable();
    }

    private void loadEmpTable() throws SQLException {
        ObservableList<Employee> employeeObservableList = FXCollections.observableArrayList();
        employeeObservableList.addAll(service.getAll());
        tblEmp.setItems(employeeObservableList);
    }

    @FXML
    void btnAddEmpOnAction(ActionEvent event) throws SQLException {
        String empid = txtempid.getText();
        String adminid = txtadminid.getText();
        String name = txtname.getText();
        String company = txtCompany.getText();
        String email = txtEmail.getText();
        String pssword = txtPassword.getText();
        Employee employee = new Employee(empid, adminid, name, company, email, pssword);
        boolean isAdd = service.addEmployee(employee);
        System.out.println(isAdd);
        if (isAdd){
            new Alert(Alert.AlertType.INFORMATION,"Employee Added is Successfully").show();
            cleanTxt();
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Employee is Not Added").show();
        }
        loadEmpTable();
    }

    @FXML
    void btnDeleteEmpOnAction(ActionEvent event) throws SQLException {
        String id = txtempid.getText();
        boolean isDelete = service.deleteCustomer(id);
        if (isDelete){
            new Alert(Alert.AlertType.INFORMATION,"Employee Delete is Successfully").show();
            cleanTxt();
        }
        loadEmpTable();
    }

    @FXML
    void btnSeartchEmpOnAction(ActionEvent event) throws SQLException {
        String id = txtempid.getText();
        Employee employee = service.searchEmployee(id);
        if (employee != null){
            new Alert(Alert.AlertType.INFORMATION,"Employee is founded").show();
            txtempid.setText(employee.getEmployeeid());
            txtadminid.setText(employee.getUserid());
            txtname.setText(employee.getName());
            txtCompany.setText(employee.getCompany());
            txtEmail.setText(employee.getEmail());
            txtPassword.setText(employee.getPassword());
        }else {
            new Alert(Alert.AlertType.ERROR,"Employee is not founded").show();
        }


    }

    @FXML
    void btnUpdateEmpOnAction(ActionEvent event) throws SQLException {
        String empid = txtempid.getText();
        String adminid = txtadminid.getText();
        String name = txtname.getText();
        String company = txtCompany.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        Employee employee = new Employee(empid, adminid, name, company, email, password);

        boolean isUpdate = service.updateEmployee(employee);
        if (isUpdate){
            new Alert(Alert.AlertType.INFORMATION,"Update is successfully").show();
            cleanTxt();
        }else {
            new Alert(Alert.AlertType.ERROR,"Update is not successfully").show();
        }
        loadEmpTable();

    }
    void cleanTxt(){
        txtempid.setText("");
        txtadminid.setText("");
        txtEmail.setText("");
        txtname.setText("");
        txtCompany.setText("");
        txtPassword.setText("");
    }

}
