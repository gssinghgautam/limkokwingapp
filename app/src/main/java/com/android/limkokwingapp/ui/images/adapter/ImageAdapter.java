package com.android.limkokwingapp.ui.images.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.limkokwingapp.R;
import com.android.limkokwingapp.data.remote.model.Photo;
import com.android.limkokwingapp.utility.ImageGalleryUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gautam on 27/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private final List<Photo> photoList;

    private final Picasso picasso;

    private int mGridItemWidth;

    private int mGridItemHeight;

    private OnPhotoClickListener onPhotoClickListener;

    public ImageAdapter(Picasso picasso) {
        this.picasso = picasso;
        photoList = new ArrayList<>();
    }

    public void setData(List<Photo> photoList) {
        this.photoList.addAll(photoList);
        notifyDataSetChanged();
    }

    public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_view, parent, false);
        view.setLayoutParams(getGridItemLayoutParams(view));
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Photo photo = photoList.get(position);
        holder.bind(photo, picasso);
        holder.itemView.setOnClickListener(view -> {
            if (onPhotoClickListener != null) {
                onPhotoClickListener.onPhotoClick(photo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return !photoList.isEmpty() ? photoList.size() : 0;
    }

    public interface OnPhotoClickListener {

        void onPhotoClick(Photo photo);
    }

    private ViewGroup.LayoutParams getGridItemLayoutParams(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int screenWidth = ImageGalleryUtils.getScreenWidth(view.getContext());
        int numOfColumns=2;
        mGridItemWidth = screenWidth / numOfColumns;
        mGridItemHeight = screenWidth / numOfColumns;

        layoutParams.width = mGridItemWidth;
        layoutParams.height = mGridItemHeight;

        return layoutParams;
    }
}
