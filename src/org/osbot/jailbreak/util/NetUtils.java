package org.osbot.jailbreak.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ethan on 1/18/2018.
 */
public class NetUtils {


    public static HttpURLConnection getConnection(String url) throws IOException {
        final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; …) Gecko/20100101 Firefox/57.0";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", USER_AGENT);
        return connection;
    }

    public static boolean isValidHwid(String hwid) {
        StringBuilder parameters = new StringBuilder();
        String VERIFY_ACCESS_URL = "http://discountbotting.com/hwid/check/check.php?";
        parameters.append("search=").append(hwid).append("&submit=Search");
        String response = null;
        try {
            response = NetUtils.postResponse(VERIFY_ACCESS_URL, parameters.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null) {
            if (response.contains("true")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isVIP(String id) {
        final String VERIFY_VIP_URL = "http://discountbotting.com/private/check/paid.php?";
        StringBuilder parameters = new StringBuilder();
        parameters.append("uid=").append(id).append("&submit=Search");
        String response = null;
        try {
            response = NetUtils.postResponse(VERIFY_VIP_URL, parameters.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null) {
            if (response.trim().equals("1")) {
                return true;
            }
        }
        return false;
    }

    public static String getResponse(String url) throws Exception {
        HttpURLConnection con = getConnection(url);
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString().trim();
    }

    public static String postResponse(String url, String parameter) throws IOException {
        HttpURLConnection connection = getConnection(url);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(parameter.getBytes());
        outputStream.close();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString().trim();
        }
        System.out.println("HTTP request error!");
        return null;
    }
}
