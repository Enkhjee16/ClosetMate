package nje.hu.closetmate;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.widget.Button;

public class WardrobeActivity extends AppCompatActivity {

    private RecyclerView recyclerClothing;
    private TextView txtEmpty, txtItemCount;

    private DatabaseHelper databaseHelper;
    private ArrayList<ClothingItem> clothingList;
    private ClothingAdapter clothingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);

        recyclerClothing = findViewById(R.id.recyclerClothing);
        txtEmpty = findViewById(R.id.txtEmpty);
        txtItemCount = findViewById(R.id.txtItemCount);
        Button btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        databaseHelper = new DatabaseHelper(this);

        recyclerClothing.setLayoutManager(new LinearLayoutManager(this));

        loadClothingItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadClothingItems();
    }

    private void loadClothingItems() {
        clothingList = databaseHelper.getAllClothingItems();

        clothingAdapter = new ClothingAdapter(clothingList);
        recyclerClothing.setAdapter(clothingAdapter);

        txtItemCount.setText(clothingList.size() + " items");

        if (clothingList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
            recyclerClothing.setVisibility(View.GONE);
        } else {
            txtEmpty.setVisibility(View.GONE);
            recyclerClothing.setVisibility(View.VISIBLE);
        }
    }
}
