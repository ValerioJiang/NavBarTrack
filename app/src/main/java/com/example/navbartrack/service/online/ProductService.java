package com.example.navbartrack.service.online;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.navbartrack.model.UserLogin;
import com.example.navbartrack.service.VolleySingleton;
import com.example.navbartrack.util.SessionManagement;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ProductService {
    private String baseUrl = "https://lam21.iot-prism-lab.cs.unibo.it/products";
    private String param = "?barcode=0000000000X00";
    private String accessToken;
    Context context;
    public static final Gson gson = new Gson();
    String res;

    public ProductService(Context ctx) {
        this.context = ctx;
    }


    //Asyncronous
    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String msg);
    }


    public void getProductByBarcode(String productBarcode, String accessToken, ProductService.VolleyResponseListener volleyResponseListener) throws JSONException {

        String urlProductByBarcode = baseUrl + "?barcode=" + productBarcode;
        this.accessToken = accessToken;

        StringRequest request = new StringRequest(Request.Method.GET, urlProductByBarcode, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.i("VOLLEY", response);

                volleyResponseListener.onResponse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                volleyResponseListener.onError("Error");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+accessToken.replaceAll("\"",""));
                return headers;
            }
        };

        VolleySingleton.getInstance(this.context).addToRequestQueue(request);
    }
}
