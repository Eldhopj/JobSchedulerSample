package com.example.eldho.jobschedulersample;
/**From android-O we cant keep a background service running anymore, system will stop the service to save the memory and battery
 * Job scheduler is more intelligent alarm manager
 * it is more intelligent it will batch the jobs together
 * we can control it a little bit , like controlling minimum waiting time and deadlines
 * Minimum API level 21*/

/**
 * create jobService class <The code or job which runs is inside this class />
 * in Manifest add the service
 * */
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
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
        ComponentName componentName = new ComponentName(this, sampleJobService.class);
        JobInfo info = new JobInfo.Builder(JOBID,componentName)

                /**there is plenty to configure below shown a few examples*/
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresCharging(false)
                .setPeriodic(15 * 60 * 1000) // from N it cant be below 15mins
                .setPersisted(true) // Even after phone get restated the job will restart
                .build();

        //Reference to the job scheduler and have its scheduler job with our info object
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int result = jobScheduler.schedule(info);

        //Check whether the job schedule is successful or not
        if (result==JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "Job schedule is successful ");
        }
        else {
            Log.d(TAG, "Job schedule is unSuccessful");
        }
    }

    public void cancelJob(View view) {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(JOBID);
        Log.d(TAG, "cancelJob ");
    }
}
