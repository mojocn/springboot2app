package springboot2app.common.util;

/**
 * @Author: kewin.cheng
 * @Date: 10/11/19 7:45 PM
 */
public class TimeUtil {

    /**
     * 私有构造函数，不允许实例化此类。
     */
    private TimeUtil() {
    }

    /**
     * 一天内的小时数。
     */
    public static final int DAY_HOURS = 24;

    /**
     * 一个小时之中的分钟数。
     */
    public static final int HOUR_MINUTES = 60;

    /**
     * 一分钟的秒数。
     */
    public static final int MINUTES_SECONDS = 60;

    /**
     * 一秒的毫秒数。
     */
    public static final int SECOND_MILLISECONDS = 1000;

    /**
     * 一天总毫秒数
     */
    public static final long DAY_MILLISECONDS = DAY_HOURS * HOUR_MINUTES * MINUTES_SECONDS * SECOND_MILLISECONDS;

    /**
     * 一天总秒数
     */
    public static final int DAY_SECONDS = DAY_HOURS * HOUR_MINUTES * MINUTES_SECONDS;

    /**
     * 获取当前时间戳。
     * @return 当前时间戳。
     */
    public static int getTimestamp() {
        return (int) (System.currentTimeMillis() / SECOND_MILLISECONDS);
    }

    /**
     * 获取当前毫秒时间戳。
     * @return 当前毫秒时间戳。
     */
    public static long getMillisTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 通过时间戳，转化成毫秒时间戳。
     * @param timestamp 待转换时间戳。
     * @return 转换之后的时间戳。
     */
    public static long getMillisTimestamp(int timestamp) {
        if (timestamp < 0) {
            throw new IllegalArgumentException("Negative timestamp value.");
        }
        return timestamp * ((long) SECOND_MILLISECONDS);
    }

    /**
     * 通过毫秒时间戳，获取时间戳。
     * @param millisTimestamp 待转换毫秒时间戳。
     * @return 转换之后的时间戳。
     */
    public static int getTimestamp(long millisTimestamp) {
        return (int) (millisTimestamp / SECOND_MILLISECONDS);
    }

    /**
     * 获取某个小时整点毫秒时间戳。
     * @param hour 整点小时数。参数范围早0~24。
     * @return 该小时的整点毫秒时间戳。
     */
    public static long getHourMillisTimestamp(int hour) {
        if (hour < 0 || hour > DAY_HOURS) {
            throw new IllegalArgumentException("Argument must be in [0. 24] range.");
        }
        return hour * HOUR_MINUTES * MINUTES_SECONDS * SECOND_MILLISECONDS;
    }

    public enum Hourage {

        /**
         * 1点。
         */
        ONE(1, "One o'clock"),

        /**
         * 2点。
         */
        TWO(2, "Two o'clock"),

        /**
         * 3点。
         */
        THREE(3, "Three o'clock"),

        /**
         * 15点。
         */
        FIFTEEN(15, "Fifteen o'clock"),

        /**
         * 23点。
         */
        TWENTY_THREE(23, "Three o'clock");

        /**
         * 小时数。
         */
        private int hour;

        /**
         * 枚举描述。
         */
        private String description;

        /**
         * 通过小时数，和枚举描述，构造枚举。
         * @param hour 小时数。
         * @param description 描述。
         */
        Hourage(int hour, String description) {
            this.hour = hour;
            this.description = description;
        }

        /**
         * 获取小时数。
         * @return 此枚举的小时数。
         */
        public int getHour() {
            return hour;
        }

        /**
         * 获取整点时间的毫秒数。
         * @return 毫秒数。
         */
        public long getHourageMillisTimestamp() {
            return getHourMillisTimestamp(this.getHour());
        }
    }
}
