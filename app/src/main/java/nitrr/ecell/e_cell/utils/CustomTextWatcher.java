package nitrr.ecell.e_cell.utils;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Pattern;

public class CustomTextWatcher implements TextWatcher {
    private Activity activity;
    private EditText text;
    private TextInputLayout layout;
    private String field_name;

    public CustomTextWatcher(EditText text, TextInputLayout layout, String field_name) {
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
            layout.setError("Empty fields not allowed.");
        } else {
            layout.setErrorEnabled(false);
        }

        if (field_name.equals(AppConstants.FIRST_NAME)) {
            Pattern pattern = Pattern.compile("[a-zA-Z]+");

            if (pattern.matcher(text_content).matches()) {
                layout.setErrorEnabled(false);
                text.requestFocus();
            } else {
                layout.setError("First Name should not contain digits or empty field.");
            }

        }


        if (field_name.equals(AppConstants.LAST_NAME)) {
            Pattern pattern = Pattern.compile("[a-zA-Z]+");

            if (pattern.matcher(text_content).matches()) {
                layout.setErrorEnabled(false);
            } else {
                layout.setError("Last Name should not contain digits or empty field.");
                text.requestFocus();
            }
        }

        if (field_name.equals(AppConstants.EMAIL)) {
            if (!Patterns.EMAIL_ADDRESS.matcher(text_content).matches()) {
                layout.setError("Invalid Email Address.");
                text.requestFocus();

            } else {
                layout.setErrorEnabled(false);
            }
        }

        if (field_name.equals(AppConstants.PASSWORD)) {
            if (text_content.length() != 8) {
                layout.setError("Password's length should at least be eight.");
                text.requestFocus();

            } else {
                layout.setErrorEnabled(false);
            }
        }

        if (field_name.equals(AppConstants.MOBILE_NO)) {
            Pattern pattern = Pattern.compile("[0-9]+");
            if (!pattern.matcher(text_content).matches() || text_content.length() != 10) {
                layout.setError("Enter a valid mobile number.");
                text.requestFocus();

            } else {
                layout.setErrorEnabled(false);
            }
        }
    }
}
