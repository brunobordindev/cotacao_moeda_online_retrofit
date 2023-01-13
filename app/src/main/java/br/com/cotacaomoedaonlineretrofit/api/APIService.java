package br.com.cotacaomoedaonlineretrofit.api;

import br.com.cotacaomoedaonlineretrofit.model.Resultado;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("USD-BRL")
    Call<Resultado> recuperarDados();
}
