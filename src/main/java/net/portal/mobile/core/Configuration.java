package net.portal.mobile.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import net.portal.utilities.Utilities;

public class Configuration {

    private String fileName;

    public Configuration(String fileName) {
        this.fileName = fileName;
    }

    public static String readApplicationFile(String key) throws Exception {
    	
        String value;
        
        try {
            Properties prop = new Properties();
            File f = new File(Utilities.getPath() + "//config.properties");
            
            if (f.exists()) {
                prop.load(new FileInputStream(f));
                value = prop.getProperty(key);
            } else {
                throw new Exception("File not found");
            }
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Failed to read from application.properties file.");
            throw ex;
        }
        if (value == null) {
            throw new Exception("Key not found in properties file");
        }
        return value;
    }

    public static Properties readTestData(String fileName) throws Exception {
    	
        Properties prop = new Properties();
        try {
            File f = new File(
                    Utilities.getPath() + "//src//test//resources//testdata//"
                            + fileName + ".properties");
            if (f.exists()) {
                prop.load(new FileInputStream(f));
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found " + fileName);
        }
        return prop;
    }

    public static String readTestData(String key, String file)
            throws Exception {
        Properties prop = new Properties();
        String value;
        try {
            File f = new File(
                    Utilities.getPath() + "//src//test//resources//testdata//"
                            + file + ".properties");
            if (f.exists()) {
                prop.load(new FileInputStream(f));
                value = prop.getProperty(key);
            } else {
                throw new Exception("File not found");
            }
        } catch (FileNotFoundException ex) {
            throw ex;
        }
        if (value == null) {
            throw new Exception("Key not found in properties file");
        }
        return value;
    }
    
    public static void updatePropertyTestData(String fileName, String key, String value)  {
        Properties props = new Properties();
        File f = new File("src//test//resources//testdata//"+fileName+".properties");
        try {
            final FileInputStream configStream = new FileInputStream(f);
            props.load(configStream);
            configStream.close();

            props.setProperty(key, value);

            final FileOutputStream output = new FileOutputStream(f);
            props.store(output, "");
            try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            output.close();
            try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        } catch (final IOException ex) {
            System.out.println(ex);
        }
    }
}
