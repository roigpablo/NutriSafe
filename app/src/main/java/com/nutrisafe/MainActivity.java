package com.nutrisafe;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nutrisafe.ui.AcercaActivity;
import com.nutrisafe.ui.AvisoActivity;
import com.nutrisafe.ui.ConsultaActivity;
import com.nutrisafe.ui.PerfilActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean aceptado = getSharedPreferences(AvisoActivity.PREFS, MODE_PRIVATE)
                .getBoolean(AvisoActivity.CLAVE_ACEPTADO, false);
        if (!aceptado) {
            startActivity(new Intent(this, AvisoActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        findViewById(R.id.btnPerfil).setOnClickListener(
                v -> startActivity(new Intent(this, PerfilActivity.class)));
        findViewById(R.id.btnConsulta).setOnClickListener(
                v -> startActivity(new Intent(this, ConsultaActivity.class)));
        findViewById(R.id.btnAcerca).setOnClickListener(
                v -> startActivity(new Intent(this, AcercaActivity.class)));
    }
}