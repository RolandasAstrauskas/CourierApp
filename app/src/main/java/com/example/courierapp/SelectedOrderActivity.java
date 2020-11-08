package com.example.courierapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class SelectedOrderActivity extends AppCompatActivity {

    ClassOrder orderItem;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_order);

        buttonBack();
        buttonDeliver();
        buttonDeliverConfirm();

        createOrderInfo();

    }

    private void createOrderInfo() {
        final int orderId = getIntent().getIntExtra("EXTRA_NOTE_ID", 0);

        List<ClassOrder> list = ClassOrderList.classOrderList;

        for (ClassOrder order : ClassOrderList.classOrderList) {

            if (order.getIdOrders() == orderId) {
                orderItem = order;
                break;
            }
        }
        TextView textDes = findViewById(R.id.textDescription2);
        textDes.setText(orderItem.getDescription());

        TextView textLoc = findViewById(R.id.textLocation);
        textLoc.setText("Adresas: \n" + orderItem.getOrderLocation());

        TextView textId = findViewById(R.id.textId);
        textId.setText("Uzsakymo ID: \n" + orderItem.getIdOrders());

        TextView textAmount = findViewById(R.id.textAmount);
        textAmount.setText("Suma: \n" + orderItem.getAmount());
    }

    private void buttonDeliverConfirm() {
        Button btnDeliverd = findViewById(R.id.buttonDelivered);
        btnDeliverd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SelectedOrderActivity.this);
                builder.setTitle("Ar patvirtinti si uzsakyma?");
                builder.setNegativeButton("Ne", null);
                builder.setPositiveButton("Taip", positiveClick);
                builder.show();

            }
        });
    }
    final DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener(){

        @Override
        public void onClick(DialogInterface dialog, int which) {
            ConnectMySql connectMySql = new ConnectMySql();
            connectMySql.execute("");
            Toast.makeText(getApplicationContext(), "Uzsakymas patvirtintas sekmingai", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getApplicationContext(), NotDeliveredOrderActivity.class);

            startActivity(intent);

        }
    };

    private void buttonDeliver() {
        Button buttonDeliver = findViewById(R.id.btnDeliver);

        buttonDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LatLng p1 = GeocodingClass.getLocationFromAddress(SelectedOrderActivity.this, orderItem.getOrderLocation());

                loadNavigationView(GeocodingClass.Lat, GeocodingClass.Lng);

            }
        });
    }

    private void buttonBack() {
        Button buttonBack = findViewById(R.id.btnBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), NotDeliveredOrderActivity.class);

                startActivity(intent);
            }
        });
    }

    private class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";

        @Override
        protected String doInBackground(String... params) {
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            try {
                Connection con = MySqlConnection.getConnection();
                String query = "UPDATE orders SET status = 1, deliveryDate = ? WHERE idOrders = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, date);
                stmt.setInt(2, orderItem.getIdOrders());
                stmt.executeUpdate();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Patvirtinti nepavyko", Toast.LENGTH_SHORT).show();
            }

            return res;
        }
    }

    public void loadNavigationView(String lat,String lng){
        Uri navigation = Uri.parse("google.navigation:q=" + lat + "," + lng + "");
        Intent navigationIntent = new Intent(Intent.ACTION_VIEW, navigation);
        navigationIntent.setPackage("com.google.android.apps.maps");
        startActivity(navigationIntent);
    }
}