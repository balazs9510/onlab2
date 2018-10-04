package hu.bme.aut.client.Network;

import hu.bme.aut.client.Model.LoginDTO;
import hu.bme.aut.client.Model.RegisterDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountApi {
    @POST("/Account/LoginMobile")
    Call<ResponseBody> postLogin(@Body LoginDTO login);
    @POST("/Account/RegisterMobile")
    Call<ResponseBody> postRegister(@Body RegisterDTO login);
}
