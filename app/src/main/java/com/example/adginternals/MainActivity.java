package com.example.adginternals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {
    private SmoothBottomBar smoothBottomBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        smoothBottomBar = (SmoothBottomBar) findViewById(R.id.bottomBar);

        smoothBottomBar.setOnItemSelectedListener(i -> {
            Fragment selectedFragment = null;
            switch (i){
                case 0:
                    //Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                    selectedFragment = new HomeFragment();
                    setTitle("Home");
                    break;
                case 1:
                    //Toast.makeText(this, "alerts", Toast.LENGTH_SHORT).show();
                    selectedFragment = new AlertsFragment();
                    setTitle("Alerts");
                    break;
                case 2:
                    //Toast.makeText(this, "mom", Toast.LENGTH_SHORT).show();
                    selectedFragment = new MomFragment();
                    setTitle("M.O.M");
                    break;
                case 3:
                    //Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                    selectedFragment = new ProfileFragment();
                    setTitle("Profile");
                    break;
            }

            //Frag Transaction
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();


        return true;
        });
        setTitle("Home");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

    }
}