package com.example.localservicefinder;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewServicesActivity extends AppCompatActivity {

    private Spinner spCategoryFilter;
    private ListView lvServices;
    private DatabaseHelper dbHelper;
    private List<String> servicesList;
    private ArrayAdapter<String> servicesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_services);

        spCategoryFilter = findViewById(R.id.spCategoryFilter);
        lvServices = findViewById(R.id.lvServices);
        dbHelper = new DatabaseHelper(this);

        List<String> categories = new ArrayList<>();
        categories.add("All");
        categories.addAll(dbHelper.getAllCategories());
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoryFilter.setAdapter(categoryAdapter);

        loadServices(null);

        spCategoryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = (String) parent.getItemAtPosition(position);
                loadServices(selectedCategory.equals("All") ? null : selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        lvServices.setOnItemClickListener((parent, view, position, id) -> {
            String selectedService = servicesList.get(position);
            showServiceDetailsDialog(selectedService);
        });
    }

    private void loadServices(String category) {
        Cursor cursor;
        if (category == null) {
            cursor = dbHelper.getAllServices();
        } else {
            cursor = dbHelper.getServicesByCategory(category);
        }

        servicesList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String serviceName = cursor.getString(cursor.getColumnIndex("ServiceName"));
                String providerName = cursor.getString(cursor.getColumnIndex("ProviderName"));
                String location = cursor.getString(cursor.getColumnIndex("Location"));
                servicesList.add(serviceName + " - " + providerName + " (" + location + ")");
            } while (cursor.moveToNext());
            cursor.close();
        }

        servicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, servicesList);
        lvServices.setAdapter(servicesAdapter);
    }

    private void showServiceDetailsDialog(String serviceDetails) {
        String[] parts = serviceDetails.split(" - ");
        String serviceName = parts[0];
        Cursor cursor = dbHelper.getServiceDetails(serviceName);

        if (cursor != null && cursor.moveToFirst()) {
            String providerName = cursor.getString(cursor.getColumnIndex("ProviderName"));
            String contactDetails = cursor.getString(cursor.getColumnIndex("ContactDetails"));
            String location = cursor.getString(cursor.getColumnIndex("Location"));
            cursor.close();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(serviceName);
            builder.setMessage(
                    "Provider: " + providerName + "\n" +
                            "Contact: " + contactDetails + "\n" +
                            "Location: " + location
            );
            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
