package com.pictureviewer.pictureviewer.instagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pictureviewer.pictureviewer.R;

import java.util.ArrayList;

//
public class Adapter extends BaseAdapter {

    Context context;
    ArrayList<Items> items;
    LayoutInflater inflater;

    public Adapter(Context context, ArrayList<Items> items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        items.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.activity_instagram_viewer, null);
        }

        Items items1 = items.get(i);

        TextView txtText = (TextView) view.findViewById(R.id.txtText);
        txtText.setText(items1.text);

        TextView txtFullName = (TextView) view.findViewById(R.id.txtFullName);
        txtFullName.setText(items1.full_name);

        ImageView image = (ImageView) view.findViewById(R.id.imgThumbnail);
        ImageLoader.getInstance().displayImage(items1.image, image);

        return view;
    }
}
