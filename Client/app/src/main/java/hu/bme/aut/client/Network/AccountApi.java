package hu.bme.aut.client.Network;

import hu.bme.aut.client.Model.LoginDTO;
import hu.bme.aut.client.Model.LoginResponseDTO;
import hu.bme.aut.client.Model.RegisterDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AccountApi {
    final static String GRANT_TYPE = "password";
    @FormUrlEncoded
    @POST("/Token")
    Call<LoginResponseDTO> postLogin(@Field("grant_type")String grantType, @Field("username") String userName, @Field("password") String password );
    @POST("/Account/RegisterMobile")
    Call<ResponseBody> postRegister(@Body RegisterDTO login);
}
