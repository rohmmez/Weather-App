package com.example.weatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyJSON_photo
{

    private String key = ""; // Google Custom Search API key
    private String cx = ""; // Search Engine ID
    public String loadJson(String query)
    {
        try
        {

            URL url = new URL ("https://www.googleapis.com/customsearch/v1?key=" +key+ "&cx=" +cx+ "&alt=json" + "&q=" +query + "&fileType=jpg,png&searchType=image&imgSize=huge");

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();


            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer(1024);
            String tmp = null;
            while((tmp = reader.readLine())!=null)
            {
                stringBuffer.append(tmp).append("\n");
            }
            reader.close();
            return stringBuffer.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }


    }
}
