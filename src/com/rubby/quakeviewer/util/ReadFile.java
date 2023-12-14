package com.rubby.quakeviewer.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {

    private String apikey;
    private String maxSize;

    public void readFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("apikey=")) {
                    apikey = line.substring("apikey=".length());
                } else if (line.startsWith("maxSize=")) {
                    maxSize = line.substring("maxSize=".length());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getApikey() {
        return apikey;
    }

    public String getMaxSize() {
        return maxSize;
    }

//    public static void main(String[] args) {
//        ConfigReader configReader = new ConfigReader();
//        configReader.readConfigFile("path/to/your/config/file.txt");
//
//        System.out.println("Apikey: " + configReader.getApikey());
//        System.out.println("MaxSize: " + configReader.getMaxSize());
//    }
}
