package br.senai.sp.agendacontatos.contato;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.ImageView;

import br.senai.sp.agendacontatos.R;
import br.senai.sp.agendacontatos.modelo.Contato;

public class CadastroContatosHelper {

    private EditText txtNome;
    private EditText txtEndereco;
    private EditText txtTelefone;
    private EditText txtEmail;
    private EditText txtLinkedin;
    private TextInputLayout layout_txt_nome;
    private TextInputLayout layout_txt_telefone;
    private ImageView foto;
    private Contato contato;

    public CadastroContatosHelper(CadastroContatosActivity activity){

        txtNome = activity.findViewById(R.id.txt_nome);
        txtEndereco = activity.findViewById(R.id.txt_endereco);
        txtTelefone = activity.findViewById(R.id.txt_telefone);
        txtEmail = activity.findViewById(R.id.txt_email);
        txtLinkedin = activity.findViewById(R.id.txt_linkedin);
        layout_txt_telefone = activity.findViewById(R.id.layout_txt_telefone);
        layout_txt_nome = activity.findViewById(R.id.layout_txt_nome);
        foto = activity.findViewById(R.id.imageview_contato);
        contato = new Contato();
    }

    public Contato getContato(){
        contato.setNome(txtNome.getText().toString());
        contato.setEndereco(txtEndereco.getText().toString());
        contato.setTelefone(txtTelefone.getText().toString());
        contato.setEmail(txtEmail.getText().toString());
        contato.setLinkedin(txtLinkedin.getText().toString());

        Bitmap bm = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bm, 300, 300, true);

        return contato;
    }

    public void preencherFormulario(Contato contato){
        txtNome.setText(contato.getNome());
        txtEndereco.setText(contato.getEndereco());
        txtTelefone.setText(contato.getTelefone());
        txtEmail.setText(contato.getEmail());
        txtLinkedin.setText(contato.getLinkedin());
        this.contato = contato;
    }


    public boolean validar (){
        boolean validado = true;
        if(txtNome.getText().toString().isEmpty()){
            layout_txt_nome.setErrorEnabled(true);
            layout_txt_nome.setError("Por favor digite um nome para o contato");
            validado = false;
        }else{
            layout_txt_nome.setErrorEnabled(false);
        }
        if(txtTelefone.getText().toString().isEmpty()){
            layout_txt_telefone.setErrorEnabled(true);
            layout_txt_telefone.setError("Por favor digite um numero para o contato");
            validado = false;
        }else{
            layout_txt_telefone.setErrorEnabled(false);
        }

        return validado;

    }

}
