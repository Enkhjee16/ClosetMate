package nje.hu.closetmate;

import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;
import android.widget.Button;

public class AnalyticsActivity extends AppCompatActivity {

    private TextView txtTotalItems, txtTotalPlans, txtCategoryCounts;

    private DatabaseHelper databaseHelper;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_analytics);

        databaseHelper = new DatabaseHelper(this);

        txtTotalItems = findViewById(R.id.txtTotalItems);

        txtTotalPlans = findViewById(R.id.txtTotalPlans);

        txtCategoryCounts = findViewById(R.id.txtCategoryCounts);
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        loadAnalytics();

    }

    @Override

    protected void onResume() {

        super.onResume();

        loadAnalytics();

    }

    private void loadAnalytics() {

        int totalItems = databaseHelper.getClothingItemCount();

        int totalPlans = databaseHelper.getOutfitPlanCount();

        Map<String, Integer> categoryCounts = databaseHelper.getCategoryCounts();

        txtTotalItems.setText("Total clothing items: " + totalItems);

        txtTotalPlans.setText("Total outfit plans: " + totalPlans);

        StringBuilder builder = new StringBuilder();

        builder.append("Category counts:\n\n");

        if (categoryCounts.isEmpty()) {

            builder.append("No clothing data yet.");

        } else {

            for (Map.Entry<String, Integer> entry : categoryCounts.entrySet()) {

                builder.append(entry.getKey())

                        .append(": ")

                        .append(entry.getValue())

                        .append("\n");

            }

        }

        txtCategoryCounts.setText(builder.toString());

    }

}

