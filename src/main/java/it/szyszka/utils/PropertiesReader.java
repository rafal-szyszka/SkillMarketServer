package it.szyszka.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by rafal on 28.09.17.
 */
public class PropertiesReader {

    private Properties myProperties;

    public PropertiesReader(Properties myProperties) {
        this.myProperties = myProperties;
    }

    public Properties readMyProperties(String fileName) {
        InputStream inputStream;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            myProperties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myProperties;
    }

}