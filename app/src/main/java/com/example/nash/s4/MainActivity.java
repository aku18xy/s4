package com.example.nash.s4;

import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nash.s4.DataBank.Category;
import com.example.nash.s4.DataBank.ConnectionData;
import com.example.nash.s4.DataBank.DataUtil;
import com.example.nash.s4.DataBank.MasterData;
import com.example.nash.s4.DataBank.NewData;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    List<Category> categories;
    TabLayout tabLayout;
    ViewPager viewPager;
    TabAdapter tabAdapter;
    AppBarLayout abLayout;
    TextView tvTitle, tvConn;
    DataUtil dataUtil;
    MasterData masterData, onlineData;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Button btnConn;
    int btnStat;
    DrawerAdapter drawerAdapter;
    ListView listView;
    ViewGroup arrowNkOpen, arrowNkClose, llFab;
    FloatingActionButton fabUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("tagLine", "onCreate");

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        listView = findViewById(R.id.listView);
        btnConn = findViewById(R.id.btnConn);
        tvConn = findViewById(R.id.tvConn);
        tvTitle = findViewById(R.id.tvToolbar);
        arrowNkClose = findViewById(R.id.arrowNkClose);
        llFab = findViewById(R.id.layoutFab);
        arrowNkOpen = findViewById(R.id.arrowNkOpen);
        fabUpload = findViewById(R.id.fabUpload);

        //TODO: supply json from network
        //// populate json /////

        // onlineData = new Gson().fromJson(STRING JSON, MasterData.class);

        dataUtil = new DataUtil(getApplicationContext());
        onlineData = new Gson().fromJson(loadJSONFromAsset(), MasterData.class);
        dataUtil.setMasterData(onlineData);
        masterData = dataUtil.getMasterData();
        categories = masterData.getCategories();

        ////////////////////////

        tabAdapter = new TabAdapter(getSupportFragmentManager(), categories);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);

        String title = masterData.getTitle();
        tvTitle.setText(title);

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        btnStat = 0;
        btnConn.setText(R.string.SCAN);
        btnConn.setOnClickListener(this);

        //TODO: populate network

        //// to populate network list ////
        // drawerAdapter = new DrawerAdapter(this, R.layout.drawer_single_item, LIST AVAILABLE NETWORK);

        List<ConnectionData> conList = new ArrayList<>();
        conList.add(new ConnectionData("ddms1"));
        conList.add(new ConnectionData("ddms2"));
        conList.add(new ConnectionData("ddms3"));

        drawerAdapter = new DrawerAdapter(this, R.layout.drawer_single_item, conList);
        listView.setAdapter(drawerAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("tagLine", String.valueOf(position));
            }
        });

        ///////////////////////////////////

        arrowNkOpen.setOnClickListener(this);
        arrowNkClose.setOnClickListener(this);
        fabUpload.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConn:

                //TODO: button utk lock connection

                // ni button utk lock connection
                // int stat : default = 0, 1 = scan/unlock, 2 = lock,

                //// Example to update data ////
//                NewData newData1 = new Gson().fromJson(loadNewData1(), NewData.class);
//                NewData newData2 = new Gson().fromJson(loadNewData2(), NewData.class);
                NewData newData3 = new Gson().fromJson(loadNewData3(), NewData.class);

                switch (btnStat) {
                    case 0:
                        btnConn.setText(R.string.LOCK);
                        btnStat = 1;
                        dataUtil.updateOnePage(newData3);
                        updateFragment();
                        break;
                    case 1:
                        btnConn.setText(R.string.UNLOCK);
                        btnStat = 2;
                        updateFragment();
                        break;
                    case 2:
                        btnConn.setText(R.string.LOCK);
                        btnStat = 1;
                        break;
                }

                //////////////////////////////////////////////////////////////////////////////

                break;

            case R.id.arrowNkClose:
                if (llFab.getVisibility() == View.VISIBLE && arrowNkOpen.getVisibility() == View.GONE) {
                    llFab.setVisibility(View.GONE);
                    llFab.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_slide_out_right));
                    arrowNkOpen.setVisibility(View.VISIBLE);
                    arrowNkOpen.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));

                }
                break;

            case R.id.arrowNkOpen:
                if (arrowNkOpen.getVisibility() == View.VISIBLE && llFab.getVisibility() == View.GONE) {
                    arrowNkOpen.setVisibility(View.GONE);
                    arrowNkOpen.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out));
                    llFab.setVisibility(View.VISIBLE);
                    llFab.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_slide_in_left));
                }
                break;

            case R.id.fabUpload:
                int pos = tabLayout.getSelectedTabPosition();
                Category category = categories.get(pos);
                if (category.getItems()!=null) {
                    String json = new Gson().toJson(category);
                    Log.d("tagLine", json);
                }

                //TODO: upload data to server

                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }

    // call this method to update data -->
    // dataUtil.updateOnePage(NewData newData);
    // updateFragment();

    // call this function to update fragment ui
    public void updateFragment() {
        int pos = tabLayout.getSelectedTabPosition();
        Fragment fr = tabAdapter.getFragment(pos);
        if (fr != null) {
            ((DynamicFragment)fr).onRefresh(pos);
        }
    }

    // assumed that this is function that return json string from network..
    // supply the string to gson as //// Example to update data //// above..

    public String loadNewData3() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().
                    open("nineconfig.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        Log.d("json", "dpt baca");
        return json;
    }

    public String loadNewData2() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().
                    open("newData2.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        Log.d("json", "dpt baca");
        return json;
    }

    public String loadNewData1() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().
                    open("newData1.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        Log.d("json", "dpt baca");
        return json;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().
                    open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        Log.d("json", "dpt baca");
        return json;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // ref : https://stackoverflow.com/questions/4828636/edittext-clear-focus-on-touch-outside/8766475
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)ev.getRawX(), (int)ev.getRawY())) {
                    v.clearFocus();
                    Keyboard keyboard = new Keyboard();
                    keyboard.hideKeyboard(this);
                }
            }
        }

        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        updateFragment();

        Log.d("tagLine", String.valueOf(position));
        int type = categories.get(position).getType();
        int vis;

        switch (type) {
            case 0:
                if (arrowNkOpen.getVisibility()==View.VISIBLE) {
                    arrowNkOpen.setVisibility(View.GONE);
                    arrowNkOpen.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out));

                }
                break;
            case 1:
                if (arrowNkOpen.getVisibility()==View.GONE) {
                    arrowNkOpen.setVisibility(View.VISIBLE);
                    arrowNkOpen.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
                }
                break;
        }

        if (llFab.getVisibility()==View.VISIBLE) {
            llFab.setVisibility(View.GONE);
            llFab.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_slide_out_right));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
