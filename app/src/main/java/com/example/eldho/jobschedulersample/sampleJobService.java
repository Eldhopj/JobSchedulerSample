package com.example.eldho.jobschedulersample;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class sampleJobService extends JobService {
    private static final String TAG = "sampleJobService";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job Started");
        doInBackgroundWork(params);

        //This will help the system to know when this method is over our job is over or not false->over, true->not over
        return true;//If the task is small and we can do the talk with in the scope return false
    }

    private void doInBackgroundWork(final JobParameters params) {
        //fake work
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.d(TAG, "run: " + i);

                    /**checks whether the job is cancelled or not*/
                    if (jobCancelled) { // if true it will stop the loop and return
                        return;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG, "Job finished");
                jobFinished(params, false); //Finish the job when background work is finished
            }
        }).start();
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "job Cancelled");

        jobCancelled = true;
        //checks we want to reschedule our job or not
        //true-> reschedule , false -> no need to reschedule
        return true;
    }
}
