package sg.edu.nus.iss.paf_workshop23.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDetails {
    private Integer id;

    private Integer loanId;

    private Integer videoId;
}
