package com.upc.hrm.hrm.Activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.upc.hrm.hrm.R;
import com.upc.hrm.hrm.Util.Mensaje;

public class ConnectActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private static final int TIMER = 4000;

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        init();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mensaje.mensajeToast(getBaseContext(),"Conectando...", 5);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent myIntent =
                                new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(myIntent);
                    }
                }, TIMER);
            }
        });
    }

    private void init(){button = (Button) findViewById(R.id.btnConectar);}
}
