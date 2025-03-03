package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDetailEntity {
    private String OrderID;
    private String ProductID;
    private int Quantity;
    private Double SubTotal;

}
