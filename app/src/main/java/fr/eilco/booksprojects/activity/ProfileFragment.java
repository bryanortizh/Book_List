package fr.eilco.booksprojects.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import fr.eilco.booksprojects.R;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView tvProfileName = view.findViewById(R.id.tvProfileName);
        TextView tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        ImageView ivProfilePic = view.findViewById(R.id.ivProfilePic);
        Button btnBack = view.findViewById(R.id.btnBack);

        // Obtener correo guardado
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", requireActivity().MODE_PRIVATE);
        String correo = prefs.getString("Correo", "");
        tvProfileEmail.setText(correo);

        // Llamar al servicio para obtener datos del usuario
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            String urlStr = "http://192.168.18.2:3000/users/search?correo=" + correo;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONObject json = new JSONObject(response.toString());
                String nombre = json.optString("Nombres", "");
                tvProfileName.setText(nombre);
                tvProfileEmail.setText(json.optString("Correo", correo));
            }
        } catch (Exception e) {
            tvProfileName.setText("Error al cargar perfil");
        }

        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
            requireActivity().findViewById(R.id.fragment_container).setVisibility(View.GONE);
            requireActivity().findViewById(R.id.btnMenu).setVisibility(View.VISIBLE);
            requireActivity().findViewById(R.id.tvTitleApp).setVisibility(View.VISIBLE);
            requireActivity().findViewById(R.id.ivSearch).setVisibility(View.VISIBLE);
            requireActivity().findViewById(R.id.cardRandomList).setVisibility(View.VISIBLE);
            requireActivity().findViewById(R.id.cardNewBooks).setVisibility(View.VISIBLE);
            requireActivity().findViewById(R.id.cardFavorites).setVisibility(View.VISIBLE);
        });
        return view;
    }
}
