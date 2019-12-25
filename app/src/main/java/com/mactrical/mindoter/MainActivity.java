package com.mactrical.mindoter;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import fragments.BluetoothFragment;
import fragments.DeviceMapFragment;
import fragments.DiagnosisFragment;
import fragments.FooterFragment;
import fragments.HeaderFragment;
import fragments.MonitorFragment;
import fragments.PastEventsFragment;
import fragments.ReportsFragment;
import fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , DeviceMapFragment.OnFragmentInteractionListener, DiagnosisFragment.OnFragmentInteractionListener ,
                    MonitorFragment.OnFragmentInteractionListener , FooterFragment.OnFragmentInteractionListener, HeaderFragment.OnFragmentInteractionListener,
                    PastEventsFragment.OnFragmentInteractionListener, ReportsFragment.OnFragmentInteractionListener ,
                    SettingsFragment.OnFragmentInteractionListener , BluetoothFragment.OnFragmentInteractionListener {


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    LinearLayout mHeaderlayout;
    LinearLayout mMainContentlayout;
    LinearLayout mFooterlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setItemIconTintList(null);


        mHeaderlayout       = findViewById(R.id.ECGDetailsLinearLayout);
        mMainContentlayout  = findViewById(R.id.MainContentLinearLayout);
        mFooterlayout       = findViewById(R.id.ECGFooterLinerLayout);


        /* load Page header */
        loadHeader();

        /* By Default Load Diagnosis Page */
        loadCardiacMonitorPage();

        /*load page footer */
        loadFooter();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cardiacmonitor ) {
            loadCardiacMonitorPage();
        } else if (id == R.id.nav_pastevents) {
            loadPastEventsPage();
        } else if (id == R.id.nav_diagnosis) {
            removeHeader();
            loadDiagnosisPage();
        } else if (id == R.id.nav_settings) {
            removeHeader();
            loadSettingsPage();
        } else if (id == R.id.nav_report) {
            removeHeader();
            loadReportsPage();
        } else if (id == R.id.nav_device_map){
            removeHeader();
            loadDeviceMapPage();
        }else if (id == R.id.nav_blueconnect){
            removeHeader();
            loadBluetoothPage();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private  void loadCardiacMonitorPage(){

        loadECGPage();

        fragmentManager = getSupportFragmentManager();
        MonitorFragment monitorFragment = MonitorFragment.newInstance();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.MainContentFrameLayout,monitorFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private  void loadPastEventsPage(){

        loadECGPage();

        fragmentManager = getSupportFragmentManager();
        PastEventsFragment pastEventsFragment = PastEventsFragment.newInstance();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.MainContentFrameLayout,pastEventsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private  void loadReportsPage(){

        loadNonECGPage();

        fragmentManager = getSupportFragmentManager();
        ReportsFragment reportsFragment = ReportsFragment.newInstance();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.MainContentFrameLayout,reportsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private  void loadDiagnosisPage(){

        loadNonECGPage();

        fragmentManager = getSupportFragmentManager();
        DiagnosisFragment diagnosisFragment = DiagnosisFragment.newInstance();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.MainContentFrameLayout,diagnosisFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private  void loadSettingsPage(){

        loadNonECGPage();

        fragmentManager = getSupportFragmentManager();
        SettingsFragment settingsFragment = SettingsFragment.newInstance();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.MainContentFrameLayout,settingsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void loadDeviceMapPage(){

        loadNonECGPage();

        fragmentManager = getSupportFragmentManager();
        DeviceMapFragment deviceMapFragment = DeviceMapFragment.newInstance();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.MainContentFrameLayout,deviceMapFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void loadBluetoothPage(){
        loadNonECGPage();

        fragmentManager = getSupportFragmentManager();
        BluetoothFragment bluetoothFragment = BluetoothFragment.newInstance();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.MainContentFrameLayout, bluetoothFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private  void loadHeader(){
        fragmentManager = getSupportFragmentManager();
        HeaderFragment headerFragment = HeaderFragment.newInstance();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ECGDetailsFrameLayout,headerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void loadFooter(){
        fragmentManager = getSupportFragmentManager();
        FooterFragment footerFragment = FooterFragment.newInstance();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ECGFooterFrameLayout,footerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void removeHeader(){
        fragmentManager = getSupportFragmentManager();
        HeaderFragment headerFragment = HeaderFragment.newInstance();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(headerFragment);
        fragmentTransaction.commit();

    }

    private void loadNonECGPage(){

        /* Disable Header */
        mHeaderlayout.setVisibility(View.GONE);

        /* Set MainContent layout to cover header layout position */
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 9.5f);
        mMainContentlayout.setLayoutParams(param);

    }

    private void loadECGPage(){

        /*Enable Header */
        mHeaderlayout.setVisibility(View.VISIBLE);

        /* Set MainContent layout to cover header layout position */
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 8.0f);
        mMainContentlayout.setLayoutParams(param);
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
