package Control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String PROPERTY_FILE ="./client.ini";
    public static int PORT;
    public static String IP;
    static {
        Properties properties = new Properties();
        try(FileInputStream propertiesFile= new FileInputStream(PROPERTY_FILE)){
            properties.load(propertiesFile);
            PORT=Integer.parseInt(properties.getProperty("PORT"));
            IP =properties.getProperty("IP_ADDRESS");
        }catch (FileNotFoundException e){
            System.out.println("Properties файл не найден");
        }catch (IOException e){
            System.out.println("Ошибка чтения файла");
        }
    }
}
