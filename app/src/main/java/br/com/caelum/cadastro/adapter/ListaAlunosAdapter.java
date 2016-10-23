package br.com.caelum.cadastro.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.modelo.Aluno;

/**
 * Created by android6406 on 23/06/16.
 */
public class ListaAlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Activity activity;

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int i) {
        return alunos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alunos.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View view;
        if (convertView == null) {
            view = activity.getLayoutInflater().inflate(R.layout.item, parent, false);
        } else {
            view = convertView;
        }

        Aluno aluno = alunos.get(i);
        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        TextView telefone = (TextView) view.findViewById(R.id.item_telefone);
        ImageView foto = (ImageView) view.findViewById(R.id.item_foto);


        nome.setText(alunos.get(i).getNome());
        telefone.setText(alunos.get(i).getTelefone());


        //   Picasso.with(activity).load("file:///"+aluno.getCaminhoFoto()).resize(100,100).centerCrop().into(foto);


        if (aluno.getCaminhoFoto() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            foto.setImageBitmap(scaledBitmap);
        }else {
            Picasso.with(activity).load(R.drawable.ic_no_image).resize(100,100).centerCrop().into(foto);
        }
            if (i % 2 == 0) {
                view.setBackgroundColor(activity.getResources().getColor(R.color.linha_par));
            } else {
                view.setBackgroundColor(activity.getResources().getColor(R.color.linha_impar));

            }
            return view;
        }


    public ListaAlunosAdapter(Activity activity,List<Aluno> alunos ) {
        this.activity = activity;
        this.alunos = alunos;

    }
}
