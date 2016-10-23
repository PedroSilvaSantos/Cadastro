package br.com.caelum.cadastro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by android6406 on 21/06/16.
 */
public class FormularioHelper {

    private Aluno aluno;

    private ImageView foto;
    private FloatingActionButton fotoButton;
    private TextInputEditText nome;
    private TextInputEditText telefone;
    private TextInputEditText email;
    private TextInputEditText endereco;
    private RatingBar nota;


    public FloatingActionButton getFotoButton() {
        return fotoButton;
    }

    public FormularioHelper(FormularioActivity activity) {

        this.aluno = new Aluno();

        this.nome=(TextInputEditText) activity.findViewById(R.id.nome);
        this.telefone=(TextInputEditText) activity.findViewById(R.id.telefone);
        this.endereco=(TextInputEditText) activity.findViewById(R.id.endereco);
        this.email=(TextInputEditText) activity.findViewById(R.id.email);
        this.nota = (RatingBar) activity.findViewById(R.id.nota);
        this.foto = (ImageView) activity.findViewById(R.id.foto);
        this.fotoButton = (FloatingActionButton) activity.findViewById(R.id.botaoFoto);





    }
    public Aluno pegaAlunoDoFormulario(){
        aluno.setNome(nome.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setEmail(email.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));
        aluno.setCaminhoFoto((String) foto.getTag() );
        return aluno;
    }
    public boolean temNOme(){
       return !nome.getText().toString().isEmpty();


    }
    public void populaAluno(Aluno aluno) {
        nome.setText(aluno.getNome());
        nota.setProgress(aluno.getNota().intValue());
        telefone.setText(aluno.getTelefone());
        endereco.setText(aluno.getEndereco());
        email.setText(aluno.getEmail());
        if (aluno.getCaminhoFoto()!=null){
            this.carregaImagem(aluno.getCaminhoFoto());
        }
        this.aluno = aluno;

    }



    public void mostraErro(){
        nome.setError("Campo nome não pode ser vazio");
    }

    public void carregaImagem(String localArquivoFoto) {


        Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
        Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(imagemFoto, imagemFoto.getWidth(),300,true);
        foto.setImageBitmap(imagemFotoReduzida);
        foto.setTag(localArquivoFoto);
        foto.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
