package fz.cs.daoyun.service.impl;

import fz.cs.daoyun.mapper.LogMapper;
import fz.cs.daoyun.service.LogService;
import fz.cs.daoyun.utils.po.Log;
import fz.cs.daoyun.utils.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Transactional
@Service
public class LogServiceImpl extends BaseService<Log> implements LogService {

    @Resource
    private LogMapper logMapper;
}
