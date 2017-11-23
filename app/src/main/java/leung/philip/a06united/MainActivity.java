package leung.philip.a06united;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import leung.philip.a06united.Model.News;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolBar;

    private RecyclerView mNewsList;

    private DatabaseReference mNewsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //when app load up
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mNewsReference = FirebaseDatabase.getInstance().getReference().child("Announcement");


        //set up ToolBar
        mToolBar = (Toolbar) findViewById(R.id.mainPage_app_bar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("06 United News");

        //setup recycleList
        mNewsList = (RecyclerView)findViewById(R.id.news_list);
        mNewsList.setHasFixedSize(true);
        mNewsList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //check if user update ID
        if(currentUser == null){
            sendToStart();
        } else {
            loadUpdatedNews();
        }
    }

    public void loadUpdatedNews(){

        //pass in 4 elements for constructor
        FirebaseRecyclerAdapter<News, NewsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<News, NewsViewHolder>(
                News.class,
                R.layout.news_layout,
                NewsViewHolder.class,
                mNewsReference.orderByChild("postedDate")

        ) {
            @Override
            protected void populateViewHolder(NewsViewHolder viewHolder, News model, int position) {

                //News data and title on mainactivity page
                viewHolder.setEventDate(model.getAnnoDate());
                viewHolder.setEventTitle(model.getAnnoTitle());

                //get the clicked page id
                final String news_id = getRef(position).getKey();

                //setup when user click the news cell
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent newsDetailIntent = new Intent(MainActivity.this, NewsDetailActivity.class);
                        newsDetailIntent.putExtra("Newsuid", news_id);
                        startActivity(newsDetailIntent);

                    }
                });


            }
        };

        //set the newslist to firebase adaptor
        mNewsList.setAdapter(firebaseRecyclerAdapter);

    }

    //go to startActivity class if no user sign in
    private void sendToStart() {

        Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();

    }

    //Menu bar set up
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    //function for menu bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout_button){

            FirebaseAuth.getInstance().signOut();
            sendToStart();

        }

        if(item.getItemId() == R.id.toMyInfoBtn){

            Intent myInfoIntent = new Intent(MainActivity.this, MyInfoActivity.class);
            startActivity(myInfoIntent);

        }

        if(item.getItemId() == R.id.toScheduleBtn){

            Intent myScheduleIntent = new Intent(MainActivity.this, ScheduleActivity.class);
            startActivity(myScheduleIntent);


        }

        return true;
    }

    //must set to static class otherwise it will crash
    public static class NewsViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public NewsViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setEventDate(String date){

            TextView newsDate = (TextView) mView.findViewById(R.id.news_DateTitle);
            newsDate.setText("Date: " + date);

        }

        public void setEventTitle(String title){

            TextView newsTitle = (TextView) mView.findViewById(R.id.news_EventTitle);
            newsTitle.setText("Event: " + title);

        }

    }
}
