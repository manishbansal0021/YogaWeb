package com.example.hp_pc.yogaweb;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListingActivity extends AppCompatActivity {

    String what="";
    private ListView listView;
    private ArrayList<ListPojo> arrayList=new ArrayList<>();
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        init();
        methodListener();
        adapter=new ListAdapter(this,R.layout.listiing_item,arrayList);
        listView.setAdapter(adapter);
        what=getIntent().getStringExtra("what");
        getSupportActionBar().setTitle("what");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fetchDataFromServer();
    }

    private void fetchDataFromServer() {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.show();

        StringRequest request=new StringRequest(Request.Method.POST, URLHelper.URL_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.cancel();
                        parseResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.cancel();
                        Toast.makeText(ListingActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("cat",what);
                return map;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void parseResponse(String response) {
        try {
            JSONObject object=new JSONObject(response);
            JSONArray array=object.getJSONArray("item");

            for(int i=0;i<array.length();i++)
            {
                JSONObject arrayObject=array.getJSONObject(i);

                String id=arrayObject.getString("id");
                String type=arrayObject.getString("type");
                String category = arrayObject.getString("category");
                String description = arrayObject.getString("description");
                String img = arrayObject.getString("img");

                ListPojo pojo = new ListPojo();
                pojo.setDecription(description);
                pojo.setCategory(category);
                pojo.setId(id);
                pojo.setImg(img);
                pojo.setType(type);

                arrayList.add(pojo);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void methodListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ListPojo pojo=arrayList.get(position);
                String des=pojo.getDecription();

                Intent intent=new Intent(ListingActivity.this,DescriptionActivity.class);
                intent.putExtra("des",des);
                intent.putExtra("title",pojo.getCategory());
                startActivity(intent);
            }
        });
    }

    private void init() {
        listView= (ListView) findViewById(R.id.listView);
    }
}
