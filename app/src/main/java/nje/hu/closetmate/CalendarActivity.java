package nje.hu.closetmate;

import android.os.Bundle;

import android.widget.Button;

import android.widget.CalendarView;

import android.widget.EditText;

import android.widget.TextView;

import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;

    private TextView txtSelectedDate;

    private EditText edtOutfitName, edtNotes;

    private Button btnSavePlan;

    private DatabaseHelper databaseHelper;

    private String selectedDate;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);

        databaseHelper = new DatabaseHelper(this);

        calendarView = findViewById(R.id.calendarView);

        txtSelectedDate = findViewById(R.id.txtSelectedDate);

        edtOutfitName = findViewById(R.id.edtOutfitName);

        edtNotes = findViewById(R.id.edtNotes);

        btnSavePlan = findViewById(R.id.btnSavePlan);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        selectedDate = getTodayDate();

        txtSelectedDate.setText("Selected date: " + selectedDate);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            selectedDate = year + "-" + formatNumber(month + 1) + "-" + formatNumber(dayOfMonth);

            txtSelectedDate.setText("Selected date: " + selectedDate);

        });

        btnSavePlan.setOnClickListener(v -> savePlan());

    }

    private void savePlan() {

        String outfitName = edtOutfitName.getText().toString().trim();

        String notes = edtNotes.getText().toString().trim();

        if (outfitName.isEmpty()) {

            Toast.makeText(this, "Please enter outfit name", Toast.LENGTH_SHORT).show();

            return;

        }

        boolean inserted = databaseHelper.insertOutfitPlan(selectedDate, outfitName, notes);

        if (inserted) {

            Toast.makeText(this, "Outfit plan saved", Toast.LENGTH_SHORT).show();

            edtOutfitName.setText("");

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

