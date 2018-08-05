package nitrr.ecell.e_cell.bquiz;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.utils.DialogFactory;

public class BquizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bquiz);

        showBQuizRulesDialog();
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
}
