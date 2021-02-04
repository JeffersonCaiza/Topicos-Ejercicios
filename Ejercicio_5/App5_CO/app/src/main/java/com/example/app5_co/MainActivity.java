package com.example.app5_co;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private Tablero fondo;
    int x, y;
    private Casilla[][] casillas;
    private boolean activo = true;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout1);
        fondo = new Tablero(this);
        fondo.setOnTouchListener(this);
        layout.addView(fondo);
        casillas = new Casilla[8][8];
        for (int f = 0; f < 8; f++){
            for (int c = 0; c < 8;c++){
                casillas[f][c] = new Casilla();
            }
        }
        this.disponerBombas();
        this.contarBombasPerimetro();
        getSupportActionBar().hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.autoCompleteToStart){
            return  true;
        }
        return  super.onOptionsItemSelected(item);
    }
    public void reiniciar(View v){
        casillas = new Casilla[8][8];
        for (int f= 0; f < 8; f++){
            for (int c= 0; c < 8; c++){
                casillas[f][c] = new Casilla();
            }
        }
        this.disponerBombas();
        this.contarBombasPerimetro();
        activo = true;
        fondo.invalidate();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        if (activo)
            for (int f = 0; f < 8; f++){
                for (int c = 0; c < 8; c++){
                    if(casillas[f][c].dentro((int)event.getX(),
                            (int) event.getY())) {
                        casillas[f][c].destapado = true;
                        if (casillas[f][c].contenido == 80){
                            Toast.makeText(this, "Booooooommmmmmmm", Toast.LENGTH_LONG).show();
                            activo=false;
                        } else if (casillas[f][c].contenido == 0)
                            recorrer(f, c);
                        fondo.invalidate();
                    }
                }
            }
        if (gano() && activo) {
            Toast.makeText(this, "Ganaste", Toast.LENGTH_LONG).show();
            activo = false;
        }
        return  true;
    }

    class Tablero extends View{
        public Tablero(Context context){
            super(context);
        }

        protected void onDraw(Canvas canvas){
            canvas.drawRGB(0, 0, 0);
            int ancho = 0;
            if (canvas.getWidth() < canvas.getWidth())
                ancho = fondo.getWidth();
            else
                ancho = fondo.getWidth();
            int anchocua = ancho / 8;
            Paint paint = new Paint();
            paint.setTextSize(20);
            Paint paint2 = new Paint();
            paint2.setTextSize(20);
            paint2.setTypeface(Typeface.DEFAULT_BOLD);
            paint2.setARGB(255, 0, 0,255);
            Paint painlinea1 = new Paint();
            painlinea1.setARGB(255, 255, 255,255);
            int filaact = 0;
            for (int f = 0; f < 8; f++){
                for (int c = 0; c < 8; c++){
                    casillas[f][c].fijarxy(c * anchocua, filaact, anchocua);
                    if (casillas[f][c].destapado == false)
                        paint.setARGB(152, 204,204,204);
                    else
                        paint.setARGB(255,153,153,153);
                    canvas.drawRect(c * anchocua, filaact, c * anchocua
                            + anchocua - 2, filaact + anchocua - 2, paint );
                    canvas.drawLine(c * anchocua, filaact, c * anchocua
                                       + anchocua,filaact, painlinea1);
                    canvas.drawLine( c * anchocua + anchocua - 1, filaact, c
                            * anchocua + anchocua - 1, filaact + anchocua,
                            painlinea1);
                    if ( casillas [f][c].contenido >=1
                            && casillas[f][c].contenido <=8
                            && casillas[f][c].destapado)
                        canvas.drawText(
                                String.valueOf(casillas[f][c].contenido), c
                                * anchocua + (anchocua / 2) - 8,
                                filaact + anchocua / 2, paint2);

                    if (casillas[f][c].contenido == 80
                            && casillas[f][c].destapado){
                        Paint bomba = new Paint();
                        bomba.setARGB(255, 255,0, 0);
                        canvas.drawCircle( c * anchocua + (anchocua / 2),
                                filaact + (anchocua / 2), 8, bomba);
                    }
                }
                filaact = filaact + anchocua;
            }

        }
    }

    private  void  disponerBombas(){
        int cantidad = 8;
        do{
            int fila = (int) (Math.random() * 8);
            int columna = (int) (Math.random() * 8);
            if (casillas[fila][columna].contenido == 0){
                casillas[fila][columna].contenido = 80;
                cantidad--;
            }
        }while (cantidad != 0);
    }

    private boolean gano (){
        int cant = 0;
        for (int f = 0; f < 8; f ++)
            for(int c = 0; c < 8; c++)
                if (casillas[f][c].destapado)
                    cant++;
        if (cant == 56)
            return  true;
        else
            return false;
    }

    private void contarBombasPerimetro(){
        for (int f = 0; f < 8; f++){
            for (int c = 0; c <  8; c++){
                if (casillas[f][c].contenido == 0){
                    int cant = contarCoordenada(f,c);
                    casillas[f][c].contenido = cant;
                }
            }
        }
    }

    int contarCoordenada (int fila, int columna){
        int total = 0;
        if(fila - 1 >=0 && columna - 1 >=0){
            if (casillas[fila-1][columna-1].contenido == 80)
                total++;
        }
        if (fila -1 >=0){
            if (casillas[fila - 1][columna].contenido == 80)
                total++;
        }
        if (fila -1 >=0 && columna + 1 < 8){
            if(casillas[fila -1][columna +1].contenido == 80)
                total++;
        }

        if (columna + 1 < 8){
            if(casillas[fila][columna + 1].contenido == 80)
                total++;
        }

        if (fila + 1 < 8 && columna + 1 < 8){
            if(casillas[fila + 1][columna + 1].contenido == 80)
                total++;
        }
        if (fila + 1 < 8){
            if(casillas[fila + 1][columna].contenido == 80)
                total++;
        }

        if (fila + 1 < 8 && columna -1 >= 0){
            if(casillas[fila + 1][columna - 1].contenido == 80)
                total++;
        }

        if (columna - 1 >=0){
            if(casillas[fila][columna - 1].contenido == 80)
                total++;
        }
        return total;
    }

    private void  recorrer(int fil, int col){
        if(fil >=0 && fil < 8 && col >=0 && col < 8){
            if(casillas[fil][col].contenido == 0){
                casillas[fil][col].destapado = true;
                casillas[fil][col].contenido = 50;
                recorrer(fil, col + 1);
                recorrer(fil, col - 1);
                recorrer(fil + 1, col );
                recorrer(fil - 1, col);
                recorrer(fil -1 , col -1);
                recorrer(fil -1, col + 1);
                recorrer(fil + 1, col + 1);
                recorrer(fil + 1, col - 1);
            }else if (casillas[fil][col].contenido >=1
                    && casillas[fil][col].contenido <= 8){
                casillas[fil][col].destapado = true;
            }
        }
    }
}