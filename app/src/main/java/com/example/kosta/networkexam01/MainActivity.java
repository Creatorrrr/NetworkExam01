package com.example.kosta.networkexam01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView)findViewById(R.id.image);

//        메인 스레드에서 네트워크에 접근할 수 있도록 하는 코드
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//        URL url = null;
//        try {
//            url = new URL("http://www.rd.com/wp-content/uploads/sites/2/2016/02/06-train-cat-shake-hands.jpg");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        img.setImageBitmap(getRemoteImage(url));

//        원래는 이렇게 해야 함
        new ImageLoadTask().execute("http://www.rd.com/wp-content/uploads/sites/2/2016/02/06-train-cat-shake-hands.jpg");
    }

    private class ImageLoadTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            URL url = null;

            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return getRemoteImage(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            img.setImageBitmap(bitmap);
        }

    }

    private Bitmap getRemoteImage(URL url) {
        Bitmap bitmap = null;

        URLConnection conn = null;

        try {
            conn = url.openConnection();
            conn.connect();

            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());

            bitmap = BitmapFactory.decodeStream(bis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
