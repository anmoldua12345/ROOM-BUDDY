package com.example.joginderpal.roombuddy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by joginderpal on 13-01-2017.
 */
public class retrieve extends Activity {

    TextView tx;
    List<String> li;
    List<String> li1;
    List<String> li2;
    RequestQueue requestQueue;
    RecyclerView rv;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
       // tx= (TextView) findViewById(R.id.text3);
        rv= (RecyclerView) findViewById(R.id.rv);
        requestQueue= Volley.newRequestQueue(this);
        final ProgressDialog progressDialog=ProgressDialog.show(this,"Wait a second","",false,false);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://192.168.0.103/retrieve.php",


                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            li=new ArrayList<String>();
                            li1=new ArrayList<String>();
                            li2=new ArrayList<String>();
                            progressDialog.dismiss();
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.getString("Connection").equals("CONNECTION OK")){
                                for(int i=0;i<jsonObject.getJSONArray("success").length();i++){
                                    JSONObject s= jsonObject.getJSONArray("success").getJSONObject(i);
                                   li.add(s.getString("address"));
                                    li1.add(s.getString("id"));
                                }
                              for (int j=0;j<li1.size();j++){
                                  li2.add(jsonObject.getString(li1.get(j)));
                              }
                                layoutManager=new LinearLayoutManager(retrieve.this);
                                rv.setLayoutManager(layoutManager);
                                adapter=new RecyclerAdapter(retrieve.this,li,li2);
                                rv.setAdapter(adapter);

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                    }
                }


        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return super.getParams();
            }
        };

        requestQueue.add(stringRequest);
    }
}
