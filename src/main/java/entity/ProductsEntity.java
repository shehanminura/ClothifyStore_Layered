package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductsEntity {
    private String ProductID;
    private String Name;
    private String Category;
    private String Size;
    private Double Price;
    private int Quantity;
    private String SupplierID;
    private String ImageURL;


}
