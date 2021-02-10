package com.example.quizcidades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    
    String[] cidades = {"barcelona","brasilia","curitiba","lasvegas","montreal","paris","riodejaneiro","salvador","saopaulo","toquio"};
    String[] nomesCorretosCidade = {"Barcelona", "Brasília", "Curitiba", "Las Vegas", "Montreal", "Paris", "Rio de Janeiro", "Salvador", "São Paulo", "Tóquio"};
    ArrayList<Integer> sorteados = new ArrayList<>();
    TextView textViewProgresso, textViewResultado;
    EditText editTextCidade;
    ImageView imageViewCidade;
    int sorteado;
    String cidadeSorteada;
    Button button;
    int pontuacao = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewProgresso = findViewById(R.id.textViewProgresso);
        textViewResultado = findViewById(R.id.textViewResultado);
        editTextCidade = findViewById(R.id.editTextCidade);
        imageViewCidade = findViewById(R.id.imageViewCidade);


        textViewResultado.setText("");

        button = findViewById(R.id.button);

        sortear();
    }

    public void adivinhar(View view){
        String resposta = editTextCidade.getText().toString();
        if(resposta.length()==0){
            Toast.makeText(this,"Digite uma resposta", Toast.LENGTH_SHORT).show();
        }else{
            resposta = resposta.toLowerCase();
            resposta = resposta.trim().replace(" ","");
            resposta = Normalizer.normalize(resposta, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            if(resposta.equals(cidadeSorteada)){
                textViewResultado.setText("Você acertou!");
                pontuacao+=25;
            }else{
                textViewResultado.setText("Você errou, a resposta certa era: " + nomesCorretosCidade[sorteado]);
            }
        }

        if(sorteados.size()==4){
            editTextCidade.setFocusable(false);
            button.setText("Ver resultado");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this,ResultadoActivity.class);
                    i.putExtra("resultado",pontuacao);
                    i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(i);
                }
            });
        }else {
            editTextCidade.setText("");
            sortear();
        }
    }

    private void sortear(){
        Random gerador = new Random();
        sorteado = gerador.nextInt(10);
        while(sorteados.contains(sorteado)){
            sorteado = gerador.nextInt(10);
        }
        sorteados.add(sorteado);
        cidadeSorteada = cidades[sorteado];

        int resourceId = getResources().getIdentifier(cidadeSorteada, "drawable",
                getPackageName());

        imageViewCidade.setImageResource(resourceId);
        textViewProgresso.setText(sorteados.size()+"/4");
    }


}