package br.senai.sp.agendacontatos.contato;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import br.senai.sp.agendacontatos.BuildConfig;
import br.senai.sp.agendacontatos.R;
import br.senai.sp.agendacontatos.dao.ContatoDAO;
import br.senai.sp.agendacontatos.modelo.Contato;

public class CadastroContatosActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int GALERIA_REQUEST = 666;
    public static final int CAMERA_REQUEST = 999;
    private CadastroContatosHelper helper;
    private ImageView imgFoto;
    private ImageButton btnCamera;
    private ImageButton btnGaleria;
    private String caminhoFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cadastro_contato);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ImageButton btnLigar = (ImageButton) findViewById(R.id.btn_ligar);
        btnLigar.setOnClickListener(this);

        helper = new CadastroContatosHelper(CadastroContatosActivity.this);
        btnCamera = findViewById(R.id.bt_camera);
        btnGaleria = findViewById(R.id.bt_galeria);
        imgFoto = findViewById(R.id.imageview_contato);


        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CadastroContatosActivity.this, "G A L E R I A", Toast.LENGTH_SHORT).show();
                Intent intentGaleria = new Intent(Intent.ACTION_GET_CONTENT);
                intentGaleria.setType("image/*");
                startActivityForResult(intentGaleria, GALERIA_REQUEST);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CadastroContatosActivity.this, "C A M E R A", Toast.LENGTH_SHORT).show();
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                String nomeArquivo = "/IMG_" + System.currentTimeMillis() + ".jpg";
                caminhoFoto = getExternalFilesDir(null) + nomeArquivo;

                File arquivoFoto = new File(caminhoFoto);
                Uri fotoUri = FileProvider.getUriForFile(
                        CadastroContatosActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        arquivoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intentCamera, CAMERA_REQUEST);
                Toast.makeText(CadastroContatosActivity.this, "FOTO CAPTURADA", Toast.LENGTH_SHORT).show();
            }
        });


        Intent intent = getIntent();
        Contato contato = (Contato) intent.getSerializableExtra("contato");

        if(contato != null){
            helper.preencherFormulario(contato);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == GALERIA_REQUEST){
            try {

                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmapFactory = BitmapFactory.decodeStream(inputStream);
                imgFoto.setImageBitmap(bitmapFactory);

            } catch (FileNotFoundException e) {

                e.printStackTrace();

            }

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
