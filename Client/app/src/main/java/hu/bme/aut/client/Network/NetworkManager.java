package hu.bme.aut.client.Network;

import hu.bme.aut.client.Model.LoginDTO;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {
    private static final String ENDPOINT_ADDRESS = "http://078c7128.ngrok.io/";
    private static NetworkManager instance;
    private AccountApi accountApi;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    private Retrofit retrofit;

    private NetworkManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT_ADDRESS)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        accountApi = retrofit.create(AccountApi.class);
    }
    public Call<ResponseBody> postLogin (LoginDTO loginData){
        return accountApi.postLogin(loginData);
    }
}
