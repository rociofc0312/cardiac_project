package com.upc.hrm.hrm.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.upc.hrm.hrm.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        final EditText txtDni = findViewById(R.id.txtDNI);
        final EditText txtContrasena = findViewById(R.id.txtPassword);

        //Read email from preferences
        SharedPreferences prefs = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String storedDni = prefs.getString("DNI", null);
        txtDni.setText(storedDni);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AndroidNetworking.get("http://192.168.1.109:8090/api/cliente/{email}/{contrasenia}")
                        .addPathParameter("email", txtDni.getText().toString())
                        .addPathParameter("contrasenia", txtContrasena.getText().toString())
                        .setTag("login")
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    //Store UserID and email
                                    int userID = 0;
                                    userID = response.getInt("id");
                                    SharedPreferences.Editor editor =
                                            getSharedPreferences("MyPreferences",
                                                    MODE_PRIVATE).edit();
                                    editor.putInt("UserID", userID);
                                    editor.putString("email", txtDni.getText().toString());
                                    editor.apply();

                                    //Move to main activity
                                    Intent myIntent =
                                            new Intent(getApplicationContext(), ConnectActivity.class);
                                    startActivity(myIntent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(),
                                            "Internal error! Please contact administrator",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                int errorCode = anError.getErrorCode();
                                if(errorCode == 404) {
                                    Toast.makeText(getApplicationContext(),
                                            "Invalid username or password",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),
                                            "Internal error! Please contact administrator",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }
}
