package com.example.catfactsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class CatListAdapter extends RecyclerView.Adapter<CatListAdapter.CatViewHolder> {

    private final LayoutInflater Inflater;
    private List<Cat> theCats;
    private Context context;
    private WeakReference<MainActivity> wrMA;

    CatListAdapter(Context context, MainActivity mainActivity){
        Inflater = LayoutInflater.from(context);
        this.context = context;
        this.theCats = new ArrayList<>();
        this.wrMA = new WeakReference<>(mainActivity);
    }

    void setCats(List<Cat> cats){
        theCats = cats;
    }

    List<Cat> getTheCats() {
        return theCats;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = Inflater.inflate(R.layout.recyclerview_item, parent,false);
        return new CatViewHolder(itemView);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, final int position) {
        if(theCats != null ) {

            if(theCats.get(position).getDownloaded()){
                Picasso.with(context).load(theCats.get(position).getImageID()).into(holder.imgItemView);

            }
            else {
                CatImageHandler okHttpHandler= new CatImageHandler(theCats.get(position).getImageID(),position,this);
                okHttpHandler.execute();
                holder.imgItemView.setImageBitmap(null);
            }

            holder.nameItemView.setText(theCats.get(position).getName());
            holder.descItemView.setText(theCats.get(position).getDescription());
            holder.countryItemView.setText(theCats.get(position).getCountry());
            holder.temperItemView.setText(theCats.get(position).getDescription());
        }
    }

    @Override
    public int getItemCount() {
        if(theCats != null ) return theCats.size();
        else return 0;
    }

    void addCat(Cat catTemp) {
        theCats.add(catTemp);
    }

    static class CatViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgItemView;
        private final TextView nameItemView;
        private final TextView descItemView;
        private final TextView countryItemView;
        private final TextView temperItemView;

        private CatViewHolder(View itemView) {
            super(itemView);
            imgItemView = itemView.findViewById(R.id.image_cat);
            nameItemView = itemView.findViewById(R.id.name_text);
            descItemView = itemView.findViewById(R.id.desc_text);
            countryItemView = itemView.findViewById(R.id.country_text);
            temperItemView = itemView.findViewById(R.id.temper_text);
        }
    }

}
