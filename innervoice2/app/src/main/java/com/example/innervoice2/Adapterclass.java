package com.example.innervoice2;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapterclass extends RecyclerView.Adapter<Adapterclass.Myviewholder> {
    ArrayList<info> list;
    ArrayList<info> lyricslist;
    ArrayList<info> songslist;
    static Recyclerviewclicklistener listener;



    Adapterclass(ArrayList<info> list, Recyclerviewclicklistener listener) {
        this.list = list;
        this.listener = listener;
        this.lyricslist = lyricslist;
        this.songslist = songslist;

    }


    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder, parent, false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        holder.id.setText(list.get(position).getSongname());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface Recyclerviewclicklistener {
        void onClick(View v, int position);

    }

    public class Myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
         TextView id;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());

            }
    }
}
