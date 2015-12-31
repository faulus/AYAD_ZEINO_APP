package com.example.ayadzeino.appesiea;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    protected TextView deuxiemevariable;
    protected RecyclerView premierevariable;
    public final static String BIERS_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";



    @OnClick(R.id.secondactivity) void takeMeToLogin(){
        Intent secondeActivite = new Intent(MainActivity.this,TestActivity.class);
        startActivity(secondeActivite);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);


        deuxiemevariable = (TextView)findViewById(R.id.hello);
        premierevariable = (RecyclerView)findViewById(R.id.findbiers);


        GetBiersServices.startActionBiers(this);

        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(), intentFilter);

        premierevariable.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        premierevariable.setAdapter(new BiersAdapter(getBiersFromFIle()));
    }

    public JSONArray getBiersFromFIle(){
        try {
            InputStream in = new FileInputStream(getCacheDir()+"/"+"bieres.json");
            byte buffer[] = new byte[in.available()];
            in.read(buffer);
            in.close();
            return new JSONArray(new String(buffer,"UTF-8"));
        }catch (IOException e){
            e.printStackTrace();

            return new JSONArray();
        }catch (JSONException e){
            e.printStackTrace();
            return new JSONArray();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class BierUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            Log.d("BeersDownload", getIntent().getAction());
            Toast.makeText(getApplicationContext(), getString(R.string.b_up), Toast.LENGTH_LONG).show();
            BiersAdapter ba = (BiersAdapter) premierevariable.getAdapter();
            ba.setNewBier(getBiersFromFIle());
        }
    }

    class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder> {

        private JSONArray biers;

        public BiersAdapter(JSONArray array){
            this.biers = array;
        }

        public void setNewBier(JSONArray update){
            this.notifyDataSetChanged();
        }

        @Override
        public BierHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_bier, parent, false);
            return new BierHolder(view);
        }

        @Override
        public void onBindViewHolder(BierHolder holder, int position) {
            try {
                JSONObject jsonObject = biers.getJSONObject(position);
                String name = jsonObject.getString("name");
                holder.name.setText(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return biers.length();
        }

        class BierHolder extends RecyclerView.ViewHolder{

            public TextView name;

            public BierHolder(View itemView) {

                super(itemView);
                this.name = (TextView)itemView;
            }
        }
    }
}
