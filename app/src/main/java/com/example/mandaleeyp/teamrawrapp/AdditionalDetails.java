package com.example.mandaleeyp.teamrawrapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;

public class AdditionalDetails extends AppCompatActivity {

    public EditText et_other;
    public CheckBox cb_envelope, cb_smallBox, cb_largeBox, cb_clothing, cb_other;

    // each index refer to one thing, so [envelope, smbox, lgbox, clothing, other]
    public Boolean[] itemBools = {false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_additional_details);

        // get the items in the XML
        et_other = (EditText) findViewById(R.id.et_other);
        cb_envelope = (CheckBox) findViewById(R.id.cb_envelope);
        cb_smallBox = (CheckBox) findViewById(R.id.cb_smallBox);
        cb_largeBox = (CheckBox) findViewById(R.id.cb_largeBox);
        cb_clothing = (CheckBox) findViewById(R.id.cb_clothing);
        cb_other = (CheckBox) findViewById(R.id.cb_other);

        // when user touches outside, we don't want to quit the dialog
        this.setFinishOnTouchOutside(false);
    }

    public void onCheckboxClicked(View view) {

        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.cb_envelope: {
                // TODO - change the index 0 of itemBools and maybe do other stuffs
                if (checked) {

                }
                break;
            }
            case R.id.cb_smallBox: {
                // TODO - change the index 1 of itemBools and maybe do other stuffs
                if (checked) {

                }
                break;
            }
            case R.id.cb_largeBox: {
                // TODO - change the index 2 of itemBools and maybe do other stuffs
                if (checked) {

                }
                break;
            }
            case R.id.cb_clothing: {
                // TODO - change the index 3 of itemBools and maybe do other stuffs
                if (checked) {

                }
                break;
            }
            case R.id.cb_other: {
                // TODO - change the index 3 of itemBools and maybe do other stuffs
                if (checked) {
                    et_other.setVisibility(View.VISIBLE);
                } else {
                    et_other.setVisibility(View.GONE);
                }
                break;
            }
            default: {
                break;
            }
        }
    }
}
