package com.android.limkokwingapp.ui.images;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.limkokwingapp.R;
import com.android.limkokwingapp.data.remote.model.Photo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gautam on 27/03/18.
 */

public class ImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.gallery_item_imageView)
    ImageView galleryItemImageView;

    public ImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Photo photo, Picasso picasso) {
        picasso.load(photo.getImageUrl())
                .fit()
                .into(galleryItemImageView);
    }
}
