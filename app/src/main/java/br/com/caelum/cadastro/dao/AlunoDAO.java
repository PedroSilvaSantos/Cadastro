package br.com.caelum.cadastro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by android6406 on 21/06/16.
 */
public class AlunoDAO extends SQLiteOpenHelper {
    private static final int VERSAO =2;
    private static final String TABELA ="Alunos";
    private static final String DATABASE ="cadastroCaelum";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String ddl = "CREATE TABLE " + TABELA
                + " (id INTEGER PRIMARY KEY, "
                + " nome TEXT NOT NULL,"
                + " telefone TEXT,"
                + " endereco TEXT,"
                + " email TEXT,"
                + " nota REAL"
                + " caminhoFoto TEXT"
                +");";
        sqLiteDatabase.execSQL(ddl);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAntiga, int novaVersao) {

        String sql ="ALTER TABLE " + TABELA+ " ADD COLUMN caminhoFoto TEXT;";
        sqLiteDatabase.execSQL(sql);
       // onCreate(sqLiteDatabase);
    }
    public AlunoDAO(Context context){
        super(context,DATABASE,null, VERSAO);
    }

    public void insere (Aluno aluno){



        getWritableDatabase().insert(TABELA,null,this.preenhe(aluno));

    }
    public List<Aluno> getLista(){
        List<Aluno> alunos = new ArrayList<Aluno>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " +TABELA + ";",null);

        while (c.moveToNext()){
            Aluno aluno = new Aluno();

            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setEmail(c.getString(c.getColumnIndex("email")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            alunos.add(aluno);
        }
        c.close();;
        return alunos;
    }
    public void deletar(Aluno aluno){
        String[] args = {aluno.getId().toString()};
        getWritableDatabase().delete(TABELA, "id=?", args);
    }
    public void alterar(Aluno aluno){
        String[] args = {aluno.getId().toString()};
        getWritableDatabase().update(TABELA,this.preenhe(aluno),"id=?", args);


    }
    public ContentValues preenhe (Aluno aluno){
        ContentValues values =new ContentValues();
        values.put ("nome", aluno.getNome());
        values.put ("telefone", aluno.getTelefone());
        values.put ("endereco", aluno.getEndereco());
        values.put ("email", aluno.getEmail());
        values.put ("nota", aluno.getNota());
        values.put("caminhoFoto", aluno.getCaminhoFoto());
        return values;
    }

    public boolean isAluno(String telefone){
        String[] parametros = {telefone};
        Cursor rawQuerry = getReadableDatabase().rawQuery("SELECT telefone FROM " +TABELA + " WHERE telefone=?", parametros);
        int total = rawQuerry.getCount();
        rawQuerry.close();

        return  total >0;
    }
}
