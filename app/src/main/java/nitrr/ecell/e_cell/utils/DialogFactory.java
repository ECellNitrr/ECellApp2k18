package nitrr.ecell.e_cell.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

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
}
