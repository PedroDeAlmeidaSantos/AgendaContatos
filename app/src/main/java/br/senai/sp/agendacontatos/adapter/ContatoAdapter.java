package br.senai.sp.agendacontatos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.senai.sp.agendacontatos.R;
import br.senai.sp.agendacontatos.modelo.Contato;

public class ContatoAdapter extends BaseAdapter{

    private List<Contato> contatos;
    private Context context;

    public ContatoAdapter(Context context, List<Contato> contatos){
        this.contatos = contatos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contatos.size();
    }

    @Override
    public Object getItem(int position) {
        return contatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contatos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Contato contato = contatos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_lista_contatos, null);

        TextView txtContato = view.findViewById(R.id.txt_contato);
        txtContato.setText(contato.getNome());

        TextView txtTelefone = view.findViewById(R.id.txt_telefone);
        txtTelefone.setText(contato.getTelefone());

        ImageView foto = view.findViewById(R.id.image_contato);

        ImageView edit = view.findViewById(R.id.image_edit);

        return view;

    }

}
