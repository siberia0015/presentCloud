package fz.cs.daoyun.service.impl;


import fz.cs.daoyun.domain.LoginLog;
import fz.cs.daoyun.mapper.LoginLogMapper;
import fz.cs.daoyun.service.ILoginLogService;
import fz.cs.daoyun.utils.tools.AddressUtil;
import fz.cs.daoyun.utils.tools.HttpContextUtil;
import fz.cs.daoyun.utils.tools.IpUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Transactional
@Service("loginLogService")
public class LoginLogServiceImpl   implements ILoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    @Override
    public void saveLoginLog(LoginLog loginLog) {
        loginLog.setLogintime(new Date());
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String ip = IpUtil.getIpAddr(request);
        loginLog.setIp(ip);
        loginLog.setLocation(AddressUtil.getCityInfo(ip));
        loginLogMapper.insert(loginLog);
    }


    @Override
    public void deleteLoginLogs(Integer id){
        loginLogMapper.deleteByPrimaryKey(id);
    }




//    @Override
//    public Long findTotalVisitCount() {
//        return this.baseMapper.findTotalVisitCount();
//    }
//
//    @Override
//    public Long findTodayVisitCount() {
//        return this.baseMapper.findTodayVisitCount();
//    }
//
//    @Override
//    public Long findTodayIp() {
//        return this.baseMapper.findTodayIp();
//    }
//
//    @Override
//    public List<Map<String, Object>> findLastSevenDaysVisitCount(User user) {
//        return null;
//    }
//
//    @Override
//    public List<Map<String, Object>> findLastSevenDaysVisitCount(User user) {
//        return this.baseMapper.findLastSevenDaysVisitCount(user);
//    }
}
