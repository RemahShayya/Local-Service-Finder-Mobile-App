package com.example.localservicefinder;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class AddServiceActivity extends AppCompatActivity {

    private EditText etServiceName, etProviderName, etContactDetails;
    private Spinner spCategory, spCity;
    private Button btnAddService;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        dbHelper = new DatabaseHelper(this);

        etServiceName = findViewById(R.id.etServiceName);
        etProviderName = findViewById(R.id.etProviderName);
        etContactDetails = findViewById(R.id.etContactDetails);
        spCategory = findViewById(R.id.spCategory);
        spCity = findViewById(R.id.spCity);
        btnAddService = findViewById(R.id.btnAddService);

        setupCategorySpinner();
        setupCitySpinner();

        btnAddService.setOnClickListener(view -> addNewService());
    }

    private void setupCategorySpinner() {
        List<String> categories = Arrays.asList("Home Services", "Outdoor Services", "Transport", "Food Services", "Technology", "Health & Fitness", "Automobile");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spCategory.setAdapter(categoryAdapter);
    }

    private void setupCitySpinner() {
        List<String> cities = Arrays.asList("Beirut", "Tripoli", "Saida", "Zahle", "Byblos", "Jounieh");
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        spCity.setAdapter(cityAdapter);
    }

    private void addNewService() {
        String serviceName = etServiceName.getText().toString().trim();
        String category = spCategory.getSelectedItem().toString();
        String providerName = etProviderName.getText().toString().trim();
        String contactDetails = etContactDetails.getText().toString().trim();
        String city = spCity.getSelectedItem().toString();

        if (serviceName.isEmpty() || providerName.isEmpty() || contactDetails.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = dbHelper.insertService(serviceName, category, providerName, contactDetails, city);
        if (isInserted) {
            Toast.makeText(this, "Service added successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        } else {
            Toast.makeText(this, "Failed to add service", Toast.LENGTH_SHORT).show();
        }
    }
}
