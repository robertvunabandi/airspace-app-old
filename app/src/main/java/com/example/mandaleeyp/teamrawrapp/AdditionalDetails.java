package com.example.mandaleeyp.teamrawrapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setTitle("Additional Details");
        setContentView(R.layout.activity_additional_details);

        // get the items in the XML
//        et_other = (EditText) findViewById(R.id.et_other);
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
                    itemBools[0] = checked;
                break;
            }
            case R.id.cb_smallBox: {
                    itemBools[1] = checked;
                break;
            }
            case R.id.cb_largeBox: {
                    itemBools[2] = checked;
                break;
            }
            case R.id.cb_clothing: {
                    itemBools[3] = checked;
                break;
            }
            case R.id.cb_other: {
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
