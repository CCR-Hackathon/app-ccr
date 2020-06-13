package br.com.catossi.meu_amigo_caminhoneiro.service;

import br.com.catossi.meu_amigo_caminhoneiro.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;

    public static Retrofit getService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
