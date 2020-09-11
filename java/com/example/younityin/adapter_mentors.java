package com.example.younityin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class adapter_mentors extends PagerAdapter {
    private Context mContext;
    private int[]mimageid =new int[]{R.drawable.mentor5,R.drawable.mentor1,R.drawable.mentor2,R.drawable.mentor3,R.drawable.mentor4,R.drawable.mentor6,R.drawable.mentor7};
    adapter_mentors(Context context){
        mContext=context;
    }
    @Override
    public int getCount() {
        return mimageid.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView=new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(mimageid[position]);
        container.addView(imageView,0);
        return imageView;
    }
}
