package com.i550.countriescatalogue.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Country extends RealmObject {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("capital")
    @Expose
    private String capital;

    @SerializedName("nativeName")
    @Expose
    private String nativeName;

    @PrimaryKey                                                     //primary key
    @SerializedName("numericCode")
    @Expose
    private Long numericCode;

    @SerializedName("currencies")
    @Expose
    private RealmList<CurrencyOfCountry> currencies;

    @SerializedName("flag")
    @Expose
    private String flag;


    public Long getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(Long numericCode) {
        this.numericCode = numericCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public RealmList<CurrencyOfCountry> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(RealmList<CurrencyOfCountry> currencies) {
        this.currencies = currencies;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
