package com.example.app1_oc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.support.v4.app.RemoteActionCompatParcelizer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;



public class MainActivity extends AppCompatActivity {
    private EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText) findViewById(R.id.editTextTextMultiLine3);
        String[] archivos = fileList();

        if (existe(archivos, "notas.txt"))
            try {
                InputStreamReader archivo = new InputStreamReader(
                        openFileInput("notas.txt"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String todo = "";
                while (linea != null) {
                    todo = todo + linea + "\n";
                    linea = br.readLine();
                }
                br.close();
                archivo.close();
                et1.setText(todo);
            } catch (IOException e) {
            }
    }

    @Override
    public  boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean existe(String[] archivos, String archbusca){
        for (int f = 0; f < archivos.length; f++)
            if (archbusca.equals(archivos[f]))
                return true;
            return false;
    }

    public void grabar(View v) {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(
                    "notas.txt", Activity.MODE_PRIVATE));
            archivo.write(et1.getText().toString());
            archivo.flush();
            archivo.close();
        }catch (IOException e) {
        }
        Toast t = Toast.makeText(this, "Los datos estan grabados",
                Toast.LENGTH_SHORT);
        t.show();
        finish();
    }
}