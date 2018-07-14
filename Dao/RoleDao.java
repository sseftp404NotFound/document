package com.slsnotfound.doucument.doucument.Dao;

import com.slsnotfound.doucument.doucument.Model.Role;
import com.slsnotfound.doucument.doucument.Model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RoleDao {
    @Insert("INSERT INTO role(password,role,question,answer) VALUES (#{password},#{role},#{question},#{answer})")
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    int insert(User user);

    @Select("Select * From role Where uid=#{userid}")
    Role getRoleByUserId(@Param("userid") String userid);

    @Update("Update role Set role=1 Where uid=#{uid}")
    void updateRole(@Param("uid") int uid);

}
