package nitrr.ecell.e_cell.events.activity;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.example.bhushan.ecell_login.R;

import java.util.ArrayList;

import nitrr.ecell.e_cell.events.Model.listitem;

public class eventsFragmentAdapter extends RecyclerView.Adapter<eventsFragmentAdapter.ViewHolder>{

    private ArrayList<listitem> eventsData;
    private Context context;
    private ImageLoader imageLoader;

    public eventsFragmentAdapter(ArrayList<listitem> eventsData, Context context) {
      this.eventsData=eventsData;
      //  imageLoader = new GlideImageLoader(context);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemlayout_events,viewGroup,false);
        return new ViewHolder(view);
    }

  //  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {

        viewHolder.textViewname.setText(eventsData.get(i).getName_response());
        Glide.with(context)
                .load(eventsData.get(i).getIcon_response()).into(viewHolder.imageViewlogo);
//        viewHolder.imageViewlogo.setImageURI(eventsData.get(i).getLogo());
    }

    @Override
    public int getItemCount() {
        return eventsData.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewname;
        public ImageView imageViewlogo;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewname=(TextView) itemView.findViewById(R.id.name_id);
            imageViewlogo=(ImageView) itemView.findViewById(R.id.logo_id);


        }
    }


}
