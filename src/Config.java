import java.util.Properties;

public class Config
{
    private Properties configFile;

    public Config()
    {
        configFile = new java.util.Properties();
        try {
            configFile.load(this.getClass().getClassLoader().
                    getResourceAsStream("config/config.properties"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return this.configFile.getProperty(key);
    }

    public Integer getPropertyAsInt(String key) {
        return Integer.parseInt(this.configFile.getProperty(key));
    }

    public Double getPropertyAsDouble(String key) {
        return Double.parseDouble(this.configFile.getProperty(key));
    }
}