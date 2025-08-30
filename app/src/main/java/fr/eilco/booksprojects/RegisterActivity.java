package fr.eilco.booksprojects;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button buttonRegister = findViewById(R.id.buttonRegister);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        EditText editTextNombres = findViewById(R.id.editTextNombres);

        buttonRegister.setOnClickListener(v -> {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String Nombres = editTextNombres.getText().toString();
            String Correo = editTextEmail.getText().toString();
            String Password = editTextPassword.getText().toString();
            String ConfirmPassword = editTextConfirmPassword.getText().toString();
            if (!Password.equals(ConfirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                URL url = new URL("http://10.0.2.2:3000/users"); 
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("Nombres", Nombres);
                jsonParam.put("Correo", Correo);
                jsonParam.put("Password", Password);
                Log.d("REGISTER_DEBUG", "JSON enviado: " + jsonParam.toString());
                OutputStream os = conn.getOutputStream();
                os.write(jsonParam.toString().getBytes("UTF-8"));
                os.close();
                int responseCode = conn.getResponseCode();
                if (responseCode == 201 || responseCode == 200) {
                    Toast.makeText(RegisterActivity.this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Error al crear usuario", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
