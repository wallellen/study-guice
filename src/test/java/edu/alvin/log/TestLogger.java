package edu.alvin.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TestLogger {
    private TestLogger() {
    }

    public static Logger logger(Object obj) {
        return LoggerFactory.getLogger(obj.getClass());
    }
}
