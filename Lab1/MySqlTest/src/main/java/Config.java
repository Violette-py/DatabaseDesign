import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private Properties properties;

    public Config(String configFilePath) {
        properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(configFilePath)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDatabaseHost() {
        return properties.getProperty("db.host");
    }

    public int getDatabasePort() {
        return Integer.parseInt(properties.getProperty("db.port"));
    }

    public String getDatabaseUsername() {
        return properties.getProperty("db.username");
    }

    public String getDatabasePassword() {
        return properties.getProperty("db.password");
    }

}
