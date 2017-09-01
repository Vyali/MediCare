package com.example.ayush.medicare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public String gender = null;
    Spinner genderPicker;
    EditText firstName, middleName, lastName, dateOfBirth, contactNumber, address;
    TextInputLayout fnameLayout, mnameLayout, lnameLayout, dobLayout, numLayout, addressLayout, gdlayout;

    FirebaseAuth mAuth;
    FirebaseUser user;
    Button submit, cancel;
    private DatabaseReference mEditDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive", "Logout in progress");
                //At this point you should start the login activity and finish this one
                finish();
            }
        }, intentFilter);

        mEditDatabase = FirebaseDatabase.getInstance().getReference();

        firstName = (EditText) findViewById(R.id.edit_first_name);
        fnameLayout = (TextInputLayout) findViewById(R.id.input_layout_fname);

        middleName = (EditText) findViewById(R.id.edit_middle_name);
        mnameLayout = (TextInputLayout) findViewById(R.id.layout_mname);

        lastName = (EditText) findViewById(R.id.edit_last_name);
        lnameLayout = (TextInputLayout) findViewById(R.id.layout_lname);

        dateOfBirth = (EditText) findViewById(R.id.date_picker);
        dobLayout = (TextInputLayout) findViewById(R.id.layout_dob);

        contactNumber = (EditText) findViewById(R.id.contact_num);
        numLayout = (TextInputLayout) findViewById(R.id.layout_contanct);

        address = (EditText) findViewById(R.id.edit_address);
        addressLayout = (TextInputLayout) findViewById(R.id.layout_contanct);


        genderPicker = (Spinner) findViewById(R.id.gender_chooser);
        gdlayout = (TextInputLayout) findViewById(R.id.layout_gender);

        submit = (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.cancel);

        firstName.addTextChangedListener(new MyTextWatcher(firstName));
        middleName.addTextChangedListener(new MyTextWatcher(middleName));
        lastName.addTextChangedListener(new MyTextWatcher(lastName));

        dateOfBirth.addTextChangedListener(new MyTextWatcher(dateOfBirth));
        contactNumber.addTextChangedListener(new MyTextWatcher(contactNumber));

        address.addTextChangedListener(new MyTextWatcher(address));


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderPicker.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(EditDetails.this,"values submitted",Toast.LENGTH_LONG).show();
                Toast.makeText(EditDetails.this, "id" + user.getUid() + "name" + firstName.getText().toString() + "phone" + contactNumber.getText().toString(), Toast.LENGTH_LONG).show();
                Log.w("values", "id" + user.getUid() + "name" + firstName.getText());
                editUserDetails(user.getUid(), firstName.getText().toString(), middleName.getText().toString(),
                        lastName.getText().toString(), address.getText().toString(),
                        contactNumber.getText().toString(), dateOfBirth.getText().toString()

                );


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(EditDetails.this,UserProfile.class)
                finish();
            }
        });


        mEditDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firstName.setText((String) dataSnapshot.child("users").child(user.getUid()).child("fname").getValue());
                middleName.setText((String) dataSnapshot.child("users").child(user.getUid()).child("mname").getValue());
                lastName.setText((String) dataSnapshot.child("users").child(user.getUid()).child("lname").getValue());
                address.setText((String) dataSnapshot.child("users").child(user.getUid()).child("address").getValue());
                contactNumber.setText((String) dataSnapshot.child("users").child(user.getUid()).child("phone").getValue());
                dateOfBirth.setText((String) dataSnapshot.child("users").child(user.getUid()).child("dob").getValue());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Cancelled", "unable to show the values" + databaseError);
            }
        });


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!sharedPreferences.getBoolean("first_time", false)) {
            mEditDatabase.child("users").child(user.getUid()).child("email").setValue(user.getEmail());
            Toast.makeText(this, "mail id set up", Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("first_time", true);
            editor.commit();
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        // gd.setText(adapterView.getItemAtPosition(pos).toString());
        gender = adapterView.getItemAtPosition(pos).toString();
        //Log.d("gender","gender is"+gender);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void editUserDetails(String userID, String fname, String mname, String lname, String address, String phone, String dob) {
        User muser = new User(fname, mname, lname, address, phone, dob, gender);
        mEditDatabase.child("users").child(userID).setValue(muser);

    }
}


class User {
    public String fname, mname, lname, address, gender, phone, dob;


    public User(String fname, String mname, String lname, String address, String phone, String dob, String gender) {
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.dob = dob;

    }


}


class MyTextWatcher implements TextWatcher {

    private View view;

    MyTextWatcher(View view) {
        this.view = view;
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void afterTextChanged(Editable editable) {
        switch (view.getId()) {


        }
    }
}