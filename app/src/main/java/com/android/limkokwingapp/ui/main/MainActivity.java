package com.android.limkokwingapp.ui.main;

import android.annotation.SuppressLint;
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
import com.android.limkokwingapp.ui.main.presenter.MainPresenter;
import com.android.limkokwingapp.ui.main.view.MainContract;
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
        txtMobile.setText(user.getMobileNumber());
    }

    @OnClick({R.id.txt_mobile, R.id.btn_logout, R.id.btn_view_image, R.id.txt_name})
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

            case R.id.txt_name:
                if (user != null) {
                    openEditNameDialog(user);
                }
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
    public void onUpdateMobileNumber(String mobileNumber) {
        Utils.showSnackShort(mainView, getString(R.string.mobile_update_success_msg));
        txtMobile.setText(mobileNumber);
    }

    @Override
    public void onUpdateName(String fName, String lName) {
        Utils.showSnackShort(mainView, getString(R.string.name_updated_msg));
        txtName.setText(String.format(Locale.getDefault(), "%s %s", fName, lName));
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

    @SuppressLint("InflateParams")
    private void openEditMobileDialog(User user) {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View subView = inflater.inflate(R.layout.dialog_mobile_edit, null, false);
        TextInputEditText mEditMobile = subView.findViewById(R.id.edit_mobile_number);
        mEditMobile.setText(user.getMobileNumber());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.update_mobile));
        builder.setView(subView);
        final AlertDialog alertDialog = builder.create();

        builder.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
            String mobile = mEditMobile.getText().toString();
            user.setMobileNumber(mobile);
            presenter.updateMobileNumber(user);
            alertDialog.dismiss();
        });

        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> alertDialog.dismiss());

        builder.show();
    }

    @SuppressLint("InflateParams")
    private void openEditNameDialog(User user) {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View subView = inflater.inflate(R.layout.dialog_name_edit, null, false);
        TextInputEditText mEditFirstName = subView.findViewById(R.id.edit_first_name);
        TextInputEditText mEditLastName = subView.findViewById(R.id.edit_last_name);
        mEditFirstName.setText(user.getFirstName());
        mEditLastName.setText(user.getLastName());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.update_name));
        builder.setView(subView);
        final AlertDialog alertDialog = builder.create();

        builder.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
            String firstName = mEditFirstName.getText().toString();
            String lastName = mEditLastName.getText().toString();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            presenter.updateName(user);
            alertDialog.dismiss();
        });

        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> alertDialog.dismiss());

        builder.show();
    }
}
