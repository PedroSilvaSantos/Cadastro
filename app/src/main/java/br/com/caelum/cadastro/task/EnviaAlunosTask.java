package br.com.caelum.cadastro.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import br.com.caelum.cadastro.ListaAlunosActivity;
import br.com.caelum.cadastro.converter.AlunoConverter;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.support.WebClient;

/**
 * Created by android6406 on 23/06/16.
 */
public class EnviaAlunosTask extends AsyncTask<Void,Void, String > {
    Context contexto;

    public EnviaAlunosTask(Context ctx) {
        this.contexto=ctx;
    }



    @Override
    protected String doInBackground(Void... voids) {
        AlunoConverter alunoConverter = new AlunoConverter();
        AlunoDAO dao = new AlunoDAO (contexto);
        String json=alunoConverter.toJSON(dao.getLista());
        WebClient client = new WebClient();
        String media = client.post(json);
        return media;
    }

    @Override
    protected void onPostExecute (String result){
        Toast.makeText(contexto, result, Toast.LENGTH_LONG).show();
    }
}
