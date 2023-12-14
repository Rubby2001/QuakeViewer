package com.rubby.quakeviewer.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.rubby.quakeviewer.entity.assets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JsonRequest {
    public static List<assets> postRequest(String queryString, String ApiKey, String maxSize) {
        List<assets> resultList = new ArrayList<>();

        try {
            HttpURLConnection connection = getHttpURLConnection(ApiKey);
            String postData;
            if (Integer.parseInt(maxSize)>0 && Integer.parseInt(maxSize)<=10000){
                postData="{\"query\": \""+queryString+"\", \"start\": 0, \"size\":"+ maxSize+"}";
            }
            else{
                return resultList;
            }
            //System.out.println(postData);

            // Write the POST data to the connection
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = postData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read the response from the server
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                // Parse the JSON response
                JSONObject jsonObject = JSON.parseObject(response.toString());
                String message = jsonObject.getString("message");
                if (Objects.equals(message, "Successful.")){
                    JSONArray data = jsonObject.getJSONArray("data");
                    if (data!=null){
                        for(int i=0;i<data.size();i++){
                            JSONObject dataObject = data.getJSONObject(i);
                            //处理components，加到cms中
                            String cms="";
                            JSONArray components = dataObject.getJSONArray("components");
                            if(components!=null){
                                for(int j=0;j<components.size();j++){
                                    JSONObject componentObject = components.getJSONObject(j);
                                    cms+=componentObject.getString("product_name_cn")+",";
                                }
                            }
                            if (cms!=""){
                                // 找到最后一个逗号的索引
                                int lastIndex = cms.lastIndexOf(",");

                                // 如果最后一个逗号在字符串末尾，则截取字符串去掉最后一个逗号
                                if (lastIndex == cms.length() - 1) {
                                    cms=cms.substring(0, lastIndex);
                                }
                            }


                            //处理ip,port,domain
                            String ip = dataObject.getString("ip");
                            String port = dataObject.getString("port");
                            String domain = dataObject.getString("domain");

                            //处理service name
                            JSONObject serviceJSONObject = dataObject.getJSONObject("service");
                            String service_name = serviceJSONObject.getString("name");

                            String icp="";
                            String company="";
                            String title="";
                            String url="";

                            //处理service http
                            JSONObject httpJSONObject = serviceJSONObject.getJSONObject("http");
                            if (httpJSONObject!=null){
                                //处理title
                                title = httpJSONObject.getString("title");
                                JSONArray urlArray = JSON.parseArray(httpJSONObject.getString("http_load_url"));
                                url = urlArray.getString(0);


                                //处理icp main_license
                                JSONObject icpJSONObject = httpJSONObject.getJSONObject("icp");
                                if (icpJSONObject!=null){
                                    icpJSONObject = icpJSONObject.getJSONObject("main_licence");
                                    icp = icpJSONObject.getString("licence");
                                    company = icpJSONObject.getString("unit");
                                }
                            }

                            assets assets = new assets(i+1,url,ip,port,domain,service_name,title,cms,icp,company);
                            resultList.add(assets);
                        }

                    }
                }
            }

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    private static HttpURLConnection getHttpURLConnection(String apiKey) throws IOException {
        URL apiUrl = new URL("https://quake.360.net/api/v3/search/quake_service");
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

        // Set the necessary headers for a POST request
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("X-QuakeToken", apiKey);  // Example: Adding Authorization header

        connection.setDoOutput(true);
        return connection;
    }

//    public static void main(String[] args) {
//        // Example usage:
//        String apiUrl = "https://example.com/api";
//        String postData = "{\"key1\":\"value1\",\"key2\":\"value2\"}";
//        String fieldName = "desiredField";
//        String authToken = "yourAuthToken";  // Replace with your actual authentication token
//
//        List<String> result = postRequest(apiUrl, postData, fieldName, authToken);
//
//        // Do something with the result array
//        for (String value : result) {
//            System.out.println(value);
//        }
//    }
}

