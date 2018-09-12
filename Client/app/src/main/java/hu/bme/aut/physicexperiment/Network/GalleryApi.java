package hu.bme.aut.physicexperiment.Network;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GalleryApi {
    String ENDPOINT_URL = "http://10.0.2.2";
    String MULTIPART_FORM_DATA = "multipart/form-data";
    String PHOTO_MULTIPART_KEY_IMG = "image";

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file, @Part("name") RequestBody name, @Part("description") RequestBody description);
}
