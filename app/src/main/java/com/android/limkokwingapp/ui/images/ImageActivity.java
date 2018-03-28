package com.android.limkokwingapp.ui.images;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.limkokwingapp.R;
import com.android.limkokwingapp.data.remote.model.Photo;
import com.android.limkokwingapp.ui.images.adapter.ImageAdapter;
import com.android.limkokwingapp.ui.images.presenter.ImagePresenter;
import com.android.limkokwingapp.ui.images.view.ImageContract;
import com.android.limkokwingapp.utility.ApiConstant;
import com.android.limkokwingapp.utility.EndlessRecyclerOnScrollListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class ImageActivity extends AppCompatActivity implements ImageContract.View, ImageAdapter.OnPhotoClickListener {

    @Inject
    ImagePresenter mImagePresenter;

    @Inject
    Picasso mPicasso;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.gallery_recycler_view)
    RecyclerView mGalleryRecyclerView;

    @BindView(R.id.progress)
    ProgressBar mProgressBar;

    @BindView(R.id.empty_view)
    LinearLayout mEmptyView;

    @BindView(R.id.btn_retry)
    Button btnRetry;

    private ImageAdapter mImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
        btnRetry.setOnClickListener(view -> {
            mImagePresenter.onRetry();
        });
    }

    private void initView() {
        mImageAdapter = new ImageAdapter(mPicasso);
        mGalleryRecyclerView.setAdapter(mImageAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mGalleryRecyclerView.setLayoutManager(layoutManager);
        mGalleryRecyclerView.setHasFixedSize(true);
        mGalleryRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                mImagePresenter.loadNextPage();
            }
        });
        mImageAdapter.setOnPhotoClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mImagePresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mImagePresenter.stop();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        if (mImageAdapter.getItemCount() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showImages(List<Photo> photoList) {
        mImageAdapter.setData(photoList);
    }

    @Override
    public void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void onPhotoClick(Photo photo) {
        Intent intent = new Intent(ImageActivity.this, FullImageActivity.class);
        intent.putExtra(ApiConstant.PHOTO_EXTRA, photo);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
