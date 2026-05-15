package nje.hu.closetmate;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView txtSelectedDate;
    private Spinner spinnerTop, spinnerBottom, spinnerShoes, spinnerAccessory;
    private EditText edtNotes;
    private Button btnSavePlan, btnBack;

    private DatabaseHelper databaseHelper;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        databaseHelper = new DatabaseHelper(this);

        calendarView = findViewById(R.id.calendarView);
        txtSelectedDate = findViewById(R.id.txtSelectedDate);
        spinnerTop = findViewById(R.id.spinnerTop);
        spinnerBottom = findViewById(R.id.spinnerBottom);
        spinnerShoes = findViewById(R.id.spinnerShoes);
        spinnerAccessory = findViewById(R.id.spinnerAccessory);
        edtNotes = findViewById(R.id.edtNotes);
        btnSavePlan = findViewById(R.id.btnSavePlan);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        selectedDate = getTodayDate();
        txtSelectedDate.setText("Selected date: " + selectedDate);

        setupOutfitSpinners();

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + formatNumber(month + 1) + "-" + formatNumber(dayOfMonth);
            txtSelectedDate.setText("Selected date: " + selectedDate);
        });

        btnSavePlan.setOnClickListener(v -> savePlan());
    }

    private void setupOutfitSpinners() {
        setClothingSpinner(spinnerTop, "Top", "Select top");
        setClothingSpinner(spinnerBottom, "Bottom", "Select bottom");
        setClothingSpinner(spinnerShoes, "Shoes", "Select shoes");
        setClothingSpinner(spinnerAccessory, "Accessory", "No accessory");
    }

    private void setClothingSpinner(Spinner spinner, String category, String defaultText) {
        ArrayList<ClothingItem> items = databaseHelper.getClothingItemsByCategory(category);
        ArrayList<String> names = new ArrayList<>();

        names.add(defaultText);

        for (ClothingItem item : items) {
            names.add(item.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                names
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void savePlan() {
        String top = spinnerTop.getSelectedItem().toString();
        String bottom = spinnerBottom.getSelectedItem().toString();
        String shoes = spinnerShoes.getSelectedItem().toString();
        String accessory = spinnerAccessory.getSelectedItem().toString();
        String notes = edtNotes.getText().toString().trim();

        if (top.equals("Select top") || bottom.equals("Select bottom") || shoes.equals("Select shoes")) {
            Toast.makeText(this, "Please choose top, bottom, and shoes", Toast.LENGTH_SHORT).show();
            return;
        }

        String outfitName = top + " + " + bottom + " + " + shoes;

        if (!accessory.equals("No accessory")) {
            outfitName += " + " + accessory;
        }

        boolean inserted = databaseHelper.insertOutfitPlan(selectedDate, outfitName, notes);

        if (inserted) {
            Toast.makeText(this, "Outfit plan saved", Toast.LENGTH_SHORT).show();
            edtNotes.setText("");
        } else {
            Toast.makeText(this, "Failed to save plan", Toast.LENGTH_SHORT).show();
        }
    }

    private String getTodayDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return formatter.format(new Date());
    }

    private String formatNumber(int number) {
        return number < 10 ? "0" + number : String.valueOf(number);
    }
}