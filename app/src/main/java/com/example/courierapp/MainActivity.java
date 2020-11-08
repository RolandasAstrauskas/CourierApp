package com.example.courierapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class MainActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button button;
    public static String userCourierId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginUser();

    }

    private void loginUser() {

        editTextUsername = findViewById(R.id.txtVartotojas);
        editTextPassword = findViewById(R.id.txtPassword);
        button = findViewById(R.id.btnPrisijungti);

    button.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            CheckLogin checkLogin = new CheckLogin();
            checkLogin.execute("");

        }
    });
}

public class CheckLogin extends AsyncTask<String,String,String> {


       @Override
       protected String doInBackground(String... params) {
           String userName = editTextUsername.getText().toString();
           String password = editTextPassword.getText().toString();

            try {
                Connection con = MySqlConnection.getConnection();
                if (con != null) {
                    String query = "SELECT idcourier FROM courier WHERE userName = ? AND password = ?  ";
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setString(1, userName);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();
                    if(rs.next())
                    {
                        userCourierId =rs.getString("idcourier");
                        Intent intent = new Intent(getApplicationContext(), CourierMenuActivity.class);
                        startActivity(intent);
                    }
                }
            }
            catch (Exception ex)
            { Toast.makeText(getApplicationContext(), "Prisijungti nepavyko", Toast.LENGTH_SHORT).show();}

           return userCourierId;

       }

  }

}
