package tut.ac.za.ordertrolley;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import tut.ac.za.ordertrolley.classes.Constants;
import tut.ac.za.ordertrolley.classes.Store;
import tut.ac.za.ordertrolley.ui.SpacesItemDecoration;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rvStore;
    private DatabaseReference db;
    private List<Store> stores;
    private FirebaseRecyclerAdapter<Store,StoreViewHolder> adapter;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";
    private TextView tvEmail;
    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);












        db = FirebaseDatabase.getInstance().getReference().child("Shop").child("ShopList");




        adapter = new FirebaseRecyclerAdapter<Store, StoreViewHolder>(Store.class,R.layout.store_view,StoreViewHolder.class,db) {
            @Override
            protected void populateViewHolder(StoreViewHolder viewHolder, final Store model, final int position) {

                progressBar.setVisibility(View.GONE);
                viewHolder.setImage(model.getImageUrl());
                viewHolder.setStoreName(model.getName());

                CardView cdStore = viewHolder.getCardView();

                cdStore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String key = getRef(position).getKey();
                        Intent intent = new Intent(MainActivity.this,StoreMapActivity.class);
                        intent.putExtra(Constants.STORE,model);
                        intent.putExtra(Constants.KEY,key);

                        startActivity(intent);

                    }
                });
            }
        };




        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing_cart);

        rvStore = (RecyclerView) findViewById(R.id.rvStores);
        rvStore.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rvStore.setLayoutManager(new GridLayoutManager(this,2));

        rvStore.setAdapter(adapter);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);


        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        tvEmail.setText(mAuth.getCurrentUser().getEmail());
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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            // Handle the camera action
        } else if (id == R.id.nav_feedback) {

            Intent intent = new Intent(MainActivity.this,FeedbackActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_previous_orders) {

            Intent intent = new Intent(MainActivity.this,PreviousOrderActivity.class);

            startActivity(intent);

        } else if (id == R.id.nav_signOut) {

            mAuth.signOut();

            Toast.makeText(MainActivity.this,"Signed Out",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,UserSignInActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static class StoreViewHolder extends  RecyclerView.ViewHolder{

        private View mView;

        public StoreViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }


        public void setStoreName(String name)
        {

            TextView tvStore = (TextView) mView.findViewById(R.id.tvStore);
            tvStore.setText(name);
        }

        public void setImage(String url)
        {


            Uri uri = Uri.parse(url);
            SimpleDraweeView draweeView = (SimpleDraweeView) mView.findViewById(R.id.ivStore);
            draweeView.setImageURI(uri);

        }


        public CardView getCardView()
        {
            CardView cardView = (CardView) mView.findViewById(R.id.cdStore);

            return  cardView;

        }
    }
}
