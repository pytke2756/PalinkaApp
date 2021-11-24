package com.example.palinkaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnFelvetel;
    private Button btnKeres;
    private Button btnListaz;
    private TextView txtviewLista;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        txtviewLista.setMovementMethod(new ScrollingMovementMethod());

        btnFelvetel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent felvetel = new Intent(MainActivity.this, AdatFelvetelActivity.class);
                startActivity(felvetel);
                finish();
            }
        });

        btnKeres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent keres = new Intent(MainActivity.this, AdatKeresesActivity.class);
                startActivity(keres);
                finish();
            }
        });

        btnListaz.setOnClickListener(v -> {
            Cursor adatok = db.listaz();
            if (adatok.getCount() == 0){
                Toast.makeText(this, "Nincs az adatbázisban bejegyzés", Toast.LENGTH_SHORT).show();
            } else {
                StringBuilder bobTheBuilder = new StringBuilder();
                while (adatok.moveToNext()){
                    bobTheBuilder.append("ID: ").append(adatok.getInt(0));
                    bobTheBuilder.append(System.lineSeparator());
                    bobTheBuilder.append("Főző: ").append(adatok.getString(1));
                    bobTheBuilder.append(System.lineSeparator());
                    bobTheBuilder.append("Gyümölcs: ").append(adatok.getString(2));
                    bobTheBuilder.append(System.lineSeparator());
                    bobTheBuilder.append("Alkohol: ").append(adatok.getInt(3));
                    bobTheBuilder.append(System.lineSeparator());
                    bobTheBuilder.append(System.lineSeparator());
                }
                txtviewLista.setText(bobTheBuilder.toString());
                Toast.makeText(this, "Betöltés sikeres", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void init(){
        btnFelvetel = findViewById(R.id.btn_felvetel);
        btnKeres = findViewById(R.id.btn_keres);
        btnListaz = findViewById(R.id.btn_listaz);
        txtviewLista = findViewById(R.id.txtview_lista);
        db = new DBHelper(this);
    }

}