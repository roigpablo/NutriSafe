package com.nutrisafe.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nutrisafe.R;
import com.nutrisafe.domain.ResultadoBusqueda;

import java.util.ArrayList;
import java.util.List;

public class ResultadosAdapter extends RecyclerView.Adapter<ResultadosAdapter.Fila> {

    public interface AlPulsar {
        void pulsado(ResultadoBusqueda resultado);
    }

    private final List<ResultadoBusqueda> datos = new ArrayList<>();
    private final AlPulsar listener;

    public ResultadosAdapter(AlPulsar listener) {
        this.listener = listener;
    }

    public void actualizar(List<ResultadoBusqueda> nuevos) {
        datos.clear();
        datos.addAll(nuevos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fila onCreateViewHolder(@NonNull ViewGroup padre, int tipo) {
        View vista = LayoutInflater.from(padre.getContext())
                .inflate(R.layout.item_resultado, padre, false);
        return new Fila(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull Fila fila, int posicion) {
        ResultadoBusqueda r = datos.get(posicion);
        fila.nombre.setText(r.nombre);
        fila.marca.setText(r.marca);
        Glide.with(fila.miniatura).load(r.imagenUrl).into(fila.miniatura);
        fila.itemView.setOnClickListener(v -> listener.pulsado(r));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    static class Fila extends RecyclerView.ViewHolder {
        final ImageView miniatura;
        final TextView nombre;
        final TextView marca;

        Fila(View vista) {
            super(vista);
            miniatura = vista.findViewById(R.id.ivMiniatura);
            nombre = vista.findViewById(R.id.tvNombreResultado);
            marca = vista.findViewById(R.id.tvMarcaResultado);
        }
    }
}