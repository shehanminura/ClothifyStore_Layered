package controller;

import com.jfoenix.controls.JFXTextField;
import dto.Products;
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
import service.custom.ProductsService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductsFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colImg;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colProId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colSupp;

    @FXML
    private AnchorPane loadContentEmployee;

    @FXML
    private TableView<Products> tblProducts;

    @FXML
    private JFXTextField txtPrId;

    @FXML
    private JFXTextField txtProCategory;

    @FXML
    private JFXTextField txtProImg;

    @FXML
    private JFXTextField txtProName;

    @FXML
    private JFXTextField txtProPrice;

    @FXML
    private JFXTextField txtProQty;

    @FXML
    private JFXTextField txtProSize;

    @FXML
    private JFXTextField txtProSup;

    ProductsService service = ServiceFactory.getInstance().getServiceType(ServiceType.PRODUCTS);
    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProId.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("Size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colSupp.setCellValueFactory(new PropertyValueFactory<>("SupplierID"));
        colImg.setCellValueFactory(new PropertyValueFactory<>("ImageURL"));
        loadTableProducts();
    }

    private void loadTableProducts() throws SQLException {
        ObservableList<Products> observableList = FXCollections.observableArrayList();
        observableList.addAll(service.getAll());
        tblProducts.setItems(observableList);
    }

    @FXML
    void btnAddProOnAction(ActionEvent event) throws SQLException {
        String prid = txtPrId.getText();
        String name = txtProName.getText();
        String category = txtProCategory.getText();
        String size = txtProSize.getText();
        Double price = Double.parseDouble(txtProPrice.getText());
        Integer qty = Integer.parseInt(txtProQty.getText());
        String supid = txtProSup.getText();
        String imglink=txtProImg.getText();

        Products products = new Products(prid, name, category, size, price, qty, supid, imglink);
        System.out.println(products);
        boolean isAdd = service.addProducts(products);
        if (isAdd){
            new Alert(Alert.AlertType.CONFIRMATION,"Products Added Succesfully").show();
            loadTableProducts();
        }else {
            new Alert(Alert.AlertType.ERROR,"Products Added Failed !").show();

        }
    }

    @FXML
    void btnDeleteProOnAction(ActionEvent event) throws SQLException {
        String id = txtPrId.getText();
        boolean isDelete = service.deleteProducts(id);
        if (isDelete){
            new Alert(Alert.AlertType.INFORMATION,"Delete is Successfully").show();
            loadTableProducts();
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Delete is not Successfully").show();

        }
    }

    @FXML
    void btnSeartchProOnAction(ActionEvent event) throws SQLException {
        String id = txtPrId.getText();
        Products products = service.serchProducts(id);
        if (products != null){
            txtProName.setText(products.getName());
            txtProCategory.setText(products.getCategory());
            txtProSize.setText(products.getSize());
            txtProPrice.setText(String.valueOf(products.getPrice()));
            txtProQty.setText(String.valueOf(products.getQuantity()));
            txtProSup.setText(products.getSupplierID());
            txtProImg.setText(products.getImageURL());
        }
    }

    @FXML
    void btnUpdateProOnAction(ActionEvent event) throws SQLException {
        Products products = new Products(txtPrId.getText(), txtProName.getText(), txtProCategory.getText(), txtProSize.getText(), Double.parseDouble(txtProPrice.getText()), Integer.parseInt(txtProQty.getText()), txtProSup.getText(), txtProImg.getText());
        boolean isupdated = service.updateProducts(products);
        if (isupdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Update is SuccessFully").show();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION,"Update is Not SuccessFull").show();
        }
        loadTableProducts();
    }


}
