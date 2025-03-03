package controller.dashbord;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class LoginDashbordController {

    @FXML
    private AnchorPane LandRAnchor;

    public void btnLoginEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/View/loginEmpForm.fxml");

        if (resource == null){
            new Alert(Alert.AlertType.ERROR,"Employee Not Found").show();
        }

        assert resource !=null;
        Parent load = FXMLLoader.load(resource);
        this.LandRAnchor.getChildren().clear();
        this.LandRAnchor.getChildren().add(load);

    }

    public void btnLoginAdminOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/View/loginAdminForm.fxml");

        if (resource == null){
            new Alert(Alert.AlertType.ERROR,"Employee Not Found").show();
        }

        assert resource !=null;
        Parent load = FXMLLoader.load(resource);
        this.LandRAnchor.getChildren().clear();
        this.LandRAnchor.getChildren().add(load);
    }

}
