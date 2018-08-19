package nitrr.ecell.e_cell.activities;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
import nitrr.ecell.e_cell.adapters.bquiz.BquizAnswerAdapter;
import nitrr.ecell.e_cell.model.bquiz.Answer;
import nitrr.ecell.e_cell.model.bquiz.BQuizQuestionResponse;
import nitrr.ecell.e_cell.model.bquiz.QuestionDetailsModel;
import nitrr.ecell.e_cell.model.auth.GenericResponse;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.DialogFactory;
import nitrr.ecell.e_cell.utils.ProgressDialog;
import nitrr.ecell.e_cell.utils.SelectAnswerInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BquizActivity extends AppCompatActivity implements SelectAnswerInterface, View.OnClickListener{

    private CardView cvQuestion, cvQuestionImage;
    private TextView tvQuestion, timer;
    private ImageView ivQuestion;
    private RecyclerView rvAnswers;
    private DonutProgress donutProgress;
    private Button btnSubmitAnswer;
    private Boolean retryQuestion = false;

    private BquizAnswerAdapter adapter;
    private List<QuestionDetailsModel> questionDetailsModels = new ArrayList<>();
    MyCountDownTimer myCountDownTimer;
    private Answer answer;
    private ProgressDialog progressDialog;
    private int countbackPress = 0;
    private Bitmap bm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bquiz);
        initview();
        initAdapter();
        progressDialog.showDialog("Charge your brain. Question is coming", this);
        apiCall();
    }

    private DialogInterface.OnClickListener clickListenerPositive = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };
    private DialogInterface.OnClickListener clickListenerNegative = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

    private void initview(){
        cvQuestion = findViewById(R.id.cvQuestion);
        cvQuestionImage = findViewById(R.id.cvQuestionImage);

        tvQuestion = findViewById(R.id.tvQuestion);
        timer = findViewById(R.id.timer);

        ivQuestion = findViewById(R.id.ivQuestion);
        donutProgress = findViewById(R.id.donut_progress);
        btnSubmitAnswer = findViewById(R.id.btnSubmitAnswer);
        rvAnswers = findViewById(R.id.rvAnswers);
        rvAnswers.setLayoutManager(new LinearLayoutManager(this));
        answer = new Answer();
        progressDialog = new ProgressDialog();
        btnSubmitAnswer.setOnClickListener(this);
        ivQuestion.setOnClickListener(this);
        makeLayoutsVisible();
    }

    private void initAdapter(){
        adapter = new BquizAnswerAdapter(this, questionDetailsModels, this);
        rvAnswers.setAdapter(adapter);
    }

    private void apiCall(){
        ApiServices api = AppClient.getInstance().createServiceWithAuth(ApiServices.class, this);
        Call<BQuizQuestionResponse> call = api.getQuestion(retryQuestion);
        call.enqueue(new Callback<BQuizQuestionResponse>() {
            @Override
            public void onResponse(Call<BQuizQuestionResponse> call, Response<BQuizQuestionResponse> response) {
                if(response.isSuccessful()){
                    BQuizQuestionResponse question = response.body();
                    if(null != question){
                        retryQuestion = false;
                        makeLayoutsVisible();
                        setData(question);
                        questionDetailsModels.addAll(question.getOptions());
                        answer.setOptionId(null);
                        answer.setQuestionId(question.getId());
                    }
                }
            }

            @Override
            public void onFailure(Call<BQuizQuestionResponse> call, Throwable t) {
                retryQuestion = true;
                progressDialog.showDialog("Some Error occured.Please wait while we get your question.",BquizActivity.this);
                apiCall();
            }
        });
        progressDialog.hideDialog();
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
                    retryQuestion = true;
                    progressDialog.showDialog("Some Error occured.Please wait while we get your question.",BquizActivity.this);
                    apiCall();
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    //todo:Save image so that zooming doesnt load image again
                    BitmapDrawable bmDrawable = (BitmapDrawable) resource;
                    bm = bmDrawable.getBitmap();
                    adapter.notifyDataSetChanged();
                    if(0 != question.getTime()){
                        donutProgress.setMax(question.getTime());
                        myCountDownTimer = new MyCountDownTimer(question.getTime()*1000, 1000);
                        myCountDownTimer.start();
                    }
                    else{
                        donutProgress.setMax(20);
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
            bm = BitmapFactory.decodeResource(getResources(), R.drawable.bquiz_logo);
            Glide.with(this).load(R.drawable.bquiz_logo).apply(RequestOptions.circleCropTransform()).into(ivQuestion);
            adapter.notifyDataSetChanged();
            if(0 != question.getTime()){
                donutProgress.setMax(question.getTime());
                myCountDownTimer = new MyCountDownTimer(question.getTime()*1000, 1000);
                myCountDownTimer.start();
            }
            else{
                donutProgress.setMax(20);
                myCountDownTimer = new MyCountDownTimer(20000, 1000);
                myCountDownTimer.start();
            }
        }
    }

    private void submitAnswer(Answer answer){
        ApiServices api = AppClient.getInstance().createServiceWithAuth(ApiServices.class, this);
        Call<GenericResponse> call = api.submitAnswer(answer);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if(response.isSuccessful()){
                    GenericResponse responseBody = response.body();
                    if(null != responseBody){
                        if (responseBody.getSuccess()){
                            makeLayoutsInvisible();
                            tvQuestion.setText("Your Answer has been successfully submitted, kindly wait for next question.");
                            myCountDownTimer.cancel();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(BquizActivity.this, "Please Submit again", Toast.LENGTH_LONG).show();
            }
        });
        progressDialog.hideDialog();
    }

    private void makeLayoutsInvisible(){
        rvAnswers.setVisibility(View.GONE);
        timer.setVisibility(View.GONE);
        donutProgress.setVisibility(View.GONE);
        btnSubmitAnswer.setVisibility(View.GONE);

    }


    private void makeLayoutsVisible(){
        rvAnswers.setVisibility(View.VISIBLE);
        timer.setVisibility(View.VISIBLE);
        donutProgress.setVisibility(View.VISIBLE);
        btnSubmitAnswer.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onStop() {
        Toast.makeText(BquizActivity.this, getString(R.string.bquiz_penalty_text), Toast.LENGTH_LONG).show();
        if(myCountDownTimer.getTimeLeft()>=5000){
            long timeLeft = myCountDownTimer.getTimeLeft()-5000;
            myCountDownTimer.cancel();
            myCountDownTimer = new MyCountDownTimer(timeLeft, 1000);
            myCountDownTimer.start();
        }
        else{
            submitAnswer(answer);
        }
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        DialogFactory.showDialog(DialogFactory.BQUIZ_RULES, BquizActivity.this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BquizActivity.super.onBackPressed();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }, true, getString(R.string.bquiz_timeout_title), getString(R.string.bquiz_timeout_text), getString(R.string.bquiz_rules_ok_btn), getString(R.string.bquiz_dialog_cancel_btn));



    }

    @Override
    public void selectAnswer(Integer optionId) {
        answer.setOptionId(optionId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmitAnswer:
                progressDialog.showDialog("Submitting your answer. Please wait.",BquizActivity.this);
                submitAnswer(answer);
                break;
            case R.id.ivQuestion:
                openPopupImage();
                break;
        }
    }

    private void openPopupImage() {

        AppCompatDialog avdialog = new AppCompatDialog(this);
        avdialog.setContentView(R.layout.image_popup_dialog);
        avdialog.setCancelable(true);
        DialogFactory.setDynamicDialogHeightWidth(this, avdialog, 1.0f, 0.5f, true);
        ImageView ivImagePopup = avdialog.findViewById(R.id.ivQuestionImage);
        if (ivImagePopup != null) {
            //todo:Place url or bitmat here
            Glide.with(this).load(bm).into(ivImagePopup);
        }
        avdialog.show();

    }


    public class MyCountDownTimer extends CountDownTimer {

        long timeLeft=0;

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public long getTimeLeft(){
            return timeLeft;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timeLeft = millisUntilFinished;
            int progress = (int) (millisUntilFinished/1000);
            if(Integer.toString(progress).length()>1){
                timer.setText("00:"+Integer.toString(progress));
            }
            else{
                timer.setText("00:0"+Integer.toString(progress));
            }
            donutProgress.setProgress(donutProgress.getMax()-progress);
        }

        @Override
        public void onFinish() {

            DialogFactory.showDialog(DialogFactory.BQUIZ_RULES, BquizActivity.this, clickListenerPositive, clickListenerNegative, true, getString(R.string.bquiz_timeout_title), getString(R.string.bquiz_timeout_text), getString(R.string.bquiz_rules_ok_btn));
            submitAnswer(answer);
        }
    }
}
