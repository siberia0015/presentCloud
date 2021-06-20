package fz.cs.daoyun.service;

import fz.cs.daoyun.domain.Sign;
import fz.cs.daoyun.domain.StartSign;
import fz.cs.daoyun.domain.StudentSignInfo;

import java.util.Date;
import java.util.List;

public interface ISignService {

    void startSign(StartSign startSign) throws Exception;

    void endSign(Integer startSignId) throws Exception;

    StartSign signStatus(Integer id) throws Exception;

    StartSign findByparams(Integer classId, String dateString) throws Exception;

    StartSign findNearestStartSignByClassId(Integer classId) throws Exception;

    StartSign findById(Integer startSignId) throws Exception;

    Sign findByStartSignId(Integer startSignId, Long userId) throws Exception;

    List<Sign> selectAllTime(Integer classId);

    void deleteByClassid(Integer classid) throws Exception;

    void makeSign(Sign sign) throws Exception;

    /*根据签到号查询已签到学生*/
    List<StudentSignInfo> selectSigned(Integer startSignId) throws Exception;

    /*根据签到号查询未签到学生*/
    List<StudentSignInfo> selectUnsigned(Integer startSignId, Integer classId) throws Exception;

    /*查看班级所有签到事件*/
    List<StartSign> selectStartSignByClassId(Integer classId) throws Exception;
}
