package nitrr.ecell.e_cell.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class DialogFactory {

    private DialogFactory() {

    }

    public static final int BQUIZ_NOT_ACTIVE_ID = 1;

    public static void showDialog(int id, final Context context, DialogInterface.OnClickListener clickListenerPositive, DialogInterface.OnClickListener clickListenerNegative, DialogInterface.OnCancelListener cancelListener, Object... d) {
        switch (id) {
            case BQUIZ_NOT_ACTIVE_ID:
                getDialog(context, clickListenerPositive, clickListenerNegative, cancelListener, d);
                break;
            default:
                break;
        }
    }

    private static void getDialog(Context context, DialogInterface.OnClickListener clickListenerPositive, DialogInterface.OnClickListener clickListenerNegative,  DialogInterface.OnCancelListener cancelListener, Object... d) {
        final AlertDialog alertDialog = new AlertDialog.Builder(
                context).create();
        alertDialog.setTitle(d[0].toString());
        alertDialog.setMessage(d[1].toString());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, d[2].toString(), clickListenerPositive);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, d[3].toString(), clickListenerNegative);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }
}
