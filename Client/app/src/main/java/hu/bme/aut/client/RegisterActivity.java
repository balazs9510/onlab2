package hu.bme.aut.client;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.bme.aut.client.Model.RegisterDTO;
import hu.bme.aut.client.Network.NetworkManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    public static final int CANCEL_RESULT_CODE = 201;
    public static final int REGISTERED_RESULT_CODE = 202;
    public static final String REGISTER_PASSWORD_KEY = "REGISTER_PASSWORD_KEY";
    public static final String REGISTER_EMAIL_KEY = "REGISTER_EMAIL_KEY";
    @BindView(R.id.register_et_email)
    EditText etEmail;
    @BindView(R.id.register_et_age)
    EditText register_et_age;
    @BindView(R.id.register_et_job)
    EditText register_et_job;
    @BindView(R.id.register_et_name)
    EditText register_et_name;
    @BindView(R.id.register_et_password)
    EditText register_et_password;
    @BindView(R.id.register_et_repassword)
    EditText register_et_repassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_tv_cancel)
    public void cancelRegister() {
        setResult(CANCEL_RESULT_CODE);
        finish();
    }

    @OnClick(R.id.register_tv_register)
    public void register() {
        if (validateInputs()) {
            NetworkManager networkManager = NetworkManager.getInstance();
            networkManager.postRegister(getRegisterDTO()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code() == 200){
                        Intent intent = new Intent();
                        intent.putExtra(REGISTER_EMAIL_KEY, etEmail.getText().toString());
                        intent.putExtra(REGISTER_PASSWORD_KEY, register_et_password.getText().toString());
                        setResult(REGISTERED_RESULT_CODE, intent);
                        finish();
                    }
                    Snackbar.make(etEmail, R.string.register_failure, Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(TAG, t.toString());
                    Snackbar.make(etEmail, R.string.unexpected_error_register, Snackbar.LENGTH_SHORT).show();
                }
            });

        }
    }

    private boolean validateInputs() {
        boolean result = true;
        if (TextUtils.isEmpty(register_et_name.getText().toString())) {
            register_et_name.setError(getString(R.string.name_required));
            result = false;
        }
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError(getString(R.string.email_required));
            result = false;
        }
        //Todo rendes email validáció
        if (!etEmail.getText().toString().contains("@")) {
            etEmail.setError(getString(R.string.not_valid_email_1));
            result = false;
        }
        if (TextUtils.isEmpty(register_et_password.getText().toString()) || register_et_password.getText().toString().length() < 6) {
            register_et_password.setError(getString(R.string.password_validation_message));
            result = false;
        }
        if (!register_et_repassword.getText().toString().equals(register_et_password.getText().toString())) {
            register_et_repassword.setError(getString(R.string.repassword_validation_message));
            result = false;
        }
        return result;
    }
    private RegisterDTO getRegisterDTO(){
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setName(register_et_name.getText().toString());
        registerDTO.setAge(Integer.parseInt(register_et_age.getText().toString()));
        registerDTO.setEmail(etEmail.getText().toString());
        registerDTO.setJob(register_et_job.getText().toString());
        registerDTO.setPassword(register_et_password.getText().toString());
        registerDTO.setConfirmPassword(register_et_repassword.getText().toString());

        return registerDTO;
    }
}
