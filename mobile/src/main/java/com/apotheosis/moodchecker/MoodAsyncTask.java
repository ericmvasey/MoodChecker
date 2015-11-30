package com.apotheosis.moodchecker;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

public class MoodAsyncTask extends AsyncTask<Integer, Void, Integer>
{
    private Context c;
    public MoodAsyncTask(Context c)
    {
        this.c = c;
    }

    @Override
    protected Integer doInBackground(Integer... params)
    {
        int type = params[0];

        try
        {
            URL url = new URL("http://68.32.73.161/mood/mood.php/write/" + String.valueOf(type));
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);

            return conn.getResponseCode();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return 404;
    }

    @Override
    protected void onPostExecute(Integer result)
    {
        if(result != 200)
        {
            Toast.makeText(c, "Submission failed.", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(c, "Submission successful!", Toast.LENGTH_LONG).show();
        }
    }
}
