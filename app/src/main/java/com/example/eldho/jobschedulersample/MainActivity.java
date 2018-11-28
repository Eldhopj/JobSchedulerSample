package com.example.eldho.jobschedulersample;
/**From android-O we cant keep a background service running anymore, system will stop the service to save the memory and battery
 * Job scheduler is more intelligent alarm manager
 * it is more intelligent it will batch the jobs together
 * we can control it a little bit , like controlling minimum waiting time and deadlines
 * Minimum API level 21*/

/**
 * create startJobService class <The code or job which runs is inside this class />
 * in Manifest add the service
 * */
import android.app.job.JobScheduler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private final int JOBID = 123;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void scheduleJob(View view) {
        JobInfoClass.startJobService(getApplicationContext());
    }

    public void cancelJob(View view) {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(JOBID);
        Log.d(TAG, "cancelJob ");
    }
}
