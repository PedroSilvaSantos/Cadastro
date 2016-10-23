package br.com.caelum.cadastro;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URI;
import java.util.List;
import java.util.jar.Manifest;

import br.com.caelum.cadastro.adapter.ListaAlunosAdapter;
import br.com.caelum.cadastro.converter.AlunoConverter;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.permissao.Permissao;
import br.com.caelum.cadastro.support.WebClient;
import br.com.caelum.cadastro.task.EnviaAlunosTask;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;
    private List<Aluno> alunos;

    private void carregaLista(){
        AlunoDAO dao = new AlunoDAO(this);
        alunos=dao.getLista();
        dao.close();
        //ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1,alunos);
        ListaAlunosAdapter adapter = new ListaAlunosAdapter(this,alunos);

        this.listaAlunos.setAdapter(adapter);
    }

    @Override
    protected void  onResume(){
        super.onResume();
        this.carregaLista();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        Permissao.fazPermissao(this);
        listaAlunos = (ListView) findViewById(R.id.lista);
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Aluno aluno=(Aluno) listaAlunos.getItemAtPosition(posicao);
                //Toast.makeText(ListaAlunosActivity.this, "ID: "+aluno.getId(), Toast.LENGTH_SHORT).show();
                Intent edicao = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                edicao.putExtra("aluno",aluno);
                startActivity(edicao);


            }
        });
        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
        @Override
        public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
            Aluno aluno=(Aluno) listaAlunos.getItemAtPosition(posicao);
            Toast.makeText(ListaAlunosActivity.this,"nome do Aluno: "+aluno.getNome(),Toast.LENGTH_LONG).show();
            return false;
        }}) ;


        FloatingActionButton btnCadastro = (FloatingActionButton) findViewById(R.id.btnCadastro);
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);

                startActivity(intent);
            }
        });
        registerForContextMenu(listaAlunos);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSeleionadao = (Aluno) listaAlunos.getAdapter().getItem(info.position);
        final MenuItem ligar =menu.add("Ligar");
        final MenuItem enviarSms= menu.add("Enviar Sms");
        final MenuItem acharMapa = menu.add("Achar no mapa");
        final MenuItem enviarEmail = menu.add("Enviar Email");
        MenuItem deletar= menu.add("Deletar");


        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                    AlunoDAO alunoDAO = new AlunoDAO(ListaAlunosActivity.this);
                    alunoDAO.deletar(alunoSeleionadao);
                    alunoDAO.close();
                ListaAlunosActivity.this.carregaLista();

                return true;


            }

        });
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent ligacao = new Intent(Intent.ACTION_CALL);
                ligacao.setData(Uri.parse("tel:" + alunoSeleionadao.getTelefone()));
                startActivity(ligacao);
                return true;
            }
        });
        enviarSms.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent sms = new Intent(Intent.ACTION_VIEW);
                sms.setData(Uri.parse("sms:"+alunoSeleionadao.getTelefone()));
                enviarSms.setIntent(sms);
                startActivity(sms);
                return true;

            }
        });
        acharMapa.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent mapa = new Intent(Intent.ACTION_VIEW);
                mapa.setData(Uri.parse("geo:0,0?z=14&q="+alunoSeleionadao.getEndereco()));
                acharMapa.setIntent(mapa);
                startActivity(mapa);
                return true;

            }
        });
        enviarEmail.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("text/plain");

                email.putExtra(Intent.EXTRA_EMAIL, alunoSeleionadao.getEmail());
                email.putExtra(Intent.EXTRA_SUBJECT, "Nota");

                //email.
                startActivity(email);
                return true;

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_lista_alunos,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){

            case R.id.menu_enviar_notas:
                AlunoDAO dao = new AlunoDAO(this);
                List<Aluno> alunos=dao.getLista();
                dao.close();
                EnviaAlunosTask calcula = new EnviaAlunosTask(this);
                calcula.execute();
                return true;

            case R.id.menu_receber_provas:
                Intent provas = new Intent(this,ProvasActivity.class);
                startActivity(provas);
                return true;

            case R.id.menu_mapa:
                Intent mapa = new Intent(this,MostraAlunosActivity.class);
                startActivity(mapa);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
