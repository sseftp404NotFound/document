package com.slsnotfound.doucument.doucument.Dao;

import com.slsnotfound.doucument.doucument.Model.Proposal;
import com.slsnotfound.doucument.doucument.Model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProposalDao {
    @Insert("INSERT INTO proposal(pid,pname,pwriterid,pwritername,ptext,psubmittime,pendtime,pstatus,psupport,preject,referrer,recorder,register,starter) VALUES (#{pid},#{pname},#{pwriterid},#{pwritername},#{ptext},#{psubmittime},#{pendtime},#{pstatus},#{psupport},#{preject},#{referrer},#{recorder},#{register},#{starter})")
    @Options(useGeneratedKeys = true, keyProperty = "pid")
    int insert(Proposal proposal);

    @Select("Select * From proposal")
    List<Proposal> getAllProposals();

    @Select("Select * From proposal Where pstatus=0")
    List<Proposal> getFirstProposals();

    @Select("Select * From proposal Where pstatus=1")
    List<Proposal> getSecondProposals();

    @Select("Select * From proposal Where pstatus=2")
    List<Proposal> getThirdProposals();

    @Select("Select * From proposal Where pstatus=3")
    List<Proposal> getForthProposals();

    @Select("Select * From proposal Where pstatus=4")
    List<Proposal> getLegalProposals();

    @Select("Select * From proposal Where pid=#{pid}")
    Proposal getProposalByPID(@Param("pid") int pid);

    @Update("Update proposal Set psupport=psupport+1 Where pid=#{pid}")
    void support(@Param("pid") int pid);

    @Update("Update proposal Set preject=preject+1 Where pid=#{pid}")
    void reject(@Param("pid") int pid);

    @Update("Update proposal Set pstatus=pstatus+1 Where pid=#{pid}")
    void updateStatus(@Param("pid") int pid);

    @Update("Update proposal Set referrer=#{referrer} Where pid=#{pid}")
    void updatereferrer(@Param("pid") int pid,@Param("referrer") String referrer);

    @Update("Update proposal Set recorder=#{recorder} Where pid=#{pid}")
    void updaterecorder(@Param("pid") int pid,@Param("recorder") String recorder);

    @Update("Update proposal Set register=#{register} Where pid=#{pid}")
    void updateregister(@Param("pid") int pid,@Param("register") String register);

    @Update("Update proposal Set starter=#{starter} Where pid=#{pid}")
    void updatestarter(@Param("pid") int pid,@Param("starter") String starter);
}
