package nitrr.ecell.e_cell.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.activities.BquizActivity;
import nitrr.ecell.e_cell.model.bquiz.BQuizStatusResponse;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.AppConstants;
import nitrr.ecell.e_cell.utils.DialogFactory;
import nitrr.ecell.e_cell.utils.ProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeBQuizFragment extends Fragment {

    private ProgressDialog progressDialog;

    private DialogInterface.OnClickListener clickListenerNegativeStatus = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };
    private DialogInterface.OnClickListener clickListenerPositiveRules = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            Intent intent = new Intent(getActivity(), BquizActivity.class);
            startActivity(intent);
        }
    };
    private DialogInterface.OnClickListener clickListenerNegativeRules = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };
    private DialogInterface.OnClickListener clickListenerPositiveStatus = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            progressDialog.showDialog("Checking, if Bquiz is active now...", getContext());
            apiCallForBquizStatus();
        }
    };

    public static HomeBQuizFragment newInstance(String text) {
        HomeBQuizFragment f = new HomeBQuizFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bquiz_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog();
        Typeface bebasNeue = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BebasNeue.ttf");
        ImageView imageView = getView().findViewById(R.id.bqImageView);
        Glide.with(getActivity())
                .load(AppConstants.IMAGE_LOCATIONS[2])
                .into(imageView);


        TextView textView = getView().findViewById(R.id.bquiz_custom_view_text);
        textView.setText(AppConstants.HOME_TITLES[2]);
        textView.setTypeface(bebasNeue);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.showDialog("Checking, if Bquiz is active now...", getContext());
                apiCallForBquizStatus();
            }
        });

    }

    private void apiCallForBquizStatus() {
        ApiServices apiServices = AppClient.getInstance().createService(ApiServices.class);
        Call<BQuizStatusResponse> call = apiServices.getBquizStatus();
        call.enqueue(new Callback<BQuizStatusResponse>() {
            @Override
            public void onResponse(Call<BQuizStatusResponse> call, Response<BQuizStatusResponse> response) {
                progressDialog.hideDialog();
                if (response.isSuccessful()) {
                    BQuizStatusResponse jsonResponse = response.body();
                    if (jsonResponse != null) {
                        if (jsonResponse.isActive()) {
                            DialogFactory.showDialog(DialogFactory.BQUIZ_RULES, getContext(), clickListenerPositiveRules, clickListenerNegativeRules, false, getString(R.string.bquiz_rules_title), getString(R.string.bquiz_rules_detail), getString(R.string.bquiz_rules_ok_btn), getString(R.string.bquiz_dialog_cancel_btn));
                        } else {
                            DialogFactory.showDialog(DialogFactory.BQUIZ_NOT_ACTIVE_ID, getContext(), clickListenerPositiveStatus, clickListenerNegativeStatus, true, getString(R.string.bquiz_dialog_title), getString(R.string.bquiz_dialog_msg), getString(R.string.bquiz_dialog_retry_btn), getString(R.string.bquiz_dialog_cancel_btn));
                        }
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.something_went_wrong_msg), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BQuizStatusResponse> call, Throwable t) {
                progressDialog.hideDialog();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
