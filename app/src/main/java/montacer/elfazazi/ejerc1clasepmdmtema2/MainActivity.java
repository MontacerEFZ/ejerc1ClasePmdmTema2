package montacer.elfazazi.ejerc1clasepmdmtema2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import java.io.UnsupportedEncodingException;
import java.util.Base64;

import montacer.elfazazi.ejerc1clasepmdmtema2.configuracion.Constantes;
import montacer.elfazazi.ejerc1clasepmdmtema2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences(Constantes.USUARIOS, MODE_PRIVATE); //esto es para acceder al fichero

        comprobarLogin();

        binding.btnLoginMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.txtUsuarioMain.getText().toString().equalsIgnoreCase("Progresa")
                && binding.txtContrasenyaMain.getText().toString().equals("Alumno")){

                    SharedPreferences.Editor editor = sp.edit();
                    String codificado = codificarContrasenya();
                    editor.putString(Constantes.USUARIO, binding.txtUsuarioMain.getText().toString());
                    editor.putString(Constantes.CONTRASENYA, codificado);
                    editor.apply(); //importante el apply, no olvidar

                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                    // finish(); // al volver hacia atras, se cerrara la aplicacion en lugar de volver al main

                    binding.txtUsuarioMain.setText("");
                    binding.txtContrasenyaMain.setText("");
                }else{
                    Toast.makeText(MainActivity.this, "datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String codificarContrasenya() {
        /*
        char[] caracteres = binding.txtContrasenyaMain.getText().toString().toLowerCase().toCharArray();
        for (int i = 0; i < caracteres.length; i++) {
            switch (caracteres[i]){
                case'a': caracteres[i] = 'r';
                break;
                case'e': caracteres[i] = 't';
                break;
                case'i': caracteres[i] = 'y';
                break;
                case'o': caracteres[i] = 'p';
                break;
                case'u': caracteres[i] = 'j';
                break;
                default:caracteres[i] += 2;
            }
        }
           return String.valueOf(caracteres);
         */

        String resultado = null;
        try {
            resultado = new String(Base64.getEncoder().encode((binding.txtContrasenyaMain.getText().toString().toLowerCase()).getBytes("UTF-8")),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(resultado);
    }

    private void comprobarLogin() {
        /*
        if (sp.getString(Constantes.USUARIO, "").equalsIgnoreCase("Progresa")
        && sp.getString(Constantes.CONTRASENYA, "").equals("Alumno")){
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }
         */

        if (sp.contains(Constantes.USUARIO) && sp.contains(Constantes.CONTRASENYA)){
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }
    }
}