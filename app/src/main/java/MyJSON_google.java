package com.example.weatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyJSON_google
{
    private static final String API1 = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String API2 = ""; // Google geocoding API key

    public String loadJson (String add)
    {
        try
        {
            String urlString = API1 + add + API2;

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
