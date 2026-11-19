package com.nutrisafe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutrisafe.R;
import com.nutrisafe.viewmodel.BusquedaViewModel;

public class BusquedaActivity extends AppCompatActivity {

    private static final long ESPERA_MS = 500;
    private static final int MINIMO_CARACTERES = 3;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable busquedaPendiente;
    private BusquedaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        EditText etBusqueda = findViewById(R.id.etBusqueda);
        ProgressBar pbBusqueda = findViewById(R.id.pbBusqueda);
        TextView tvMensaje = findViewById(R.id.tvMensajeBusqueda);
        RecyclerView rvResultados = findViewById(R.id.rvResultados);

        ResultadosAdapter adapter = new ResultadosAdapter(resultado -> {
            Intent intent = new Intent(this, ConsultaActivity.class);
            intent.putExtra(ConsultaActivity.EXTRA_CODIGO, resultado.codigo);
            startActivity(intent);
        });
        rvResultados.setLayoutManager(new LinearLayoutManager(this));
        rvResultados.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(BusquedaViewModel.class);
        viewModel.getResultados().observe(this, adapter::actualizar);
        viewModel.getCargando().observe(this, cargando ->
                pbBusqueda.setVisibility(cargando ? View.VISIBLE : View.GONE));
        viewModel.getMensaje().observe(this, texto -> {
            tvMensaje.setText(texto);
            tvMensaje.setVisibility(texto == null ? View.GONE : View.VISIBLE);
        });

        etBusqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                programarBusqueda(s.toString().trim());
            }
        });
    }

    private void programarBusqueda(String texto) {
        if (busquedaPendiente != null) {
            handler.removeCallbacks(busquedaPendiente);
        }
        if (texto.length() < MINIMO_CARACTERES) {
            viewModel.limpiar();
            return;
        }
        busquedaPendiente = () -> viewModel.buscar(texto);
        handler.postDelayed(busquedaPendiente, ESPERA_MS);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}