package hu.bme.aut.physicexperiment.Model;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;

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
    private final Context context;

    public GalleryInteractor(Context context) {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GalleryApi.ENDPOINT_URL)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.galleryApi = retrofit.create(GalleryApi.class);
    }

    private static <T> void runCallOnBackgroundThread(final Call<T> call, final ResponseListener<T> listener) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final T response = call.execute().body();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onResponse(response);
                        }
                    });

                } catch (final Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onError(e);
                        }
                    });
                }
            }
        }).start();
    }

    public void uploadImage(Uri fileUri, String name, String description, ResponseListener<ResponseBody> responseListener) {
        File file = new File(fileUri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(PHOTO_MULTIPART_KEY_IMG, file.getName(), requestFile);

        RequestBody nameParam = RequestBody.create(okhttp3.MultipartBody.FORM, name);
        RequestBody descriptionParam = RequestBody.create(okhttp3.MultipartBody.FORM, description);

        Call<ResponseBody> uploadImageRequest = galleryApi.uploadImage(body, nameParam, descriptionParam);
        runCallOnBackgroundThread(uploadImageRequest, responseListener);
    }
    public Call<ResponseBody> getString(){
       return galleryApi.getString();
    }

    public interface ResponseListener<T> {
        void onResponse(T t);

        void onError(Exception e);
    }
}
