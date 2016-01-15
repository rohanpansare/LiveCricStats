package rohan.com.livecricstats;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rohan.com.livecricstats.R;

public class activity_feed_list extends AppCompatActivity {
    private String mResults;
    private static final String TAG = "RecyclerViewExample";
    private List<FeedItem> feedsList;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private ProgressBar progressBar;
    private List<FeedItem> feedItemList = new ArrayList<FeedItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_feed_list);
        Intent intent = getIntent();
        mResults = intent.getStringExtra("Rohan");
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
       progressBar.setVisibility(View.GONE);
   parseResult(mResults);

    }
//    protected void onPostExecute(Integer result) {
//        // Download complete. Let us update UI
//        progressBar.setVisibility(View.GONE);
//
//        if (result == 1) {
//            adapter = new MyRecyclerAdapter(activity_feed_list.this, feedsList);
//            mRecyclerView.setAdapter(adapter);
//        } else {
//            Toast.makeText(activity_feed_list.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);

            JSONArray posts = response.getJSONArray("items");
           // feedsList = new ArrayList<>();
            if (null == feedItemList) {
                feedItemList = new ArrayList<FeedItem>();
            }

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();
                item.setTitle(post.optString("title"));
                Log.d("Rohan",item.getTitle());
         //       item.setThumbnail(post.optString("matchId"));

                feedItemList.add(item);
            }

            adapter = new MyRecyclerAdapter(activity_feed_list.this, feedItemList);
            mRecyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}