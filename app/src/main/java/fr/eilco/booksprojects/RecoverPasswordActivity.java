package fr.eilco.booksprojects;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class RecoverPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        Button buttonRecoverPassword = findViewById(R.id.buttonRecoverPassword);
        buttonRecoverPassword.setOnClickListener(v -> {
            // Lógica de recuperación de contraseña
            finish(); // Vuelve al login después de recuperar
        });
    }
}
