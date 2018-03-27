package com.android.limkokwingapp.ui.login;

import android.content.SharedPreferences;

import com.android.limkokwingapp.BasePresenter;
import com.android.limkokwingapp.data.entity.User;
import com.android.limkokwingapp.data.repository.local.UserDataRepository;
import com.android.limkokwingapp.rx.SchedulerProvider;
import com.android.limkokwingapp.utility.ApiConstant;
import com.android.limkokwingapp.utility.ValidationUtils;
import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;

import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by gautam on 26/03/18.
 */

public class LoginPresenter extends BasePresenter<LoginContract.LoginView> implements LoginContract.Presenter {

    private final UserDataRepository userDataSource;

    private final SchedulerProvider appSchedulerProvider;

    private final LoginContract.LoginView view;

    private final SharedPreferences sharedPreferences;

    private final BehaviorRelay<RequestState> requestStateObserver
            = BehaviorRelay.createDefault(RequestState.IDLE);

    @Inject
    public LoginPresenter(LoginContract.LoginView view, UserDataRepository userDataSource, SchedulerProvider appSchedulerProvider, SharedPreferences sharedPreferences) {
        super(view);
        this.view = view;
        this.userDataSource = userDataSource;
        this.appSchedulerProvider = appSchedulerProvider;
        this.sharedPreferences = sharedPreferences;

        observeRequestState();
    }

    @Override
    public void start() {
        super.start();
        view.attach();
        boolean isLoggedIn = sharedPreferences.getBoolean(ApiConstant.USER_LOGGED_IN, false);
        if (isLoggedIn) {
            String email = sharedPreferences.getString(ApiConstant.USER_EMAIL, "");
            fetchUser(email);
        }
    }

    @Override
    public void loginUser() {
        String email = view.getEmail();
        String password = view.getPassword();
        if (!ValidationUtils.isValidString(email)) {
            view.onEmailEmpty();
            return;
        }

        if (!ValidationUtils.isValidString(password)) {
            view.onPasswordEmpty();
            return;
        }

        if (!ValidationUtils.isValidEmail(email)) {
            view.onEmailInvalid();
            return;
        }

        if (!ValidationUtils.isValidPassword(password)) {
            view.onPasswordInvalid();
            return;
        }

        userDataSource.loginUser(new LoginModel(email, password))
                .subscribeOn(appSchedulerProvider.computation())
                .observeOn(appSchedulerProvider.ui())
                .doOnSubscribe(s -> publishRequestState(RequestState.LOADING))
                .doOnSuccess(s -> publishRequestState(RequestState.COMPLETE))
                .doOnError(t -> publishRequestState(RequestState.ERROR))
                .doOnComplete(() -> publishRequestState(RequestState.COMPLETE))
                .subscribe(observer);
    }

    @Override
    public void fetchUser(String emailId) {
        addDisposable(userDataSource.checkIfUserExists(emailId)
                .subscribeOn(appSchedulerProvider.computation())
                .observeOn(appSchedulerProvider.ui())
                .doOnSubscribe(s -> publishRequestState(RequestState.LOADING))
                .doOnSuccess(s -> publishRequestState(RequestState.COMPLETE))
                .doOnError(t -> publishRequestState(RequestState.ERROR))
                .doOnComplete(() -> publishRequestState(RequestState.COMPLETE))
                .subscribe(user -> {
                    if (user != null) {
                        sharedPreferences.edit().putBoolean(ApiConstant.USER_LOGGED_IN, true).apply();
                        sharedPreferences.edit().putString(ApiConstant.USER_EMAIL, user.getEmail()).apply();
                        view.onValidationSuccess(user);
                    }
                }));
    }

    private MaybeObserver<User> observer = new MaybeObserver<User>() {
        boolean isUserExists = false;

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(User user) {
            isUserExists = user != null;
            if (isUserExists) {
                sharedPreferences.edit().putBoolean(ApiConstant.USER_LOGGED_IN, true).apply();
                sharedPreferences.edit().putString(ApiConstant.USER_EMAIL, user.getEmail()).apply();
                view.onValidationSuccess(user);
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            if (!isUserExists) {
                view.onValidationFailed();
            }
        }
    };

    private void publishRequestState(RequestState requestState) {
        addDisposable(Observable.just(requestState)
                .observeOn(appSchedulerProvider.ui())
                .subscribe(requestStateObserver));
    }


    private void observeRequestState() {
        requestStateObserver.subscribe(requestState -> {
            switch (requestState) {
                case IDLE:
                    break;
                case LOADING:
                    view.showProgress();
                    break;
                case COMPLETE:
                    view.hideProgress();
                    break;
                case ERROR:
                    view.hideProgress();
                    break;
            }
        }, Throwable::printStackTrace);
    }

    @Override
    public void stop() {
        super.stop();
        view.detach();
    }
}
