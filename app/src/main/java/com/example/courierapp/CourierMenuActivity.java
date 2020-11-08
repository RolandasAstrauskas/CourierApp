package com.example.courierapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CourierMenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_menu);

        buttonBack();

        buttonDelivered();

        buttonNotDelivered();

        buttonEmpty();
    }

    private void buttonEmpty() {
        Button buttonEmpty = findViewById(R.id.btnEmpty);
        buttonEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CourierInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void buttonDelivered() {
        Button buttonDelivered = findViewById(R.id.btnDelivered);

        buttonDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), DeliveredOrdersActivity.class);
                startActivity(intent);
            }
        });
    }

    private void buttonNotDelivered() {
        Button buttonNotDelevered = findViewById(R.id.btnNotDelivered);

        buttonNotDelevered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), NotDeliveredOrderActivity.class);
                startActivity(intent);

            }
        });
    }

    private void buttonBack() {
        Button buttonBack = findViewById(R.id.btnOut);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }

}