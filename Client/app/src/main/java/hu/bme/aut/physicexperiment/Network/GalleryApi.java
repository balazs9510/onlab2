package hu.bme.aut.physicexperiment.Network;

import hu.bme.aut.physicexperiment.Model.Experiment;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GalleryApi {
    String ENDPOINT_URL = "http://69ea612a.ngrok.io";
    String MULTIPART_FORM_DATA = "multipart/form-data";
    String PHOTO_MULTIPART_KEY_IMG = "image";

    @Multipart
    @POST("/api/Experiment")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file, @Part("name") RequestBody name, @Part("description") RequestBody description);
    @GET("/api/Experiment")
    Call<ResponseBody> getString();

    @POST("/api/Experiment")
    Call<Experiment> createExperiment(@Body Experiment experiment);
}
