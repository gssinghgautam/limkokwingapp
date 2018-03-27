package com.android.limkokwingapp.ui.images;

import com.android.limkokwingapp.BaseView;
import com.android.limkokwingapp.data.remote.model.Photo;

import java.util.List;

/**
 * Created by gautam on 27/03/18.
 */

public interface ImageContract {

    interface View extends BaseView {

        void showEmptyView();

        void showImages(List<Photo> photoList);
    }

    interface Presenter {
        void loadNextPage();
    }

}

