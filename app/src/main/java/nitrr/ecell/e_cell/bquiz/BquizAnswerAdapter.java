package nitrr.ecell.e_cell.bquiz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.bquiz.model.Answer;
import nitrr.ecell.e_cell.bquiz.model.QuestionDetailsModel;
import nitrr.ecell.e_cell.utils.SelectAnswerInterface;

public class BquizAnswerAdapter extends RecyclerView.Adapter<BquizAnswerAdapter.MyViewHolder>{

    private Context context;
    private LayoutInflater layoutInflater;
    private List<QuestionDetailsModel> questionDetailsModels = new ArrayList<>();
    private SelectAnswerInterface callback;
    private int lastSelected = -1;
//    private int lastSecondSelected = -2;

    public BquizAnswerAdapter(Context context, List<QuestionDetailsModel> questionDetailsModels, SelectAnswerInterface callback) {
        this.context = context;
        this.questionDetailsModels = questionDetailsModels;
        layoutInflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_bquiz_answers, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        if(null != questionDetailsModels){
            final QuestionDetailsModel data = questionDetailsModels.get(position);
            if(null != data){
                holder.tvAnswerNumber.setText(position + ".");

                if(null != data.getValue()){
                    holder.tvAnswerText.setText(data.getValue());
                }

                holder.cvAnswer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Todo : change color and select answer answer
                        callback.selectAnswer(data.getKey());
                        lastSelected = holder.getAdapterPosition();
                        notifyDataSetChanged();
                        Log.e("card clicked ====", "true");
                    }
                });

                if(position == lastSelected){
                    holder.cvAnswer.setCardBackgroundColor(context.getResources().getColor(R.color.bquizDarkBg));
                    holder.tvAnswerText.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    holder.tvAnswerNumber.setTextColor(context.getResources().getColor(R.color.colorWhite));
                }
                else{
                    holder.cvAnswer.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhite));
                    holder.tvAnswerText.setTextColor(context.getResources().getColor(R.color.colorText));
                    holder.tvAnswerNumber.setTextColor(context.getResources().getColor(R.color.colorText));
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
        CardView cvAnswer;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvAnswerText = itemView.findViewById(R.id.tvAnswerText);
            tvAnswerNumber = itemView.findViewById(R.id.tvAnswerNumber);
            cvAnswer = itemView.findViewById(R.id.cvAnswer);
        }
    }

}
