package com.example.practica02;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int CAMERA_PIC_REQUEST = 1;
    private Uri uriImage, location;
    TextInputEditText nombre, edad, email, telefono, link;
    Button guardar, captura, direccion;
    ImageView imagen;
    Bitmap ImageCamera;
    private int contador = 0;
    List<String> lista = new ArrayList<>();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = (TextInputEditText)findViewById(R.id.txtnombres);
        edad = (TextInputEditText)findViewById(R.id.txtedad);
        email = (TextInputEditText)findViewById(R.id.txtcorreo);
        telefono = (TextInputEditText)findViewById(R.id.txttelefono);
        link = (TextInputEditText)findViewById(R.id.txturl);
        guardar = (Button)findViewById(R.id.btnGuardar);
        imagen = (ImageView)findViewById(R.id.Imvfoto);
        captura = (Button)findViewById(R.id.btnCaptura);
        direccion = (Button)findViewById(R.id.btnDireccion);


        captura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent camarafoto =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camarafoto, CAMERA_PIC_REQUEST);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendToReceptorApp(uriImage);
            }
        });

        direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri location = Uri.parse("geo:0.0?q=Universidad+Privada" +
                        "+de+Tacna,+Granada,+Tacna");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                        location);
                startActivity(mapIntent);
            }
        });
    }

    //Metodo para capturar todos los elementos al enviar o guardar:
    private void sendToReceptorApp(Uri uriToImage){

        Intent enviodatos = new Intent(MainActivity.this, ResultadoActivity.class);
        if(contador == 0){
            Toast.makeText(getApplicationContext(), "Debe de tomar una foto",Toast.LENGTH_SHORT).show();
        }else{
            //Bitmap bitmap =((BitmapDrawable)imagen.getDrawable()).getBitmap();
            enviodatos.setAction(Intent.ACTION_SEND_MULTIPLE);
            enviodatos.putExtra("nombre",nombre.getText().toString());
            enviodatos.putExtra("edad",edad.getText().toString());
            enviodatos.putExtra("email",email.getText().toString());
            enviodatos.putExtra("telefono",telefono.getText().toString());
            enviodatos.putExtra("url",link.getText().toString());
            enviodatos.putExtra("captura", ImageCamera);
            enviodatos.setType("*/*");
            startActivity(enviodatos);
            finish();
        }
    }

    //metodo captura
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_PIC_REQUEST){
            if(resultCode == RESULT_OK){
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                ImageView imagen = (ImageView)findViewById(R.id.Imvfoto);
                imagen.setImageBitmap(bitmap);
                ImageCamera = bitmap;
                uriImage = data.getData();
                contador ++;

            }
        }
    }
}