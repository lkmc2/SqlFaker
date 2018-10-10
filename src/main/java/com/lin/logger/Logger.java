package com.lin.logger;

/**
 * 日志记录器
 * @author lkmc2
 * @since 1.0.0
 */
public final class Logger {

    // 类名
    private final String clazzName;

    // 是否打印信息
    private static volatile boolean isDebug = true;

    Logger(Class<?> clazz) {
        this.clazzName = clazz.getName();
    }

    /**
     * 打印一般信息（可关闭）
     * @param msg 消息
     */
    public void info(String msg) {
        if (isDebug) {
            System.out.println(msg);
        }
    }

    /**
     * 打印重要信息（不可关闭）
     * @param msg 消息
     */
    public void print(String msg) {
        System.out.println(msg);
    }

    /**
     * 打印错误信息（不可关闭）
     * @param msg 消息
     */
    public void error(String msg) {
        System.err.println(String.format("[ %s ] %s", clazzName, msg));
    }

    /**
     * 设置是否关闭一般信息的打印
     * @param isDebug 是否关闭一般信息的打印，false为关闭
     */
    public static void setDebug(boolean isDebug) {
        Logger.isDebug = isDebug;
    }

}
