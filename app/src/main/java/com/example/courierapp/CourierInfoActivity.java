package com.example.courierapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourierInfoActivity extends AppCompatActivity {

    public int idcourier = Integer.parseInt(MainActivity.userCourierId);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_info);

        getCourierInfo();

        buttonBack();
        buttonCahngeUserName();

    }

    private void buttonCahngeUserName() {
        Button btnUserNChange = findViewById(R.id.buttonUserNameChange);
        btnUserNChange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CourierInfoActivity.this);
                builder.setTitle("Keisti vartotojo varda");

                final EditText input = new EditText(CourierInfoActivity.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                String m_Text = input.getText().toString();

                builder.setPositiveButton("Patvirtinti", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConnectMySql2 connectMySql2 = new ConnectMySql2();
                        connectMySql2.execute("");

                    }
                });
                builder.setNegativeButton("Atsaukti", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    private class ConnectMySql2 extends AsyncTask<String, Void, String> {
        String res = "";
        String m_Text;

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = MySqlConnection.getConnection();
                String query = "UPDATE courier SET userName = ? WHERE idcourier = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, m_Text);
                stmt.setInt(2, idcourier);
                stmt.executeUpdate();
            } catch (Exception e) {

            }

            return res;
        }
    }

    private void buttonBack() {
        Button btnbakc = findViewById(R.id.btnback3);

        btnbakc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CourierMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getCourierInfo() {
        ConnectMySql connectMySql = new ConnectMySql();
        connectMySql.execute("");
    }

    private class ConnectMySql extends AsyncTask<String, Void, String> {
        String rs = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        TextView txtName = findViewById(R.id.textCName);
        TextView txtLastName = findViewById(R.id.textLastName);
        TextView txtUserName = findViewById(R.id.textUserName);
        TextView txtPassword = findViewById(R.id.textPassword);
        TextView txtEmail = findViewById(R.id.textEmail);
        ImageView imgView = findViewById(R.id.imageView);

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = MySqlConnection.getConnection();
                byte b[];
                Blob blob;
                String query = "SELECT name, lastName, userName, email, password, courierPhoto FROM courier WHERE idcourier = ?;";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, idcourier);
                ResultSet rs = stmt.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    txtName.setText(rs.getString("name"));
                    txtLastName.setText(rs.getString("lastName"));
                    txtUserName.setText("Jusu prisijungimas \n" + rs.getString("userName"));
                    txtPassword.setText("Jusu slaptazodis \n" + rs.getString("password"));
                    txtEmail.setText(rs.getString("email"));

                    blob = rs.getBlob("courierPhoto");
                    b = blob.getBytes(1, (int)blob.length());
                    Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                    imgView.setImageBitmap(bitmap);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return rs;
        }
    }
}









