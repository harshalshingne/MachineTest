package com.wednesday.machinetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wednesday.machinetest.adapter.DataAdapter;
import com.wednesday.machinetest.fragment.PreviewDialog;
import com.wednesday.machinetest.model.ArtiestDetails;
import com.wednesday.machinetest.model.Result;
import com.wednesday.machinetest.retrofit.APIClient;
import com.wednesday.machinetest.retrofit.APIInterface;
import com.wednesday.machinetest.room.DatabaseClient;
import com.wednesday.machinetest.room.SongDetails;
import com.wednesday.machinetest.room.SongsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<ArtiestDetails> mArrayList=new ArrayList<>();
    private DataAdapter mAdapter;
    APIInterface apiInterface;
    ProgressBar progressBar;
    private TextView tvSearchSongs;
    SongsAdapter songsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        if (isNetworkConnected()){
            Toast.makeText(MainActivity.this, "Internet Connected", Toast.LENGTH_SHORT).show();
            tvSearchSongs.setVisibility(View.VISIBLE);
            mArrayList.clear();
        }else {

            getSong();
        }

    }

    private void initViews(){
        mRecyclerView =findViewById(R.id.recycler_view);
        progressBar=findViewById(R.id.progress_circular);
        tvSearchSongs=findViewById(R.id.tv_no_result);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void loadData(String name){
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Result> call = apiInterface.getArtists(name);
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                Log.e("Responses ",result+"");

                if (result != null) {

                    if (result.getResults().size() > 1) {
                        processBookings(result.getResults());
                        progressBar.setVisibility(View.GONE);
                        tvSearchSongs.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                call.cancel();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Log.d("TAGG ", t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void processBookings(List<ArtiestDetails> details){

        for (ArtiestDetails artiestDetails1:details){

            ArtiestDetails artiestDetails=new ArtiestDetails();
            artiestDetails.setCollectionCensoredName(artiestDetails1.getCollectionCensoredName());
            artiestDetails.setCollectionArtistName(artiestDetails1.getCollectionArtistName());
            artiestDetails.setPreviewUrl(artiestDetails1.getPreviewUrl());
            artiestDetails.setArtworkUrl100(artiestDetails1.getArtworkUrl100());
            mArrayList.add(artiestDetails);
            saveSong(artiestDetails.getCollectionCensoredName(),artiestDetails.getCollectionArtistName(),
                    artiestDetails.getArtworkUrl100(),artiestDetails.getPreviewUrl());
        }

        mAdapter = new DataAdapter(MainActivity.this,mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnPreviewClick(new onPreviewClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        if (isNetworkConnected()){
            search(searchView);
        }else {
            searchRoom(searchView);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mArrayList.clear();
                loadData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if (mAdapter!=null)
//                mAdapter.getFilter().filter(newText);

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNetworkConnected()){
            Toast.makeText(MainActivity.this, "Internet Connected", Toast.LENGTH_SHORT).show();
            tvSearchSongs.setVisibility(View.VISIBLE);
            mArrayList.clear();
        }else {
            mArrayList.clear();
            getSong();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isNetworkConnected()){
            Toast.makeText(MainActivity.this, "Internet Connected", Toast.LENGTH_SHORT).show();
            tvSearchSongs.setVisibility(View.VISIBLE);
            mArrayList.clear();
        }else {
            mArrayList.clear();
            getSong();
        }
    }

    public class onPreviewClickListener implements DataAdapter.onPreviewClick{

        @Override
        public void onPreview(ArtiestDetails artiestDetails) {

            Toast.makeText(MainActivity.this, "preview is loading...", Toast.LENGTH_SHORT).show();
            DialogFragment dialogFragment = new PreviewDialog();

            Bundle bundle = new Bundle();
            bundle.putBoolean("notAlertDialog", true);
            bundle.putString("preview", artiestDetails.getPreviewUrl());
            dialogFragment.setArguments(bundle);

            FragmentTransaction  ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            dialogFragment.show(ft, "dialog");
        }
    }

    //get data from room
    private void getSong() {
        Toast.makeText(MainActivity.this, "You are offline mode", Toast.LENGTH_SHORT).show();
        class GetTasks extends AsyncTask<Void, Void, List<SongDetails>> {

            @Override
            protected List<SongDetails> doInBackground(Void... voids) {
                List<SongDetails> songDetailsList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .songDao()
                        .getAll();
                return songDetailsList;
            }

            @Override
            protected void onPostExecute(List<SongDetails> songDetails) {
                super.onPostExecute(songDetails);
                songsAdapter= new SongsAdapter(MainActivity.this, songDetails);
                mRecyclerView.setAdapter(songsAdapter);
                songsAdapter.setOnPreviewClick(new onPreviewRoomClickListener());
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    private void searchRoom(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSong();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
    }
    //get data from room
    private void saveSong(final String CollectionCensoredName, final String CollectionArtistName, final String ArtworkUrl100,final String previewUrl) {

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                SongDetails songDetails = new SongDetails();
                songDetails.setCollectionCensoredName(CollectionCensoredName);
                songDetails.setCollectionArtistName(CollectionArtistName);
                songDetails.setArtworkUrl100(ArtworkUrl100);
                songDetails.setPreviewUrl(previewUrl);
               // songDetails.setFinished(false);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .songDao()
                        .insert(songDetails);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

    public class onPreviewRoomClickListener implements SongsAdapter.onPreviewClick{

        @Override
        public void onPreview(SongDetails songDetails) {
            DialogFragment dialogFragment = new PreviewDialog();
            Bundle bundle = new Bundle();
            bundle.putString("preview", songDetails.getPreviewUrl());
            dialogFragment.setArguments(bundle);

            FragmentTransaction     ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            dialogFragment.show(ft, "dialog");

        }
    }

}