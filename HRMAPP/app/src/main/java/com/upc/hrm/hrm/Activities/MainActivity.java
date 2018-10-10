package com.upc.hrm.hrm.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.upc.hrm.hrm.Entities.Medida;
import com.upc.hrm.hrm.R;

import org.json.JSONObject;

import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String ESTRES_CARDIACO = "69";
    private static final int LOWER_BOUND_HEART = 61;
    private static final int UPPER_BOUND_HEART = 65;
    private static final int LOWER_BOUND_OXIGENATION = 97;
    private static final int UPPER_BOUND_OXIGENATION = 99;
    private static final int TIMER = 5000;

    TextView txtRitmoCardiaco;
    TextView txtOxigenacion;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidNetworking.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        txtRitmoCardiaco = findViewById(R.id.txtRitmoCardiaco);
        txtOxigenacion = findViewById(R.id.txtOxigenacion);

        Button btnSave = findViewById(R.id.btnSave);
        Button btnStop = findViewById(R.id.btnStop);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int randomHeart = random.nextInt((UPPER_BOUND_HEART) - (LOWER_BOUND_HEART)) + LOWER_BOUND_HEART;
                //int randomOxigenation = random.nextInt((UPPER_BOUND_OXIGENATION) - (LOWER_BOUND_OXIGENATION)) + UPPER_BOUND_OXIGENATION;

                Medida objMedida = new Medida();

                //Read user id from preferences
                SharedPreferences prefs = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                int storedUserID = prefs.getInt("UserID", 0);

                getSendData(randomHeart,objMedida,storedUserID);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacksAndMessages(null);
            }
        });

    }

    private void generateRandomHeartBounds(int startHeart, Medida objMedida, int storedUserID){
        Random random = new Random();
        int randomHeartLoop = random.nextInt((startHeart+2) - (startHeart-2)) + (startHeart-2);
        int randomOxigenationLoop = random.nextInt((UPPER_BOUND_OXIGENATION) - (LOWER_BOUND_OXIGENATION)) + UPPER_BOUND_OXIGENATION;

        txtRitmoCardiaco.setText(String.valueOf(randomHeartLoop));
        txtOxigenacion.setText(String.valueOf(randomOxigenationLoop));

        objMedida.ritmoCardiaco = randomHeartLoop;
        objMedida.oxigenacion = randomOxigenationLoop;
        objMedida.estresCardiaco =  ESTRES_CARDIACO;

        sendData(objMedida, storedUserID);
    }

    private void sendData(Medida objMedida, int storedUserID){
        AndroidNetworking.post("http://192.168.1.108:8090/api/usuarios/{id}/medidas")
                .addPathParameter("id", String.valueOf(storedUserID))
                .addApplicationJsonBody(objMedida) // posting java object
                .setTag("medida")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.println(Log.ASSERT, "Success", "It worked!!!");
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.println(Log.ERROR, "Error",
                                "Some error!!" + error.getErrorBody()
                                        + " - " + error.getErrorDetail()
                                        + " - " + error.getResponse());
                    }
                });
    }

    public void getSendData(final int startHeart, final Medida objMedida, final int storedUserID) {
        handler.postDelayed(new Runnable() {
            public void run() {
                generateRandomHeartBounds(startHeart, objMedida, storedUserID);          // this method will contain your almost-finished HTTP calls
                handler.postDelayed(this, TIMER);
            }
        }, TIMER);
    }

}
