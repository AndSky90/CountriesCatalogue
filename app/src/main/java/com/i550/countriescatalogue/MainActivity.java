package com.i550.countriescatalogue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.i550.countriescatalogue.UI.CountriesListFragment;
import com.i550.countriescatalogue.Model.Country;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnCountryClickListener {

    ArrayList<Country> listFromJson;
    public static final String TAG = "LOGGING";
    FragmentManager fm;
    Fragment container;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listFromJson = new ArrayList<>();

        realm = Realm.getDefaultInstance();

        if (realm.isEmpty()) {
            loadDataGlobal();
        }

        fm = getSupportFragmentManager();
        container = fm.findFragmentById(R.id.fragment_container);
        if (container == null) {
            container = new CountriesListFragment();
            fm.beginTransaction().add(R.id.fragment_container, container).commit();
        }


     /*   if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.edition_container, new MyEditionFragment())
                    .add(R.id.list_container, new MyListFragment())
                    .commit();
        }*/

    }

    private void loadDataGlobal() {

        App.getRetrofitApi().callAllData().enqueue(new Callback<ArrayList<Country>>() {
            @Override
            public void onResponse( Call<ArrayList<Country>> call,  Response<ArrayList<Country>> response) {
                if (response.isSuccessful()) {
                    listFromJson.clear();
                    listFromJson.addAll(response.body());
                    Log.d(TAG, " raw: " + response.raw());
                    Log.d(TAG, " onResponse response.body(): " + response.body());

                    realm.beginTransaction();
                    final List<Country> list = realm.copyToRealmOrUpdate(listFromJson);       //TODO убрать апдейт в конце и проверить что всё верно работает
                    realm.commitTransaction();

                   /* realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                        }
                    });
*/
                } else {
                    int statusCode = response.code();
                    try {
                        Log.e(TAG, "statusCode: " + statusCode + "onResponse response.errorBody(): " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure( Call<ArrayList<Country>> call, Throwable t) {
                Log.e(TAG, " onFailure: " + call);

            }
        });
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }

    @Override
    public void onCountryClick(String numericCode) {

    }
}
