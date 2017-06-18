package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pojo.Condominio;

/**
 * Created by mauriciocantu on 13/06/17.
 */

public class CondominioDAO {

    private SQLiteDatabase db;
    public static final String NOME_TABELA = "condominio";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_QTAPARTAMENTOS = "qtapartamentos";
    public static final String COLUNA_ELEVADOR = "temelevador";
    public static final String COLUNA_AREATOTAL = "areatotal";
    public static final String CRIAR_TABELA = "CREATE TABLE "+NOME_TABELA+"("+COLUNA_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT" +
            " NULL UNIQUE, "+COLUNA_NOME+" TEXT, "+COLUNA_QTAPARTAMENTOS+" TEXT, "+COLUNA_ELEVADOR+" TEXT, " +
            COLUNA_AREATOTAL+" TEXT)";

    public CondominioDAO(Context ctx){
        db = SQLHelper.getInstance(ctx).getWritableDatabase();
    }

    public boolean salvar(Condominio condominio){
        long i = db.insert(NOME_TABELA, null, condominioContentValues(condominio));

        if (i == -1) {
            return false;
        }else{
            return true;
        }
    }

    //delete from tabela where id = ?
    public boolean deletar(Condominio condominio){
        String where = COLUNA_ID+ "=?";

        String[] whereArgs = {
                condominio.getId()
        };

        long i = db.delete(NOME_TABELA, where, whereArgs);

        //Se id for igual a -1, retorna false, se não, retorna verdadeiro
        //(o método delete retorna -1 quando a query falha na execução)
        return i == -1 ? false : true;
    }

    public boolean editar(Condominio condominio){
        String where = COLUNA_ID + " =?";

        String[] whereArgs = {
                condominio.getId().toString()
        };

        long i = db.update(NOME_TABELA, condominioContentValues(condominio), where, whereArgs);

        //Se id for igual a -1, retorna false, se não, retorna verdadeiro
        //(o método update retorna -1 quando a query falha na execução)
        return i == -1 ? false : true;

    }

    public ArrayList<Condominio> listar(){
        ArrayList<Condominio> listaCondominios = new ArrayList<Condominio>();
        String sqlBusca = "select * from " + NOME_TABELA;
        Cursor cursor = db.rawQuery(sqlBusca, null);

        Condominio condominio;

        int indiceId = cursor.getColumnIndex(COLUNA_ID);
        int indiceNome = cursor.getColumnIndex(COLUNA_NOME);
        int indiceElevador = cursor.getColumnIndex(COLUNA_ELEVADOR);
        int indiceAreaTotal = cursor.getColumnIndex(COLUNA_AREATOTAL);
        int indiceQtApartamentos = cursor.getColumnIndex(COLUNA_QTAPARTAMENTOS);

        while(cursor.moveToNext()){

            condominio = new Condominio();

            condominio.setId(cursor.getString(indiceId));
            condominio.setNome(cursor.getString(indiceNome));
            condominio.setAreaTotal(cursor.getString(indiceAreaTotal));
            condominio.setQtApartamentos(cursor.getString(indiceQtApartamentos));
            condominio.setTemElevador(cursor.getString(indiceElevador));

            listaCondominios.add(condominio);

        }

        return listaCondominios;
    }

    public ContentValues condominioContentValues(Condominio condominio){

        ContentValues cv = new ContentValues();

        cv.put(COLUNA_NOME, condominio.getNome());
        cv.put(COLUNA_AREATOTAL, condominio.getAreaTotal());
        cv.put(COLUNA_ELEVADOR, condominio.getTemElevador());
        cv.put(COLUNA_QTAPARTAMENTOS, condominio.getQtApartamentos());

        return cv;

    }



}