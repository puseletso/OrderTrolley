package tut.ac.za.ordertrolley;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import tut.ac.za.ordertrolley.classes.Constants;
import tut.ac.za.ordertrolley.classes.Item;
import tut.ac.za.ordertrolley.classes.Store;
import tut.ac.za.ordertrolley.ui.FragmentBaby;
import tut.ac.za.ordertrolley.ui.FragmentCleaning;
import tut.ac.za.ordertrolley.ui.FragmentGrocery;
import tut.ac.za.ordertrolley.ui.FragmentHealth;

public class StoreCatalogueActivity extends AppCompatActivity {

    private static Store store;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static List<Item> orderItems = new ArrayList<>();
    private  String key;
    private String userID;
    private boolean isUpdate;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_catalogue);

        orderItems.clear();
        Intent intent = getIntent();
        store = (Store) intent.getSerializableExtra(Constants.STORE);
        key = intent.getStringExtra(Constants.KEY);
        userID = intent.getStringExtra(Constants.USERID);
        isUpdate = intent.getBooleanExtra(Constants.UPDATE,false);
        position = intent.getIntExtra(Constants.POSITION,-1);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());



        FragmentBaby baby = new FragmentBaby();
        baby.setKey(key);
        baby.setUpdate(isUpdate);
        baby.setPosition(position);
        baby.setStore(store);


        FragmentCleaning cleaning = new FragmentCleaning();
        cleaning.setKey(key);
        cleaning.setUpdate(isUpdate);
        cleaning.setPosition(position);
        cleaning.setStore(store);

        FragmentGrocery grocery = new FragmentGrocery();
        grocery.setKey(key);
        grocery.setUpdate(isUpdate);
        grocery.setPosition(position);
        grocery.setStore(store);

        FragmentHealth health = new FragmentHealth();
        health.setKey(key);
        health.setUpdate(isUpdate);
        health.setPosition(position);
        health.setStore(store);



       /* FragmentBeer beer = new FragmentBeer();
        beer.setKey(key);

        FragmentVodka vodka = new FragmentVodka();
        vodka.setKey(key);

        FragmentWine wine = new FragmentWine();
        wine.setKey(key);


        adapter.addFragment(beer, "Beer");
        adapter.addFragment(vodka, "Vodka");
        adapter.addFragment(wine, "Wine");*/

        adapter.addFragment(baby, "Baby");
        adapter.addFragment(grocery, "Grocery");
        adapter.addFragment(health, "Health");
        adapter.addFragment(cleaning, "Household");

        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store_catalogue, menu);



        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // User user  = fh.getUSer(mFirebaseUser.getUid());


        int id = item.getItemId();


        if(id == R.id.action_cart)
        {

            store.setItemList(orderItems);
            Intent intent = new Intent(StoreCatalogueActivity.this,CartActivity.class);
            intent.putExtra(Constants.STORE,store);
            intent.putExtra(Constants.USERID,userID);
            intent.putExtra(Constants.KEY,key);
            startActivity(intent);
        }








        return super.onOptionsItemSelected(item);
    }


}
