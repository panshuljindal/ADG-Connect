package com.example.adginternals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
            switch (i){
                case 0:
                    Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(this, "alerts", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(this, "mom", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                    break;
            }

        return true;
        });
    }
}