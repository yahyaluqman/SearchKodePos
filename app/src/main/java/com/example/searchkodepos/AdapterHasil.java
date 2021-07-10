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
    //Mendeklarasikan variabel dengan tipe data Interger dan String
    Integer[] kodepos;
    String[] provinsi, kota, kecamatan, kelurahan;

    //Untuk menampung data array dari kodepos, provinsi, kota, kecamatan dan kelurahan
    public AdapterHasil(@NonNull Context context, String[] provinsi, String[] kota, String[] kecamatan, String[] kelurahan, Integer[] kodepos){
        super(context, R.layout.listview_adapter_hasil, R.id.kodepos, kodepos);
        this.kodepos = kodepos;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.kelurahan = kelurahan;
    }

    //Menggunakan View row untuk setiap item layout
    public View getView(final int position, View converView, ViewGroup parent) {
        //Untuk mendapatkan inflator di konstruktor
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Untuk mengembang xml listview_adapater_hasil menampilkan row
        @SuppressLint("ViewHolder") View row = inflater.inflate(R.layout.listview_adapter_hasil, parent, false);

        //Menghubungkan setiap variabel dengan componen Textview pada layout
        TextView tvKodepos = row.findViewById(R.id.kodepos);
        TextView tvProvinsi = row.findViewById(R.id.provinsi);
        TextView tvKota = row.findViewById(R.id.kota);
        TextView tvKecamatan = row.findViewById(R.id.kecamatan);
        TextView tvKelurahan = row.findViewById(R.id.kelurahan);

        //menampilkan value dari setiap variabel[posisi] kedalam setiap TextView
        tvKodepos.setText(String.valueOf(kodepos[position]));
        tvProvinsi.setText(provinsi[position]);
        tvKota.setText(kota[position]);
        tvKecamatan.setText(kecamatan[position]);
        tvKelurahan.setText(kelurahan[position]);

        //Untuk Mengembalikan row yang dihasilkan
        return row;
    }
}
