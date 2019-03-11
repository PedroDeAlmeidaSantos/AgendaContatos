package br.senai.sp.agendacontatos.contato;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import br.senai.sp.agendacontatos.R;
import br.senai.sp.agendacontatos.dao.ContatoDAO;
import br.senai.sp.agendacontatos.modelo.Contato;

public class CadastroContatosActivity extends AppCompatActivity implements View.OnClickListener {

    private CadastroContatosHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contatos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ImageButton btnLigar = (ImageButton) findViewById(R.id.btn_ligar);
        btnLigar.setOnClickListener(this);

        helper = new CadastroContatosHelper(CadastroContatosActivity.this);

        Intent intent = getIntent();
        Contato contato = (Contato) intent.getSerializableExtra("contato");

        if(contato != null){
            helper.preencherFormulario(contato);
        }
    }

    public void onClick(View view) {
        Contato telefone = helper.getContato();
        Uri uri = Uri.parse("tel:"+ telefone.getTelefone());
        Intent intent = new Intent(Intent.ACTION_DIAL,uri);

        startActivity(intent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Contato contato = helper.getContato();
        if(contato.getId() == 0){
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_cadastro_contatos, menu);
        }else{
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_cadastro_contatos2, menu);
        }


        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Contato contato = helper.getContato();

        ContatoDAO dao = new ContatoDAO(this);
        switch (item.getItemId()){
            case R.id.menu_salvar:

                if(contato.getId() == 0){
                    CadastroContatosHelper helper = new CadastroContatosHelper(this);

                    if(helper.validar() == true){
                        dao.salvar(contato);

                        dao.close();
                        Toast.makeText(this, contato.getNome() + " registrado!", Toast.LENGTH_LONG).show();
                        finish();
                    }

                }else{
                    CadastroContatosHelper helper = new CadastroContatosHelper(this);

                    if(helper.validar() == true) {
                        dao.atualizar(contato);

                        dao.close();
                        Toast.makeText(this, contato.getNome() + " atualizado!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }



                break;

            case R.id.menu_excluir:
                dao.excluir(contato);
                Toast.makeText(this, contato.getNome() + " deletado!", Toast.LENGTH_LONG).show();
                dao.close();
                finish();
                break;

            case R.id.menu_cancelar:
                finish();

                break;

            case android.R.id.home:
                finish();
                break;

            default:

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
