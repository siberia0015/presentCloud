package fz.cs.daoyun.service;

import fz.cs.daoyun.domain.Sign;
import fz.cs.daoyun.domain.StartSign;

import java.util.Date;
import java.util.List;

public interface ISignService {

    void startSign(StartSign startSign) throws Exception;

    void endSign(Integer startSignId) throws Exception;

    StartSign signStatus(Integer id) throws Exception;

    StartSign findByparams(Integer classId, String dateString) throws Exception;

    StartSign findNearestStartSignByClassId(Integer classId) throws Exception;

    Sign findByStartSignId(Integer startSignId, Long userId) throws Exception;

    List<Sign> findAllTime(Integer classId);

    void deleteByClassid(Integer classid) throws Exception;

    void makeSign(Sign sign) throws Exception;

}
