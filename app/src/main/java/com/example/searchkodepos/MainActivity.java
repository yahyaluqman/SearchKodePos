package com.example.searchkodepos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.searchkodepos.DataBase.DataBaseAccess;


public class MainActivity extends AppCompatActivity {
    //Mendeklarasikan variabel dengan tipe ImageButton, EditText dan ListView
    ImageButton favorit;
    EditText input;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Menghubungkan setiap variabel dengan favorit, input dan listView pada layout
        favorit = findViewById(R.id.favorit);
        input = findViewById(R.id.input);
        listView = findViewById(R.id.listView);

        //Membuat action klik untuk membuka menu favorit
        favorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FavoritActivity.class));
            }
        });

        //Untuk mengambil data dari database ketika user mengetik provinsi/kota/kecamatan/kelurahan/kodepos
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    //Untuk menampilkan listView
                    listView.setVisibility(View.VISIBLE);
                    //Untuk membuka database dari DataBasaAccess ke MainActivity
                    DataBaseAccess dataBaseAccess = DataBaseAccess.getInstance(MainActivity.this);
                    dataBaseAccess.open();

                    //Untuk mengambil data menggunakan query
                    Cursor data = dataBaseAccess.Where("kodepos", "provinsi LIKE '%" + s.toString() + "%' " +
                            "OR kota LIKE '%" + s.toString() + "%' " +
                            "OR kecamatan LIKE '%" + s.toString() + "%' " +
                            "OR kelurahan LIKE '%" + s.toString() + "%' " +
                            "OR kodepos LIKE '" + s.toString() + "%'");
                    //untuk menentukan jumlah array
                    if (data.getCount() != 0) {
                        String[] provinsi = new String[data.getCount()];
                        String[] kota = new String[data.getCount()];
                        String[] kecamatan = new String[data.getCount()];
                        String[] kelurahan = new String[data.getCount()];
                        Integer[] kodepos = new Integer[data.getCount()];
                        int i = 0;
                        //Untuk mengisi nilai array
                        while (data.moveToNext()) {
                            provinsi[i] = data.getString(0);
                            kota[i] = data.getString(1);
                            kecamatan[i] = data.getString(2);
                            kelurahan[i] = data.getString(3);
                            kodepos[i] = data.getInt(4);
                            i++;
                        }
                        //Untuk membuat adapter layout kodepos saat di MainActivity
                        AdapterHasil adapterHasil = new AdapterHasil(MainActivity.this, provinsi, kota, kecamatan, kelurahan, kodepos);
                        listView.setAdapter(adapterHasil);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //Untuk membuat dialog di MainActivity
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                //Untuk menampilakan  judul "Tambahkan Data Ini Ke Favorit?" di popup
                                builder.setTitle("Tambahkan Data Ini Ke Favorit?")
                                        //Untuk memberikan dialog button "Ya" di popup
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            //untuk menambahkan kodepos ke favorit
                                            public void onClick(DialogInterface dialog, int which) {
                                                boolean isInserted = dataBaseAccess.insertFavorit(provinsi[position], kota[position], kecamatan[position], kelurahan[position], kodepos[position]);
                                                if(isInserted){
                                                    //Untuk menampilkan pesan "Berhasil Menambahkan Ke Favorit"
                                                    Toast.makeText(MainActivity.this, "Berhasil Menambahkan Ke Favorit", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    //Untuk menampilkan pesan "Gagal Menambahkan Ke Favorit"
                                                    Toast.makeText(MainActivity.this, "Gagal Menambahkan Ke Favorit", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        //Untuk memberikan dialog button "Tidak" di popup
                                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                            @Override
                                            //Untuk menutup dialog
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                //Untuk membuat alertDialog
                                AlertDialog alertDialog = builder.create();
                                //Untuk menampilkan/memanggil alertDialog
                                alertDialog.show();
                            }
                        });
                    }
                } else {
                    //Untuk menghilangkan listView
                    listView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    //keluar dari aplikasi ketika tombol back di tekan
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        moveTaskToBack(true);
    }
}