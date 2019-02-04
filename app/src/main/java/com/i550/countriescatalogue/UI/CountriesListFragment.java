package com.i550.countriescatalogue.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.i550.countriescatalogue.MainActivity;
import com.i550.countriescatalogue.Model.Country;
import com.i550.countriescatalogue.OnCountryClickListener;
import com.i550.countriescatalogue.R;

public class CountriesListFragment extends Fragment {

    private Realm realm;
    private OnCountryClickListener mListener;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        realm = Realm.getDefaultInstance();
        View v = inflater.inflate(R.layout.fragment_list_countries, container, false);
        RecyclerView countriesRecyclerView = v.findViewById(R.id.recycleViewCountries);

      //  CountriesListAdapter countriesAdapter = new CountriesListAdapter(getContext(), realm.where(Country.class).findAll());               ////////

        RealmAdapter adapter = new RealmAdapter(realm.where(Country.class).findAll());

        adapter.setOnItemClickListener((numericCode) -> {
                    if (mListener != null) {
                        mListener.onCountryClick(numericCode);
                        Log.d(MainActivity.LOGGING_TAG, " onClick: " + numericCode);}

        });
        countriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        countriesRecyclerView.setAdapter(adapter);
        return v;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCountryClickListener) {
            mListener = (OnCountryClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCountryClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onDestroyView() {
        realm.close();
        super.onDestroyView();
    }
}
