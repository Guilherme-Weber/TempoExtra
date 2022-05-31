package com.example.tempoextra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tempoextra.roomdatabase.UserDao;
import com.example.tempoextra.roomdatabase.UserDatabase;
import com.example.tempoextra.roomdatabase.UserEntity;

public class MainActivity extends AppCompatActivity {


    TextView textView;
    EditText emailtext, senhatext;
    Button btn_acessar, btn_cadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }
        setTheme(R.style.Theme_TempoExtra);//SPASH SCREEN

        setContentView(R.layout.activity_main);//ATIVIDADE
        getSupportActionBar().hide();


        emailtext = findViewById(R.id.emailText);
        senhatext = findViewById(R.id.senhaText);

        btn_acessar = findViewById(R.id.btn_acesso);
        btn_cadastro = findViewById(R.id.btn_acessar);

        btn_acessar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Função de Login
                //Validação de Input

                final String email = emailtext.getText().toString();
                final String senha = senhatext.getText().toString();

                if (email.isEmpty() || senha.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Preencha Todos os Campos!", Toast.LENGTH_SHORT).show();

                } else {
                    //Login de administrador
                    if (email.equals("admin") && (senha.equals("1234"))) {
                        telaAdm();
                        Toast.makeText(getApplicationContext(), "Bem vindo Administrador!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //Realizar o query

                        UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                        final UserDao userDao = userDatabase.userDao();
                        new Thread(new Runnable() {

                            @Override
                            public void run() {

                                UserEntity userEntity = userDao.login(email, senha);
                                if (userEntity == null) {
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {

                                            Toast.makeText(getApplicationContext(), "Login ou Senha Incorretos!", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                } else {

                                    //FAZER LÓGICA PARA ENTRAR NA TELA CORRETA

                                    //SALVAR O QUE SAIU DO BANCO
                                    String email = userEntity.getUserId();
                                    String nome = userEntity.getNome();
                                    String curso = userEntity.getCurso();
//                                    startActivity(new Intent(MainActivity.this, TelaHomeCoordenador.class)
//                                            .putExtra("nome", nome));

                                    startActivity(new Intent(MainActivity.this, TelaHomeCoordenador.class)
                                            .putExtra("nome", nome).putExtra("curso", curso));

                                }
                            }
                        }).start();

                    }


                }

            }
        });

        btn_cadastro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                telaCadastro();//TELA TIPO CADASTRO

            }
        });


    }

    public void telaCadastro() {

        Intent tela = new Intent(MainActivity.this, TelaCadastroAluno.class);
        startActivity(tela);
        finish();

    }

    public void telaAdm() {

        Intent tela = new Intent(MainActivity.this, TelaAdm.class);
        startActivity(tela);
        finish();

    }

}