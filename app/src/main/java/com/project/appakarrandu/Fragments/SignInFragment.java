package com.project.appakarrandu.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.project.appakarrandu.AuthActivity;
import com.project.appakarrandu.Constant;
import com.project.appakarrandu.HomeActivity;
import com.project.appakarrandu.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInFragment extends Fragment {

    private View view;
    private TextInputLayout layoutNik, layoutPassword;
    private TextInputEditText txtNik, txtName, txtEmail, txtPassword;
    private TextView txtSignUp;
    private Button btnLogin;
    private ProgressDialog dialog;

    public SignInFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_sign_in_fragments,container,false);
        init();
        return view;
    }

    private void init() {
        layoutNik = view.findViewById(R.id.txtlayoutniksignin);
        layoutPassword = view.findViewById(R.id.txtlayoutpasswordsignin);
        txtNik = view.findViewById(R.id.txtNik);
        txtPassword = view.findViewById(R.id.txtPassword);
        txtSignUp = view.findViewById(R.id.txtSignUp);
        btnLogin = view.findViewById(R.id.btnLogin);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        txtSignUp.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer, new SignUpFragment()).commit();
        });

        btnLogin.setOnClickListener(v->{
            if (validate()){
                login();
            }
        });

        txtNik.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!txtNik.getText().toString().isEmpty()){
                    layoutNik.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtPassword.getText().toString().length()>7){
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void login() {
        dialog.setMessage("Logging in");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.LOGIN,response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("token", object.getString("token"));
                    editor.putString("nik", user.getString("nik"));
                    editor.putString("name", user.getString("name"));
                    editor.putString("email", user.getString("email"));
                    editor.putBoolean("isLoggedIn",true);
                    editor.apply();
                    startActivity(new Intent(((AuthActivity)getContext()), HomeActivity.class));
                    ((AuthActivity) getContext()).finish();
                    Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        },error->{
            error.printStackTrace();
            dialog.dismiss();
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("nik",txtNik.getText().toString().trim());
                map.put("password",txtPassword.getText().toString().trim());
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    private boolean validate() {
        if(txtNik.getText().toString().isEmpty()){
            layoutNik.setErrorEnabled(true);
            layoutNik.setError("NIK is Required");
            return false;
        }
        if(txtPassword.getText().toString().length()<8){
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Required at least 8 character");
            return false;
        }
        return true;
    }
}