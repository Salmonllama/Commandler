package studio.spiderling.Commandler;

import java.io.*;
import java.util.Properties;

public class FrameworkConfig {

    private static String fileName = "config.properties";

    static void firstTimeSetup() {
        if (new File(fileName).exists()) {
            System.out.println("FrameworkConfig file exists, skipping first time setup");
        }
        else if (new File("config.example.properties").exists()) {
            System.out.println(
                "It seems you haven't configured your properties file!\n" +
                "rename 'config.example.properties' to 'config.properties'\n" +
                "and fill in your token and desired default prefix."
            );
        }
        else {
            System.out.println("FrameworkConfig file does not exist, we need to create it!");
            // Create and set the first time config file.
            Properties properties = new Properties();
            properties.setProperty("token", "YOUR-TOKEN-HERE");
            properties.setProperty("default-prefix", "?");

            try {
                properties.store(new FileWriter(new File(fileName)), null);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
     }

     private static Properties loadConfigFile() {
        Properties properties = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(fileName);
        }
        catch (FileNotFoundException e) {
            System.out.println("Couldn't find config file " + e);
        }

        try {
            properties.load(input);
        }
        catch (IOException e) {
            System.out.println("Couldn't read the config file" + e);
        }

        return properties;
    }

    public static String getToken() {
        return loadConfigFile().getProperty("token");
    }

    public static String getDefaultPrefix() {
        return loadConfigFile().getProperty("default-prefix");
    }
    
    public static void setDefaultPrefix(String newPrefix) {
    	loadConfigFile().setProperty("default-prefix", newPrefix);
    }
}