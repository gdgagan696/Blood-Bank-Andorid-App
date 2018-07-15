package com.example.gagan.bloodbank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import org.parceler.Parcels;

import java.io.Serializable;


public class UserProfile extends AppCompatActivity implements Serializable{

    TextView mName;
    TextView mBloodGroup;
    TextView mEmail;
    TextView mGender;
    TextView mDonor;
    TextView mAddress;
    TextView mState;
    TextView mCity;
    Button mEditProfile;
    Button mSignOut;
    DatabaseReference databaseReference;
    String userId;
    UserInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        //initUI();
        //setButtonOnClickListener();


//        Intent intent = getIntent();
//        userId =  intent.getStringExtra("ID");
//        Log.d("AVNI","userid in info " + userId);
        //FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        //FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //String userId = firebaseUser.getUid();
        //databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(userId);

    }

    private void initUI() {
        mName = (TextView)findViewById(R.id.name);
        mBloodGroup = (TextView)findViewById(R.id.blood_group);
        mEmail = (TextView)findViewById(R.id.email);
        mDonor= (TextView) findViewById(R.id.Donor);
        mGender = (TextView)findViewById(R.id.gender);
        mAddress = (TextView)findViewById(R.id.addr);
        mState = (TextView)findViewById(R.id.state);
        mCity = (TextView)findViewById(R.id.city);
        mEditProfile = (Button)findViewById(R.id.edit);
        mSignOut= (Button)findViewById(R.id.logout);
    }




    private void setButtonOnClickListener() {
        mEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bundle bundle = new Bundle();
                // bundle.putBoolean("edit",true);
                // bundle.putParcelable("abc",Parcels.wrap(userInfo));
                //Log.d("AVNI","bundle in profileinfo  " + bundle);
                Intent intent = new Intent(UserProfile.this, hello.class);
//               intent.putExtra("edit",true);
//               intent.putExtra("id",userId);
                //intent.putExtra("abc",userInfo);
                // intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        initUI();
        setButtonOnClickListener();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Userinfo userInfo = dataSnapshot.child(userId).getValue();
                userInfo = dataSnapshot.getValue(UserInfo.class);
                mName.setText(userInfo.getName());
                mBloodGroup.setText("Blood Group:  " + userInfo.getBloodgroup());
                mEmail.setText("Email:  " + userInfo.getEmail());
                mAddress.setText("Address:  " + userInfo.getAddress());
                mState.setText("State:  " + userInfo.getState());
                mCity.setText("City:  " + userInfo.getCity());
                mGender.setText("Gender:  " + userInfo.getGender());
                if(userInfo.getDonor().equals("Donor"))   mDonor.setText("Donor:  Yes");
                else     mDonor.setText("Donor:  No");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }
}