package com.macbook.mosso.cameramosso;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.net.HttpURLConnection;
import java.util.Date;


public class Principal extends AppCompatActivity {
    private static EditText nameCarpeta;
    private static Button sendPhotoScreen, upPhoto;
    private static final int CAPTURE_IMAGE_ACTIVIY_REQUEST_CODE = 100;
    private HttpURLConnection urlConnection;
    private int sdWrite = 1;
    private String phatPhoto = Environment.getExternalStorageDirectory().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        nameCarpeta = (EditText) findViewById(R.id.editText);
        sendPhotoScreen = (Button) findViewById(R.id.button);
        sendPhotoScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**Boton si tiene de capturar imagen*/
                //Si tienes memoria memoria externa
                if (sdWrite == 1) {
                    if (setNameCarperta(nameCarpeta) != null) {
                       // Toast.makeText(getApplicationContext(), "Usted no cuenta con memoria Externa", Toast.LENGTH_LONG).show();
                        File photo = new File(phatPhoto + "/CAPUFE_FOTO/" + setNameCarperta(nameCarpeta));
                        photo.mkdirs();
                        Date fecha = new Date();
                        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        File out = new File(photo + "/" + fecha.getMinutes()+"_"+fecha.getSeconds()+ ".jpg");
                        System.out.println(out);
                        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
                        startActivityForResult(i, CAPTURE_IMAGE_ACTIVIY_REQUEST_CODE);
                    } else { //Si nombre de la carpeta esta vacio
                        Toast.makeText(getApplicationContext(), "Llena el campo requerido", Toast.LENGTH_LONG).show();
                    }
                } else  {//Si no hay Memoria
                    System.out.println("Directorio Externo:::"+Environment.getExternalStorageDirectory());
                  //  Toast.makeText(getApplicationContext(), "Usted no cuenta con memoria Externa", Toast.LENGTH_LONG).show();
                    File photo = new File("/storage/extSdCard/" + "/CAPUFE_FOTO/" + setNameCarperta(nameCarpeta));
                    photo.mkdirs();
                    Date fecha = new Date();
                    Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    File out = new File(photo + "/" +fecha.getMinutes()+"_"+fecha.getSeconds()+ ".jpg");
                    System.out.println(out);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
                    startActivityForResult(i, CAPTURE_IMAGE_ACTIVIY_REQUEST_CODE);


                }
            }
        });
    }


    public String setNameCarperta(EditText text) {

        String carpeta = text.getText().toString().trim();
        if (carpeta.equals("")) {
            return null;
        } else {
            return carpeta;
        }
    }
    //Menu inflater
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.principal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.disk_local:
                sdWrite = 1;

                Toast.makeText(getApplicationContext(), "VALOR DE LA SD: "+sdWrite, Toast.LENGTH_LONG).show();

            case R.id.memory_sd:
                sdWrite = 2;
                Toast.makeText(getApplicationContext(), "VALOR DE LA SD: "+sdWrite, Toast.LENGTH_LONG).show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
