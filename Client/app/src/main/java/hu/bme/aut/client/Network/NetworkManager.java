package hu.bme.aut.client.Network;

import hu.bme.aut.client.Model.CreateExperimentDTO;
import hu.bme.aut.client.Model.LoginDTO;
import hu.bme.aut.client.Model.LoginResponseDTO;
import hu.bme.aut.client.Model.RegisterDTO;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {
    private static final String ENDPOINT_ADDRESS = "http://83f4efe7.ngrok.io/";
    private static NetworkManager instance;
    private AccountApi accountApi;
    private ExperimentApi experimentApi;

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
        experimentApi = retrofit.create(ExperimentApi.class);
    }

    public Call<LoginResponseDTO> postLogin(String userName, String password) {
        return accountApi.postLogin(AccountApi.GRANT_TYPE, userName, password);
    }

    public Call<ResponseBody> postRegister(RegisterDTO registerDTO) {
        return accountApi.postRegister(registerDTO);
    }

    public Call<ResponseBody> postExperiment(CreateExperimentDTO experiment) {
        return experimentApi.postExperiment(experiment);
    }
}
