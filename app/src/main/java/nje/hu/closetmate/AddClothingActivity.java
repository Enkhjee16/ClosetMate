// AddClothingActivity.java
package nje.hu.closetmate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class AddClothingActivity extends AppCompatActivity {

    private ImageView imgClothing;
    private EditText edtName;
    private Spinner spinnerCategory, spinnerColor, spinnerSeason, spinnerStyle, spinnerOccasion;
    private Button btnChooseImage, btnSaveClothing;

    private Uri selectedImageUri;
    private DatabaseHelper databaseHelper;

    private final ActivityResultLauncher<String> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    imgClothing.setImageURI(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothing);

        databaseHelper = new DatabaseHelper(this);

        imgClothing = findViewById(R.id.imgClothing);
        edtName = findViewById(R.id.edtName);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerColor = findViewById(R.id.spinnerColor);
        spinnerSeason = findViewById(R.id.spinnerSeason);
        spinnerStyle = findViewById(R.id.spinnerStyle);
        spinnerOccasion = findViewById(R.id.spinnerOccasion);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnSaveClothing = findViewById(R.id.btnSaveClothing);

        setupSpinners();

        btnChooseImage.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
        btnSaveClothing.setOnClickListener(v -> saveClothingItem());
    }

    private void setupSpinners() {
        setSpinner(spinnerCategory, new String[]{"Top", "Bottom", "Shoes", "Outerwear", "Accessory"});
        setSpinner(spinnerColor, new String[]{"Black", "White", "Blue", "Red", "Green", "Brown", "Beige", "Gray", "Pink", "Other"});
        setSpinner(spinnerSeason, new String[]{"Spring", "Summer", "Autumn", "Winter", "All Season"});
        setSpinner(spinnerStyle, new String[]{"Casual", "Formal", "Sport", "Streetwear", "Classic"});
        setSpinner(spinnerOccasion, new String[]{"School", "Work", "Party", "Travel", "Daily"});
    }

    private void setSpinner(Spinner spinner, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                items
        );
        spinner.setAdapter(adapter);
    }

    private void saveClothingItem() {
        String name = edtName.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter item name", Toast.LENGTH_SHORT).show();
            return;
        }

        String imageUri = selectedImageUri != null ? selectedImageUri.toString() : "";

        String category = spinnerCategory.getSelectedItem().toString();
        String color = spinnerColor.getSelectedItem().toString();
        String season = spinnerSeason.getSelectedItem().toString();
        String style = spinnerStyle.getSelectedItem().toString();
        String occasion = spinnerOccasion.getSelectedItem().toString();

        boolean inserted = databaseHelper.insertClothingItem(
                name,
                imageUri,
                category,
                color,
                season,
                style,
                occasion
        );

        if (inserted) {
            Toast.makeText(this, "Clothing item saved", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to save item", Toast.LENGTH_SHORT).show();
        }
    }
}