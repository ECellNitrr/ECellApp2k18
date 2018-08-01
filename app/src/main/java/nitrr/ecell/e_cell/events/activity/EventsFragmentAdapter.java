package nitrr.ecell.e_cell.events.activity;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.activities.HomeActivity;
import nitrr.ecell.e_cell.events.Model.EventsData;

public class EventsFragmentAdapter extends RecyclerView.Adapter<EventsFragmentAdapter.ViewHolder>{

    private ArrayList<EventsData> eventsDataList;
    private Context context;
    private LinearLayout linearLayout;

    public EventsFragmentAdapter(ArrayList<EventsData> eventsDataList, Context context) {
      this.eventsDataList=eventsDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemlayout_events,viewGroup,false);
        return new ViewHolder(view);
    }

  //  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
        final EventsData eventsData = eventsDataList.get(i);
        viewHolder.textViewname.setText(eventsData.getName_response());
        Glide.with(context)
                .load(eventsData.getIcon_response()).into(viewHolder.imageViewlogo);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsDetailFragment eventsDetailFragment=new EventsDetailFragment();
                eventsDetailFragment.setData(eventsData);
                ((HomeActivity)context).addFragment(eventsDetailFragment,"Event Fragment Details");
            }
        });
    }

    @Override
    public int getItemCount() {

        Log.e("size====", eventsDataList.size()+"");
        return eventsDataList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewname;
        public ImageView imageViewlogo;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewname=(TextView) itemView.findViewById(R.id.name_id);
            imageViewlogo=(ImageView) itemView.findViewById(R.id.logo_id);
            linearLayout=(LinearLayout) itemView.findViewById(R.id.linearlayouteventitem);

        }
    }


}
