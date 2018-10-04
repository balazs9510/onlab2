package hu.bme.aut.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    public static final int CANCEL_RESULT_CODE = 201;
    public static final int REGISTERED_RESULT_CODE = 202;
    public static final String REGISTER_EMAIL_KEY = "REGISTER_EMAIL_KEY";
    @BindView(R.id.register_et_email)
    EditText etEmail;

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
    public void saveRegister() {
        Intent intent = new Intent();
        intent.putExtra(REGISTER_EMAIL_KEY, etEmail.getText().toString());
        setResult(REGISTERED_RESULT_CODE, intent);
        finish();
    }
}
