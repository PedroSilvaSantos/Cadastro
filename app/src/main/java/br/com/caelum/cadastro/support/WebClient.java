package br.com.caelum.cadastro.support;

import java.io.IOException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by android6406 on 23/06/16.
 */
public class WebClient {
    public String post (String json) {
        try {
            String url = "https://www.caelum.com.br/mobile";
            MediaType tipo = MediaType.parse("application/json, charset=utf-8");
            RequestBody body = RequestBody.create(tipo, json);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).post(body).build();
            Response response = client.newCall(request).execute();
            String resposta = response.body().string();

            return resposta;
        } catch (IOException e) {
            e.printStackTrace();
        }

    return " ";



    }
}
