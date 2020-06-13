package br.com.catossi.meu_amigo_caminhoneiro.service;

import br.com.catossi.meu_amigo_caminhoneiro.model.Payload;
import br.com.catossi.meu_amigo_caminhoneiro.utils.Constants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface APIInterface {

    @POST(Constants.VOICE)
    Call<Payload> postVoice(@Body Payload payload);



}