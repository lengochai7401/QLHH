package com.example.qlhh;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class GoodsManager {
    private static final String TAG = "GoodsManager";
    private static final String BASE_URL = "http://192.168.1.3/api.php";

    private static GoodsManager instance;

    private GoodsManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized GoodsManager getInstance() {
        if (instance == null) {
            instance = new GoodsManager();
        }
        return instance;
    }


    public boolean addGoods(Goods goods) {
        try {
            String url = BASE_URL;
            String params = "action=insert&ten=" + goods.getTenloai() + "&tenkd=" + goods.getTenloaikd();
            String response = sendHttpRequest(url, params);
            return response.trim().equals("1");
        } catch (IOException e) {
            Log.e(TAG, "Error adding goods: " + e.getMessage());
            return false;
        }
    }

    public boolean updateGoods(Goods goods)  {
        try {
            String url = BASE_URL;
            String params = "action=update&id=" + goods.getIdloai() + "&ten=" + goods.getTenloai() + "&tenkd=" + goods.getTenloaikd();
            String response = sendHttpRequest(url, params);
            return response.trim().equals("1");
        } catch (IOException e) {
            Log.e(TAG, "Error updating goods: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteGoods(Goods goods) {
        try {
            String url = BASE_URL;
            String params = "action=delete&id=" + goods.getIdloai();
            String response = sendHttpRequest(url, params);
            return response.trim().equals("1");
        } catch (IOException e) {
            Log.e(TAG, "Error deleting goods: " + e.getMessage());
            return false;
        }
    }

    public List<Goods> getAllGoods() {
        try {
            String url = BASE_URL;
            String params = "";
            String response = sendHttpRequest(url, params);
            JSONArray jsonArray = new JSONArray(response);
            List<Goods> goodsList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("idloai");
                String name = jsonObject.getString("tenloai");
                String category = jsonObject.getString("tenloaikd");
                goodsList.add(new Goods(id, name, category));
            }
            return goodsList;
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error getting all goods: " + e.getMessage());
            return null;
        }
    }

    private String sendHttpRequest(String url, String params) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Set the request method to POST
        con.setRequestMethod("POST");

        // Set the request headers
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Set the request body (i.e. the POST parameters)
        con.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
        writer.write(params);
        writer.flush();
        writer.close();

        // Read the response from the server
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Return the response as a string
        return response.toString();
    }

}
