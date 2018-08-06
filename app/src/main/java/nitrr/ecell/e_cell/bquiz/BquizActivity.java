package nitrr.ecell.e_cell.bquiz;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;
import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.bquiz.model.BQuizQuestionResponse;
import nitrr.ecell.e_cell.bquiz.model.QuestionDetailsModel;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.DialogFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BquizActivity extends AppCompatActivity {

    private CardView cvQuestion, cvQuestionImage;
    private TextView tvQuestion, timer;
    private ImageView ivQuestion;
    private RecyclerView rvAnswers;
    private DonutProgress donutProgress;

    private BquizAnswerAdapter adapter;
    private List<QuestionDetailsModel> questionDetailsModels = new ArrayList<>();
    MyCountDownTimer myCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bquiz);
        initview();
        showBQuizRulesDialog();
        initAdapter();
    }

    private void initview(){
        cvQuestion = findViewById(R.id.cvQuestion);
        cvQuestionImage = findViewById(R.id.cvQuestionImage);

        tvQuestion = findViewById(R.id.tvQuestion);
        timer = findViewById(R.id.timer);

        ivQuestion = findViewById(R.id.ivQuestion);
        donutProgress = findViewById(R.id.donut_progress);
        rvAnswers = findViewById(R.id.rvAnswers);
        rvAnswers.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showBQuizRulesDialog(){
        DialogFactory.showDialog(DialogFactory.BQUIZ_RULES,this,clickListenerPositive,null,null,getString(R.string.bquiz_rules_title), getString(R.string.bquiz_rules_detail), getString(R.string.bquiz_rules_ok_btn));
    }

    private DialogInterface.OnClickListener clickListenerPositive = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

    private void initAdapter(){
        adapter = new BquizAnswerAdapter(this, questionDetailsModels);
        rvAnswers.setAdapter(adapter);
    }

    private void apiCall(){
        ApiServices api = AppClient.getInstance().createService(ApiServices.class);
        Call<BQuizQuestionResponse> call = api.getQuestion();
        call.enqueue(new Callback<BQuizQuestionResponse>() {
            @Override
            public void onResponse(Call<BQuizQuestionResponse> call, Response<BQuizQuestionResponse> response) {
                if(response.isSuccessful()){
                    BQuizQuestionResponse question = response.body();
                    if(null != question){
                        setData(question);
                        questionDetailsModels.addAll(question.getOptions());
                    }
                }
            }

            @Override
            public void onFailure(Call<BQuizQuestionResponse> call, Throwable t) {

            }
        });
    }

    private void setData(BQuizQuestionResponse question){
        if(null != question.getText()){
            tvQuestion.setText(question.getText());
        }
        if(question.isImageIncluded()){
            Glide.with(this)
                    .load(question.getImageUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivQuestion);
        }
        else{
            //todo : set placeholder image for bquiz
            adapter.notifyDataSetChanged();
        }
    }

    private void startTimer(int time){

    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished/1000);

//            progressBar.setProgress(progressBar.getMax()-progress);
        }

        @Override
        public void onFinish() {
            finish();
        }
    }
}
