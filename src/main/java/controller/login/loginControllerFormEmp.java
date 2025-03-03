package controller.login;

import com.jfoenix.controls.JFXTextField;
import dto.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;
import service.ServiceFactory;
import service.custom.EmployeeService;
import util.ServiceType;

import java.io.IOException;
import java.sql.SQLException;

public class loginControllerFormEmp {

    @FXML
    private AnchorPane LandRAnchor;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPassword;


    EmployeeService service = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);

    @FXML
    void btnLoginOnAction(ActionEvent event) throws SQLException, IOException {
        String key="1234";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);

        Employee empByEmail = service.getEmpByEmail(txtEmail.getText());

        if (basicTextEncryptor.decrypt(empByEmail.getPassword()).equals(txtPassword.getText())){
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/Employee_Dashbord.fxml"))));
            stage.show();
            Stage currentStage = (Stage) txtEmail.getScene().getWindow();
            currentStage.close();
        }




    }

}
