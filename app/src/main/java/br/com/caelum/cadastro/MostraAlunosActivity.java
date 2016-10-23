package br.com.caelum.cadastro;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by android6406 on 24/06/16.
 */
public class MostraAlunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_mostra_alunos);
        MapaFragment mapaFragment = new MapaFragment();
        android.support.v4.app.FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.mostra_alunos_mapa, mapaFragment);
        tx.commit();
    }



}
