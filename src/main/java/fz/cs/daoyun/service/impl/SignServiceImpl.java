package fz.cs.daoyun.service.impl;

import fz.cs.daoyun.domain.Sign;
import fz.cs.daoyun.domain.StartSign;
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

    @Override
    public StartSign findByparams(Integer classId, String dateString) throws  Exception {return startSignMapper.findByParams(classId, dateString );}

    @Override
    public StartSign findNearestStartSignByClassId(Integer classId) throws Exception {
        List<StartSign> startSigns = startSignMapper.findStartSignByClassId(classId);
        if (startSigns.size() == 0) {
            return null;
        } else {
            return startSigns.get(0);
        }
    }

    @Override
    public Sign findByStartSignId(Integer startSignId, Long userId)throws  Exception {return signMapper.selectByStartSignId(startSignId, userId);}

    @Override
    public List<Sign> findAllTime(Integer classId) {return signMapper.findAlltime(classId);}

    @Override
    public void deleteByClassid(Integer classId) throws  Exception {signMapper.deleteByClassId(classId);}

    @Override
    public void makeSign(Sign sign) throws Exception {signMapper.insert(sign);}

}
