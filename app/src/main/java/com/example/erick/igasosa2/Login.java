package com.example.erick.igasosa2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements ExitDialog.ExitListener{

    private EditText editEmail,editSenha;
    private Button btnLogar, btnNovo;
    private TextView txtResetSenha;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        inicializaComponentes();
        eventoClicks();
    }

    private void eventoClicks() {

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Cadastro.class);
                startActivity(i);
            }
        });
        
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                String senha = editSenha.getText().toString().trim();
                login(email, senha);
            }
        });

        txtResetSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, ResetSenha.class);
                startActivity(i);
            }
        });

    }

    private void login(String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            notificar("Bem vindo! Vamos economizar?");
                            Intent i = new Intent (Login.this, Perfil.class);
                            startActivity(i);
                        }else{
                            alert("e-mail ou senha incorretos");
                        }
                    }
                });
    }

    public void notificar(String s){

        Intent intent = new Intent(this, Login.class);
        int id = (int) (Math.random()*1000);
        PendingIntent pi =  PendingIntent.getActivity(getBaseContext(),  id,  intent,  PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager)  getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("com.example.erick.igasosa2",
                    "iGasosaNotify",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new Notification.Builder(this, "com.example.erick.igasosa2")
                .setContentTitle("iGasosa")
                .setContentText(s).setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentIntent(pi).build();

        notification.flags|=  Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(id,  notification);

    }

    private void alert(String s) {

        Toast.makeText(Login.this,s, Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes() {
        editEmail = (EditText) findViewById(R.id.editLoginEmail);
        editSenha = (EditText) findViewById((R.id.editLoginSenha));
        btnLogar = (Button) findViewById(R.id.btnLoginLogar);
        btnNovo = (Button) findViewById(R.id.btnLoginNovo);
        txtResetSenha = (TextView) findViewById(R.id.txtResetSenha);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

    @Override
    public void onBackPressed() {

        ExitDialog exitDialog = new ExitDialog();
        exitDialog.show(getFragmentManager(),"exitDialog");
        notificar("Já vai?");
    }

    @Override
    public void onExit() {
        finish();
    }

}
