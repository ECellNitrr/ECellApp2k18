package nitrr.ecell.e_cell.utils;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.regex.Pattern;

import nitrr.ecell.e_cell.R;

public class CustomTextWatcher implements TextWatcher {
    private Activity activity;
    private EditText text;
    private TextInputLayout layout;
    private String field_name;

    public CustomTextWatcher(Activity activity, EditText text, TextInputLayout layout, String field_name) {
        this.activity = activity;
        this.text = text;
        this.layout = layout;
        this.field_name = field_name;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text_content = text.getText().toString().trim();

        if (text_content.equals("")) {
            layout.setError(activity.getResources().getString(R.string.error_common));
        } else {
            layout.setErrorEnabled(false);
        }

        if (field_name.equals(AppConstants.FIRST_NAME)) {
            Pattern pattern = Pattern.compile("[a-zA-Z]+");

            if (pattern.matcher(text_content).matches()) {
                layout.setErrorEnabled(false);
                text.requestFocus();
            } else {
                layout.setError(activity.getResources().getString(R.string.error_first_name));
            }

        }


        if (field_name.equals(AppConstants.LAST_NAME)) {
            Pattern pattern = Pattern.compile("[a-zA-Z]+");

            if (pattern.matcher(text_content).matches()) {
                layout.setErrorEnabled(false);
            } else {
                layout.setError(activity.getResources().getString(R.string.error_last_name));
                text.requestFocus();
            }
        }

        if (field_name.equals(AppConstants.EMAIL)) {
            if (!Patterns.EMAIL_ADDRESS.matcher(text_content).matches()) {
                layout.setError(activity.getResources().getString(R.string.error_email));
                text.requestFocus();

            } else {
                layout.setErrorEnabled(false);
            }
        }

        if (field_name.equals(AppConstants.PASSWORD)) {
            if (text_content.length() < 6) {
                layout.setError(activity.getResources().getString(R.string.error_pass));
                text.requestFocus();

            } else {
                layout.setErrorEnabled(false);
            }
        }

        if (field_name.equals(AppConstants.MOBILE_NO)) {
            if (!checkPhoneNumber(text_content)) {
                layout.setError(activity.getResources().getString(R.string.error_mobile));
                text.requestFocus();

            } else {
                layout.setErrorEnabled(false);
            }
        }
    }

    private boolean checkPhoneNumber(String phone) {

        Pattern pattern = Pattern.compile("[0-9]+");

        if (!pattern.matcher(phone).matches() || phone.length() != 10)
            return false;

        String first = String.valueOf(phone.charAt(0));

        for (int i = 0; i < 6; i++) {
            if (first.equals(Integer.toString(i)))
                return false;
        }

        return true;
    }

}
