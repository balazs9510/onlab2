package hu.bme.aut.client.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class LoginResponseDTO {

@SerializedName("access_token")
@Expose
private String accessToken;
@SerializedName("token_type")
@Expose
private String tokenType;
@SerializedName("expires_in")
@Expose
private Integer expiresIn;
@SerializedName("userName")
@Expose
private String userName;
@SerializedName("experimentDate")
@Expose
private String experimentDate;

public String getAccessToken() {
return accessToken;
}

public void setAccessToken(String accessToken) {
this.accessToken = accessToken;
}

public String getTokenType() {
return tokenType;
}

public void setTokenType(String tokenType) {
this.tokenType = tokenType;
}

public Integer getExpiresIn() {
return expiresIn;
}

public void setExpiresIn(Integer expiresIn) {
this.expiresIn = expiresIn;
}

public String getUserName() {
return userName;
}

public void setUserName(String userName) {
this.userName = userName;
}


}