package com.upc.hrm.hrm.Util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

public class Mensaje {

    public static void mensajeToast(Context context, String mensaje, int duracion){
        Toast.makeText(context,mensaje,duracion).show();
    }

    public static void mensajeSnack(View view, String mensaje, int duracion){
        Snackbar.make(view,mensaje,duracion).show();
    }
}
