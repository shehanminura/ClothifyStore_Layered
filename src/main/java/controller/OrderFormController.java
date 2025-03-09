package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lombok.SneakyThrows;
import service.ServiceFactory;
import service.custom.EmployeeService;
import service.custom.OrderDetailsService;
import service.custom.OrderService;
import service.custom.ProductsService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    @FXML
    private JFXComboBox<?> cmdEmployeeid;

    @FXML
    private JFXComboBox<?> cmdProductsId;

    @FXML
    private TableColumn<?, ?> colEmployee;

    @FXML
    private TableColumn<?, ?> colItem;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblTime;

    @FXML
    private AnchorPane loadContentEmployee;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtOrderId;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtStock;

    @FXML
    private JFXTextField txtUnitPrice;

    EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);
    ProductsService productsService = ServiceFactory.getInstance().getServiceType(ServiceType.PRODUCTS);
    OrderService orderService = ServiceFactory.getInstance().getServiceType(ServiceType.ORDER);

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItem.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        colEmployee.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHande"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        getEmployerid();
        getItemid();
        loadDateAndTime();

        cmdProductsId.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null){
                try {
                    getProductsDetails(newValue.toString());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    private void getProductsDetails(String newValue) throws SQLException {
        Products products = productsService.serchProducts(newValue);
        System.out.println(products);
        txtName.setText(products.getName());
        txtUnitPrice.setText(String.valueOf(products.getPrice()));
        txtStock.setText(String.valueOf(products.getQuantity()));

    }

    private void getItemid() throws SQLException {
        ArrayList<Products> all = productsService.getAll();

        ObservableList observableList = FXCollections.observableArrayList();
        for (Products products : all){
            observableList.add(products.getProductID());
        }
        cmdProductsId.setItems(observableList);
    }

    private void getEmployerid() throws SQLException {
        ArrayList<Employee> employeer =employeeService.getAll();

        ObservableList observableList = FXCollections.observableArrayList();
        for (Employee emp : employeer) {
            observableList.add((emp.getEmployeeid()));
        }
        cmdEmployeeid.setItems(observableList);
    }

    @FXML
    ObservableList<CartTM> cartTMObservableList = FXCollections.observableArrayList();

    @FXML
    void OnAddtoCartAction(ActionEvent event) {
        String empid = cmdEmployeeid.getValue().toString();
        String itemid= cmdProductsId.getValue().toString();
        String name = txtName.getText();

        int qty = Integer.parseInt(txtQty.getText());
        int stock = Integer.parseInt(txtStock.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double total = unitPrice * qty;

        if (qty>stock){
            new Alert(Alert.AlertType.ERROR,"Out of the Stock").show();
            return;
        }
        boolean isDuplicate = false;

        for (CartTM cartitem : cartTMObservableList){
            if (cartitem.getItemid().equals(itemid)){
                int newQty = cartitem.getQtyOnHande()+qty;
                double newTotal = cartitem.getUnitPrice()*newQty;

                cartitem.setQtyOnHande(newQty);
                cartitem.setTotal(newTotal);
                isDuplicate = true;

                tblCart.refresh();
                break;
            }
        }
        if (!isDuplicate){
            cartTMObservableList.add(
                    new CartTM(
                            itemid,
                            empid,
                            name,
                            qty,
                            unitPrice,
                            total
                    )
            );
        }

        tblCart.setItems(cartTMObservableList);
        calcNetTotal();
    }
    private  void calcNetTotal(){
        Double netTotal = 0.0;
        for (CartTM cartTM: cartTMObservableList){
            netTotal+=cartTM.getTotal();
        }
        lblNetTotal.setText(netTotal.toString());
    }

    @FXML
    void PlaceOrderOnAction(ActionEvent event) throws SQLException {

        String productId = cmdProductsId.getValue().toString();
        String employeeid = cmdEmployeeid.getValue().toString();
        String totalCost = lblNetTotal.getText();
        String date = lblDate.getText();

        List<OrderDetail> orderDetails = new ArrayList<>();
        cartTMObservableList.forEach( cartTM -> {
            orderDetails.add(new OrderDetail(
                    txtOrderId.getText(),
                    cartTM.getItemid(),
                    cartTM.getQtyOnHande(),
                    cartTM.getTotal()
            ));
        });
        boolean isadd = orderService.addOrder(new Order(productId, employeeid, totalCost, date, orderDetails));
        if (isadd){
            new Alert(Alert.AlertType.ERROR,"Order Place Successfully").show();
        }
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e ->{
            LocalTime localTime = LocalTime.now();
            lblTime.setText(
                    localTime.getHour()+":"+localTime.getMinute()+":"+localTime.getSecond()
            );

        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


}
