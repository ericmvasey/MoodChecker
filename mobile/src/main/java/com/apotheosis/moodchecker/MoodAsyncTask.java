package com.apotheosis.moodchecker;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MoodAsyncTask extends AsyncTask<String, Void, Integer>
{
    private Context c;
    public MoodAsyncTask(Context c)
    {
        this.c = c;
    }

    @Override
    protected Integer doInBackground(String... params)
    {
        String user = params[0];
        String emotion = params[1];
        String level = params[2];

        try
        {
            URL url = new URL("http://68.32.73.161/mood/mood.php/write");
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write("user="+user+"&emotion="+emotion+"&level="+level);
            writer.flush();
            writer.close();


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
        if(result == 401)
        {
            Toast.makeText(c, "Bad username.", Toast.LENGTH_LONG).show();
        }
        else if(result != 200)
        {
            Toast.makeText(c, "Submission failed.", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(c, "Submission successful!", Toast.LENGTH_LONG).show();
        }
    }
}
