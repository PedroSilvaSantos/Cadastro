package br.com.caelum.cadastro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.caelum.cadastro.modelo.Prova;

/**
 * Created by android6406 on 24/06/16.
 */
public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (isDeitado()){
            transaction.replace(R.id.provas_lista, new ListaProvasFragment());
            transaction.replace(R.id.provas_detalhes, new DetalhesProvaFragment());

        }else {


        transaction.replace(R.id.provas_view, new ListaProvasFragment());
        }
        transaction.commit();
    }
    public boolean isDeitado(){
        return getResources().getBoolean(R.bool.isDeitado);
    }
    public void selecionaProva(Prova prova){
        FragmentManager manager =getSupportFragmentManager();

        if (isDeitado()){
            DetalhesProvaFragment detalhesProvaFragment = (DetalhesProvaFragment)manager.findFragmentById(R.id.provas_detalhes);
            detalhesProvaFragment.populaCamposComDados(prova);

        }else {
            Bundle argumentos = new Bundle();
            argumentos.putSerializable("prova",prova);

            DetalhesProvaFragment detalhesProvaFragment = new DetalhesProvaFragment();
            detalhesProvaFragment.setArguments(argumentos);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.provas_view,detalhesProvaFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
