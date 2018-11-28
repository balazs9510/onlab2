package hu.bme.aut.client.Network;

import android.net.Uri;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hu.bme.aut.client.Model.CreateExperimentDTO;
import hu.bme.aut.client.Model.ExperimentDTO;
import hu.bme.aut.client.Model.LoginResponseDTO;
import hu.bme.aut.client.Model.RegisterDTO;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static hu.bme.aut.client.Network.ExperimentApi.MULTIPART_FORM_DATA;
import static hu.bme.aut.client.Network.ExperimentApi.PHOTO_MULTIPART_KEY_IMG;

public class NetworkManager {
    private static final String ENDPOINT_ADDRESS = "http://2338940b.ngrok.io";
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
                .client(new OkHttpClient
                        .Builder()
                        .readTimeout(120, TimeUnit.SECONDS)
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .build())
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

    public Call<ResponseBody> postExperiment(String authToken, CreateExperimentDTO experiment) {
        return experimentApi.postExperiment("Bearer " + authToken, experiment);
    }

    public Call<String> getMyId(String authToken) {
        return accountApi.getMyId("Bearer " + authToken);
    }

    public Call<List<ExperimentDTO>> getUserExperiments(String authToken, String userId) {
        return accountApi.getUserExperiments("Bearer " + authToken, userId);
    }

    public Call<ExperimentDTO> getExperiment(String authToken, String id) {
        return experimentApi.getExperiment("Bearer " + authToken, id);
    }
    public Call<ResponseBody> stopExperiment(String authToken, String id) {
        return experimentApi.stopExperiment("Bearer " + authToken, id);
    }
    public Call<ResponseBody> uploadImage(String authToken, String id, Uri fileUri) {
        File file = new File(fileUri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(PHOTO_MULTIPART_KEY_IMG, file.getName(), requestFile);
       // RequestBody idParam = RequestBody.create(okhttp3.MultipartBody.FORM, id);
        return experimentApi.uploadImage("Bearer " + authToken, body, id);
    }
}
