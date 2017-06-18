package com.example.mauriciocantu.prova4k;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by mauriciocantu on 13/06/17.
 */

public class OpcoesActivity extends ListActivity {

    private ArrayAdapter<String> opcoesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializaComponentes();
    }

    public void inicializaComponentes(){
        opcoesAdapter = new ArrayAdapter<String>(OpcoesActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.opcoes));
        setListAdapter(opcoesAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        if (position == 0){
            Intent telaCadastro = new Intent(OpcoesActivity.this, TelaAcao.class);
            telaCadastro.putExtra("acao", "cadastrar");
            startActivity(telaCadastro);
        } else if (position == 1){
            Intent telaLista = new Intent(OpcoesActivity.this, ListaActivity.class);
            startActivity(telaLista);
        }
    }
}
