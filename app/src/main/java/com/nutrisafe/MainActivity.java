package com.nutrisafe;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nutrisafe.ui.ConsultaActivity;
import com.nutrisafe.ui.PerfilActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnPerfil).setOnClickListener(
                v -> startActivity(new Intent(this, PerfilActivity.class)));
        findViewById(R.id.btnConsulta).setOnClickListener(
                v -> startActivity(new Intent(this, ConsultaActivity.class)));
    }
}