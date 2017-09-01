package com.example.ayush.medicare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {


    RecyclerView myRecyclerView;
    NotificationAdapter myNotificationAdapter;
    List<NotificationDetails> myNotificationList;
    String body;
    ArrayList<String> bodyList;
    FirebaseAuth mAuth;
    FirebaseUser user;
    private DatabaseReference myReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        bodyList = new ArrayList<>();
        myRecyclerView = (RecyclerView) findViewById(R.id.notification_recycler);
        myNotificationList = new ArrayList<>();
        //prepareNotificationList();

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

       /* myReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

       myReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               Object b= dataSnapshot.child("users").child(user.getUid()).child("notification").getValue();
               bodyList.add(b.toString());
               generateNotification(bodyList);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });



        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                body= getIntent().getExtras().getString(key);
                bodyList.add(body);
                myReference.child("users").child(user.getUid()).child("notification").setValue(bodyList);

                Log.d("notification", "Key: " + key + " Value: " + body);
            }
        }


        myNotificationAdapter= new NotificationAdapter(this,myNotificationList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(layoutManager);
        myRecyclerView.setAdapter(myNotificationAdapter);
*/
    }



   /* @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("body_list",bodyList);
    }
*/


   /* @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        intent = getIntent();
        body=intent.getStringExtra("body");
        generateNotification(body);

    }
    */
   /*  private  void prepareNotificationList(){
        NotificationDetails a = new NotificationDetails("abcd", "asfdasd");
        myNotificationList.add(a);
        a = new NotificationDetails("adfdf", "assfddasd");
        myNotificationList.add(a);
        a = new NotificationDetails("ab234cd", "23424");
        myNotificationList.add(a);
        a = new NotificationDetails("ab2323cd", "asfd234asd");
        myNotificationList.add(a);
        a = new NotificationDetails("abc23454d", "as23rwerfdasd");
        myNotificationList.add(a);
        a = new NotificationDetails("ab2342cd", "asfdwerasd");
        myNotificationList.add(a);

    }*/

    public void generateNotification(ArrayList<String> blist) {


        for (String b : blist) {
            NotificationDetails d = new NotificationDetails(b);

            myNotificationList.add(d);

        }
    }
}
