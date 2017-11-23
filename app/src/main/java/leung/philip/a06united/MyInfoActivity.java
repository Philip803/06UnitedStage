package leung.philip.a06united;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyInfoActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    //firebase setup
    private FirebaseDatabase mdatabase;
    private DatabaseReference myRef;

    private CircleImageView mImageView;
    private TextView mPlayerName;
    private TextView mEmail;
    private TextView mCurrentTeam;
    private TextView mTeamNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        mImageView = (CircleImageView) findViewById(R.id.myInfo_image);
        mPlayerName = (TextView) findViewById(R.id.myinfo_playerName);
        mEmail = (TextView) findViewById(R.id.myinfo_email);
        mCurrentTeam = (TextView) findViewById(R.id.myinfo_currentTeam);
        mTeamNumber = (TextView) findViewById(R.id.myinfo_number);

        //set up toolbar
        setUpToolbar();

        //retrieve firebase user UID
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

        //firebase reference
        setUpFirebase(uid);


    }

    //set up toolbar
    private void setUpToolbar(){

        mToolbar = (Toolbar)findViewById(R.id.myInfo_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Info");

    }

    //firebase reference
    private void setUpFirebase(String uid){

        mdatabase = FirebaseDatabase.getInstance();
        myRef = mdatabase.getReference().child("UserLogin").child(uid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String player_name = dataSnapshot.child("Player Name").getValue().toString();
                String email = dataSnapshot.child("Email").getValue().toString();
                String currentTeam = dataSnapshot.child("Current Team").getValue().toString();
                String teamNumber = dataSnapshot.child("Jersey Number").getValue().toString();
                String imageURL = dataSnapshot.child("imageURL").getValue().toString();


                mPlayerName.setText(player_name);
                mEmail.setText(email);
                mCurrentTeam.setText(currentTeam);
                mTeamNumber.setText("Number: " + teamNumber);
                Picasso.with(MyInfoActivity.this).load(imageURL).into(mImageView);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Failed to read value
                Toast.makeText(MyInfoActivity.this, "Fail to download info", Toast.LENGTH_LONG).show();

            }
        });



    }



}
