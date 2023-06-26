package sg.edu.nus.iss.paf_workshop23.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_workshop23.model.LoanDetails;

@Repository
public class LoanDetailsRepo {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String insertSQL = "insert into loan_details values (null, ?, ?)";

    public Integer createLoanDetails(LoanDetails loanDetails){
        KeyHolder generatedKey = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertSQL, new String [] {"id"});
                ps.setInt(1, loanDetails.getLoanId());
                ps.setInt(2, loanDetails.getVideoId());
                return ps;
            }
            
        };

        jdbcTemplate.update(psc, generatedKey);
        Integer result = generatedKey.getKey().intValue();
        return result;
    }
}
