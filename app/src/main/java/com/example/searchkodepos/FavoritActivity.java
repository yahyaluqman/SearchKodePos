package com.example.searchkodepos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.searchkodepos.DataBase.DataBaseAccess;

public class FavoritActivity extends AppCompatActivity {
    //Mendeklarasikan variabel dengan tipe data ListView
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);

        //Menghubungkan variabel listView dengan componen listView pada layout
        listView = findViewById(R.id.listView);

        //Untuk membuka database dari DataBasaAccess ke FavoritActivity
        DataBaseAccess dataBaseAccess = DataBaseAccess.getInstance(FavoritActivity.this);
        dataBaseAccess.open();

        //Untuk mendapatkan kodepos favorit
        Cursor data = dataBaseAccess.Get("favorit");

        //Untuk memberikan pesan "Tidak Ada KodePos Tersimpan"
        if (data.getCount() == 0) {
            Toast.makeText(this, "Tidak Ada KodePos Tersimpan", Toast.LENGTH_SHORT).show();
        } else {
            //untuk menentukan jumlah array
            Integer[] id = new Integer[data.getCount()];
            String[] provinsi = new String[data.getCount()];
            String[] kota = new String[data.getCount()];
            String[] kecamatan = new String[data.getCount()];
            String[] kelurahan = new String[data.getCount()];
            Integer[] kodepos = new Integer[data.getCount()];
            int i = 0;
            //Untuk mengisi nilai array
            while (data.moveToNext()) {
                id[i] = data.getInt(0);
                provinsi[i] = data.getString(1);
                kota[i] = data.getString(2);
                kecamatan[i] = data.getString(3);
                kelurahan[i] = data.getString(4);
                kodepos[i] = data.getInt(5);
                i++;
            }
            //Untuk membuat adapter layout kodepos saat di FavoritActivity
            AdapterHasil adapterHasil = new AdapterHasil(FavoritActivity.this, provinsi, kota, kecamatan, kelurahan, kodepos);
            listView.setAdapter(adapterHasil);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long ids) {
                    //Untuk membuat dialog di FavoritActivity
                    AlertDialog.Builder builder = new AlertDialog.Builder(FavoritActivity.this);
                    //Untuk menampilakan  judul dialog "Hapus Data Ini Dari Favorit?" di popup
                    builder.setTitle("Hapus Data Ini Dari Favorit?")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Untuk menghapus tabel/kode pos favorit
                                    Integer isDeleted = dataBaseAccess.Delete("favorit", "id=?", String.valueOf(id[position]));
                                    if (isDeleted > 0) {
                                        //Untuk menampilkan pesan "Data Terhapus"
                                        Toast.makeText(FavoritActivity.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                                        startActivity(getIntent());
                                    } else {
                                        //Untuk menampilkan "Gagal Hapus Data"
                                        Toast.makeText(FavoritActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            //Untuk memberikan dialog "Tidak" di popup
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                //Untuk menutup dialog
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            //Untuk memberikan dialog "HAPUS SEMUA" di popup
                            .setNeutralButton("HAPUS SEMUA", new DialogInterface.OnClickListener() {
                                @Override
                                //untuk hapus semua data dari database tabel/kode pos favorit
                                public void onClick(DialogInterface dialog, int which) {
                                    Cursor data = dataBaseAccess.DeleteAll("favorit");
                                    if(data.getCount() > 0) {
                                        startActivity(getIntent());
                                    } else {
                                        startActivity(getIntent());
                                    }
                                }
                            });
                    //Untuk membuat alertDialog
                    AlertDialog alertDialog = builder.create();
                    //Untuk menampilkan alertDialog
                    alertDialog.show();
                }
            });
        }
    }

    @Override
    //kembali ke main activity ketika tombol back di tekan
    public void onBackPressed() {
        startActivity(new Intent(FavoritActivity.this, MainActivity.class));
        super.onBackPressed();
    }
}