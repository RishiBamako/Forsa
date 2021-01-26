package com.bartering.forsa.NavigationDrawer;

/**
 * Created by Ravi on 29/07/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bartering.forsa.R;

import java.util.List;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data;
    List count;
    private LayoutInflater inflater;
    private Context context;


    RelativeLayout relativeLayout;
    public static int selected_item = 0;

    RelativeLayout layout;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        count.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.drawelistitemlalyout);

        layout = new RelativeLayout(context);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());

        Glide.with(context)
                .load(current.getBitmap_Icon())
                .placeholder(R.drawable.image)
                .into(holder.drawerImageViewId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView drawerImageViewId;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            drawerImageViewId = (ImageView) itemView.findViewById(R.id.drawerImageViewId);

        }
    }
}
