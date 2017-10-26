package parcial.android.parcialandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    CallbackManager callbackManager;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv=(TextView)findViewById(R.id.contrasenaOlvidada);
tv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent (getApplicationContext(),ReestablecerContrasena.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
});
        // Obtengo la instancia de firebase
        mAuth = FirebaseAuth.getInstance();
        //inicializo los campos de texto para ingresar email y password
        email=(EditText) findViewById(R.id.emailLogin);
        password=(EditText) findViewById(R.id.contrasenaLogin);
        //Hago uso de los metodos expuestos por facebook para el login
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.button_facebook_sign_in);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //si el login con facebook fue correcto, entonces abro la pantalla principal
                Intent intent= new Intent(getApplicationContext(), ListaActividades.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(intent);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void usuarioNuevo(View v){
     Intent intent = new Intent(this, Registro.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void iniciarSesion(View v){
        //Verificar que se hayan escrito el correo y la contraseña
        if(email.getText().toString().equals("")){
            Toast.makeText(Login.this, "Ingrese el email",
                    Toast.LENGTH_LONG).show();
        }else  if(password.getText().toString().equals("")){
            Toast.makeText(Login.this, "Ingrese la contraseña",
                    Toast.LENGTH_LONG).show();
        }else {
            // Se procede a consumir el metodo de firebase para logueo con email y password
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//Cambio a la pantalla principal porque si se pudo loguear
                                FirebaseUser user = mAuth.getCurrentUser();

                                Toast.makeText(Login.this, "Logueo exitoso "+user.getDisplayName(),
                                        Toast.LENGTH_LONG).show();
                                //cambio la vista a la actividad principal donde se van a crear las tareas
                                Intent intent= new Intent(getApplicationContext(), ListaActividades.class);
                                startActivity(intent);
                            } else {
    //Error en el logueo
                                    Toast.makeText(Login.this, "Logueo fallido",
                                        Toast.LENGTH_LONG).show();

                            }


                        }
                    });

        }
    }

}
