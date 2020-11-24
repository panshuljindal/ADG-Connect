package com.example.adginternals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.view.View.*;

public class MOMdetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_o_mdetails);

       CharSequence bulletedList = BulletTextUtils.makeBulletList(5,"Everyone has to get atleast 5 participants from their end.",
               "Valid reason has to be provided for not attending the meeting in the ADG Internals app.",
               "Desk duties will be alloted and everyone is asked to report on time.");
       TextView t = findViewById(R.id.momDialogPointsDiscussed);
       t.setText(bulletedList);

        Button okBtn = findViewById(R.id.momDialogOKBtn);

        okBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
        });
    }
}