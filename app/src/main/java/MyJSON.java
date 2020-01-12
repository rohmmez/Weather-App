package com.example.weatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyJSON
{
    private static final String API ="https://api.forecast.io/forecast/fcc8d79dae85b12e892927e7b394023e/";

    public String loadJson (String loc)
    {
        try
        {
            String urlString = API + loc;

            URL url = new URL(urlString);

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
