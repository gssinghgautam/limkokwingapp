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

    private final ImageContract.View view;

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
        addDisposable(
                flickerDataSource.searchFlickerPhotos("cat", currentPage)
                        .subscribeOn(appSchedulerProvider.computation())
                        .observeOn(appSchedulerProvider.ui())
                        .doOnSubscribe(s -> publishRequestState(RequestState.LOADING))
                        .doOnError(t -> publishRequestState(RequestState.ERROR))
                        .doOnComplete(() -> publishRequestState(RequestState.COMPLETE))
                        .subscribe(flickerPhotosConsumer)
        );
    }


    private Consumer<FlickerPhotos> flickerPhotosConsumer = new Consumer<FlickerPhotos>() {
        @Override
        public void accept(FlickerPhotos flickerPhotos) throws Exception {
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

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public void loadNextPage() {
        if (currentPage <= totalPage) {
            currentPage++;
            addDisposable(
                    flickerDataSource.searchFlickerPhotos("cat", currentPage)
                            .subscribeOn(appSchedulerProvider.computation())
                            .observeOn(appSchedulerProvider.ui())
                            .doOnSubscribe(s -> publishRequestState(RequestState.LOADING))
                            .doOnError(t -> publishRequestState(RequestState.ERROR))
                            .doOnComplete(() -> publishRequestState(RequestState.COMPLETE))
                            .subscribe(flickerPhotosConsumer)
            );
        }
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
