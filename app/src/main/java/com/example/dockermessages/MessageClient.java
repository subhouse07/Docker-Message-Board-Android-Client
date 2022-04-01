package com.example.dockermessages;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MessageClient {
    private final RequestQueue requestQueue;
    private static final String BASE_URL = "http://10.0.2.2:3000/";
    private static final String SEND_ENDPOINT = "sendMessage";
    private static final String GET_ENDPOINT = "getMessages";
    private static final String SEND_TAG = "SEND_TAG";
    private static final String GET_TAG = "GET_TAG";

    public MessageClient(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
    }

    public void cancelRequests() {
        requestQueue.cancelAll(GET_TAG);
        requestQueue.cancelAll(SEND_TAG);
        requestQueue.stop();
    }

    public void getMessagesJSON(Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, String.format("%s%s", BASE_URL, GET_ENDPOINT), null, responseListener, errorListener);
        request.setTag(GET_TAG);
        requestQueue.add(request);
    }

    public void sendMessage(Message message, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) throws JSONException {
        JSONObject messageJSON = new JSONObject();
        messageJSON.put("sender", message.getSender());
        messageJSON.put("msgText", message.getMsgText());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, String.format("%s%s", BASE_URL, SEND_ENDPOINT), messageJSON, responseListener, errorListener){
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        request.setTag(SEND_TAG);
        requestQueue.add(request);
    }
}
