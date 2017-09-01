package com.example.ayush.medicare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements EhRecord.OnFragmentInteractionListener, Ambulance.OnFragmentInteractionListener,
        home.OnFragmentInteractionListener, HomeFront.OnFragmentInteractionListener, DoctorFragment.OnFragmentInteractionListener {

    static String value;
    android.support.v4.app.Fragment fragment;

    //FirebaseDatabase database=FirebaseDatabase.getInstance();
    android.support.v4.app.FragmentTransaction transaction;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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


        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

      /*  android.support.v4.app.Fragment fragment= new home();
        android.support.v4.app.FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        //transaction.addToBackStack(null);
        transaction.commit();*/
        fragment = new home();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();

        /*Drawable img= resize(getDrawable(R.drawable.fbp));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions(),ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageDrawable(img);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);
        */

        BottomNavigationView btmNavigationView = (BottomNavigationView) findViewById(R.id.btm_view);
        btmNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.homeView:
                                fragment = new home();
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.content, fragment);
                                //transaction.addToBackStack(null);
                                transaction.commit();

                                break;
                            //case R.id.my_profile:
                               /* Fragment fra= new AboutMe();
                                FragmentTransaction trans=getFragmentManager().beginTransaction();
                                trans.replace(R.id.home_fragment,fra);
                                trans.addToBackStack(null);
                                trans.commit();
                              */
                            case R.id.ehr:
                                fragment = new EhRecord();
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.content, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;

                            case R.id.ambulance:
                                fragment = new Ambulance();
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.content, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;


                        }
                        return false;
                    }
                }
        );

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value = dataSnapshot.child("p1").child("about").child("email").getValue(String.class);

                Log.d("data", "Value  is " + value);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("******failed****", "Failed to read value.", databaseError.toException());

            }
        });
    }
    /*
    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 50, 50, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_button, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent intent = new Intent(this, UserProfile.class);
                startActivity(intent);
                return true;
            case R.id.notification_button:
                Intent i = new Intent(this, NotificationActivity.class);
                startActivity(i);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
