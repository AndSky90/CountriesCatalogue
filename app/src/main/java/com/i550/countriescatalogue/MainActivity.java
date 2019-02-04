package com.i550.countriescatalogue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.realm.Realm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.i550.countriescatalogue.Model.Repository;
import com.i550.countriescatalogue.UI.CountriesListFragment;
import com.i550.countriescatalogue.UI.CountryFragment;
import com.i550.countriescatalogue.UI.SplashFragment;

public class MainActivity extends AppCompatActivity implements OnCountryClickListener {

    public static final String LOGGING_TAG = "LOGGING_TAG";
    private static final String COUNTRY_TAG = "COUNTRY";
    private static final String LIST = "LIST";
    FragmentManager fm;
    Fragment container;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        fm = getSupportFragmentManager();
        container = fm.findFragmentById(R.id.fragment_container);

        if (container == null) {
            container = new SplashFragment();
            fm.beginTransaction().add(R.id.fragment_container, container).commit();
        }

        if (realm.isEmpty()) {
            if (checkInternet()) {
                Repository.loadDataFromApi();
                showCountriesListFragment();
            }
        } else {
            showCountriesListFragment();
        }


     /*   if(savedInstanceState == null) {
            fm
                    .beginTransaction()
                    .add(R.id.edition_container, new MyEditionFragment())
                    .add(R.id.list_container, new MyListFragment())
                    .commit();
        }*/


       /* if (container == null) {
            container = new MainFragment();
            fm.beginTransaction().add(R.id.fragment_container, container).commit();
        }*/

    }

    private void showCountriesListFragment() {
        CountriesListFragment countriesListFragment = new CountriesListFragment();

            fm.beginTransaction().remove(container).add(R.id.fragment_container, countriesListFragment, LIST).commit();
        }


    private Boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @Override
    public void onCountryClick(Long numericCode) {
        CountryFragment countryFragment = CountryFragment.newInstance(numericCode);
        fm.beginTransaction()
                .hide(fm.findFragmentByTag(LIST))
                .add(R.id.fragment_container, countryFragment, COUNTRY_TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(COUNTRY_TAG)
                .commit();
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}
