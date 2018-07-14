package com.slsnotfound.doucument.doucument.Dao;

import com.slsnotfound.doucument.doucument.Model.Rule;
import com.slsnotfound.doucument.doucument.Model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RuleDao {
    @Select("Select * From rule")
    List<Rule> getAllRules();

    @Insert("INSERT INTO rule(rwritername,rname,rtext) VALUES (#{rwritername},#{rname},#{rtext})")
    @Options(useGeneratedKeys = true, keyProperty = "rid")
    int insert(Rule rule);

    @Select("Select * From rule Where rid=#{rid}")
    Rule getRuleByRID(@Param("rid") int rid);
}
