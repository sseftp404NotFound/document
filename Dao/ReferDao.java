package com.slsnotfound.doucument.doucument.Dao;

import com.slsnotfound.doucument.doucument.Model.Proposal;
import com.slsnotfound.doucument.doucument.Model.Refer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReferDao {
    @Select("Select * From refer")
    List<Refer> getAllRefers();

    @Insert("INSERT INTO refer(uid,oname,rstatus,rid,rtext) VALUES (#{uid},#{oname},#{rstatus},#{rid},#{rtext})")
    @Options(useGeneratedKeys = true, keyProperty = "idrefer")
    int insert(Refer refer);

    @Select("Select * From refer Where rid=#{rid} and rstatus=0;")
    List<Refer> getRefersByRid(@Param("rid") int rid);

    @Select("Select * From refer Where uid=#{uid};")
    List<Refer> getRefersByUid(@Param("uid") int uid);

    @Select("Select oname From refer Where uid=#{uid}")
    String getOnameByUid(@Param("uid") int uid);

    @Update("Update refer Set rtext=#{rtext} Where uid=#{uid}")
    void updateRtext(@Param("uid") int uid,@Param("rtext") String rtext);

    @Update("Update refer Set rstatus=rstatus+1 Where uid=#{uid}")
    void updateRstatus(@Param("uid") int uid);

    @Select("Select * From refer Where oname='专委会' and rstatus=1")
    List<Refer> getFirstRefers();

    @Select("Select * From refer Where oname='行业分会' and rstatus=1")
    List<Refer> getSecondRefers();

    @Select("Select * From refer Where oname='研究会' and rstatus=1")
    List<Refer> getThirdRefers();
}
