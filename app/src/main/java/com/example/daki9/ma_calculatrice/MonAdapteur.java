package com.example.daki9.ma_calculatrice;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class MonAdapteur extends ArrayAdapter<String> {
    List<String> l;
    Context c;
    int r;
    public MonAdapteur(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.l=objects;
        this.c=context;
        this.r=resource;

    }


    @Override
    public View getView(int position,View convertView, ViewGroup parent) {

        LayoutInflater inflater= ((Activity)c).getLayoutInflater();
        View v=inflater.inflate(r,parent,false);
        return v;
    }
}
