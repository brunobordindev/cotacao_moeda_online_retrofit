package br.com.cotacaomoedaonlineretrofit.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConifg {

    public static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(DollarConfig.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
