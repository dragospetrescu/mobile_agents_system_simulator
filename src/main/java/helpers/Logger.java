package helpers;

public class Logger {

    public static void i(String message) {
        System.out.println(LoggerTypes.INFO + " -> " +message);
    }
    public static void d(String message) {
        System.out.println(LoggerTypes.DEBUG + " -> " +message);
    }
    public static void w(String message) {
        System.out.println(LoggerTypes.WARNING + " -> " +message);
    }
    public static void e(String message) {
        System.out.println(LoggerTypes.ERROR + " -> " +message);
    }


    enum LoggerTypes {
        INFO,
        DEBUG,
        WARNING,
        ERROR
    }
}
