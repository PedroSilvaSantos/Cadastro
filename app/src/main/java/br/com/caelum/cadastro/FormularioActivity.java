package br.com.caelum.cadastro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.widget.Toast;

import java.io.File;

import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {


    public String getLocalArquivoFoto() {
        return localArquivoFoto;
    }

    public void setLocalArquivoFoto(String localArquivoFoto) {
        this.localArquivoFoto = localArquivoFoto;
    }

    private String localArquivoFoto;
    private static final int TIRA_FOTO=123;
    private FormularioHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.helper = new FormularioHelper(this);

        Intent intent=this.getIntent();
        Aluno aluno=(Aluno)intent.getSerializableExtra("aluno");
        if (aluno!=null){
            this.helper.populaAluno(aluno);
        }
        FloatingActionButton foto =helper.getFotoButton();
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localArquivoFoto = getExternalFilesDir(null)+"/"+System.currentTimeMillis()+"jpg";
                Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri localFoto = Uri.fromFile(new File(localArquivoFoto));
                irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT,localFoto);
                startActivityForResult(irParaCamera,TIRA_FOTO);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
      getMenuInflater().inflate(R.menu.menu_formulario, menu);

        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno = helper.pegaAlunoDoFormulario();
                AlunoDAO alunoDAO = new AlunoDAO(this);
                if (aluno.getId()==null){
                    if (helper.temNOme()){



                        alunoDAO.insere(aluno);
                        finish();

                    }
                    else{
                        helper.mostraErro();
                        return true;
                    }

                }else{
                    alunoDAO.alterar(aluno);
                }
                alunoDAO.close();
                finish();




                //Toast.makeText(FormularioActivity.this, "Nome: "+helper.pegaAlunoDoFormulario().getNome(), Toast.LENGTH_SHORT).show();


                //finish();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode==TIRA_FOTO){
            if (resultCode== Activity.RESULT_OK){
                helper.carregaImagem(this.getLocalArquivoFoto());
            }else {
                this.localArquivoFoto=null;
            }

        }
    }

}
