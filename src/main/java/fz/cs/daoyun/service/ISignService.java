package fz.cs.daoyun.service;

import fz.cs.daoyun.domain.Sign;
import fz.cs.daoyun.domain.StartSign;

import java.util.Date;
import java.util.List;

public interface ISignService {


    /*添加签到记录*/
    void addSign(Sign sign) throws  Exception;

    /*查询(需要传入当前日期（如：2000-11-11）)*/
    List<Sign> findAllAtCurrentDay(String date,  Integer classId ) throws  Exception;

    /*查询指定用户当前签到记录*/
    List<Sign> findCurrentRecord(String username, Integer classid) throws Exception;

    void deleteByClassid(Integer classid) throws Exception;

    void startSign(StartSign startSign) throws Exception;

    void endSign(Integer startSignId) throws Exception;

    StartSign signStatus(Integer id) throws Exception;

    StartSign findByparams(Integer classid, String dateString) throws Exception;

    Sign findByStartSignId(Integer startSignId, String username) throws Exception;

    List<Sign> findAllTime(Integer classId);

}
