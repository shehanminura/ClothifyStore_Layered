package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class SupplierEntity {
    private String SupplierID;
    private String Name;
    private String Company;
    private String Email;
}
