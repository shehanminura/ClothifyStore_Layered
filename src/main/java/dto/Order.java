package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Order {
    private String itemid;
    private String employeeId;
    private String totalcost;
    private String orderdate;

    private List<OrderDetail> orderDetails;
}
