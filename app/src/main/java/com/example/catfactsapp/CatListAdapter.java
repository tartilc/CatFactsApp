package com.example.catfactsapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

    private final LayoutInflater mInflater;
    private List<Cat> mCats;
    private Context context;
    private WeakReference<MainActivity> wrMA;

    CatListAdapter(Context context, MainActivity mainActivity){
         mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mCats = new ArrayList<>();
        this.wrMA = new WeakReference<>(mainActivity);
    }

    void setmCats(List<Cat> cats){
        mCats = cats;
    }

    List<Cat> getmCats() {
        return mCats;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent,false);
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
        if(mCats != null ) {

            if(mCats.get(position).getDownloaded()){
                Picasso.with(context).load(mCats.get(position).getImageID()).into(holder.imgItemView);

            }
            else {
                CatImageHandler okHttpHandler= new CatImageHandler(mCats.get(position).getImageID(),position,this);
                okHttpHandler.execute();
                holder.imgItemView.setImageBitmap(null);
            }

            holder.nameItemView.setText(mCats.get(position).getName());
            holder.descItemView.setText(mCats.get(position).getDescription());
            holder.countryItemView.setText(mCats.get(position).getCountry());
            holder.temperItemView.setText(mCats.get(position).getDescription());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("name",mCats.get(position).getName());
                    intent.putExtra("image_url",mCats.get(position).getImageID());
                    intent.putExtra("description",mCats.get(position).getDescription());
                    intent.putExtra("country",mCats.get(position).getCountry());
                    intent.putExtra("breed",mCats.get(position).getBreed());
                    intent.putExtra("breed_id",mCats.get(position).getBreed_id());
                    intent.putExtra("temperament",mCats.get(position).getTemperament());

                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if(mCats != null ) return mCats.size();
        else return 0;
    }

    void addCat(Cat catTemp) {
        mCats.add(catTemp);
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
