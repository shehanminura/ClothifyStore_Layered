package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Employee{
    private String employeeid;
    private String userid;
    private String name;
    private String company;
    private String email;
    private String password;
}
