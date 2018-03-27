package com.android.limkokwingapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.limkokwingapp.R;
import com.android.limkokwingapp.data.entity.User;
import com.android.limkokwingapp.ui.images.ImageActivity;
import com.android.limkokwingapp.ui.login.LoginActivity;
import com.android.limkokwingapp.utility.ApiConstant;
import com.android.limkokwingapp.utility.Utils;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @Inject
    MainPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txt_name)
    TextView txtName;

    @BindView(R.id.txt_mobile)
    TextView txtMobile;

    @BindView(R.id.btn_logout)
    Button btnLogout;

    @BindView(R.id.btn_view_image)
    Button btnViewImage;

    @BindView(R.id.main)
    CoordinatorLayout mainView;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        user = getIntent().getParcelableExtra(ApiConstant.USER_EXTRA);
        intiView();
    }

    private void intiView() {
        txtName.setText(String.format(Locale.getDefault(), "%s %s", user.getFirstName(), user.getLastName()));
        txtMobile.setText(user.getMobile());
    }

    @OnClick({R.id.txt_mobile, R.id.btn_logout, R.id.btn_view_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_mobile:
                if (user != null) {
                    openEditMobileDialog(user);
                }
                break;
            case R.id.btn_logout:
                presenter.logout();
                break;
            case R.id.btn_view_image:
                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void toEmptyMobile() {
        Utils.showSnackShort(mainView, getString(R.string.mobile_empty_error_msg));
    }

    @Override
    public void toInvalidMobile() {
        Utils.showSnackShort(mainView, getString(R.string.invalid_mobile_error_msg));
    }

    @Override
    public void onUpdateSuccess(User user) {
        Utils.showSnackShort(mainView, getString(R.string.mobile_update_success_msg));
        txtMobile.setText(user.getMobile());
    }

    @Override
    public void onUpdateFailed() {
        Utils.showSnackShort(mainView, getString(R.string.mobile_update_failed_msg));
    }

    @Override
    public void onLogout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void openEditMobileDialog(User user) {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View subView = inflater.inflate(R.layout.dialog_mobile_edit, null);
        TextInputEditText mEditMobile = subView.findViewById(R.id.edit_mobile_number);
        mEditMobile.setText(user.getMobile());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.update_mobile));
        builder.setView(subView);
        final AlertDialog alertDialog = builder.create();

        builder.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
            String mobile = mEditMobile.getText().toString();
            user.setMobile(mobile);
            presenter.updateUserInfo(user);
            alertDialog.dismiss();
        });

        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> alertDialog.dismiss());

        builder.show();
    }
}
