package montacer.elfazazi.ejerc1clasepmdmtema2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import montacer.elfazazi.ejerc1clasepmdmtema2.configuracion.Constantes;
import montacer.elfazazi.ejerc1clasepmdmtema2.databinding.ActivityMainBinding;
import montacer.elfazazi.ejerc1clasepmdmtema2.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding binding;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences(Constantes.USUARIOS, MODE_PRIVATE); //esto es para acceder al fichero

        binding.lbUsuarioSecond.setText(sp.getString(Constantes.USUARIO, ""));
        String contrasenya = descodificarContrasenya();
        binding.lbContrasenyaSecond.setText(contrasenya);

        binding.btnLogOutSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                finish();
            }
        });

    }

    private String descodificarContrasenya() {
        /*
        char[] caracteres = sp.getString(Constantes.CONTRASENYA, "").toCharArray();
        for (int i = 0; i < caracteres.length; i++) {
            switch (caracteres[i]){
                case'r': caracteres[i] = 'a';
                    break;
                case't': caracteres[i] = 'e';
                    break;
                case'y': caracteres[i] = 'i';
                    break;
                case'p': caracteres[i] = 'o';
                    break;
                case'j': caracteres[i] = 'u';
                    break;
                default:caracteres[i] -= 2;
            }
        }
        return String.valueOf(caracteres);
        */
        String resultado = null;
        try {
            resultado = new String(Base64.getDecoder().decode(sp.getString(Constantes.CONTRASENYA, "").getBytes("UTF-8")),
                    "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }
}