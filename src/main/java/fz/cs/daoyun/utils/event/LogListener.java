package fz.cs.daoyun.utils.event;

import fz.cs.daoyun.service.LogService;
import fz.cs.daoyun.utils.po.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


/**
 * 日志事件监听
 *
 * @author cjbi@outlook.com
 */
@Component
public class LogListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LogService logService;

    @EventListener
    public void handleLogEvent(Log log) {
        logger.info("Handling log event.", log);
        logService.create(log);
    }

}
