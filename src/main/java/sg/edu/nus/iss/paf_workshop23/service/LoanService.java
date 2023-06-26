package sg.edu.nus.iss.paf_workshop23.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.paf_workshop23.model.Customer;
import sg.edu.nus.iss.paf_workshop23.model.Loan;
import sg.edu.nus.iss.paf_workshop23.model.LoanDetails;
import sg.edu.nus.iss.paf_workshop23.model.Video;
import sg.edu.nus.iss.paf_workshop23.repository.CustomerRepo;
import sg.edu.nus.iss.paf_workshop23.repository.LoanDetailsRepo;
import sg.edu.nus.iss.paf_workshop23.repository.LoanRepo;
import sg.edu.nus.iss.paf_workshop23.repository.VideoRepo;

// dirty read - reading uncommitted change of a concurrent uncommitted transaction
// non repeatable read - get different get different values/rows on a concurrent transaction update
// phantom read - get different rows after executing a query if there is an update/insert transaction happening

// isolation properties
// READ_COMITTED - prevents dirty read. Need a requery if there is a transaction committed concurrently 
//	REPEATABLE_READ - prevents dirty read and non-repeatable read, but will not prevent phantom record
//	SERIALIZABLE - prevents dirty read, non-repeatable read and phantom records

@Service
// @Transactional(propagation = Propagation.REQUIRED)
public class LoanService {
    @Autowired
    LoanRepo loanRepo;

    @Autowired
    LoanDetailsRepo loanDetailsRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    VideoRepo videoRepo;

    @Transactional
    public boolean loanVideo(Customer customer, List<Video> videos){
        boolean success = false;

        //pre-requisite
        //1. retrive all video records
        List<Video> allVideos = videoRepo.findAll();

        // 1. check that all videos are available, count > 0
        boolean available = true;
        for (Video video: videos){

            List<Video> filteredVideoList = allVideos.stream()
                .filter(v -> v.getId().equals(video.getId()))
                .collect(Collectors.toList());

            if (!filteredVideoList.isEmpty()){
                if (filteredVideoList.get(0).getAvailableCount() == 0){
                    available = false;
                    // throw a custom exception/ built in exception
                } else {
                    // reducing the video quantity in the video table 
                    // for the video that the user loan 
                    Video updateVideoEntity = filteredVideoList.get(0);
                    updateVideoEntity.setAvailableCount(updateVideoEntity.getAvailableCount() - 1);
                    videoRepo.updateVideo(updateVideoEntity);
                }
            }
            
        }

        // 2. create a loan record
        // 3. create the loan details that tie to the loan
        if (available){
            Loan loan = new Loan();
            loan.setCustomerId(customer.getId());
            loan.setLoanDate(Date.valueOf(LocalDate.now()));

            Integer createdLoanId = loanRepo.createLoan(loan);

            for (Video video: videos){
                LoanDetails loanDetails = new LoanDetails();
                loanDetails.setLoanId(createdLoanId);
                loanDetails.setVideoId(video.getId());

                loanDetailsRepo.createLoanDetails(loanDetails);
            }

            success = true;
        }

        return success;
    }
}
