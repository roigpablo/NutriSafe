package com.nutrisafe.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.nutrisafe.R;
import com.nutrisafe.data.local.Usuario;
import com.nutrisafe.domain.Alergenos;
import com.nutrisafe.domain.CalculadoraCalorias;
import com.nutrisafe.viewmodel.PerfilViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PerfilActivity extends AppCompatActivity {

    private static final String[] ACTIVIDADES = {
            "Sedentario", "Actividad ligera", "Actividad moderada", "Actividad alta"
    };
    private static final double[] FACTORES = {1.2, 1.375, 1.55, 1.725};

    private PerfilViewModel viewModel;
    private EditText etNombre, etEdad, etPeso, etAltura;
    private RadioGroup rgSexo;
    private RadioButton rbHombre, rbMujer;
    private Spinner spActividad;
    private CheckBox cbCeliaco, cbDiabetico;
    private TextView tvKcal;
    private final List<CheckBox> checksAlergenos = new ArrayList<>();
    private Usuario perfilActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        etPeso = findViewById(R.id.etPeso);
        etAltura = findViewById(R.id.etAltura);
        rgSexo = findViewById(R.id.rgSexo);
        rbHombre = findViewById(R.id.rbHombre);
        rbMujer = findViewById(R.id.rbMujer);
        spActividad = findViewById(R.id.spActividad);
        cbCeliaco = findViewById(R.id.cbCeliaco);
        cbDiabetico = findViewById(R.id.cbDiabetico);
        tvKcal = findViewById(R.id.tvKcal);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        spActividad.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, ACTIVIDADES));

        LinearLayout llAlergenos = findViewById(R.id.llAlergenos);
        for (String nombre : Alergenos.NOMBRES) {
            CheckBox cb = new CheckBox(this);
            cb.setText(nombre);
            llAlergenos.addView(cb);
            checksAlergenos.add(cb);
        }

        viewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        viewModel.getPerfil().observe(this, this::mostrarPerfil);

        btnGuardar.setOnClickListener(v -> guardar());
    }

    private void mostrarPerfil(Usuario u) {
        if (u == null) {
            return;
        }
        perfilActual = u;
        etNombre.setText(u.nombre);
        etEdad.setText(String.valueOf(u.edad));
        etPeso.setText(String.valueOf(u.pesoKg));
        etAltura.setText(String.valueOf(u.alturaCm));
        rbHombre.setChecked("H".equals(u.sexo));
        rbMujer.setChecked("M".equals(u.sexo));
        for (int i = 0; i < FACTORES.length; i++) {
            if (FACTORES[i] == u.factorActividad) {
                spActividad.setSelection(i);
            }
        }
        cbCeliaco.setChecked(u.celiaco);
        cbDiabetico.setChecked(u.diabetico);

        List<String> guardados = Arrays.asList(u.alergenos.split(","));
        for (int i = 0; i < checksAlergenos.size(); i++) {
            checksAlergenos.get(i).setChecked(guardados.contains(Alergenos.TAGS[i]));
        }

        double tmb = CalculadoraCalorias.calcularTmb(u.sexo, u.edad, u.pesoKg, u.alturaCm);
        int kcal = CalculadoraCalorias.calcularMantenimiento(tmb, u.factorActividad);
        tvKcal.setText("Calorías de mantenimiento: " + kcal + " kcal/día");
    }

    private void guardar() {
        int edad;
        double peso;
        double altura;
        try {
            edad = Integer.parseInt(etEdad.getText().toString().trim());
            peso = Double.parseDouble(etPeso.getText().toString().trim());
            altura = Double.parseDouble(etAltura.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Revisa edad, peso y altura", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario u = perfilActual != null ? perfilActual : new Usuario();
        u.nombre = etNombre.getText().toString().trim();
        u.sexo = rgSexo.getCheckedRadioButtonId() == R.id.rbMujer ? "M" : "H";
        u.edad = edad;
        u.pesoKg = peso;
        u.alturaCm = altura;
        u.factorActividad = FACTORES[spActividad.getSelectedItemPosition()];
        u.celiaco = cbCeliaco.isChecked();
        u.diabetico = cbDiabetico.isChecked();

        List<String> marcados = new ArrayList<>();
        for (int i = 0; i < checksAlergenos.size(); i++) {
            if (checksAlergenos.get(i).isChecked()) {
                marcados.add(Alergenos.TAGS[i]);
            }
        }
        u.alergenos = String.join(",", marcados);

        viewModel.guardar(u);
        Toast.makeText(this, "Perfil guardado", Toast.LENGTH_SHORT).show();
    }
}