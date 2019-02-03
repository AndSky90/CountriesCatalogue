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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnCountryClickListener} interface
 * to handle interaction events.
 */

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

        CountriesListAdapter countriesAdapter = new CountriesListAdapter(getContext(), realm.where(Country.class).findAll());
        countriesAdapter.setOnItemClickListener((key) -> {
                    if (mListener != null) {
                        mListener.onCountryClick(key);
                        Log.d(MainActivity.TAG, " onClick: " + key);}

        });
        countriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        countriesRecyclerView.setAdapter(countriesAdapter);
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
