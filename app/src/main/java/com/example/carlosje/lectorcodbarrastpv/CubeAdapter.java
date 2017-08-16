package com.example.carlosje.lectorcodbarrastpv;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by carlosje on 8/9/2017.
 */

public class CubeAdapter extends RecyclerView.Adapter<CubeAdapter.CubeViewHolder> {

    private List<Cube> items;

    public static class CubeViewHolder extends RecyclerView.ViewHolder{
        public TextView tx_fecha_R, tx_user_R, tx_ubicacion_R;

        public CubeViewHolder(View v){

            super(v);

            tx_fecha_R=(TextView) v.findViewById(R.id.tx_TPV);
            tx_user_R=(TextView) v.findViewById(R.id.tx_user_R);
            tx_ubicacion_R=(TextView) v.findViewById(R.id.tx_ubicacion_R);


        }

    }


    public CubeAdapter(List<Cube> items){ this.items= items;}

    @Override
    public int getItemCount(){return items.size();}

    @Override
    public CubeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cube_card, viewGroup, false);
        return new CubeViewHolder(v);



    }


    @Override

    public void onBindViewHolder(CubeViewHolder viewHolder, int i){
        viewHolder.tx_fecha_R.setText(items.get(i).getFecha());
        viewHolder.tx_ubicacion_R.setText(items.get(i).getUbicacion());
        viewHolder.tx_user_R.setText(items.get(i).getUsuario());


    }



}
