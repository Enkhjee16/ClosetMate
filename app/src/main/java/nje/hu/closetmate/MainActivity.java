package nje.hu.closetmate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnAddClothing, btnWardrobe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnWardrobe = findViewById(R.id.btnWardrobe);
        btnAddClothing = findViewById(R.id.btnAddClothing);

        btnWardrobe.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WardrobeActivity.class);
            startActivity(intent);
        });

        btnAddClothing.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddClothingActivity.class);
            startActivity(intent);
        });
    }
}