package sg.edu.nus.iss.paf_workshop23.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_workshop23.model.Video;

@Repository
public class VideoRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String findAllVideosSql = "select * from video";

    private final String insertVideoSql = "insert into video (title, synopsis, available_count) values (?, ?, ?)";

    private final String updateVideoSql = "update video set title = ?, synopsis = ?, available_count = ? where id = ?";

    public List<Video> findAll(){
        return jdbcTemplate.query(findAllVideosSql, 
            BeanPropertyRowMapper.newInstance(Video.class));
    }

    public int updateVideo(Video video){
        int result = jdbcTemplate.update(updateVideoSql, video.getTitle(), video.getSynopsis(),
            video.getAvailableCount(), video.getId());
        return result;
    }

    public int insertVideo(Video video){
        int result = jdbcTemplate.update(insertVideoSql, video.getTitle(), video.getSynopsis()
            , video.getAvailableCount());

        return result;
    }

}

