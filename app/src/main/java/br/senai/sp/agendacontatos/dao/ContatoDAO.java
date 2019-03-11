package br.senai.sp.agendacontatos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.senai.sp.agendacontatos.modelo.Contato;

public class ContatoDAO extends SQLiteOpenHelper {

    public ContatoDAO(Context context){
        super(context, "db_contato", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "" +
                "CREATE TABLE tbl_contatos(" +
                "id INTEGER PRIMARY KEY," +
                "nome TEXT NOT NULL," +
                "endereco TEXT NOT NULL," +
                "telefone TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "linkedin TEXT NOT NULL)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void salvar(Contato contato){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getContentValues(contato);
        db.insert("tbl_contatos", null, dados);

    }

    private ContentValues getContentValues(Contato contato) {
        ContentValues dados = new ContentValues();

        dados.put("nome", contato.getNome());
        dados.put("endereco", contato.getEndereco());
        dados.put("telefone", contato.getTelefone());
        dados.put("email", contato.getEmail());
        dados.put("linkedin", contato.getLinkedin());

        return dados;
    }

    public void excluir(Contato contato){
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {String.valueOf(contato.getId())};

        db.delete("tbl_contatos", "id = ?", params);
    }

    public List<Contato> getContatos() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tbl_contatos";

        Cursor c = db.rawQuery(sql, null);

        List<Contato> contatos = new ArrayList<>();
        while (c.moveToNext()){
            Contato contato = new Contato();
            contato.setId(c.getInt(c.getColumnIndex("id")));
            contato.setNome(c.getString(c.getColumnIndex("nome")));
            contato.setEndereco(c.getString(c.getColumnIndex("endereco")));
            contato.setTelefone(c.getString(c.getColumnIndex("telefone")));
            contato.setEmail(c.getString(c.getColumnIndex("email")));
            contato.setLinkedin(c.getString(c.getColumnIndex("linkedin")));
            contatos.add(contato);
        }

        return contatos;
    }

    public void atualizar(Contato contato){
        SQLiteDatabase db = getReadableDatabase();
        String[] params = {String.valueOf(contato.getId())};

        ContentValues dados = getContentValues(contato);
        db.update("tbl_contatos", dados, "id = ?", params);
    }
}
