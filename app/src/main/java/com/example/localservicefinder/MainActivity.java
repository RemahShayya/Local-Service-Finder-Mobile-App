package com.example.localservicefinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddService = findViewById(R.id.btnAddService);
        btnAddService.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddServiceActivity.class);
            startActivity(intent);
        });

        Button btnViewServices = findViewById(R.id.btnViewServices);
        btnViewServices.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewServicesActivity.class);
            startActivity(intent);
        });
    }
}
