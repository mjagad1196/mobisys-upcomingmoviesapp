package com.mihirjagad.upcomingmoviesapp.Util;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mihirjagad.upcomingmoviesapp.Model.ImagesDataModel;
import com.mihirjagad.upcomingmoviesapp.R;

import java.util.ArrayList;

/**
 * Created by mihirjagad on 10/7/17.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private ImageLoader imageLoader;
    private Context mContext;
    private ArrayList<ImagesDataModel> mImageArrayList;

    public ViewPagerAdapter(Context mContext,ArrayList<ImagesDataModel> imageArrayList) {
        super();

        this.mContext = mContext;
        this.mImageArrayList = imageArrayList;
    }

    @Override
    public int getCount() {
        if(mImageArrayList.size()>=5){
            return 5;
        }else{
            return  mImageArrayList.size();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_images_item, container, false);
        NetworkImageView imageView = (NetworkImageView) itemView.findViewById(R.id.img_movie_poster);

        ImagesDataModel imagesDataModel = mImageArrayList.get(position);

        imageLoader = CustomImageRequest.getInstance(mContext).getImageLoader();
        imageLoader.get(imagesDataModel.getFilepath(), ImageLoader.getImageListener(imageView,0,0));

        imageView.setImageUrl(imagesDataModel.getFilepath(), imageLoader);

        container.addView(itemView);
        return itemView;
    }
}
