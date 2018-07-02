package com.example.erick.igasosa2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetSenha extends AppCompatActivity {

    private EditText editEmail;
    private Button btnResetsenha;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_senha);
        Toolbar toolbar = findViewById(R.id.toolbar_resetSenha);
        setSupportActionBar(toolbar);
        inicializarComponentes();
        eventoClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions_reset,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_reset:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void eventoClick() {
        btnResetsenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                resetSenha(email);
            }
        });
    }

    private void resetSenha(String email) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(ResetSenha.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    alert("Um e-mail foi enviado para alterar sua senha.");
                    finish();
                }else{
                    alert("Email não registrado");
                }
            }
        });
    }

    private void alert(String s) {
        Toast.makeText(ResetSenha.this, s, Toast.LENGTH_SHORT).show();
    }

    private void inicializarComponentes() {
        editEmail = (EditText) findViewById(R.id.editResetEmail);
        btnResetsenha = (Button) findViewById(R.id.btnResetSenha);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
