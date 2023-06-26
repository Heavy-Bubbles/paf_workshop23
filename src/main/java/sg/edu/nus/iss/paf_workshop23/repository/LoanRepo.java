package sg.edu.nus.iss.paf_workshop23.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_workshop23.model.Loan;

@Repository
public class LoanRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String selectSql = "select * from loan";

    private final String insertSql = "insert into loan values (null, ?, ?, ?)";

    public List<Loan> findAllLoans(){
        return jdbcTemplate.query(selectSql, BeanPropertyRowMapper.newInstance(Loan.class));
    }

    public Integer createLoan(Loan loan){
        KeyHolder generatedKey = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertSql, new String[] {"id"});
                ps.setInt(1, loan.getCustomerId());
                ps.setDate(2, loan.getLoanDate());
                ps.setDate(3, loan.getReturnDate());
                return ps;
            }
            
        };

        jdbcTemplate.update(psc, generatedKey);
        Integer result = generatedKey.getKey().intValue();
        return result;
    }
}
