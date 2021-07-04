package com.example.searchkodepos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;


public class AdapterHasil extends ArrayAdapter {

    Integer[] kodepos;
    String[] provinsi, kota, kecamatan, kelurahan;

    public AdapterHasil(@NonNull Context context, String[] provinsi, String[] kota, String[] kecamatan, String[] kelurahan, Integer[] kodepos){
        super(context, R.layout.listview_adapter_hasil, R.id.kodepos, kodepos);
        this.kodepos = kodepos;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.kelurahan = kelurahan;
    }

    public View getView(final int position, View converView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View row = inflater.inflate(R.layout.listview_adapter_hasil, parent, false);

        TextView tvKodepos = row.findViewById(R.id.kodepos);
        TextView tvProvinsi = row.findViewById(R.id.provinsi);
        TextView tvKota = row.findViewById(R.id.kota);
        TextView tvKecamatan = row.findViewById(R.id.kecamatan);
        TextView tvKelurahan = row.findViewById(R.id.kelurahan);

        tvKodepos.setText(String.valueOf(kodepos[position]));
        tvProvinsi.setText(provinsi[position]);
        tvKota.setText(kota[position]);
        tvKecamatan.setText(kecamatan[position]);
        tvKelurahan.setText(kelurahan[position]);

        return row;
    }
}
