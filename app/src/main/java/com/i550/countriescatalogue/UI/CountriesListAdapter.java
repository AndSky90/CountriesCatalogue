package com.i550.countriescatalogue.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.i550.countriescatalogue.Model.Country;
import com.i550.countriescatalogue.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.RealmResults;

public class CountriesListAdapter extends RecyclerView.Adapter<CountriesListAdapter.ViewHolder> {
    private static ClickListener clickListener;
    private LayoutInflater inflater;

    private RealmResults<Country> leadsList;

    CountriesListAdapter(Context context, RealmResults<Country> leadsList) {
        inflater = LayoutInflater.from(context);
        this.leadsList = leadsList;

    }
   @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Country country = leadsList.get(position);

     //   holder.flagImage.setImageDrawable();
        holder.countryTitle.setText(country.getName());
    }

    @Override
    public int getItemCount() {
        return leadsList.size();
    }

    void setOnItemClickListener(ClickListener clickListener) {
        CountriesListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(Long numericCode);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {              ////////////////////
        private ImageView flagImage;                                                               ////////////////////
        private TextView countryTitle;                                                              ////////////////////

        ViewHolder(LayoutInflater i, ViewGroup parent) {                                          ////////////////////
            super(i.inflate(R.layout.country_list_item, parent, false));             ////////////////////
            flagImage = itemView.findViewById(R.id.flagImage);                                        ////////////////////
            countryTitle = itemView.findViewById(R.id.countryTitle);                                        ////////////////////
            itemView.setOnClickListener(this);
        }               //itemView - экземпляр данной вью

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(Long.valueOf(getAdapterPosition()));
        }
    }
}
