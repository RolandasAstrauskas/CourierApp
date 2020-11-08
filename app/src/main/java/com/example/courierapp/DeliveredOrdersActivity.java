package com.example.courierapp;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class DeliveredOrdersActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        getDeliveredList();
    }
    private ArrayList<ClassListItemDelivered> itemArrayList;
    private DeliveredOrdersActivity.MyAppAdapter myAppAdapter;
    private ListView listDelivered;
    public int  courierId = Integer.parseInt(MainActivity.userCourierId);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered_orders);
        setTitle("Pristatyti uzsakymai");

        buttonBack();

    }

    private void getDeliveredList() {
        listDelivered = findViewById(R.id.listDelivered);
        itemArrayList = new ArrayList<ClassListItemDelivered>();

        ConnectMySql connectMySql = new ConnectMySql();
        connectMySql.execute("");

    }

    private class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";

        @Override
        protected String doInBackground(String... params) {
            try {
                Connection con = MySqlConnection.getConnection();
                String query = "SELECT description, deliveryDate, amount, orderLocation FROM orders WHERE status = 1 AND courierId = ?;";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, courierId);
                String result = "Database Connection Successful\n";
                ResultSet rs = stmt.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                if (rs != null) {
                    while (rs.next()) {
                        itemArrayList.add(new ClassListItemDelivered(rs.getString("description"), rs.getString("deliveryDate"),
                                rs.getFloat("amount"), rs.getString("orderLocation")));
                    }
                }
                res = result;
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String msg) {
            myAppAdapter = new DeliveredOrdersActivity.MyAppAdapter(itemArrayList, DeliveredOrdersActivity.this);
            listDelivered.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listDelivered.setAdapter(myAppAdapter);
        }
    }

    public class MyAppAdapter extends BaseAdapter {
        public class ViewHolder {
            TextView textDescription, textDate, textAmount, textLocation;
        }

        public List<ClassListItemDelivered> listDelivered;
        public Context context;

        ArrayList<ClassListItemDelivered> arraylist;

        private MyAppAdapter(List<ClassListItemDelivered> listDelivered, Context context) {
            this.listDelivered = listDelivered;
            this.context = context;
            arraylist = new ArrayList<ClassListItemDelivered>();
            arraylist.addAll(listDelivered);

        }

        @Override
        public int getCount() {
            return listDelivered.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            DeliveredOrdersActivity.MyAppAdapter.ViewHolder viewHolder = null;
            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_delivered_content, parent, false);
                viewHolder = new DeliveredOrdersActivity.MyAppAdapter.ViewHolder();
                viewHolder.textDescription = rowView.findViewById(R.id.textDescription);
                viewHolder.textDate = rowView.findViewById(R.id.textDate);
                viewHolder.textAmount = rowView.findViewById(R.id.textAmount);
                viewHolder.textLocation = rowView.findViewById(R.id.textLocation);


                rowView.setTag(viewHolder);
            } else {
                viewHolder = (DeliveredOrdersActivity.MyAppAdapter.ViewHolder) convertView.getTag();
            }

            viewHolder.textDescription.setText(listDelivered.get(position).getDescription());
            viewHolder.textDate.setText("Pristatymo data: \n" + listDelivered.get(position).getDeliveryDate());
            viewHolder.textAmount.setText("Suma:\n" + listDelivered.get(position).getAmount());
            viewHolder.textLocation.setText("Pristatymo vieta: \n" + listDelivered.get(position).getOrderLocation());

            return rowView;
        }
    }

    private void buttonBack() {
        Button buttonBack = findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CourierMenuActivity.class);

                startActivity(intent);

            }
        });
    }
}