package com.example.eldho.jobschedulersample;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class JobInfoClass {

    private static final String TAG = "JobInfoClass";
    private static boolean sInitialized; //to keep track of whether the job is started or not
    private static final int JOBID = 123;

    synchronized public static void startJobService(@NonNull final Context context){
        if (sInitialized) return; // checks if the job is already been setup, if true return

        ComponentName componentName = new ComponentName(context, SampleJobService.class);
        JobInfo info = new JobInfo.Builder(JOBID,componentName)

                /**there is plenty to configure below shown a few examples*/
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresCharging(false)
                .setPeriodic(15 * 60 * 1000) // from N it cant be below 15mins
                .setPersisted(true) // Even after phone get restated the job will restart
                .build();

        //Reference to the job scheduler and have its scheduler job with our info object
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        int result = jobScheduler.schedule(info);

        //Check whether the job schedule is successful or not
        if (result==JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "Job schedule is successful ");
            sInitialized = true;
        }
        else {
            Log.d(TAG, "Job schedule is unSuccessful");
            sInitialized = false;
        }
    }
}
