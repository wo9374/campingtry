package com.example.mylogin.SEARCH.Detail;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DetailInformationRequest extends StringRequest {
    //서버 URL 설정 (php파일 연동)
    final static private String URL = "http://3.34.136.232/DetailInformation.php";
    private Map<String, String> map;

    public DetailInformationRequest(int code, Response.Listener<String> listener)
    {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("code", code + "");
        System.out.println(code+ "코드@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}