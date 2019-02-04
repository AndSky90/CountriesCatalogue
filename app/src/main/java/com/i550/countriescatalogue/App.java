package com.i550.countriescatalogue;

import android.app.Application;
import android.graphics.drawable.PictureDrawable;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.i550.countriescatalogue.svgWorks.GlideApp;
import com.i550.countriescatalogue.svgWorks.SvgSoftwareLayerSetter;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class App extends Application {

    private static ServerApi retrofitApi;
    private static RequestBuilder<PictureDrawable> requestBuilder;
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(realmConfig);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.eu/rest/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitApi = retrofit.create(ServerApi.class);


        requestBuilder = GlideApp.with(this)
                .as(PictureDrawable.class)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .transition(withCrossFade())
                .error(android.R.drawable.stat_sys_warning)
                .listener(new SvgSoftwareLayerSetter());
    }


    public static ServerApi getRetrofitApi() {
        return retrofitApi;
    }
    public static RequestBuilder<PictureDrawable> getSvgBuilder() {
        return requestBuilder;
    }
}
