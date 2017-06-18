package com.example.mauriciocantu.prova4k;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import dao.CondominioDAO;
import pojo.Condominio;

/**
 * Created by mauriciocantu on 13/06/17.
 */

public class TelaAcao extends AppCompatActivity {

    private EditText etCondominio, etAreaTotal;
    private CheckBox cbElevador;
    private Spinner spAps;
    private Button btAcao, btDeletar;
    private Condominio condominio;
    private CondominioDAO condominioDAO;
    private ArrayAdapter<String> numeroApsAdapter;
    private Dialog dialogDeletar;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_acao);

        inicializaComponentes();

        switch (getIntent().getStringExtra("acao")){

            case "cadastrar":

                btAcao.setText("Cadastrar");

                condominio = new Condominio();

                this.btAcao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        condominio.setNome(etCondominio.getText().toString());

                        condominio.setAreaTotal(etAreaTotal.getText().toString());

                        if(cbElevador.isChecked()){
                            condominio.setTemElevador("Sim");
                        }else{
                            condominio.setTemElevador("Não");
                        }

                        condominio.setQtApartamentos(spAps.getSelectedItem().toString());

                        boolean salvou = condominioDAO.salvar(condominio);

                        if (salvou){
                            Toast.makeText(getApplicationContext(), "Salvou", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Deu ruim", Toast.LENGTH_LONG).show();
                        }

                        finish();
                    }
                });
                break;

            case "editar":

                dialogDeletar = createDialog();

                btAcao.setText("Editar");

                btDeletar.setVisibility(View.VISIBLE);

                condominio = (Condominio) getIntent().getSerializableExtra("condominio");

                etCondominio.setText(condominio.getNome());
                etAreaTotal.setText(condominio.getAreaTotal());

                if (condominio.getTemElevador().equals("Sim")){
                    cbElevador.toggle();
                }

                spAps.setSelection(Integer.parseInt(condominio.getQtApartamentos())-1);

                btAcao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        condominio.setNome(etCondominio.getText().toString());

                        condominio.setAreaTotal(etAreaTotal.getText().toString());

                        if (cbElevador.isChecked()){
                            condominio.setTemElevador("Sim");
                        }else{
                            condominio.setTemElevador("Não");
                        }

                        condominio.setQtApartamentos(spAps.getSelectedItem().toString());

                        boolean editou = condominioDAO.editar(condominio);

                        if (editou){
                            Toast.makeText(getApplicationContext(), "Editado", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Erro ao editar", Toast.LENGTH_LONG).show();
                        }

                        finish();
                    }
                });

                btDeletar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialogDeletar.show();

                    }
                });

                break;
        }
    }

    private void inicializaComponentes(){
        etCondominio = (EditText) findViewById(R.id.et_condominio);
        etAreaTotal = (EditText) findViewById(R.id.et_areatotal);
        cbElevador = (CheckBox) findViewById(R.id.cb_elevador);
        spAps = (Spinner) findViewById(R.id.sp_aps);
        btAcao = (Button) findViewById(R.id.bt_acao);
        btDeletar = (Button) findViewById(R.id.bt_deletar);
        condominioDAO = new CondominioDAO(getApplicationContext());
        numeroApsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.aps));
        spAps.setAdapter(numeroApsAdapter);
    }

    private Dialog createDialog(){

        builder = new AlertDialog.Builder(TelaAcao.this);

        builder.setMessage("Deletar?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean salvou = condominioDAO.deletar(condominio);

                if (salvou){
                    Toast.makeText(getApplicationContext(), "Deletado", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_LONG).show();
                }

                finish();

            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        return builder.create();

    }

}
