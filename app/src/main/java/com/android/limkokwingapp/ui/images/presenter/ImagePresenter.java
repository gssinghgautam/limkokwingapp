package com.android.limkokwingapp.ui.images.presenter;

import com.android.limkokwingapp.BasePresenter;
import com.android.limkokwingapp.data.remote.model.FlickerPhotos;
import com.android.limkokwingapp.data.repository.remote.FlickerDataSource;
import com.android.limkokwingapp.rx.SchedulerProvider;
import com.android.limkokwingapp.ui.images.view.ImageContract;
import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by gautam on 27/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public class ImagePresenter extends BasePresenter<ImageContract.View> implements ImageContract.Presenter {

    private ImageContract.View view;

    private final FlickerDataSource flickerDataSource;

    private final SchedulerProvider appSchedulerProvider;

    private final BehaviorRelay<RequestState> requestStateObserver
            = BehaviorRelay.createDefault(RequestState.IDLE);

    private int currentPage = 1;

    private int totalPage;

    @Inject
    public ImagePresenter(ImageContract.View view, FlickerDataSource flickerDataSource, SchedulerProvider appSchedulerProvider) {
        super(view);
        this.view = view;
        this.flickerDataSource = flickerDataSource;
        this.appSchedulerProvider = appSchedulerProvider;

        observeRequestState();
    }

    @Override
    public void start() {
        super.start();
        loadItems();
    }

    private void loadItems() {
        addDisposable(flickerDataSource.searchFlickerPhotos("cat", currentPage)
                .subscribeOn(appSchedulerProvider.computation())
                .observeOn(appSchedulerProvider.ui())
                .doOnSubscribe(s -> publishRequestState(RequestState.LOADING))
                .doOnError(t -> publishRequestState(RequestState.ERROR))
                .doOnComplete(() -> publishRequestState(RequestState.COMPLETE))
                .subscribe(flickerPhotosConsumer, flickerError));
    }

    private Consumer<FlickerPhotos> flickerPhotosConsumer = flickerPhotos -> {
        if (flickerPhotos != null && flickerPhotos.getStat().equalsIgnoreCase("ok")) {
            if (flickerPhotos.getPhotos() != null) {
                setCurrentPage(flickerPhotos.getPhotos().getPage());
                setTotalPage(flickerPhotos.getPhotos().getPages());
                if (flickerPhotos.getPhotos().getPhoto() != null && !flickerPhotos.getPhotos().getPhoto().isEmpty()) {
                    view.showImages(flickerPhotos.getPhotos().getPhoto());
                } else {
                    view.showEmptyView();
                }
            }
        }
    };

    private Consumer<Throwable> flickerError = throwable -> publishRequestState(RequestState.ERROR);

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
                    view.hideEmptyView();
                    view.showProgress();
                    break;
                case COMPLETE:
                    view.hideProgress();
                    break;
                case ERROR:
                    view.showEmptyView();
                    view.hideProgress();
                    break;
            }
        }, Throwable::printStackTrace);
    }

    private void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    private void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public void loadNextPage() {
        if (currentPage <= totalPage) {
            currentPage++;
            addDisposable(flickerDataSource.searchFlickerPhotos("cat", currentPage)
                    .subscribeOn(appSchedulerProvider.computation())
                    .observeOn(appSchedulerProvider.ui())
                    .doOnSubscribe(s -> publishRequestState(RequestState.LOADING))
                    .doOnError(t -> publishRequestState(RequestState.ERROR))
                    .doOnComplete(() -> publishRequestState(RequestState.COMPLETE))
                    .subscribe(flickerPhotosConsumer));
        }
    }

    @Override
    public void onRetry() {
        loadItems();
    }
}
