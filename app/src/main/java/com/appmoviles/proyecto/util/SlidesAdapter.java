package com.appmoviles.proyecto.util;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appmoviles.proyecto.R;

public class SlidesAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public int[] lista_imagenes = {
            R.drawable.imagen1,
            R.drawable.imagen2,
            R.drawable.imagen3,
            R.drawable.imagen4,
            R.drawable.imagen5,
            R.drawable.imagen6,
            R.drawable.imagen7
    };

    public int[] lista_background = {
            Color.rgb(55, 55, 55),
            Color.rgb(55, 55, 55),
            Color.rgb(55, 55, 55),
            Color.rgb(55, 55, 55),
            Color.rgb(55, 55, 55),
            Color.rgb(55, 55, 55),
            Color.rgb(55, 55, 55)
    };


    public SlidesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return lista_imagenes.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (LinearLayout) o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_images, container, false);
        ImageView imageView = view.findViewById(R.id.imagen_vista_registro);
        imageView.setImageResource(lista_imagenes[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
