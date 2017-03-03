package edu.usc.a_karmakar.congress_lookup_hw9;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "edu.usc.a_karmakar.congress_lookup_hw9.MESSAGE";
    private String[] mNavLinks;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Bundle sis = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_menu_launcher);
        setSupportActionBar(myToolbar);

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);         // Set the drawer toggle as the DrawerListener
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.nav_legs) {
                    setTitle("Legislators");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.legislator_frame, LegsParentFragment.newInstance())
                            .commit();
                } else if (id == R.id.nav_bill) {
                    setTitle("Bills");
                    if (sis == null)
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.legislator_frame, BillParentFragment.newInstance())
                                .commit();
                } else if (id == R.id.nav_comm) {
                    setTitle("Committees");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.legislator_frame, CommParentFragment.newInstance())
                            .commit();
                } else if (id == R.id.nav_favorite) {
                    setTitle("Favorites");
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.legislator_frame, FavoriteMainFragment.newInstance())
                            .commit();
                } else if (id == R.id.nav_about) {
                    setTitle("About Me");
                    Intent intent = new Intent(MainActivity.this, PersonalDetailsActivity.class);
                    startActivity(intent);
                }

                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        // setting up first fragment
        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.legislator_frame, LegsParentFragment.newInstance())
                    .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_settings:
                    // User chose the "Settings" item, show the app settings UI...
                    return true;

                default:
                    // If we got here, the user's action was not recognized.
                    // Invoke the superclass to handle it.
                    return super.onOptionsItemSelected(item);

            }
        }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
}


