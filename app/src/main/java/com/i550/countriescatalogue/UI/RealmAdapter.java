package com.i550.countriescatalogue.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.i550.countriescatalogue.App;
import com.i550.countriescatalogue.Model.Country;
import com.i550.countriescatalogue.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class RealmAdapter extends RealmRecyclerViewAdapter<Country, RealmAdapter.MyViewHolder> {

    private static RealmAdapter.ClickListener clickListener;

     RealmAdapter(@Nullable OrderedRealmCollection data) {
        super(data, true);
    }

    @Override @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.country = getItem(position);
        App.getSvgBuilder()
                .load(holder.country.getFlag())
                .into(holder.flagImage);
        holder.countryTitle.setText(holder.country.getName());
    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).getNumericCode();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView flagImage;
        private TextView countryTitle;
        private Country country;

        MyViewHolder(View view) {
            super(view);
            flagImage = itemView.findViewById(R.id.flagImage);
            countryTitle = itemView.findViewById(R.id.countryTitle);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(country.getNumericCode());
        }
    }

    void setOnItemClickListener(RealmAdapter.ClickListener clickListener) {
        RealmAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(Long numericCode);
    }

}

