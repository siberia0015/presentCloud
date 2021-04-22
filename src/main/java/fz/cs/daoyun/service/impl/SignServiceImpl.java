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

    /*添加签到记录*/
    @Override
    public void addSign(Sign sign) throws  Exception{
        //先查询当前用户有无签到过的记录，
        // 有：通过用户名以及classid查找当前课程的签到记录， 并将本条记录的签到次数更新为签到记录条数+1，
        // 无：就插入签到记录
        String username = sign.getUserName();
        Integer classId = sign.getClassId();
        Integer score = sign.getScore();
        Integer startSignId= sign.getStartSignId();
        if(score == null){
            score = 2;
        }
        List<Sign> signs = signMapper.findByusername(username, classId);
        if ( signs == null || signs.size() == 0){
            //没有签到记录, 直接为当前用户插入一条签到记录
            Sign sig = new Sign();
            sig.setClassId(classId);
            sig.setUserName(username);
            sig.setSignTime(new Date());
            sig.setSingnTimes(1);
            sig.setScore(score);
            sig.setStartSignId(startSignId);
            signMapper.insert(sig);
        }else {
            // 有记录， 求记录长度
           Integer  size = signs.size();
            Sign sig = new Sign();
            sig.setClassId(classId);
            sig.setUserName(username);
            sig.setSignTime(new Date());
            sig.setSingnTimes(size+1);
            sig.setScore(score*(size+1));
            sig.setStartSignId(startSignId);
            signMapper.insert(sig);
        }

    }

    /*查询(需要传入当前日期（如：2000-11-11）)*/
    @Override
    public List<Sign> findAllAtCurrentDay(String date,  Integer classId ) throws  Exception{
        List<Sign> signs = signMapper.selectAllByDate(date, classId);
        return signs;
    }

    /*查询指定用户的签到记录（所有时间, 当前课程）*/
    public List<Sign> findUserSignRecord(String username, Integer classId) throws  Exception{
        List<Sign> signs = signMapper.findByusername(username, classId);
        return signs;
    }

    /*查询指定用户当前签到记录*/
    @Override
    public List<Sign> findCurrentRecord(String username, Integer classid) throws  Exception{

//        Date date = new Date();
//        String string_date = DateUtil.toDateString(date);

        List<Sign> records = signMapper.findCurrentRecord(username, classid);
        return records;
    }

    @Override
    public void deleteByClassid(Integer classid) throws  Exception {
        signMapper.deleteByClassid(classid);
    }

    @Override
    public void startSign(StartSign startSign) throws Exception {
        startSignMapper.insert(startSign);
    }

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
    public StartSign signStatus(Integer id) throws Exception {
        return startSignMapper.selectByPrimaryKey(id);
    }

    @Override
    public StartSign findByparams(Integer classid, String dateString) throws  Exception{
        return startSignMapper.findByParams(classid, dateString );
    }

    @Override
    public Sign findByStartSignId(Integer startSignId, String username)throws  Exception {
        return signMapper.selectByStartSignId(startSignId, username);
    }

    @Override
    public List<Sign> findAllTime(Integer classId) {
        return signMapper.findAlltime(classId);
    }


}
