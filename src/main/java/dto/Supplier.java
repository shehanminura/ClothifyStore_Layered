package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class Supplier {
    private String SupplierID;
    private String Name;
    private String Company;
    private String Email;

}
