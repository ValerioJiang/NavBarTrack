package com.example.navbartrack.service.online;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import com.example.navbartrack.model.UserLogin;
import com.example.navbartrack.service.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class UserLoginService {
    public static final String loginUrl = "https://lam21.modron.network/auth/login";

    Context context;
    public static final Gson gson = new Gson();
    String res;

    public UserLoginService(Context ctx){
        this.context = ctx;
    }


    //Asyncronous
    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(String msg);
    }

    /**
     * LOGIN
     */
    public void login(UserLogin logUser, UserRegistrationService.VolleyResponseListener volleyResponseListener) throws JSONException {


        final String requestBody = gson.toJson(logUser);

        StringRequest request = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {

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
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = null;
                if (response != null) {
                    try {
                        responseString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    // can get more details such as response.headers
                }
                return  Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        VolleySingleton.getInstance(this.context).addToRequestQueue(request);
    }
}
