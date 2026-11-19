package com.nutrisafe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.nutrisafe.MainActivity;
import com.nutrisafe.R;

public class AvisoActivity extends AppCompatActivity {

    public static final String PREFS = "nutrisafe";
    public static final String CLAVE_ACEPTADO = "aviso_aceptado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aviso);

        CheckBox cbAcepto = findViewById(R.id.cbAcepto);
        Button btnContinuar = findViewById(R.id.btnContinuar);

        cbAcepto.setOnCheckedChangeListener((boton, marcado) -> btnContinuar.setEnabled(marcado));

        btnContinuar.setOnClickListener(v -> {
            getSharedPreferences(PREFS, MODE_PRIVATE)
                    .edit()
                    .putBoolean(CLAVE_ACEPTADO, true)
                    .apply();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}