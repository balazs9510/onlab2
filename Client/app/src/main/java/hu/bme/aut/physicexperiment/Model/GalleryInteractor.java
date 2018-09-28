package hu.bme.aut.physicexperiment.Model;

import android.net.Uri;

import java.io.File;

import hu.bme.aut.physicexperiment.Network.GalleryApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static hu.bme.aut.physicexperiment.Network.GalleryApi.MULTIPART_FORM_DATA;
import static hu.bme.aut.physicexperiment.Network.GalleryApi.PHOTO_MULTIPART_KEY_IMG;

public class GalleryInteractor {
    private final GalleryApi galleryApi;

    public GalleryInteractor() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GalleryApi.ENDPOINT_URL)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.galleryApi = retrofit.create(GalleryApi.class);
    }


    public Call<ResponseBody> uploadImage(Uri fileUri, String name, String description) {
        File file = new File(fileUri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(PHOTO_MULTIPART_KEY_IMG, file.getName(), requestFile);

        RequestBody nameParam = RequestBody.create(okhttp3.MultipartBody.FORM, name);
        RequestBody descriptionParam = RequestBody.create(okhttp3.MultipartBody.FORM, description);

        return galleryApi.uploadImage(body, nameParam, descriptionParam);

    }

    public Call<ResponseBody> getString() {
        return galleryApi.getString();
    }

    public Call<Experiment> createExperiment(Experiment e) {
        return galleryApi.createExperiment(e);
    }
}
