package sg.edu.nus.iss.paf_workshop23.model;

import java.sql.Date;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    private Integer id;
    private Integer customerId;

    @FutureOrPresent(message = "Loan date cannot be in the past!")
    private Date loanDate;

    @Future(message = "Return date must be in the future!")
    private Date returnDate;
}
