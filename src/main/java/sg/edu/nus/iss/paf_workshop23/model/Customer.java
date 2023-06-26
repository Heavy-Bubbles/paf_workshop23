package sg.edu.nus.iss.paf_workshop23.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private Integer id;
    @NotBlank (message = "First name is mandatory!")
    private String firstName;
    
    @NotBlank (message = "First name is mandatory!")
    private String lastName;
    
}
