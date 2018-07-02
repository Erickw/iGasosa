package com.example.erick.igasosa2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Perfil extends AppCompatActivity {

    private TextView textEmail, textID;
    private Button btnLogOut;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        Toolbar toolbar = findViewById(R.id.toolbar_perfil);
        setSupportActionBar(toolbar);
        inicializaComponentes();
        eventoClicks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions_perfil,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_download:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void inicializaComponentes() {

        textEmail = (TextView) findViewById(R.id.textPerfilEmail);
        textID = (TextView) findViewById(R.id.textPerfilId);
        btnLogOut = (Button) findViewById(R.id.btnPerfilLogout);
    }


    private void eventoClicks() {

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conexao.logOut();
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();
        verificaUser();
    }

    private void verificaUser() {

        if(user == null){
            finish();
        }
        else{
            textEmail.setText("Email: " + user.getEmail());
            textID.setText("ID" + user.getUid());
        }
    }
}
