package nitrr.ecell.e_cell.bquiz;

import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;
import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.bquiz.model.Answer;
import nitrr.ecell.e_cell.bquiz.model.BQuizQuestionResponse;
import nitrr.ecell.e_cell.bquiz.model.QuestionDetailsModel;
import nitrr.ecell.e_cell.model.GenericResponse;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.SelectAnswerInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BquizActivity extends AppCompatActivity implements SelectAnswerInterface{

    private CardView cvQuestion, cvQuestionImage;
    private TextView tvQuestion, timer;
    private ImageView ivQuestion;
    private RecyclerView rvAnswers;
    private DonutProgress donutProgress;

    private BquizAnswerAdapter adapter;
    private List<QuestionDetailsModel> questionDetailsModels = new ArrayList<>();
    MyCountDownTimer myCountDownTimer;
    private Answer answer;
    private int disqualificationCoounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bquiz);
        initview();
        initAdapter();
        apiCall();
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
        answer = new Answer();
    }

    private void initAdapter(){
        adapter = new BquizAnswerAdapter(this, questionDetailsModels, this);
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
                        answer.setQuestionId(question.getId());
                    }
                }
            }

            @Override
            public void onFailure(Call<BQuizQuestionResponse> call, Throwable t) {

            }
        });
    }

    private void setData(final BQuizQuestionResponse question){
        if(null != question.getText()){
            tvQuestion.setText(question.getText());
        }
        if(question.isImageIncluded()){
            Glide.with(this)
                    .load(question.getImageUrl()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    adapter.notifyDataSetChanged();
                    if(0 != question.getTime()){
                        myCountDownTimer = new MyCountDownTimer(question.getTime()*1000, 1000);
                        myCountDownTimer.start();
                    }
                    else{
                        myCountDownTimer = new MyCountDownTimer(20000, 1000);
                        myCountDownTimer.start();
                    }
                    return false;
                }

            })
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivQuestion);
        }
        else{
            Glide.with(this).load(R.drawable.bquiz_logo).apply(RequestOptions.circleCropTransform()).into(ivQuestion);
            adapter.notifyDataSetChanged();
            if(0 != question.getTime()){
                myCountDownTimer = new MyCountDownTimer(question.getTime()*1000, 1000);
                myCountDownTimer.start();
            }
            else{
                myCountDownTimer = new MyCountDownTimer(20000, 1000);
                myCountDownTimer.start();
            }
        }
    }

    private void submitAnswer(Answer answer){
        //todo: Add a progress dialog
        ApiServices api = AppClient.getInstance().createService(ApiServices.class);
        Call<GenericResponse> call = api.submitAnswer(answer);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if(response.isSuccessful()){
                    GenericResponse responseBody = response.body();
                    if(null != responseBody){
                        if (responseBody.getSuccess()){
                            //todo:Answer submitted
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(BquizActivity.this, "Please Submit again", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void selectAnswer(Integer optionId) {
        answer.setOptionId(optionId);
    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished/1000);
            donutProgress.setProgress(donutProgress.getMax()-progress);
        }

        @Override
        public void onFinish() {

//            finish();
            //todo: api call for submitting selected answer or null
        }
    }
}
