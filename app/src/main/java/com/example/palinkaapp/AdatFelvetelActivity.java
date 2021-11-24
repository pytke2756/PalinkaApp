package com.example.palinkaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdatFelvetelActivity extends AppCompatActivity {

    private EditText etFozo;
    private EditText etGyumolcs;
    private EditText etAlkohol;
    private Button btnFelvetelFelAct;
    private Button btnVisszaFelAct;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adat_felvetel);

        init();

        btnFelvetelFelAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fozo = etFozo.getText().toString().trim();
                String gyumolcs = etGyumolcs.getText().toString().trim();
                String alkohol = etAlkohol.getText().toString().trim();

                if (fozo.isEmpty() || gyumolcs.isEmpty() || alkohol.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Minden mező kitöltése kötelező", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        int alkholInt = Integer.parseInt(alkohol);
                        if (alkholInt < 1){
                            Toast.makeText(getApplicationContext(), "Nem lehet 1-nél kisebb", Toast.LENGTH_SHORT).show();
                        }else{
                            if (db.rogzites(fozo, gyumolcs, alkholInt)){
                                Toast.makeText(getApplicationContext(), "Sikeres rögzítés",
                                        Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(getApplicationContext(), "Sikeretelen rögzítés",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch (NumberFormatException ex){
                        Toast.makeText(getApplicationContext(), "Az alkoholszázaléknak számnak kell lennie",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnVisszaFelAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vissza = new Intent(AdatFelvetelActivity.this, MainActivity.class);
                startActivity(vissza);
                finish();
            }
        });
    }

    private void init(){
        etFozo = findViewById(R.id.et_fozo);
        etGyumolcs = findViewById(R.id.et_gyumolcs);
        etAlkohol = findViewById(R.id.et_alkohol);
        btnFelvetelFelAct = findViewById(R.id.btn_felvetel_felact);
        btnVisszaFelAct = findViewById(R.id.btn_vissza_felact);
        db = new DBHelper(this);
    }
}