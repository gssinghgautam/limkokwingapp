package com.android.limkokwingapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.limkokwingapp.R;
import com.android.limkokwingapp.data.entity.User;
import com.android.limkokwingapp.ui.main.MainActivity;
import com.android.limkokwingapp.ui.signup.SignUpActivity;
import com.android.limkokwingapp.utility.ApiConstant;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {

    private static final String TAG = "LoginActivity";

    @Inject
    LoginPresenter presenter;

    @BindView(R.id.edit_email_layout)
    TextInputEditText mEditEmailLayout;

    @BindView(R.id.email_input_layout)
    TextInputLayout mEmailInputLayout;

    @BindView(R.id.edit_password_layout)
    TextInputEditText mEditPasswordLayout;

    @BindView(R.id.password_input_layout)
    TextInputLayout mPasswordInputLayout;

    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @BindView(R.id.progress)
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void attach() {
        mEditEmailLayout.addTextChangedListener(textWatcher);
        mEditPasswordLayout.addTextChangedListener(textWatcher);
    }

    @Override
    public void detach() {
        mEditEmailLayout.addTextChangedListener(null);
        mEditPasswordLayout.addTextChangedListener(null);
    }

    @Override
    public void onValidationSuccess(User user) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(ApiConstant.USER_EXTRA, user);
        startActivity(intent);
        finish();
    }

    @Override
    public void onEmailEmpty() {
        Toast.makeText(this, getString(R.string.empty_fields_error_msg), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPasswordEmpty() {
        Toast.makeText(this, getString(R.string.empty_fields_error_msg), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmailInvalid() {
        mEmailInputLayout.setError(getString(R.string.invalid_email_error_msg));
    }

    @Override
    public void onPasswordInvalid() {
        mPasswordInputLayout.setError(getString(R.string.invalid_password_error_msg));
    }

    @Override
    public void onValidationFailed() {
        Toast.makeText(this, R.string.user_not_exists, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getEmail() {
        return mEditEmailLayout.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEditPasswordLayout.getText().toString();
    }

    @OnClick({R.id.btn_login, R.id.tv_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                presenter.loginUser();
                break;
            case R.id.tv_sign_up:
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (mEditEmailLayout.hasFocus()) {
                mEmailInputLayout.setError("");
            }

            if (mEditPasswordLayout.hasFocus()) {
                mPasswordInputLayout.setError("");
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}
