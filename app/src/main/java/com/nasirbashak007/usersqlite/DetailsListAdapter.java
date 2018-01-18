package com.nasirbashak007.usersqlite;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Nasir Basha K on 17-01-2018.
 */

public class DetailsListAdapter extends ArrayAdapter<PersonDetails> {

    Activity activity;
    int resource;
    List<PersonDetails> list;


    public DetailsListAdapter(Activity activity, int resource, List<PersonDetails> list) {
        super(activity, resource, list);
        this.activity = activity;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();//go throught this later

        View view = layoutInflater.inflate(resource, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.getImages);


        TextView name = (TextView) view.findViewById(R.id.getName);
        TextView email = (TextView) view.findViewById(R.id.getEmail);

        name.setText(list.get(position).getName());
        email.setText(list.get(position).getEmail());

        byte[] image = list.get(position).getImageUri();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        imageView.setImageBitmap(bitmap);

        Glide.with(activity).load(list.get(position).getImageUri()).into(imageView);


        return view;
    }
}
