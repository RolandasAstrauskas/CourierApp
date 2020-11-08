package com.example.courierapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;


public class NotDeliveredOrderActivity extends AppCompatActivity {

    public int indexOrder;
    public int orderId;
    public int  courierId = Integer.parseInt(MainActivity.userCourierId);

    @Override
    protected void onStart() {
        super.onStart();
        getWaitingDelivery();
    }

    private ArrayList<ClassOrder> itemArrayList;
    private MyAppAdapter myAppAdapter;
    private ListView listOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_delivered_order);
        setTitle("Nepristatyti uzsakymai");

        buttonBack();

        createListView();

    }

    private void createListView() {
        final DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getApplicationContext(), SelectedOrderActivity.class);
                intent.putExtra("EXTRA_NOTE_ID", orderId);
                startActivity(intent);

            }
        };

            ListView orderListView = findViewById(R.id.listOrder);
            orderListView.setAdapter(myAppAdapter);

            orderListView.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    orderId = ClassOrderList.classOrderList.get(position).getIdOrders();
                    AlertDialog.Builder builder = new AlertDialog.Builder(NotDeliveredOrderActivity.this);
                    builder.setTitle("Ar norite vykdyti si uzsakyma?");
                    builder.setNegativeButton("Ne", null);
                    builder.setPositiveButton("Taip", positiveClick);
                    builder.show();
                }
            });
        }

        private void buttonBack () {
            Button button = findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), CourierMenuActivity.class);

                    startActivity(intent);

                }
            });
        }

    private void getWaitingDelivery() {
        listOrder = findViewById(R.id.listOrder);
        itemArrayList = new ArrayList<ClassOrder>();

        ConnectMySql connectMySql = new ConnectMySql();
        connectMySql.execute("");
    }

   private class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Connection con = MySqlConnection.getConnection();
                String query = "SELECT amount, idOrders, description, orderLocation FROM orders WHERE status = 0 AND courierId = ?;";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, courierId);
                ResultSet rs = stmt.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                if (rs != null) {
                    while (rs.next()) {
                        itemArrayList.add(new ClassOrder(rs.getString("description"), rs.getString("orderLocation"), rs.getInt("idOrders"), rs.getDouble("amount")));
                    }
                    ClassOrderList.classOrderList = itemArrayList;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String msg) {
            myAppAdapter = new MyAppAdapter(itemArrayList, NotDeliveredOrderActivity.this);
            listOrder.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listOrder.setAdapter(myAppAdapter);
        }
    }

    public class MyAppAdapter extends BaseAdapter {
        public class ViewHolder {
            TextView textName, textName2;
        }

        public List<ClassOrder> orderList;

        public Context context;
        ArrayList<ClassOrder> arraylist;

        private MyAppAdapter(List<ClassOrder> orderList, Context context) {
            this.orderList = orderList;
            this.context = context;
            arraylist = new ArrayList<ClassOrder>();
            arraylist.addAll(orderList);

        }

        @Override
        public int getCount() {
            return orderList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            indexOrder = orderList.get(position).getIdOrders();

            View rowView = convertView;
            ViewHolder viewHolder = null;
            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_content, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.textName = rowView.findViewById(R.id.textName);
                viewHolder.textName2 = rowView.findViewById(R.id.textName2);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.textName.setText(orderList.get(position).getDescription());
            viewHolder.textName2.setText(orderList.get(position).getOrderLocation());

            return rowView;
        }

    }















}



