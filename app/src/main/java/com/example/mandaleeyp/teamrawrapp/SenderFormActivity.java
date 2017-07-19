package com.example.mandaleeyp.teamrawrapp;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class SenderFormActivity extends AppCompatActivity {

    public EditText et_other;
    public CheckBox cb_envelope, cb_smallBox, cb_largeBox, cb_clothing, cb_other;

    // each index refers to an item: [envelope, smbox, lgbox, clothing, other]
    public Boolean[] itemBools = {false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inflate layout
        setContentView(R.layout.activity_sender_form);

        // get the checkbox items in the XML
        et_other = (EditText) findViewById(R.id.et_other);
        cb_envelope = (CheckBox) findViewById(R.id.cb_envelope);
        cb_smallBox = (CheckBox) findViewById(R.id.cb_smallBox);
        cb_largeBox = (CheckBox) findViewById(R.id.cb_largeBox);
        cb_clothing = (CheckBox) findViewById(R.id.cb_clothing);
        cb_other = (CheckBox) findViewById(R.id.cb_other);

        // get other items in the XML
        TextInputLayout recipientNameWrapper = (TextInputLayout) findViewById(R.id.til_recipientNameWrapper);
        TextInputLayout recipientNumWrapper = (TextInputLayout) findViewById(R.id.til_recipientNumWrapper);

        // setting hint for TILs
        recipientNameWrapper.setHint("Name");
        recipientNumWrapper.setHint("Contact number");

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
                    // make it invisible and clear the text in it
                    et_other.setVisibility(View.INVISIBLE);
                    et_other.setText("");
                }
                break;
            }
            default: {
                break;
            }
        }
    }
}
