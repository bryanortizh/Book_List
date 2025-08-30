package fr.eilco.booksprojects;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecoverPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        Button buttonRecoverPassword = findViewById(R.id.buttonRecoverPassword);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);

        buttonRecoverPassword.setOnClickListener(v -> {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String Correo = editTextEmail.getText().toString();
            String Password = editTextPassword.getText().toString();
            try {
                URL url = new URL("http://10.0.2.2:3000/usersPassword");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("Correo", Correo);
                jsonParam.put("Password", Password);
                Log.d("RECOVER_DEBUG", "JSON enviado: " + jsonParam.toString());
                OutputStream os = conn.getOutputStream();
                os.write(jsonParam.toString().getBytes("UTF-8"));
                os.close();
                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    Toast.makeText(RecoverPasswordActivity.this, "Contraseña actualizada", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RecoverPasswordActivity.this, "Error al actualizar contraseña", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(RecoverPasswordActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
