package com.example.foodgyan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.foodgyan.Fragments.AddFragment;
import com.example.foodgyan.Fragments.ChatBotFragment;
import com.example.foodgyan.Fragments.HomeFragment;
import com.example.foodgyan.Fragments.SearchFragment;
import com.example.foodgyan.Fragments.TrackerFragment;
import com.example.foodgyan.R;
import com.example.foodgyan.SetGoalsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private DatabaseReference Ref;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private BottomNavigationItemView itemView;
    private String currentUserID;
    private FirebaseUser currentUser;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            ChangeActivity(LoginActivity.class);
        }

        InitializeFields();


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        androidx.appcompat.app.ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawer, mToolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_bot);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        navigationView.setNavigationItemSelectedListener(this);

        final View header = navigationView.getHeaderView(0);

        Ref.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("username").exists()) {
                    String name = dataSnapshot.child("username").getValue().toString();
                    TextView nameText = (TextView) header.findViewById(R.id.sidenav_header_name);
                    nameText.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;


            switch (menuItem.getItemId()) {

                case R.id.bot_home: {
                    selectedFragment = new HomeFragment();
                    break;
                }

                case R.id.bot_tracker: {
                    selectedFragment = new TrackerFragment();
                    break;
                }
                case R.id.bot_add:
                    selectedFragment = new AddFragment();
                    break;
                case R.id.bot_search: {
                    selectedFragment = new SearchFragment();
                    break;

                }
                case R.id.bot_chatbot: {
                    selectedFragment = new ChatBotFragment();
                    break;
                }

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };


    private void InitializeFields() {

        SetupToolbar();
        Ref = FirebaseDatabase.getInstance().getReference();
        currentUserID = currentUser.getUid();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_bot);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    }

    private void SetupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.main_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("FoodGyan");


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void ChangeActivity(Class Activity) {
        Intent intent = new Intent(MainActivity.this, Activity);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.side_profile) {

            ChangeActivity(ProfileActivity.class);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        }  else if (id == R.id.side_logout) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            ChangeActivity(LoginActivity.class);
            mAuth.signOut();


        }
        else if (id == R.id.side_goals) {
            Intent intent = new Intent(MainActivity.this, SetGoalsActivity.class);
            startActivity(intent);


        }

        return true;
    }
}
