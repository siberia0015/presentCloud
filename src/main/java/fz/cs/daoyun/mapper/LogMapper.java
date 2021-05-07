package fz.cs.daoyun.mapper;


import fz.cs.daoyun.utils.po.Log;
import fz.cs.daoyun.utils.tools.MyMapper;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface LogMapper extends MyMapper<Log> {

}
