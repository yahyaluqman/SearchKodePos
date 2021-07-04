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

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);

        listView = findViewById(R.id.listView);

        DataBaseAccess dataBaseAccess = DataBaseAccess.getInstance(FavoritActivity.this);
        dataBaseAccess.open();

        Cursor data = dataBaseAccess.Get("favorit");

        if (data.getCount() == 0) {
            Toast.makeText(this, "Tidak Ada KodePos Tersimpan", Toast.LENGTH_SHORT).show();
        } else {
            Integer[] id = new Integer[data.getCount()];
            String[] provinsi = new String[data.getCount()];
            String[] kota = new String[data.getCount()];
            String[] kecamatan = new String[data.getCount()];
            String[] kelurahan = new String[data.getCount()];
            Integer[] kodepos = new Integer[data.getCount()];
            int i = 0;
            while (data.moveToNext()) {
                id[i] = data.getInt(0);
                provinsi[i] = data.getString(1);
                kota[i] = data.getString(2);
                kecamatan[i] = data.getString(3);
                kelurahan[i] = data.getString(4);
                kodepos[i] = data.getInt(5);
                i++;
            }
            AdapterHasil adapterHasil = new AdapterHasil(FavoritActivity.this, provinsi, kota, kecamatan, kelurahan, kodepos);
            listView.setAdapter(adapterHasil);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long ids) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FavoritActivity.this);
                    builder.setTitle("Hapus Data Ini Dari Favorit?")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Integer isDeleted = dataBaseAccess.Delete("favorit", "id=?", String.valueOf(id[position]));
                                    if (isDeleted > 0) {
                                        Toast.makeText(FavoritActivity.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                                        startActivity(getIntent());
                                    } else {
                                        Toast.makeText(FavoritActivity.this, "Gagal Hapus Data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setNeutralButton("HAPUS SEMUA", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Cursor data = dataBaseAccess.DeleteAll("favorit");
                                    if(data.getCount() > 0) {
                                        startActivity(getIntent());
                                    } else {
                                        startActivity(getIntent());
                                    }
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FavoritActivity.this, MainActivity.class));
        super.onBackPressed();
    }
}