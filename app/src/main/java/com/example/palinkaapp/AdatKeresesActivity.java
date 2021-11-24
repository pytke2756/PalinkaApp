package com.example.palinkaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdatKeresesActivity extends AppCompatActivity {

    /*
    * et_fozo_keres
    * et_gyumolcs_keres
    * btn_keres_keres
    * btn_vissza_keres
    * */

    private EditText etFozoKeres;
    private EditText etGyumolcsKeres;
    private Button btnKeresKeres;
    private Button btnVisszaKeres;
    private TextView txtview_keres;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adat_kereses);

        init();

        btnKeresKeres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fozoKeres = etFozoKeres.getText().toString().trim();
                String gyumolcsKeres = etGyumolcsKeres.getText().toString().trim();
                if (fozoKeres.isEmpty() || gyumolcsKeres.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Mindent tölts ki!", Toast.LENGTH_SHORT).show();
                }else{
                    Cursor palinkaKeres = db.palinkaKeres(fozoKeres, gyumolcsKeres);
                    int stmtDb = palinkaKeres.getCount();
                    if (stmtDb == 0){
                        txtview_keres.setText("Az megadott adatokkal nem található pálinka");
                    }
                    else if (stmtDb == 1){
                        StringBuilder bobTheBuilder = new StringBuilder();

                        while (palinkaKeres.moveToNext()){
                            bobTheBuilder.append("Alkoholtartalom: ").append(palinkaKeres.getString(0)).append("%");
                            bobTheBuilder.append(System.lineSeparator());
                        }
                        txtview_keres.setText(bobTheBuilder.toString());
                    }else{
                        txtview_keres.setText("Hiba a keresés során.");
                    }
                }
            }
        });

        btnVisszaKeres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vissza = new Intent(AdatKeresesActivity.this, MainActivity.class);
                startActivity(vissza);
                finish();
            }
        });
    }

    private void init(){
        etFozoKeres = findViewById(R.id.et_fozo_keres);
        etGyumolcsKeres = findViewById(R.id.et_gyumolcs_keres);
        btnKeresKeres = findViewById(R.id.btn_keres_keres);
        btnVisszaKeres = findViewById(R.id.btn_vissza_keres);
        txtview_keres = findViewById(R.id.txtview_keres);
        db = new DBHelper(this);
    }
}