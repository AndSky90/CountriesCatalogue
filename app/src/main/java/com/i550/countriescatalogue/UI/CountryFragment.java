package com.i550.countriescatalogue.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import io.realm.Realm;
import io.realm.RealmList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.i550.countriescatalogue.App;
import com.i550.countriescatalogue.Model.Country;
import com.i550.countriescatalogue.Model.CurrencyOfCountry;
import com.i550.countriescatalogue.R;

public class CountryFragment extends Fragment {

    private static final String PARAM_KEY = "key";
    private static final String NUMERIC_CODE_KEY = "numericCode";
    private Realm realm;
    private Long numericCode;

    public static CountryFragment newInstance(Long numericCode) {
        CountryFragment fragment = new CountryFragment();
        Bundle args = new Bundle();
        args.putLong(PARAM_KEY, numericCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            numericCode = getArguments().getLong(PARAM_KEY);

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_country, container, false);
        realm = Realm.getDefaultInstance();

        Country country = realm.where(Country.class).equalTo(NUMERIC_CODE_KEY, numericCode).findFirst();

        TextView name = v.findViewById(R.id.name);
        name.setText(country.getName());
        TextView capital = v.findViewById(R.id.capital);
        capital.setText(country.getCapital());
        TextView currencies = v.findViewById(R.id.currencies);

        RealmList<CurrencyOfCountry> currencyList = country.getCurrencies();
        StringBuilder builder = new StringBuilder();
        for (CurrencyOfCountry c : currencyList) {
            builder.append(c.getSymbol()).append(" — ").append(c.getName()).append("\n");
        }
        currencies.setText(builder.toString());

        ImageView flag = v.findViewById(R.id.flag);

        App.getSvgBuilder()
                .load(country.getFlag())
                .into(flag);

        return v;

    }

    @Override
    public void onDestroyView() {
        realm.close();
        super.onDestroyView();
    }

}
