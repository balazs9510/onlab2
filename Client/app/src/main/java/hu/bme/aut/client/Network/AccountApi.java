package hu.bme.aut.client.Network;

import java.util.List;

import hu.bme.aut.client.Model.ExperimentDTO;
import hu.bme.aut.client.Model.LoginDTO;
import hu.bme.aut.client.Model.LoginResponseDTO;
import hu.bme.aut.client.Model.RegisterDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccountApi {
    final static String GRANT_TYPE = "password";
    @FormUrlEncoded
    @POST("/Token")
    Call<LoginResponseDTO> postLogin(@Field("grant_type")String grantType, @Field("username") String userName, @Field("password") String password );
    @POST("/Account/RegisterMobile")
    Call<ResponseBody> postRegister(@Body RegisterDTO login);
    @GET("/Account/MyId")
    Call<String> getMyId(@Header("Authorization") String authHeader);
    @GET("/account/{userId}/experiments")
    Call<List<ExperimentDTO>> getUserExperiments(@Header("Authorization") String authHeader, @Path("userId") String userId);

}
