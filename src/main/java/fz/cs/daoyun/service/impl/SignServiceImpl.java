package fz.cs.daoyun.service.impl;

import fz.cs.daoyun.domain.Sign;
import fz.cs.daoyun.domain.StartSign;
import fz.cs.daoyun.domain.StudentSignInfo;
import fz.cs.daoyun.mapper.SignMapper;
import fz.cs.daoyun.mapper.StartSignMapper;
import fz.cs.daoyun.service.ISignService;
import fz.cs.daoyun.utils.tools.DateUtil;
import fz.cs.daoyun.utils.tools.Result;
import fz.cs.daoyun.utils.tools.ResultCodeEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class SignServiceImpl implements ISignService {

    @Resource
    private SignMapper signMapper;

    @Resource
    private StartSignMapper startSignMapper;

    @Override
    public void startSign(StartSign startSign) throws Exception {startSignMapper.insert(startSign);}

    /**
     * 结束签到
     * @param startSignId
     * @throws Exception
     */
    @Override
    public void endSign(Integer startSignId) throws Exception {
        StartSign startSign = startSignMapper.selectByPrimaryKey(startSignId);
        if (startSign.getType() == 0) {
            long startTime = startSign.getStartTime().getTime();
            long currentTime = new Date().getTime();
            long endTime = Math.min(currentTime, startTime + (60 * 1000) * startSign.getTimeLimit());
            startSignMapper.endSign(startSignId, new Date(endTime));
        } else if (startSign.getType() == 1) {
            startSignMapper.endSign(startSignId, new Date());
        }
    }

    /**
     * 查看签到状态
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public StartSign signStatus(Integer id) throws Exception {return startSignMapper.selectByPrimaryKey(id);}

    /**
     * 根据参数查找签到
     * @param classId
     * @param dateString
     * @return
     * @throws Exception
     */
    @Override
    public StartSign findByparams(Integer classId, String dateString) throws  Exception {return startSignMapper.findByParams(classId, dateString );}

    /**
     * 根据班级ID查找最近的一次签到
     * @param classId
     * @return
     * @throws Exception
     */
    @Override
    public StartSign findNearestStartSignByClassId(Integer classId) throws Exception {
        List<StartSign> startSigns = startSignMapper.findStartSignByClassId(classId);
        if (startSigns.size() == 0) {
            return null;
        } else {
            return startSigns.get(0);
        }
    }

    /**
     * 根据id查找签到
     * @param startSignId
     * @return
     * @throws Exception
     */
    @Override
    public StartSign findById(Integer startSignId) throws Exception {
        StartSign startSign = startSignMapper.selectByPrimaryKey(startSignId);
        return startSign;
    }

    /**
     * 根据签到号和用户号查找签到记录
     * @param startSignId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Sign findByStartSignId(Integer startSignId, Long userId)throws  Exception {return signMapper.selectByStartSignId(startSignId, userId);}

    /**
     * 根据班级号查询所有签到记录
     * @param classId
     * @return
     */
    @Override
    public List<Sign> selectAllTime(Integer classId) {return signMapper.findAlltime(classId);}

    /**
     * 删除该班级的所有签到记录
     * @param classId
     * @throws Exception
     */
    @Override
    public void deleteByClassid(Integer classId) throws  Exception {signMapper.deleteByClassId(classId);}

    /**
     * 签到
     * @param sign
     * @throws Exception
     */
    @Override
    public void makeSign(Sign sign) throws Exception {signMapper.insert(sign);}

    /**
     * 选择已签到的同学
     * @param startSignId
     * @return
     * @throws Exception
     */
    @Override
    public List<StudentSignInfo> selectSigned(Integer startSignId) throws Exception {
        return signMapper.selectSigned(startSignId);
    }

    /**
     * 选择未签到的同学
     * @param startSignId
     * @param classId
     * @return
     * @throws Exception
     */
    @Override
    public List<StudentSignInfo> selectUnsigned(Integer startSignId, Integer classId) throws Exception {
        return signMapper.selectUnsigned(startSignId, classId);
    }

    @Override
    public List<StartSign> selectStartSignByClassId(Integer classId) throws Exception {
        return startSignMapper.findStartSignByClassId1(classId);
    }
}
