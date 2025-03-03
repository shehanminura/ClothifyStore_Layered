package controller;

import dbconnection.DBConnection;
import dto.OrderDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import service.ServiceFactory;
import service.custom.OrderDetailsService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrderDetailController implements Initializable {

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colqty;

    @FXML
    private AnchorPane loadContentEmployee;

    @FXML
    private TableView<OrderDetail> tblSupplier;

    OrderDetailsService service = ServiceFactory.getInstance().getServiceType(ServiceType.ORDERDETAILS);

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("SubTotal"));
        OrderDetailsloadTable();
    }

    private void OrderDetailsloadTable() throws SQLException {
        ObservableList<OrderDetail> adminObservableList = FXCollections.observableArrayList();
        adminObservableList.addAll(service.getAll());
        tblSupplier.setItems(adminObservableList);
    }

    @FXML
    void btnGetReportOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/Clothify.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());

            JasperExportManager.exportReportToHtmlFile(jasperPrint,"orderDetails.pdf");

            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
