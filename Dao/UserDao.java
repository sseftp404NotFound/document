package com.slsnotfound.doucument.doucument.Dao;

import com.slsnotfound.doucument.doucument.Model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("Select * From user")
    List<User> getAllUsers();

    @Select("Select * From user Where username=#{username}")
    User getUserByUsername(@Param("username") String username);

    @Select("Select username From user Where uid=#{uid}")
    String getUsernameByUid(@Param("uid") int uid);

    @Select("Select * From user Where uid=#{userid}")
    User getUserByUserID(@Param("userid") String userid);

    @Insert("INSERT INTO user(username,sex,birthday,phonenum) VALUES (#{username},#{sex},#{birthday},#{phonenum})")
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    int insert(User user);

    @Update("Update user Set name=#{name},address=#{address},company=#{company},profession=#{profession},email=#{email},wechat=#{wechat} Where uid=#{uid}")
    int informaintain(@Param("uid") int uid, @Param("name") String name, @Param("address") String address, @Param("company") String company, @Param("profession") String profession, @Param("email") String email, @Param("wechat") String wechat);

}
