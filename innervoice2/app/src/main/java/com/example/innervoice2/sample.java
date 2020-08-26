package com.example.innervoice2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class sample extends AppCompatActivity  {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    DatabaseReference ref;
    ArrayList<info> list;
    ArrayList<info> songslist;
    ArrayList<info> lyricslist;
    RecyclerView recyclerView;
    SearchView searchView;
    Adapterclass.Recyclerviewclicklistener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        toolbar=findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.nav_view);
        ref= FirebaseDatabase.getInstance().getReference().child("details");
        recyclerView = findViewById(R.id.recview);
        searchView=findViewById(R.id.search);

        getSupportActionBar();
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,
                toolbar,0,0);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity(new Intent(sample.this,MainActivity.class));
                        break;
                    case R.id.About:
                        startActivity(new Intent(sample.this,About.class));
                        break;
                }
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ref!=null){
            ref.addValueEventListener((new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        list = new ArrayList<>();
                        songslist=new ArrayList<>();
                        lyricslist=new ArrayList<>();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            list.add(ds.getValue(info.class));
                            songslist.add(ds.getValue(info.class));
                            lyricslist.add(ds.getValue(info.class));
                        }
                         setOncClickListener(); 
                        Adapterclass adapterclass = new Adapterclass(list,listener);
                        recyclerView.setAdapter(adapterclass);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            }));
        }
        if(searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
    }

    private void setOncClickListener () {
        listener = new Adapterclass.Recyclerviewclicklistener() {
            @Override
            public void onClick(View v, int position) {
                 position = recyclerView.getChildLayoutPosition(v);
                 Intent i =new Intent(v.getContext(),songpage.class);
                 i.putExtra("songspace",songslist.get(position).getTrackpath());
                 i.putExtra("title", list.get(position).getSongname());
                 i.putExtra("lyricsspace",lyricslist.get(position).getSonglyrics());
                 v.getContext().startActivity(i);
            }
        };
    }

    private void search(String str) {
        ArrayList<info> mylist =new ArrayList<>();
        for (info object:list){
            if(object.getSongname().toLowerCase().contains(str.toLowerCase())){
                mylist.add(object);
            }
        }
        Adapterclass adapterclass=new Adapterclass(mylist,listener);
        recyclerView.setAdapter(adapterclass);
    }


}