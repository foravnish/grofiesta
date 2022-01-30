package com.app.grofiesta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.app.grofiesta.R;

import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {
    Context context;
    int images[];
    List<String> txt=new ArrayList<>();
    List<String> txt2=new ArrayList<>();
    LayoutInflater layoutInflater;

    public CustomPagerAdapter(Context context, int[] images, ArrayList<String> txt, ArrayList<String> txt2) {
        this.context = context;
        this.images = images;
        this.txt=txt;
        this.txt2=txt2;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        TextView textView=(TextView)itemView.findViewById(R.id.txtView);
        textView.setText(txt.get(position));

        TextView textView2=(TextView)itemView.findViewById(R.id.txtView2);
        textView2.setText(txt2.get(position));

        imageView.setImageResource(images[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }



}
