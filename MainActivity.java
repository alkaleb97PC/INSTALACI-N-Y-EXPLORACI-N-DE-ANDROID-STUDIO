package com.example.ordenamientomillon;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Ordenamiento1M";
    private static final int CANTIDAD = 1_000_000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // La tarea se ejecuta fuera del hilo principal para no bloquear la interfaz.
        new Thread(this::ejecutarPrueba, "OrdenamientoWorker").start();
    }

    private void ejecutarPrueba() {
        int[] numeros = new int[CANTIDAD];
        Random aleatorio = new Random();

        long inicioTotal = SystemClock.elapsedRealtimeNanos();
        long inicioGeneracion = SystemClock.elapsedRealtimeNanos();
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = aleatorio.nextInt();
        }
        long finGeneracion = SystemClock.elapsedRealtimeNanos();

        long inicioOrdenamiento = SystemClock.elapsedRealtimeNanos();
        Arrays.sort(numeros);
        long finOrdenamiento = SystemClock.elapsedRealtimeNanos();

        boolean ordenado = true;
        for (int i = 1; i < numeros.length; i++) {
            if (numeros[i - 1] > numeros[i]) {
                ordenado = false;
                break;
            }
        }
        long finTotal = SystemClock.elapsedRealtimeNanos();

        Log.i(TAG, "=== INICIO DE RESULTADOS ===");
        Log.i(TAG, "Cantidad de enteros: " + numeros.length);
        Log.i(TAG, String.format(Locale.US, "Generacion: %.3f ms", (finGeneracion - inicioGeneracion) / 1_000_000.0));
        Log.i(TAG, String.format(Locale.US, "Ordenamiento: %.3f ms", (finOrdenamiento - inicioOrdenamiento) / 1_000_000.0));
        Log.i(TAG, String.format(Locale.US, "Tiempo total (incluye verificacion): %.3f ms", (finTotal - inicioTotal) / 1_000_000.0));
        Log.i(TAG, "Ordenado correctamente: " + ordenado);
        Log.i(TAG, "Primeros 5: " + Arrays.toString(Arrays.copyOfRange(numeros, 0, 5)));
        Log.i(TAG, "Ultimos 5: " + Arrays.toString(Arrays.copyOfRange(numeros, numeros.length - 5, numeros.length)));
        Log.i(TAG, "=== FIN DE RESULTADOS ===");
    }
}
