package leung.philip.a06united;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView mNewsDate;
    private TextView mNewsTitle;
    private TextView mNewsBody;

    private DatabaseReference mNewsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        setNewsDetails();

    }

    public void setNewsDetails(){

        String newsDetailID = getIntent().getStringExtra("Newsuid");

        mNewsDatabase = FirebaseDatabase.getInstance().getReference().child("Announcement").child(newsDetailID);

        mNewsDate = (TextView)findViewById(R.id.news_detail_date);
        mNewsTitle = (TextView)findViewById(R.id.news_detail_title);
        mNewsBody = (TextView)findViewById(R.id.news_detail_body);

        mNewsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String newsDate = dataSnapshot.child("annoDate").getValue().toString();
                String newsTitle = dataSnapshot.child("annoTitle").getValue().toString();
                String newsBody = dataSnapshot.child("announcement").getValue().toString();

                mNewsDate.setText(newsDate);
                mNewsTitle.setText(newsTitle);
                mNewsBody.setText(newsBody);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(NewsDetailActivity.this,"Check Internet Connection",Toast.LENGTH_LONG).show();

            }
        });



    }




}
