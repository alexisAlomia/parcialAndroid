package parcial.android.parcialandroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class Adaptador  extends RecyclerView.Adapter<Adaptador.ViewHolder> {
    private List<Actividad> listaActividades;


    //Declaro variables para poner los valores en cada uno de los cards
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nombreActividad;
        public TextView fechaActividad;
        public ViewHolder(View v) {
            super(v);

            nombreActividad=(TextView) v.findViewById(R.id.nombreActividad);
            fechaActividad=(TextView) v.findViewById(R.id.fechaActividad);
        }
    }

    //Listado de actividades desde firebase
    public Adaptador(List<Actividad> listaActividades) {
        this.listaActividades=listaActividades;
    }


    @Override
    public Adaptador.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        //crea un card por cada elemento de la vista
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Recorro el listado de actividades para crear cada card
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.nombreActividad.setText(listaActividades.get(position).getNombreActividad());
        Date d = listaActividades.get(position).getFechaActividad();
        if(d==null){
            d= new Date(System.currentTimeMillis());

        }else{

        }
        holder.fechaActividad.setText(d.toString());

    }


    @Override
    public int getItemCount() {
        return listaActividades.size();
    }
}
