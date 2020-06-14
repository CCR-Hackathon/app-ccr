package br.com.catossi.meu_amigo_caminhoneiro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class LoginActivity extends AppCompatActivity {

    private ImageButton cadButton;
    private ImageButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cadButton = findViewById(R.id.cadButton);

        cadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(i);
            }
        });

        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


    }
}
