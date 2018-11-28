package hu.bme.aut.client.Network;

import java.util.List;

import hu.bme.aut.client.Model.CreateExperimentDTO;
import hu.bme.aut.client.Model.ExperimentDTO;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ExperimentApi {
    String MULTIPART_FORM_DATA = "multipart/form-data";
    String PHOTO_MULTIPART_KEY_IMG = "image";
    @POST("/api/Experiment")
    Call<ResponseBody> postExperiment(@Header("Authorization") String authHeader,@Body CreateExperimentDTO experiment);
    @GET("/api/Experiment/")
    Call<List<ExperimentDTO>> getMyExperiments(@Header("Authorization") String authHeader);
    @GET("/api/Experiment/{id}")
    Call<ExperimentDTO> getExperiment(@Header("Authorization") String authHeader, @Path("id") String id);

    @GET("api/experiment/stopexperiment/{id}")
    Call<ResponseBody> stopExperiment(@Header("Authorization") String authHeader, @Path("id") String id);
    @Multipart
    @POST("/api/ExperimentImage/{experimentId}")
    Call<ResponseBody> uploadImage(@Header("Authorization") String authHeader,  @Part MultipartBody.Part file, @Path("experimentId") String experimentId);
}
