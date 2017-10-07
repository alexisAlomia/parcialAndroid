package parcial.android.parcialandroid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PopUpCrearActividad extends Dialog implements
        android.view.View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private DatabaseReference mDatabase;
    private Button btnGuardar;
    private EditText nombreActividad;
    private EditText fechaActividad;
    private int diaSelect;
    private int mesSelect;
    private int anoSelect;
    private Context context;



    public PopUpCrearActividad(Context context) {

        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_crear_actividad);
        //Obtengo la instancia de la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nombreActividad=(EditText) findViewById(R.id.nombreActividad);
        btnGuardar=(Button) findViewById(R.id.btnCrearActivi);
//seteo la accion del metodo de registrar actividad
       btnGuardar.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             try {
                                                 Actividad activ = new Actividad();
                                                 //Seteo el nombre de la taera
                                                 activ.setNombreActividad(nombreActividad.getText().toString());
                                                 SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
                                                 //Conversion de fecha seleccionada
                                                 Date fecha = null;
                                                 try {

                                                     fecha = formatoDelTexto.parse(fechaActividad.getText().toString());

                                                 } catch (Exception ex) {
                                                     ex.printStackTrace();
                                                 }
                                                 //Pongo la fecha a la tarea  y envio llos datos a firebase
                                                 activ.setFechaActividad(fecha);
                                                 mDatabase.child("Actividad").push().setValue(activ);
                                                 Toast.makeText(getContext().getApplicationContext(), "Actividad creada",
                                                         Toast.LENGTH_LONG).show();
                                                 dismiss();
                                                 Intent i = new Intent(getContext(), ListaActividades.class);
                                                 i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                 getContext().startActivity(i);
                                             } catch (Exception e) {
                                                 Toast.makeText(getContext().getApplicationContext(), "Error creando actividad " + e,
                                                         Toast.LENGTH_LONG).show();
                                             }
                                         }
                                     }

        );
        fechaActividad= (EditText) findViewById(R.id.fechaActividad);
        fechaActividad.setOnClickListener(this);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int anoSe, int mesSe, int diaSe) {
    anoSelect=anoSe;
        mesSelect=mesSe;
        diaSelect=diaSe;
        String fechaSelect=anoSelect+"-"+(mesSelect+1)+"-"+diaSelect;
        fechaActividad.setText(fechaSelect);
    }

    @Override
    public void onClick(View view) {
        Calendar calendario = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog popUp = new DatePickerDialog(getContext(), this,
                calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH));
        popUp.show();
    }
}
