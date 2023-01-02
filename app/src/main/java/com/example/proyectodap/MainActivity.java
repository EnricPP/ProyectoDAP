package com.example.proyectodap;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listamain = findViewById(R.id.list_view_main);
        ArrayList<String> dispositivos = new ArrayList<String>();
        TextView txt_prueba = findViewById(R.id.txt_pulsado);
        Button btn_logout = findViewById(R.id.btn_logout);

        for (int i=1; i<=10; i++){
            dispositivos.add("Elemento "+i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, dispositivos);

        listamain.setAdapter(adapter);
        listamain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                txt_prueba.setText(adapter.getItem(i).toString());
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startLoginActivity = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(startLoginActivity);
                finish();
            }
        });
    }
}