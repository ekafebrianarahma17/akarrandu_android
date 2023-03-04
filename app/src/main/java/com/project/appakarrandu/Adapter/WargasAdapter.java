package com.project.appakarrandu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.appakarrandu.Models.Warga;
import com.project.appakarrandu.R;

import java.util.ArrayList;

public class WargasAdapter extends RecyclerView.Adapter<WargasAdapter.WargaHolder>{

    private Context context;
    private ArrayList<Warga> list;

    public WargasAdapter(Context context, ArrayList<Warga> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public WargaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list,parent,false);
        return new WargaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WargaHolder holder, int position) {
        Warga warga = list.get(position);
        holder.txtNama.setText(warga.getNama());
        holder.txtNik.setText(warga.getNik());
        holder.txtAlamat.setText(warga.getAlamat());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class WargaHolder extends RecyclerView.ViewHolder{

        private TextView txtNama, txtNik, txtAlamat;
        private ImageButton btnOption;

        public WargaHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txtNamaWarga);
            txtNik = itemView.findViewById(R.id.txtNikWarga);
            txtAlamat = itemView.findViewById(R.id.txtAlamatWarga);
            btnOption = itemView.findViewById(R.id.btnOption);
        }
    }
}
