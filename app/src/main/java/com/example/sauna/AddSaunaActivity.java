package com.example.sauna;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddSaunaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sauna);

        Button addBtn = findViewById(R.id.opt3_addBtn);
        addBtn.setOnClickListener(new addSauna());
    }

    private class addSauna implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // retrieve data input into String vars
            String make = ((EditText) findViewById(R.id.opt3_make)).getText().toString().trim();
            String code = ((EditText) findViewById(R.id.opt3_code)).getText().toString().trim();
            String woodType = ((EditText) findViewById(R.id.opt3_woodType)).getText().toString().trim();
            String capacity = ((EditText) findViewById(R.id.opt3_capacity)).getText().toString().trim();
            String heating = ((EditText) findViewById(R.id.opt3_heating)).getText().toString().trim();
            String price = ((EditText) findViewById(R.id.opt3_price)).getText().toString().trim();
            String imgURL = ((EditText) findViewById(R.id.opt3_imgUrl)).getText().toString().trim();
            TextView errMsg = findViewById(R.id.opt3_error_msg);

            if (make.length() == 0) {
                toast("Make field is empty");
            } else if (code.length() == 0) {
                toast("Product code field is empty");
            } else if (SaunaList.getInstance().contains(new Sauna(code))) {
                toast("Product code already in use");
            } else if (woodType.length() == 0) {
                toast("Wood Type field is empty");
            } else if (capacity.length() == 0) {
                toast("Capacity field is empty");
            } else if (heating.length() == 0) {
                toast("Heating field is empty");
            } else if (price.length() == 0) {
                toast("Price field is empty");
            } else if (imgURL.length() == 0) {
                toast("Image URL field is empty");
            } else {
                SaunaList.addSauna(new Sauna(make, code, woodType, Integer.parseInt(capacity),
                      heating, Double.parseDouble(price), imgURL));
                toast("New Sauna added. Code: "+code);
            }
        }
    }

    private void toast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

}
