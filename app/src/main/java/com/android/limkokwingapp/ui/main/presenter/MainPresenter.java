package com.android.limkokwingapp.ui.main.presenter;

import android.content.SharedPreferences;

import com.android.limkokwingapp.BasePresenter;
import com.android.limkokwingapp.data.entity.User;
import com.android.limkokwingapp.data.repository.local.UserDataSource;
import com.android.limkokwingapp.rx.AppSchedulerProvider;
import com.android.limkokwingapp.ui.main.view.MainContract;
import com.android.limkokwingapp.utility.ApiConstant;
import com.android.limkokwingapp.utility.ValidationUtils;
import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by gautam on 26/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private final MainContract.View view;

    private final UserDataSource userDataSource;

    private final AppSchedulerProvider appSchedulerProvider;

    private final SharedPreferences sharedPreferences;

    private final BehaviorRelay<RequestState> requestStateObserver
            = BehaviorRelay.createDefault(RequestState.IDLE);

    @Inject
    public MainPresenter(MainContract.View view, UserDataSource userDataSource, AppSchedulerProvider appSchedulerProvider, SharedPreferences sharedPreferences) {
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
    }

    @Override
    public void updateMobileNumber(User user) {
        if (!isValid(user.getMobileNumber())) {
            return;
        }
        addDisposable(Completable.fromRunnable(() -> userDataSource.updateUser(user))
                .subscribeOn(appSchedulerProvider.computation())
                .observeOn(appSchedulerProvider.ui())
                .doOnSubscribe(s -> publishRequestState(RequestState.LOADING))
                .doOnError(t -> {
                    view.onUpdateFailed();
                    publishRequestState(RequestState.ERROR);
                })
                .doOnComplete(() -> publishRequestState(RequestState.COMPLETE))
                .subscribe(() -> view.onUpdateMobileNumber(user.getMobileNumber())));
    }

    private boolean isValid(String mobile) {
        if (!ValidationUtils.isValidString(mobile)) {
            view.toEmptyMobile();
            return false;
        }
        if (!ValidationUtils.isValidMobile(mobile)) {
            view.toInvalidMobile();
            return false;
        }
        return true;
    }

    @Override
    public void updateName(User user) {
        addDisposable(Completable.fromRunnable(() -> userDataSource.updateUser(user))
                .subscribeOn(appSchedulerProvider.computation())
                .observeOn(appSchedulerProvider.ui())
                .doOnSubscribe(s -> publishRequestState(RequestState.LOADING))
                .doOnError(t -> {
                    view.onUpdateFailed();
                    publishRequestState(RequestState.ERROR);
                })
                .doOnComplete(() -> publishRequestState(RequestState.COMPLETE))
                .subscribe(() -> view.onUpdateName(user.getFirstName(), user.getLastName())));
    }

    @Override
    public void logout() {
        sharedPreferences.edit().remove(ApiConstant.USER_LOGGED_IN).apply();
        sharedPreferences.edit().remove(ApiConstant.USER_EMAIL).apply();
        view.onLogout();
    }

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
    }
}
