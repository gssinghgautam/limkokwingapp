package com.android.limkokwingapp.ui.signup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.limkokwingapp.R;
import com.android.limkokwingapp.data.entity.User;
import com.android.limkokwingapp.ui.main.MainActivity;
import com.android.limkokwingapp.utility.ApiConstant;
import com.android.limkokwingapp.utility.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import dagger.android.AndroidInjection;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;

    @BindView(R.id.edit_first_name)
    TextInputEditText mFirstNameEditText;

    @BindView(R.id.first_name_input_layout)
    TextInputLayout mFirstNameInputLayout;

    @BindView(R.id.edit_last_name)
    TextInputEditText mLastNameEditText;

    @BindView(R.id.last_name_input_layout)
    TextInputLayout mLastNameInputLayout;

    @BindView(R.id.edit_email_layout)
    TextInputEditText mEmailEditText;

    @BindView(R.id.email_input_layout)
    TextInputLayout mEmailInputLayout;

    @BindView(R.id.edit_password_layout)
    TextInputEditText mPasswordEditText;

    @BindView(R.id.password_input_layout)
    TextInputLayout mPasswordInputLayout;

    @BindView(R.id.edit_mobile_layout)
    TextInputEditText mMobileEditText;

    @BindView(R.id.mobile_input_layout)
    TextInputLayout mMobileInputLayout;

    @BindView(R.id.btn_sign_up)
    Button mSingUpBtn;

    @BindView(R.id.ll_sign_up_view)
    LinearLayout llSignUpView;

    @BindView(R.id.sp_gender)
    AppCompatSpinner spGender;

    private String mGender;

    @Inject
    SignUpPresenter presenter;

    @Inject
    SharedPreferences mSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        setGenderSpinner();
    }

    private void setGenderSpinner() {
        String[] genderArray = getResources().getStringArray(R.array.gender_array);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(SignUpActivity.this, R.layout.gender_drop_down_layout,
                R.id.tv_gender, genderArray);
        spGender.setAdapter(genderAdapter);
    }

    @OnItemSelected(R.id.sp_gender)
    void onGenderSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mGender = (String) adapterView.getItemAtPosition(i);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void attach() {
        mEmailEditText.addTextChangedListener(textWatcher);
        mPasswordEditText.addTextChangedListener(textWatcher);
        mMobileEditText.addTextChangedListener(textWatcher);
    }

    @Override
    public void detach() {
        mEmailEditText.addTextChangedListener(null);
        mPasswordEditText.addTextChangedListener(null);
        mMobileEditText.addTextChangedListener(null);
    }

    @Override
    public void onSignUpSuccess(User user) {
        mSharedPreference.edit().putBoolean(ApiConstant.USER_LOGGED_IN, true).apply();
        mSharedPreference.edit().putString(ApiConstant.USER_EMAIL, user.getEmail()).apply();
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(ApiConstant.USER_EXTRA, user);
        startActivity(intent);
        finishAffinity();

    }

    @Override
    public void onFieldEmpty() {
        Utils.showToast(this, getString(R.string.empty_form_error_msg));
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
    public void onMobileInvalid() {
        mMobileInputLayout.setError(getString(R.string.invalid_mobile_error_msg));
    }

    @Override
    public void onGenderInvalid() {
        Utils.showToast(this, getString(R.string.invalid_gender));
    }

    @Override
    public void onSignUpFailed() {
        Utils.showToast(this,getString(R.string.email_exists_error_msg));
    }

    @Override
    public String getFName() {
        return mFirstNameEditText.getText().toString();
    }

    @Override
    public String getLName() {
        return mLastNameEditText.getText().toString();
    }

    @Override
    public String getEmail() {
        return mEmailEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEditText.getText().toString();
    }

    @Override
    public String getMobile() {
        return mMobileEditText.getText().toString();
    }

    @Override
    public String getGender() {
        return mGender;
    }

    @OnClick(R.id.btn_sign_up)
    public void onViewClicked() {
        presenter.doSignUp();
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
            if (mEmailEditText.hasFocus()) {
                mEmailInputLayout.setError("");
            }

            if (mPasswordEditText.hasFocus()) {
                mPasswordInputLayout.setError("");
            }

            if (mMobileEditText.hasFocus()) {
                mMobileInputLayout.setError("");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
