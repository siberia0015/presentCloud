package fz.cs.daoyun.utils.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseController {

    public BaseController() {
    }

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
