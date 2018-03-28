package com.android.limkokwingapp.ui.signup.presenter;

import com.android.limkokwingapp.BasePresenter;
import com.android.limkokwingapp.data.entity.User;
import com.android.limkokwingapp.data.repository.local.UserDataSource;
import com.android.limkokwingapp.rx.SchedulerProvider;
import com.android.limkokwingapp.ui.signup.view.SignUpContract;
import com.android.limkokwingapp.utility.ValidationUtils;
import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableMaybeObserver;

/**
 * Created by gautam on 26/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public class SignUpPresenter extends BasePresenter<SignUpContract.View> implements SignUpContract.Presenter {

    private final SignUpContract.View view;

    private final UserDataSource userDataSource;

    private final SchedulerProvider appSchedulerProvider;

    private final BehaviorRelay<RequestState> requestStateObserver
            = BehaviorRelay.createDefault(RequestState.IDLE);

    @Inject
    public SignUpPresenter(SignUpContract.View view, UserDataSource userDataSource, SchedulerProvider appSchedulerProvider) {
        super(view);
        this.view = view;
        this.userDataSource = userDataSource;
        this.appSchedulerProvider = appSchedulerProvider;

        observeRequestState();
    }

    @Override
    public void start() {
        super.start();
        view.attach();
    }

    @Override
    public void doSignUp() {
        User user = new User();
        user.setFirstName(view.getFName());
        user.setLastName(view.getLName());
        user.setEmail(view.getEmail());
        user.setPassword(view.getPassword());
        user.setMobileNumber(view.getMobile());
        user.setGender(view.getGender());

        if (!validateInfo(user)) {
            return;
        }
        userDataSource.checkIfUserExists(user.getEmail())
                .subscribeOn(appSchedulerProvider.computation())
                .observeOn(appSchedulerProvider.ui())
                .doOnSubscribe(s -> publishRequestState(RequestState.LOADING))
                .doOnSuccess(u -> publishRequestState(RequestState.COMPLETE))
                .doOnError(t -> publishRequestState(RequestState.ERROR))
                .doOnComplete(() -> publishRequestState(RequestState.COMPLETE))
                .subscribe(useDisposable(user));
    }

    private DisposableMaybeObserver<User> useDisposable(User user) {
        return new DisposableMaybeObserver<User>() {
            boolean isUserExists = false;

            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    isUserExists = true;
                }
                if (isUserExists) {
                    view.onSignUpFailed();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Completable.fromRunnable(() -> userDataSource.saveUser(user))
                        .subscribeOn(appSchedulerProvider.computation())
                        .observeOn(appSchedulerProvider.computation())
                        .subscribe(() -> view.onSignUpSuccess(user));
            }
        };
    }

    private boolean validateInfo(User user) {
        if (!ValidationUtils.isValidString(user.getEmail())
                || !ValidationUtils.isValidString(user.getPassword())
                || !ValidationUtils.isValidString(user.getMobileNumber())
                || !ValidationUtils.isValidString(user.getGender())
                ) {
            view.onFieldEmpty();
            return false;
        }
        if (!ValidationUtils.isValidEmail(user.getEmail())) {
            view.onEmailInvalid();
            return false;
        }

        if (!ValidationUtils.isValidPassword(user.getPassword())) {
            view.onPasswordInvalid();
            return false;
        }

        if (!ValidationUtils.isValidMobile(user.getMobileNumber())) {
            view.onMobileInvalid();
            return false;
        }

        if (!ValidationUtils.isValidGender(user.getGender())) {
            view.onGenderInvalid();
            return false;
        }
        return true;
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
        view.detach();
    }
}
