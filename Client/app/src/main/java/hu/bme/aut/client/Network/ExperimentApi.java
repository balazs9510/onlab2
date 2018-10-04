package hu.bme.aut.client.Network;

import hu.bme.aut.client.Model.CreateExperimentDTO;
import hu.bme.aut.client.Model.LoginDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ExperimentApi {
    @POST("/Experiments/CreateExperiment")
    Call<ResponseBody> postExperiment(@Body CreateExperimentDTO experiment);
}
