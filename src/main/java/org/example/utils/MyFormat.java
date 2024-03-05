package org.example.utils;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MyFormat {
    public static Logger logger = null;
    static {
        InputStream stream = MyFormat.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            logger = Logger.getLogger(MyFormat.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static StringBuilder fillLeft(String str, int count, boolean isLeft) {
        StringBuilder sb = new StringBuilder().append(str);
        while (sb.length()<count) {
            if (isLeft)
                sb.insert(0,' ');
            else
                sb.append(' ');
        }
        return sb.insert(0, "  ");
    }

    public static void main(String[] args) {
        logger.info("in MyClass");
        logger.warning("a test warning");
    }
}
