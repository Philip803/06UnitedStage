package leung.philip.a06united;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class ScheduleActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private RecyclerView mScheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mToolbar = (Toolbar)findViewById(R.id.Schedule_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Schedules");

        loadSchedule();

    }

    private void loadSchedule() {



    }
}
