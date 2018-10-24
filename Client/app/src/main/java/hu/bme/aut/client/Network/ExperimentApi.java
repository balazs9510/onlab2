package hu.bme.aut.client.Network;

import java.util.List;

import hu.bme.aut.client.Model.CreateExperimentDTO;
import hu.bme.aut.client.Model.Experiment;
import hu.bme.aut.client.Model.ExperimentDTO;
import hu.bme.aut.client.Model.LoginDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ExperimentApi {
    @POST("/api/Experiment")
    Call<ResponseBody> postExperiment(@Header("Authorization") String authHeader,@Body CreateExperimentDTO experiment);
    @GET("/api/Experiment/")
    Call<List<ExperimentDTO>> getMyExperiments(@Header("Authorization") String authHeader);
}
