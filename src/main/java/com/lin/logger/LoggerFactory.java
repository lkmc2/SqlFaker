package com.lin.logger;

/**
 * 日志记录器生成工厂
 * @author lkmc2
 * @since 1.0.0
 */
public final class LoggerFactory {

    /**
     * 获取日志记录器
     * @param clazz 类的类型
     * @return 日志记录器
     */
    public static Logger getLogger(Class<?> clazz) {
        return new Logger(clazz);
    }

}
