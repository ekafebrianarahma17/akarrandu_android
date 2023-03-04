package com.project.appakarrandu.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;
import com.project.appakarrandu.Adapter.WargasAdapter;
import com.project.appakarrandu.Constant;
import com.project.appakarrandu.HomeActivity;
import com.project.appakarrandu.Models.User;
import com.project.appakarrandu.Models.Warga;
import com.project.appakarrandu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    private View view;
    public static RecyclerView recyclerView;
    public static ArrayList<Warga> arrayList;
    private SwipeRefreshLayout refreshLayout;
    private WargasAdapter wargasAdapter;
    private MaterialToolbar toolbar;
    private SharedPreferences sharedPreferences;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_home, container, false);
        init();
        return view;
    }


    private void init() {
        sharedPreferences = getContext().getApplicationContext().getSharedPreferences("wargas", Context.MODE_PRIVATE);
        recyclerView = view.findViewById(R.id.recyclerHome);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout = view.findViewById(R.id.swipeHome);
        toolbar = view.findViewById(R.id.toolbarHome);
        ((HomeActivity)getContext()).setSupportActionBar(toolbar);

        getWargas();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWargas();
            }
        });
    }
//
    private void getWargas() {
        arrayList= new ArrayList<>();
        refreshLayout.setRefreshing(true);

        StringRequest request = new StringRequest(Request.Method.GET, Constant.POSTS,response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONArray array= new JSONArray(object.getString("wargas"));
                    for (int i = 0;i< array.length();i++){
                        JSONObject wargaObject = array.getJSONObject(i);
                        JSONObject userObject = wargaObject.getJSONObject("users");

                        User user = new User();
                        user.setId(userObject.getInt("id"));
                        user.setName(userObject.getString("name"));
                        user.setNik(userObject.getString("nik"));
                        user.setEmail(userObject.getString("email"));

                        Warga warga = new Warga();
                        warga.setId(wargaObject.getInt("id"));
                        warga.setNik(wargaObject.getString("nik"));
                        warga.setNama(wargaObject.getString("nama"));
                        warga.setKec(wargaObject.getString("kec"));
                        warga.setDesa(wargaObject.getString("desa"));
                        warga.setRw(wargaObject.getString("rw"));
                        warga.setRt(wargaObject.getString("rt"));
                        warga.setAlamat(wargaObject.getString("alamat"));
                        warga.setNohp(wargaObject.getString("nohp"));

                        arrayList.add(warga);
                    }
                    wargasAdapter = new WargasAdapter(getContext(),arrayList);
                    recyclerView.setAdapter(wargasAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            refreshLayout.setRefreshing(false);

        },error -> {
            error.printStackTrace();
            refreshLayout.setRefreshing(false);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = sharedPreferences.getString("token","");
                HashMap<String,String>map= new HashMap<>();
                map.put("Authorization","Bearer"+token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}

