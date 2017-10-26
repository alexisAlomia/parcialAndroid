package parcial.android.parcialandroid;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ReestablecerContrasena extends AppCompatActivity {

    private Button btnAccion;
    private FirebaseAuth auth;
    private EditText emailUsuario;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reestablecer_contrasena);

        emailUsuario=(EditText)findViewById(R.id.txtEmail);
        btnAccion=(Button)findViewById(R.id.btnRestablecer);
        auth = FirebaseAuth.getInstance();


    }
    
      public void enviarMailRestablecer(View v){
        if(emailUsuario.getText().toString().equals("")||emailUsuario==null){
            Toast.makeText(this, "Debe ingresar la dirección de correo",Toast.LENGTH_LONG).show();
        }else{
            auth.sendPasswordResetEmail(emailUsuario.getText().toString()
            )
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                               Toast.makeText(getApplicationContext(),"Se ha enviado un correo, revise su buzón",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
