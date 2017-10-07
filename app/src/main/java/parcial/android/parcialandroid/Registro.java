package parcial.android.parcialandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email;
    private EditText contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Intancia de firebase
        mAuth = FirebaseAuth.getInstance();
        email= (EditText) findViewById(R.id.mailReg);
        contrasena=(EditText) findViewById(R.id.passReg);
    }

    public void registrarUsuario( View v){
        //Verificar que se llenen los campos email y contraseña
        if(email.getText().toString().equals("")){
            Toast.makeText(Registro.this, "Ingrese el email",
                    Toast.LENGTH_LONG).show();
        }else if(contrasena.getText().toString().equals("")){
            Toast.makeText(Registro.this, "Ingrese la contraseña",
                    Toast.LENGTH_LONG).show();
        }else{
            //Consumo el meto de firebase para registrar en la base de datos
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), contrasena.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Si se crea exitosamente vuelvo a la pantalla de login
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(Registro.this, "Usuario creado exitosamente",
                                        Toast.LENGTH_LONG).show();
                                Intent inte = new Intent(getApplicationContext(),Login.class);
                                startActivity(inte);
                            } else {
                                // Error en la creacion
                                Toast.makeText(Registro.this, "Fallo el registro",
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        }
    }



}
