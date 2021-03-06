package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pojo.Condominio;

/**
 * Created by mauriciocantu on 13/06/17.
 */

public class SQLHelper extends SQLiteOpenHelper {

    private static SQLHelper instance;

    private static final String NOME_BANCO = "prova2";
    private static final int VERSAO_BANCO = 1;

    private SQLHelper(Context ctx){
        super(ctx, NOME_BANCO, null, VERSAO_BANCO);
    }

    public static SQLHelper getInstance(Context ctx){
        if(instance == null){
            instance = new SQLHelper(ctx);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CondominioDAO.CRIAR_TABELA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
