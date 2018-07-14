package com.slsnotfound.doucument.doucument.Dao;

import com.slsnotfound.doucument.doucument.Model.Review;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewDao {

    @Select("Select * From review")
    List<Review> getAllReviews();

    @Select("Select * From review Where rproid=#{pid}")
    List<Review> getReviewByPID(@Param("pid") int pid);

    @Insert("INSERT INTO review(rproid,rpname,rstatus,rtext,rtime) VALUES (#{rproid},#{rpname},#{rstatus},#{rtext},#{rtime})")
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    int insert(Review review);
}
