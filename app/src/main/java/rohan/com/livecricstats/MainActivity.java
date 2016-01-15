package rohan.com.livecricstats;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    String mLiveResults;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    String jsondata;


    @Bind((R.id.mprogressBar))ProgressBar mProgressBar;
    @Bind(R.id.refreshImageView) ImageView mRefreshImageView;
    private Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mProgressBar.setVisibility(View.INVISIBLE);

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getResults();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        mStartButton = (Button)findViewById(R.id.button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String name = mNameField.getText().toString();
                // Toast.makeText(MainActivity.this,name, Toast.LENGTH_LONG).show();
                startNewActivity();
            }
        });

        try {
         //   startNewActivity();
            getResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startNewActivity() {
      Intent intent = new Intent(this,activity_feed_list.class);
      intent.putExtra("Rohan",jsondata);
       startActivity(intent);
    }

    public void getResults() throws IOException {
        if (isNetworkAvailable()) {
            String liveUrl = "http://cricinfo-mukki.rhcloud.com/api/match/live/";
       //     toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(liveUrl).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toggleRefresh();
                            }
                        });
                        //Response response =call.execute();
                        jsondata = response.body().string();
                        Log.d("Rohan",jsondata);
                        if (response.isSuccessful()) {
                            mLiveResults = parseLiveResulta(jsondata);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });


                            Log.v(TAG, jsondata);
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "EXception Caught", e);
                    }
                }

                private void alertUserAboutError() {
                    AlertDialogFragment dialog = new AlertDialogFragment();
                    dialog.show(getFragmentManager(), "error_dialog");

                }
            });
            Log.d(TAG, "Main UI code is running");
        } else

        {
            Toast.makeText(this, "Network Unavailable", Toast.LENGTH_LONG).show();
        }

    }

    private String parseLiveResulta(String jsondata) {

        return new String();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo !=null && networkInfo.isConnected() )
        {
            isAvailable = true;
        }
        return isAvailable;
    }
    private void toggleRefresh() {
        if(mProgressBar.getVisibility()==View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        }
        else
        {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }
    private void updateDisplay() {
//        Current current = mForecast.getCurrent();
//        mTemperatureLabel.setText((int) current.getTemperature()+ "");
//        mTimelabel.setText("At " + current.getFormmatedTime() + " it will be ");
//        mHumidityValue.setText(current.getHumidity() + "");
//        mMPrecipValue.setText(current.getPrecipChance() + "%");
//        mSummaryLabel.setText(current.getSummary());
//        Drawable drawable = getResources().getDrawable(current.getIconId());
//        mIconImageView.setImageDrawable(drawable);
//        mLocationLabel.setText(stateName);

    }
}
