package br.com.cotacaomoedaonlineretrofit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.DecimalFormat;

import br.com.cotacaomoedaonlineretrofit.R;
import br.com.cotacaomoedaonlineretrofit.api.APIService;
import br.com.cotacaomoedaonlineretrofit.databinding.ActivityMainBinding;
import br.com.cotacaomoedaonlineretrofit.helper.RetrofitConifg;
import br.com.cotacaomoedaonlineretrofit.model.Moeda;
import br.com.cotacaomoedaonlineretrofit.model.Resultado;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Retrofit retrofit;
    private Resultado resultado;
    private Moeda moeda;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        decimalFormat = new DecimalFormat("0.00");

        binding.btnDollar.setOnClickListener(view -> {
            binding.linearCampos.setVisibility(View.VISIBLE);
            retrofit = RetrofitConifg.getRetrofit();
            APIService apiService = retrofit.create(APIService.class);
            apiService.recuperarDados().enqueue(new Callback<Resultado>() {
                @Override
                public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                    if (response.isSuccessful()){
                        resultado = response.body();

                        moeda = new Moeda();
                        moeda.setValorDolarCompra(resultado.USDBRL.bid);
                        moeda.setValorDolarVenda(resultado.USDBRL.ask);

                        String valorCompra = decimalFormat.format(Double.parseDouble(moeda.getValorDolarCompra()));
                        String valorVenda= decimalFormat.format(Double.parseDouble(moeda.getValorDolarVenda()));

                        binding.textDollarCompra.setText("Valor de compra R$ " + valorCompra);
                        binding.textDollarVenda.setText("Valor de venda R$ " + valorVenda);

                    }
                }

                @Override
                public void onFailure(Call<Resultado> call, Throwable t) {

                }
            });
        });

        binding.btnCompra.setOnClickListener(view -> {

            if (!binding.editCompra.getText().toString().isEmpty()){

                double pegarValor = Double.parseDouble(binding.editCompra.getText().toString());
                double passarValorDouble = Double.parseDouble(moeda.getValorDolarCompra());
                double totalCalculo = pegarValor / passarValorDouble;

                String resultado = decimalFormat.format(totalCalculo);

                binding.textResultadoCompra.setVisibility(View.VISIBLE);
                binding.textResultadoCompra.setText("U$ " + resultado);

            }else{
                Toast.makeText(getApplicationContext(), "Preencha o valor primeiro!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnVenda.setOnClickListener(view -> {

            if (!binding.editVenda.getText().toString().isEmpty()){

                double pegarValor = Double.parseDouble(binding.editVenda.getText().toString());
                double passarValorInteiro = Double.parseDouble(moeda.getValorDolarVenda());
                double total = pegarValor * passarValorInteiro;

                String resultado = decimalFormat.format(total);

                binding.textResultadoVenda.setVisibility(View.VISIBLE);
                binding.textResultadoVenda.setText("R$ " + resultado);

            }else{
                Toast.makeText(getApplicationContext(), "Preencha o valor primeiro!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}