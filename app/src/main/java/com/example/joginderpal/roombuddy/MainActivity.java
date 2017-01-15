package com.example.joginderpal.roombuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText ed1;
    Button gallery,register,location;
    ImageView image;
    Bitmap bitmap;
    int PICK=1;
    RequestQueue requestQueue,requestQueue1;
    List<Double> double1;
    List<Double> double2;
    String addres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1= (EditText) findViewById(R.id.address);
        location= (Button) findViewById(R.id.buttonone);
        gallery= (Button) findViewById(R.id.gallery);
        register= (Button) findViewById(R.id.register);
        image= (ImageView) findViewById(R.id.image);
        requestQueue= Volley.newRequestQueue(this);
        requestQueue1=Volley.newRequestQueue(this);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"SELECT PICTURE"),PICK);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog=ProgressDialog.show(MainActivity.this,"Uploading....","Please Wait....",false,false);
                StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://192.168.0.103/jsonpract.php",

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                               progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    JSONObject jsonObject1=jsonObject.getJSONObject("success");
                                    Toast.makeText(getApplicationContext(),jsonObject1.getString("register"),Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(MainActivity.this,retrieve.class);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                   Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();

                            }
                        }

                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        String image=getStringImage(bitmap);

                       String lat= String.valueOf(getIntent().getExtras().getDouble("latitude"));
                       String lon= String.valueOf(getIntent().getExtras().getDouble("longitude"));
                     //   String loc=getIntent().getExtras().getString("location");

                        HashMap<String,String> hash=new HashMap<String,String>();
                       hash.put("address",addres);
                       // hash.put("address",loc);
                        hash.put("latitude",lat);
                        hash.put("longitude",lon);
                        hash.put("image",image);
                        return hash;
                    }
                };

               requestQueue.add(stringRequest);





            }
        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  final String[] la = new String[1];
             //   final String[] lon = new String[1];
                final ProgressDialog progressDialog=ProgressDialog.show(MainActivity.this,"Wait for a second","",false,false);
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+ed1.getText().toString()+"&key=AIzaSyAXKp_EfSTFqGj_bHi7aMn7HDOJlRfrgfA", null,

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                try {
                                    addres=ed1.getText().toString();
                                    double1=new ArrayList<Double>();
                                    double2=new ArrayList<Double>();
                                    progressDialog.dismiss();
                                    JSONArray jsonArray=response.getJSONArray("results");
                                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                                    JSONObject jsonObject1=jsonObject.getJSONObject("geometry");
                                    JSONObject jsonObject2=jsonObject1.getJSONObject("location");
                                    double1.add(jsonObject2.getDouble("lat"));
                                    double2.add(jsonObject2.getDouble("lng"));

                                    Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                                    intent.putExtra("lat",double1.get(0));
                                    intent.putExtra("lon",double2.get(0));
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }

                );


                requestQueue1.add(jsonObjectRequest);
            }
        });


    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK && resultCode==RESULT_OK && data!=null && data.getData()!=null ){

            Uri filepath=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
               image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
