package fz.cs.daoyun.controller;


import com.sun.org.apache.regexp.internal.RE;
import fz.cs.daoyun.domain.Sign;
import fz.cs.daoyun.domain.StartSign;
import fz.cs.daoyun.domain.StudentSignInfo;
import fz.cs.daoyun.domain.User;
import fz.cs.daoyun.service.IClassesService;
import fz.cs.daoyun.service.ISignService;
import fz.cs.daoyun.utils.tools.DateUtil;
import fz.cs.daoyun.utils.tools.Result;
import fz.cs.daoyun.utils.tools.ResultCodeEnum;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sign")
public class SignController {
    /*签到管理模块*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ISignService signService;

    @Autowired
    private IClassesService classesService;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 判断签到地点是否处于半径内
     * @param longitude1
     * @param latitude1
     * @param longitude2
     * @param latitude2
     * @param radius
     * @return
     */
    public static boolean isInCircle(Double longitude1, Double latitude1,Double longitude2, Double latitude2,Integer radius){
        /*longitude1, latitude1 是学生的经纬度*/
        final double EARTH_RADIUS = 6378.137;////地球半径 （千米）
        double radLat1 = rad(latitude1);
        double radLat2 = rad(latitude2);
        double radLon1 = rad(longitude1);
        double radLon2 = rad(longitude2);
        //两点之间的差值
        double jdDistance = radLat1 - radLat2;
        double wdDistance = radLon1 - radLon2;
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(jdDistance / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(wdDistance / 2), 2)));

        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000d) / 10000d;
        distance = distance*1000;//将计算出来的距离千米转为米
//        System.out.println(distance);
        double r = (double)radius ;

        r = r+1694761.9;
//        System.out.println(r);
        //判断一个点是否在圆形区域内
        if (distance > r) {
            return false;
        }
        return true;
    }

    /**
     * 查询所有用户的签到次数
     * @param classId
     * @return
     */
    @GetMapping("/findAllTime")
    // @RequiresPermissions("sign:select")
    public Result<List<Sign>> findAllTime(@RequestParam("classId") Integer classId){
        logger.info("/findAllTime" + ' ' + "查询所有用户的签到次数");
        try {
            List<Sign> signs = signService.selectAllTime(classId);
            return Result.success(signs);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * 学生签到
     * @param startSignId
     * @param classId
     * @param userId
     * @param score
     * @param longitude
     * @param latitude
     * @return
     */
    @PostMapping("/sign")
    public Result sign(@RequestParam("startSignId") Integer startSignId,
                        @RequestParam("classId") Integer classId,
                        Long userId, Integer score, Double longitude, Double latitude) {
        logger.info("/sign " + "签到");
        try {
            User user = (User)SecurityUtils.getSubject().getSession().getAttribute("user");
            if (user == null && userId == null) {
                return Result.failure(ResultCodeEnum.PARAMS_MISS);
            } else if (userId == null) {
                userId = user.getUserId();
            }
            try{
                Sign sign = signService.findByStartSignId(startSignId, userId);
                StartSign startSign = signService.findNearestStartSignByClassId(classId);
                if (startSign == null) {
                    return Result.failure(ResultCodeEnum.NO_DATA);
                } else {
                    signStatus(startSign.getId());
                    startSign = signService.findNearestStartSignByClassId(classId);
                    if (startSign == null) {
                        return Result.failure(ResultCodeEnum.TimeOut);
                    }
                }
                if (sign != null) {
                    return Result.failure(ResultCodeEnum.AlreadySign);
                } else {
                    sign = new Sign();
                    sign.setUserId(userId);
                    sign.setClassId(classId);
                    sign.setSignTime(new Date());
                    if (score !=null) sign.setScore(score); else sign.setScore(2);
                    sign.setStartSignId(startSignId);
                    sign.setLongitude(longitude);
                    sign.setLatitude(latitude);
                    signService.makeSign(sign);
                    classesService.addScore(userId, classId, 2);
                    return Result.success(sign);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure(ResultCodeEnum.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * 发起签到
     * type: 签到类型 0， 限时签到， 1， 一键签到
     * @param startSign
     * @return
     */
    // @RequiresRoles(value = {"admin", "teacher"}, logical = Logical.OR)
    @PostMapping("/startSign")
    public Result startSign(@RequestBody StartSign startSign) {
        logger.info("/startSign " + "教师发起签到");
        User user = (User)SecurityUtils.getSubject().getSession().getAttribute("user");
        if (user == null) {
            return Result.failure(ResultCodeEnum.NO_CURRENT_USER);
        }
        try {
            startSign.setUserId(user.getUserId());
            startSign.setStartTime(new Date());
            if (startSign.getScore() == null) startSign.setScore(2);
            signService.startSign(startSign);
            return  Result.success(startSign);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * 结束签到
     * @param startSignId
     * @return
     */
    @PostMapping("/endSign")
    public Result endSign(@RequestParam("startSignId") Integer startSignId) {
        logger.info("/endSign " + "教师结束签到");
        try {
            signService.endSign(startSignId);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.NO_DATA);
        }
    }

    /**
     * 查看签到状态（发起者）
     * @param startSignId
     * @return
     */
    @GetMapping("/signStatus")
    public Result signStatus(@RequestParam("startSignId") Integer startSignId) {
        logger.info("/signStatus " + "查看签到状态（发起者）");
        try {
            StartSign startSign = signService.signStatus(startSignId);
            if (startSign == null) {
                return Result.failure(ResultCodeEnum.NO_DATA);
            } else {
                if (startSign.getType() == 0 && startSign.isOver() == false) { // 限时签到
                    // 判断签到是否截止
                    try{
                        long startTime = startSign.getStartTime().getTime();
                        long currentTime = new Date().getTime();
                        if (startTime + (60 * 1000) * startSign.getTimeLimit() < currentTime) {
                            logger.info("签到时限已到");
                            if (!startSign.isOver())
                                signService.endSign(startSignId);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Result.failure(ResultCodeEnum.BAD_REQUEST);
                    }
                }
                return Result.success(startSign);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * 查看班课目前是否有签到
     * @param classId
     * @return
     */
    @GetMapping("/findStartSignByClassId")
    public Result findStartSignByClassId(@RequestParam("classId") Integer classId) {
        logger.info("/findStartSignByClassId " + "查看班课目前是否有签到");
        try {
            StartSign startSign = signService.findNearestStartSignByClassId(classId);
            if (startSign == null) {
                return Result.failure(ResultCodeEnum.NO_DATA);
            } else {
                signStatus(startSign.getId());
            }
            startSign = signService.findNearestStartSignByClassId(classId);
            return Result.success(startSign);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * 查询已签到同学
     * @param startSignId
     * @return
     */
    @GetMapping("/findSigned")
    public Result findSigned(@RequestParam("startSignId") Integer startSignId) {
        logger.info("查询已签到同学");
        try {
            List<StudentSignInfo> lists = signService.selectSigned(startSignId);
            return Result.success(lists);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }

    /**
     * 查询未签到同学
     * @param startSignId
     * @return
     */
    @GetMapping("/findUnsigned")
    public Result findUnsigned(@RequestParam("startSignId") Integer startSignId, @RequestParam("classId") Integer classId) {
        logger.info("查询未签到同学");
        try {
            List<StudentSignInfo> lists = signService.selectUnsigned(startSignId, classId);
            return Result.success(lists);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCodeEnum.BAD_REQUEST);
        }
    }
}


