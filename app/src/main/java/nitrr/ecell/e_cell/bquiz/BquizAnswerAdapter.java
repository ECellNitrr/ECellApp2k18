package nitrr.ecell.e_cell.bquiz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.bquiz.model.QuestionDetailsModel;

public class BquizAnswerAdapter extends RecyclerView.Adapter<BquizAnswerAdapter.MyViewHolder>{

    private Context context;
    private LayoutInflater layoutInflater;
    private List<QuestionDetailsModel> questionDetailsModels = new ArrayList<>();

    public BquizAnswerAdapter(Context context, List<QuestionDetailsModel> questionDetailsModels) {
        this.context = context;
        this.questionDetailsModels = questionDetailsModels;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_bquiz_answers, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(null != questionDetailsModels){
            final QuestionDetailsModel data = questionDetailsModels.get(position);
            if(null != data){
                holder.tvAnswerNumber.setText(position + "");
                if(null != data.getValue()){
                    holder.tvAnswerText.setText(data.getValue());
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return questionDetailsModels.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvAnswerNumber, tvAnswerText;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvAnswerText = (TextView) itemView.findViewById(R.id.tvAnswerText);
            tvAnswerNumber = (TextView) itemView.findViewById(R.id.tvAnswerNumber);
        }
    }

}
