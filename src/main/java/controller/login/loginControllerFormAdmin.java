package controller.login;

import com.jfoenix.controls.JFXTextField;
import dto.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;
import service.ServiceFactory;
import service.custom.AdminService;
import util.ServiceType;

import java.io.IOException;
import java.sql.SQLException;

public class loginControllerFormAdmin {

    @FXML
    private AnchorPane LandRAnchor;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPassword;

    AdminService service = ServiceFactory.getInstance().getServiceType(ServiceType.ADMIN);
    @FXML
    void btnLoginOnAction(ActionEvent event) throws SQLException, IOException {
        String key="12345";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);

        String email = txtEmail.getText();
        Admin byAdmin = service.findByAdmin(email);

        if (byAdmin!= null){
            if (basicTextEncryptor.decrypt(byAdmin.getPassword()).equals(txtPassword.getText())) {
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Admin_Dashbord.fxml"))));
                stage.show();
                Stage currentStage = (Stage) txtEmail.getScene().getWindow();
                currentStage.close();
            }else {
                new Alert(Alert.AlertType.ERROR,"Check your password").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Admin not Founded").show();
        }
    }

}
