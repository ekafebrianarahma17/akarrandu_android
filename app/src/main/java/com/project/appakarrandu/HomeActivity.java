package com.project.appakarrandu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.appakarrandu.Fragments.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FloatingActionButton fab;
    private static final int GALLERY_ADD_POST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameHomeContainer,new HomeFragment()).commit();
        //init();
    }

//    private void init(){
//        fab=findViewById(R.id.fab);
//
//        fab.setOnClickListener(v->{
//            Intent i = new Intent(this, AddPostActivity.class);
//            startActivityForResult(i, GALLERY_ADD_POST);
//        });
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==GALLERY_ADD_POST && resultCode==RESULT_OK){
//            Intent i = new Intent(HomeActivity.this,AddPostActivity.class);
//            startActivity(i);
//        }
//    }
}