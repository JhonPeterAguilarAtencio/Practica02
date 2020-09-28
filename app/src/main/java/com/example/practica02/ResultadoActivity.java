package com.example.practica02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class ResultadoActivity extends AppCompatActivity {

    TextView rnombre, redad, remail, rtelefono, rurl;
    ImageView rfoto;
    Button nuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        rnombre = (TextView)findViewById(R.id.idnombre);
        redad = (TextView)findViewById(R.id.idedad);
        remail = (TextView)findViewById(R.id.idemail);
        rtelefono = (TextView)findViewById(R.id.idtelefono);
        rurl = (TextView)findViewById(R.id.idurl);
        rfoto = (ImageView)findViewById(R.id.idfoto);
        nuevo = (Button)findViewById(R.id.btnVolver);

        Intent receptor = getIntent();
        String action = receptor.getAction();
        String tipo = receptor.getType();

        if(Intent.ACTION_SEND_MULTIPLE.equals(action) && tipo != null){

            String d1 = receptor.getStringExtra("nombre");
            String d2 = receptor.getStringExtra("edad");
            String d3 = receptor.getStringExtra("email");
            String d4 = receptor.getStringExtra("telefono");
            String d5 = receptor.getStringExtra("url");

            Bitmap bitmap =(Bitmap) receptor.getParcelableExtra("captura");
            ImageView icono =(ImageView)findViewById(R.id.idfoto);
            icono.setImageBitmap(bitmap);

            rnombre.setText("Codigo: "+ " "+ d1);
            redad.setText("Nombre: " + " " + d2);
            remail.setText("Edad: " + " " + d3);
            rtelefono.setText("Correo: " + " " + d4);
            rurl.setText("Telefono: " + " " + d5);

            if(receptor.getExtras().get(Intent.EXTRA_STREAM) != null){
                Uri img =receptor.getParcelableExtra(Intent.EXTRA_STREAM);
                ImageView imageView =findViewById(R.id.idfoto);
                imageView.setImageURI(img);
            }
        }

        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rnuevo = new Intent(ResultadoActivity.this,MainActivity.class);
                startActivity(rnuevo);
                finish();
            }
        });
    }

    private void recibirdatos(){

    }
}