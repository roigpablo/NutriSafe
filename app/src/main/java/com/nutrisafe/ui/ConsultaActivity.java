package com.nutrisafe.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.nutrisafe.R;
import com.nutrisafe.data.local.Alimento;
import com.nutrisafe.viewmodel.ConsultaViewModel;

public class ConsultaActivity extends AppCompatActivity {

    private ConsultaViewModel viewModel;
    private EditText etCodigo;
    private ProgressBar pbCargando;
    private TextView tvMensaje, tvNombre, tvMarca, tvNutricion, tvAlergenos, tvTrazas, tvIngredientes;
    private LinearLayout llProducto;
    private ImageView ivProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        etCodigo = findViewById(R.id.etCodigo);
        pbCargando = findViewById(R.id.pbCargando);
        tvMensaje = findViewById(R.id.tvMensaje);
        llProducto = findViewById(R.id.llProducto);
        ivProducto = findViewById(R.id.ivProducto);
        tvNombre = findViewById(R.id.tvNombre);
        tvMarca = findViewById(R.id.tvMarca);
        tvNutricion = findViewById(R.id.tvNutricion);
        tvAlergenos = findViewById(R.id.tvAlergenos);
        tvTrazas = findViewById(R.id.tvTrazas);
        tvIngredientes = findViewById(R.id.tvIngredientes);

        viewModel = new ViewModelProvider(this).get(ConsultaViewModel.class);
        viewModel.getAlimento().observe(this, this::mostrarProducto);
        viewModel.getMensaje().observe(this, this::mostrarMensaje);
        viewModel.getCargando().observe(this, cargando ->
                pbCargando.setVisibility(cargando ? View.VISIBLE : View.GONE));

        findViewById(R.id.btnBuscar).setOnClickListener(v -> {
            String codigo = etCodigo.getText().toString().trim();
            if (codigo.isEmpty()) {
                mostrarMensaje("Escribe un código de barras");
                return;
            }
            viewModel.buscar(codigo);
        });
    }

    private void mostrarMensaje(String texto) {
        tvMensaje.setText(texto);
        tvMensaje.setVisibility(texto == null ? View.GONE : View.VISIBLE);
        if (texto != null) {
            llProducto.setVisibility(View.GONE);
        }
    }

    private void mostrarProducto(Alimento a) {
        if (a == null) {
            llProducto.setVisibility(View.GONE);
            return;
        }
        llProducto.setVisibility(View.VISIBLE);
        tvMensaje.setVisibility(View.GONE);

        tvNombre.setText(a.nombre != null ? a.nombre : "Sin nombre");
        tvMarca.setText(a.marca != null ? a.marca : "");
        tvNutricion.setText("Energía: " + formatear(a.kcal100g, "kcal")
                + "\nAzúcares: " + formatear(a.azucares100g, "g") + " (por 100 g)");
        tvAlergenos.setText("Alérgenos: " + limpiar(a.alergenos));
        tvTrazas.setText("Trazas: " + limpiar(a.trazas));
        tvIngredientes.setText(a.ingredientes != null && !a.ingredientes.isEmpty()
                ? "Ingredientes: " + a.ingredientes : "Ingredientes: sin datos");

        Glide.with(this).load(a.imagenUrl).into(ivProducto);
    }

    private String formatear(Double valor, String unidad) {
        return valor == null ? "sin datos" : Math.round(valor * 10) / 10.0 + " " + unidad;
    }

    private String limpiar(String etiquetas) {
        if (etiquetas == null || etiquetas.isEmpty()) {
            return "ninguno";
        }
        return etiquetas.replace("en:", "").replace(",", ", ");
    }
}