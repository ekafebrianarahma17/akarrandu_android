package com.project.appakarrandu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.project.appakarrandu.Fragments.HomeFragment;
import com.project.appakarrandu.Models.User;
import com.project.appakarrandu.Models.Warga;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {
    private Button btnPost;
    private EditText txtNama, txtNik, txtNohp, txtKec, txtDesa, txtRT, txtRW, txtAlamat;
    private ProgressDialog dialog;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        init();
    }

    private void init(){
        preferences = getApplicationContext().getSharedPreferences("warga", Context.MODE_PRIVATE);
        txtNama = findViewById(R.id.txtNamaAdd);
        txtNik = findViewById(R.id.txtNikAdd);
        txtKec = findViewById(R.id.txtKecAdd);
        txtDesa = findViewById(R.id.txtDesaAdd);
        txtNohp = findViewById(R.id.txtNohpAdd);
        txtRW = findViewById(R.id.txtRWAdd);
        txtRT = findViewById(R.id.txtRTAdd);
        txtAlamat = findViewById(R.id.txtAlamatAdd);
        btnPost = findViewById(R.id.btnPost);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        btnPost.setOnClickListener(v->{
            if (validate()){
                post();
            }
        });
    }

    public void cancelPost(View view) {
        super.onBackPressed();
    }

//    private boolean validate() {
//
//    }

    private void post(){
        dialog.setMessage("Tambahkan");
        dialog.show();

        StringRequest request=new StringRequest(Request.Method.POST,Constant.ADD_POST,response -> {
            try {
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success")){
                    JSONObject postObject = object.getJSONObject("wargas");
                    JSONObject userObject = postObject.getJSONObject("users");

                    User user = new User();
                    user.setId(userObject.getInt("id"));
                    user.setName(userObject.getString("name"));
                    user.setNik(userObject.getString("nik"));

                    Warga post = new Warga();
                    post.setId(postObject.getInt("id"));
                    post.setUser(user);
                    post.setNama(postObject.getString("nama"));
                    post.setNik(postObject.getString("nik"));
                    post.setNohp(postObject.getString("nohp"));
                    post.setKec(postObject.getString("kec"));
                    post.setDesa(postObject.getString("desa"));
                    post.setRw(postObject.getString("rw"));
                    post.setRt(postObject.getString("rt"));
                    post.setAlamat(postObject.getString("alamat"));
                    post.setDate(postObject.getString("created_at"));

                    HomeFragment.arrayList.add(0,post);
                    HomeFragment.recyclerView.getAdapter().notifyItemInserted(0);
                    HomeFragment.recyclerView.getAdapter().notifyDataSetChanged();
                    Toast.makeText(this, "POSTED", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();

        },error -> {
            error.printStackTrace();
            dialog.dismiss();

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = preferences.getString("token","");
                HashMap<String,String> map= new HashMap<>();
                map.put("Authorization","Bearer"+token);
                return map;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map= new HashMap<>();
                map.put("nama",txtNama.getText().toString().trim());
                map.put("nik",txtNik.getText().toString().trim());
                map.put("nohp",txtNohp.getText().toString().trim());
                map.put("kec",txtKec.getText().toString().trim());
                map.put("desa",txtDesa.getText().toString().trim());
                map.put("rw",txtRW.getText().toString().trim());
                map.put("rt",txtRT.getText().toString().trim());
                map.put("alamat",txtAlamat.getText().toString().trim());
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(AddPostActivity.this);
        queue.add(request);
    }

    private boolean validate() {
        if (txtNama.getText().toString().isEmpty()) {
            return false;
        }
        if (txtNik.getText().toString().isEmpty()) {

            return false;
        }
        if (txtKec.getText().toString().isEmpty()) {

            return false;
        }
        if (txtDesa.getText().toString().isEmpty()) {

            return false;
        }
        if (txtRW.getText().toString().isEmpty()) {

            return false;
        }
        if (txtRT.getText().toString().isEmpty()) {

            return false;
        }
        if (txtAlamat.getText().toString().isEmpty()) {

            return false;
        }
        if (txtNohp.getText().toString().isEmpty()) {

            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}