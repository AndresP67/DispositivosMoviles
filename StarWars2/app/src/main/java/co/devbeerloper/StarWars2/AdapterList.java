package co.devbeerloper.StarWars2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolderLista> {

    ArrayList<datos> lista;

    public AdapterList(ArrayList<datos> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolderLista onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_info, null, false);
        return new ViewHolderLista(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLista viewHolderLista, int posicion) {
        viewHolderLista.asignarDatos(lista.get(posicion), posicion);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolderLista extends RecyclerView.ViewHolder {
        TextView numero;
        TextView name;
        TextView height_op;
        TextView mass_diam;
        TextView hair_clim;
        TextView skin_grav;
        TextView eye_terra;
        TextView birth_surf;
        TextView gender_popu;

        public ViewHolderLista(@NonNull View itemView) {
            super(itemView);
            numero = itemView.findViewById(R.id.numero);
            name = itemView.findViewById(R.id.name);
            height_op = itemView.findViewById(R.id.height_op);
            mass_diam = itemView.findViewById(R.id.mass_diam);
            hair_clim = itemView.findViewById(R.id.hair_clim);
            skin_grav = itemView.findViewById(R.id.skin_grav);
            eye_terra = itemView.findViewById(R.id.eye_terra);
            birth_surf = itemView.findViewById(R.id.birth_surf);
            gender_popu = itemView.findViewById(R.id.gender_popu);
        }

        public void asignarDatos(datos i, int pos) {
            numero.setText((String.valueOf(pos + 1)));
            name.setText(i.getName());
            height_op.setText(i.getHeight_op());
            mass_diam.setText(i.getMass_diam());
            hair_clim.setText(i.getHair_clim());
            skin_grav.setText(i.getSkin_grav());
            eye_terra.setText(i.getEye_terra());
            birth_surf.setText(i.getBirth_surf());
            gender_popu.setText(i.getGender_popu());
        }
    }
}
