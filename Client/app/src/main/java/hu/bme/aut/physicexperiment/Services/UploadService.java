package hu.bme.aut.physicexperiment.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.File;

import hu.bme.aut.physicexperiment.Model.GalleryInteractor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UploadService extends IntentService {
    public static final String IMG_PATH = "img_path";
    public static final String FILENAME = "filename";
    public static final String IS_SUCCESS = "is_success";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "hu.bme.aut.physicexperiment.Services.receiver";
    private static final String TAG = "UploadService";
    private static final String ACTION_IMG_TAKEN = "hu.bme.aut.physicexperiment.Services.action.image_taken";
    private GalleryInteractor galleryInteractor;

    public UploadService() {
        super("UploadService");
        galleryInteractor = new GalleryInteractor();
    }

    public static void startActionImageTaken(Context context, String imgUrl, String fileName) {
        Intent intent = new Intent(context, UploadService.class);
        intent.setAction(ACTION_IMG_TAKEN);
        intent.putExtra(IMG_PATH, imgUrl);
        intent.putExtra(FILENAME, fileName);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_IMG_TAKEN.equals(action)) {
                final String imgPath = intent.getStringExtra(IMG_PATH);
                final String fileName = intent.getStringExtra(FILENAME);
                handleActionImageTaken(imgPath, fileName);
            }
        }
    }

    private void handleActionImageTaken(String imgPath, String fileName) {
        Uri imgUri = Uri.fromFile(new File(imgPath));
        galleryInteractor.uploadImage(imgUri, fileName, "").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                publishResults(response.isSuccessful(), response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Image upload failure");
            }
        });
    }

    private void publishResults(boolean isSuccess, int resultCode) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(IS_SUCCESS, isSuccess);
        intent.putExtra(RESULT, resultCode);
        sendBroadcast(intent);
    }

}
