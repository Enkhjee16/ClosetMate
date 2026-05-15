package nje.hu.closetmate;

import android.content.Intent;

import android.os.Bundle;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnAddClothing, btnWardrobe, btnCalendar, btnAnalytics;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

       // EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        btnWardrobe = findViewById(R.id.btnWardrobe);

        btnAddClothing = findViewById(R.id.btnAddClothing);

        btnCalendar = findViewById(R.id.btnCalendar);

        btnAnalytics = findViewById(R.id.btnAnalytics);

        btnWardrobe.setOnClickListener(v ->

                startActivity(new Intent(MainActivity.this, WardrobeActivity.class)));

        btnAddClothing.setOnClickListener(v ->

                startActivity(new Intent(MainActivity.this, AddClothingActivity.class)));

        btnCalendar.setOnClickListener(v ->

                startActivity(new Intent(MainActivity.this, CalendarActivity.class)));

        btnAnalytics.setOnClickListener(v ->

                startActivity(new Intent(MainActivity.this, AnalyticsActivity.class)));

    }

}
