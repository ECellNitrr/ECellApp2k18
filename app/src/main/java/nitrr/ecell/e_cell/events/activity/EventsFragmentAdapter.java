package nitrr.ecell.e_cell.events.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.activities.HomeActivity;
import nitrr.ecell.e_cell.events.Model.EventsData;

public class EventsFragmentAdapter extends RecyclerView.Adapter<EventsFragmentAdapter.ViewHolder> {

    private ArrayList<EventsData> eventsDataList;
    private Context context;

    public EventsFragmentAdapter(ArrayList<EventsData> eventsDataList, Context context) {
        this.eventsDataList = eventsDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemlayout_events, viewGroup, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final EventsData eventsData = eventsDataList.get(i);
        viewHolder.textViewname.setText(eventsData.getName_response());
        Glide.with(context)
                .load(eventsData.getIcon_response())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        viewHolder.loadingIndicatorView.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        viewHolder.loadingIndicatorView.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(viewHolder.imageViewlogo);
                viewHolder.linearLayoutEventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = (FragmentActivity) (context);
                FragmentManager fm = activity.getSupportFragmentManager();
                EventsDetailFragment eventsDetailFragment = EventsDetailFragment.newInstance();
                eventsDetailFragment.setData(eventsData);
                eventsDetailFragment.show(fm, "event details fragment");
            }
        });
    }

    @Override
    public int getItemCount() {

        Log.e("size====", eventsDataList.size() + "");
        return eventsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewname;
        public ImageView imageViewlogo;
        LinearLayout linearLayoutEventDetails;
        AVLoadingIndicatorView loadingIndicatorView;


        public ViewHolder(View itemView) {
            super(itemView);
            linearLayoutEventDetails = (LinearLayout) itemView.findViewById(R.id.event_details_linear_layout);
            textViewname = (TextView) itemView.findViewById(R.id.name_id);
            imageViewlogo = (ImageView) itemView.findViewById(R.id.logo_id);
            loadingIndicatorView = (AVLoadingIndicatorView) itemView.findViewById(R.id.indicator_progress_bar);

        }
    }


}
