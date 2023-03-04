package com.project.appakarrandu.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.project.appakarrandu.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {

    private View view;
    private TextInputLayout layoutNik, layoutName, layoutEmail, layoutPassword;
    private TextInputEditText txtNik, txtName, txtEmail, txtPassword;
    private TextView txtSignIn;
    private Button btnRegister;
    private ProgressDialog dialog;

    public SignUpFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_sign_up_fragments, container, false);
        init();
        return view;
    }

    private void init() {
        layoutNik = view.findViewById(R.id.txtlayoutniksignup);
        layoutName = view.findViewById(R.id.txtlayoutnamesignup);
        layoutEmail = view.findViewById(R.id.txtlayoutemailsignup);
        layoutPassword = view.findViewById(R.id.txtlayoutpasswordsignup);
        txtNik = view.findViewById(R.id.txtNikSignup);
        txtName = view.findViewById(R.id.txtNameSignup);
        txtEmail = view.findViewById(R.id.txtEmailSignup);
        txtPassword = view.findViewById(R.id.txtPasswordSignup);
        txtSignIn = view.findViewById(R.id.tvSignIn);
        btnRegister = view.findViewById(R.id.btnRegister);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);


        txtSignIn.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer, new SignInFragment()).commit();
        });

        btnRegister.setOnClickListener(v->{
            if (validate()){
                register();
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

        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!txtName.getText().toString().isEmpty()){
                    layoutName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!txtEmail.getText().toString().isEmpty()){
                    layoutEmail.setErrorEnabled(false);
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

    private void register() {
        dialog.setMessage("Registering");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.REGISTER, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("users");
                    SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("token", object.getString("token"));
                    editor.putString("nik", user.getString("nik"));
                    editor.putString("name", user.getString("name"));
                    editor.putString("email", user.getString("email"));
                    editor.putBoolean("isLoggedIn",true);
                    editor.apply();
                    startActivity(new Intent(((AuthActivity)getContext()), SignInFragment.class));
                    ((AuthActivity)getContext()).finish();
                    Toast.makeText(getContext(), "Register Sukses. Silahkan ke Halaman Login!", Toast.LENGTH_SHORT).show();
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
                map.put("name",txtName.getText().toString().trim());
                map.put("email",txtEmail.getText().toString().trim());
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
        if(txtName.getText().toString().isEmpty()){
            layoutName.setErrorEnabled(true);
            layoutName.setError("Name is Required");
            return false;
        }
        if(txtEmail.getText().toString().isEmpty()){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Email is Required");
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