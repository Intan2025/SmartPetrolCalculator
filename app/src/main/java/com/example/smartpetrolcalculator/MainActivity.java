package com.example.smartpetrolcalculator;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.widget.Toolbar;

import com.example.smartpetrolcalculator.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    EditText editTextPetrolPrice, editTextFuelUsage;

    RadioButton radioRON95, radioRON97, radioDiesel;
    RadioButton radioYes, radioNo;

    Button buttonCalculate;

    TextView textViewTotalCost, textViewRebate, textViewSaving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextPetrolPrice = findViewById(R.id.editTextPetrolPrice);
        editTextFuelUsage = findViewById(R.id.editTextFuelUsage);

        radioRON95 = findViewById(R.id.radioRON95);
        radioRON97 = findViewById(R.id.radioRON97);
        radioDiesel = findViewById(R.id.radioDiesel);

        radioYes = findViewById(R.id.radioYes);
        radioNo = findViewById(R.id.radioNo);

        buttonCalculate = findViewById(R.id.buttonCalculate);

        textViewTotalCost = findViewById(R.id.textViewTotalCost);
        textViewRebate = findViewById(R.id.textViewRebate);
        textViewSaving = findViewById(R.id.textViewSaving);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextPetrolPrice.getText().toString().trim().isEmpty() ||
                        editTextFuelUsage.getText().toString().trim().isEmpty()) {

                    Toast.makeText(MainActivity.this,
                            "Please enter all values",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                if (!radioRON95.isChecked() &&
                        !radioRON97.isChecked() &&
                        !radioDiesel.isChecked()) {

                    Toast.makeText(MainActivity.this,
                            "Please select petrol type",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                if (!radioYes.isChecked() &&
                        !radioNo.isChecked()) {

                    Toast.makeText(MainActivity.this,
                            "Please select BUDI status",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                double petrolPrice = Double.parseDouble(editTextPetrolPrice.getText().toString());
                double fuelUsage = Double.parseDouble(editTextFuelUsage.getText().toString());

                double totalCost = petrolPrice * fuelUsage;
                double rebate = 0;
                double saving = 0;

                // BUDI rebate only for RON95
                if (radioRON95.isChecked() && radioYes.isChecked()) {
                    rebate = fuelUsage * 1.99;
                }

                saving = totalCost - rebate;

                textViewTotalCost.setText("Total Petrol Cost: RM " + String.format("%.2f", totalCost));

                textViewRebate.setText("BUDI Rebate: RM " + String.format("%.2f", rebate));

                textViewSaving.setText("Total Saving: RM " + String.format("%.2f", saving));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_about) {

            Intent intent = new Intent(MainActivity.this,
                    AboutActivity.class);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}