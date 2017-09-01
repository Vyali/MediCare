package com.example.ayush.medicare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class UserProfile extends AppCompatActivity {
    //private GoogleApiClient mGoogleApiClient;
    TextView userProfileName;
    TextView name;
    TextView userPhone;
    TextView contactNum;
    TextView userEmail;
    TextView dob, address;

    ImageButton userProfilePic;
    ImageView logout, edit;
    FirebaseAuth mAuth;
    FirebaseUser currUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    private GoogleApiClient muserGoogleApiClient;


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


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


        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();

        GoogleSignInOptions gsouser = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        muserGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, null)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gsouser)
                .build();


        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();

        userProfileName = (TextView) findViewById(R.id.user_profile_name);
        name = (TextView) findViewById(R.id.username);
        dob = (TextView) findViewById(R.id.dob);
        address = (TextView) findViewById(R.id.addressid);


        userPhone = (TextView) findViewById(R.id.user_profile_phone);
        userEmail = (TextView) findViewById(R.id.user_profile_email);
        userProfilePic = (ImageButton) findViewById(R.id.user_profile_photo);
        contactNum = (TextView) findViewById(R.id.contactnum);
        logout = (ImageView) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(UserProfile.this,"signed out ",Toast.LENGTH_LONG).show();
                mAuth.signOut();
                //Intent intent = new Intent(UserProfile.this,LoginActivity.class);
                //startActivity(intent);

                Auth.GoogleSignInApi.signOut(muserGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                Intent intent = new Intent(UserProfile.this, LoginActivity.class);
                                startActivity(intent);

                                Intent broadcastIntent = new Intent();
                                broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                                sendBroadcast(broadcastIntent);
                            }
                        }
                );
            }
        });

        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, EditDetails.class);
                startActivity(intent);

            }
        });

        userProfileName.setText(currUser.getDisplayName());
        userPhone.setText(currUser.getPhoneNumber());
        userEmail.setText(currUser.getEmail());
        name.setText(currUser.getDisplayName());
        //dob.setText("enter your date of birth");
        Uri imgUri = currUser.getPhotoUrl();

        Log.d("imageuri", "image uri is" + imgUri);
        //InputStream inputStream = getContentResolver().openInputStream(imgUri);
        // Drawable myDrawable = Drawable.createFromStream(inputStream, imgUri.toString());
        //userProfilePic.setImageURI(imgUri);
        Picasso.with(UserProfile.this).load(imgUri).placeholder(R.drawable.ic_account_circle_black_24dp).transform(new CircleTransform()).into(userProfilePic);


        //adding email to the users database  this will be executed only once


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //fname= (String) dataSnapshot.child("user").child("user1").child("firstname").getValue(String.class);
                //  lname = (String) dataSnapshot.child("user").child("uesr1").child("lastname").getValue(String.class);
                // phone = (String) dataSnapshot.child("user").child("user1").child("contact").child("phone").getValue(String.class);
                //email =  (String) dataSnapshot.child("user").child("user1").child("contact").child("email").getValue(String.class);
                // dataSnapshot.child("users").child(currUser.getUid()).child("email")
                // Log.d("data ","vdata "+fname+lname+"  "+phone);


                dob.setText(dataSnapshot.child("users").child(currUser.getUid()).child("dob").getValue(String.class));
                userPhone.setText(dataSnapshot.child("users").child(currUser.getUid()).child("phone").getValue(String.class));
                address.setText(dataSnapshot.child("users").child(currUser.getUid()).child("address").getValue(String.class));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dob.setText("date of birth");
                userPhone.setText("mob num");
                Log.w("user profiel failed", "Failed to read user details value.", databaseError.toException());
            }
        });


        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();


    }

}


class CircleTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size);
        int y = (source.getHeight() - size);

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);


        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}