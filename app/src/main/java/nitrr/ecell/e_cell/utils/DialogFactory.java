package nitrr.ecell.e_cell.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;

public class DialogFactory {

    private DialogFactory() {

    }

    public static final int BQUIZ_NOT_ACTIVE_ID = 1;
    public static final int BQUIZ_RULES = 2;
    public static final int CONNECTION_PROBLEM_DIALOG = 3;

    public static void showDialog(int id, final Context context, DialogInterface.OnClickListener clickListenerPositive, DialogInterface.OnClickListener clickListenerNegative, boolean isCancellable, Object... d) {
        switch (id) {
            case BQUIZ_NOT_ACTIVE_ID:
                getDialog(context, clickListenerPositive, clickListenerNegative, isCancellable, d);
                break;
            case BQUIZ_RULES:
                getDialog(context, clickListenerPositive, clickListenerNegative, isCancellable, d);
                break;
            case CONNECTION_PROBLEM_DIALOG:
                getDialog(context, clickListenerPositive, clickListenerNegative, isCancellable, d);
                break;
            default:
                break;
        }
    }

    private static void getDialog(Context context, DialogInterface.OnClickListener clickListenerPositive, DialogInterface.OnClickListener clickListenerNegative,  boolean isCancellable, Object... d) {
        final AlertDialog alertDialog = new AlertDialog.Builder(
                context).create();
        alertDialog.setTitle(d[0].toString());
        alertDialog.setMessage(d[1].toString());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, d[2].toString(), clickListenerPositive);
        if (d.length > 3){
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, d[3].toString(), clickListenerNegative);
        }
        if (isCancellable){
            alertDialog.setCancelable(true);
        }else {
            alertDialog.setCancelable(false);
        }
        alertDialog.show();
    }

    public static void setDynamicDialogHeightWidth(AppCompatActivity activity, AppCompatDialog dialog, float widthDimen, float heightDimen, boolean isHeightDynamic) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);

        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * widthDimen);
        layoutParams.width = dialogWindowWidth;

        int dialogWindowHeight = (int) (displayHeight * heightDimen);

        if (isHeightDynamic) {
            layoutParams.height = dialogWindowHeight;
        } else {
            layoutParams.height = (ViewGroup.LayoutParams.MATCH_PARENT);//dialogWindowHeight;
        }
        dialog.getWindow().setAttributes(layoutParams);
    }

}
