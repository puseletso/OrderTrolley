package tut.ac.za.ordertrolley;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tut.ac.za.ordertrolley.classes.Constants;
import tut.ac.za.ordertrolley.classes.Item;
import tut.ac.za.ordertrolley.classes.Order;
import tut.ac.za.ordertrolley.ui.SpacesItemDecoration;

public class PreviousOrderItemsActivity extends AppCompatActivity {

    private DatabaseReference db;
    private RecyclerView rvOrderItem;
    private FirebaseAuth mAuth;
    private FirebaseRecyclerAdapter<Item,ItemViewHolder> adapter;
    private String key;
    private Order order;
    private ProgressBar progressBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_order_items);

        Intent intent = getIntent();

        key = intent.getStringExtra(Constants.KEY);
        order = (Order) intent.getSerializableExtra(Constants.ORDER);
        getSupportActionBar().setTitle(order.getStoreDistance().getStore().getName());
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        db = FirebaseDatabase.getInstance().getReference().child("Order").child("OrderList").child(mAuth.getCurrentUser().getUid()).child(key).child("storeDistance").child("store").child("itemList");
        rvOrderItem = (RecyclerView) findViewById(R.id.rvOrderItem);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing_cart);
        rvOrderItem.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
       // rvOrderItem.setAdapter(adapter);
        rvOrderItem.setLayoutManager(new GridLayoutManager(this,2));


        adapter = new FirebaseRecyclerAdapter<Item, ItemViewHolder>(Item.class,R.layout.cart_item_view,ItemViewHolder.class,db) {
            @Override
            protected void populateViewHolder(ItemViewHolder viewHolder, Item model, int position) {


                progressBar.setVisibility(View.GONE);
                viewHolder.setImage(model.getImageUrl());
                viewHolder.setItemName(model.getName());
                viewHolder.setItemPrice(model.getPrice());


            }
        };


        rvOrderItem.setAdapter(adapter);

    }


    private static  class ItemViewHolder extends  RecyclerView.ViewHolder
    {

        private View mView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }




        public void setImage(String url)
        {

            SimpleDraweeView ivItem = (SimpleDraweeView) mView.findViewById(R.id.ivItem);
            Uri uri = Uri.parse(url);
            ivItem.setImageURI(uri);

        }

        public void  setItemName(String name)
        {
            TextView tvItem = (TextView)mView.findViewById(R.id.tvItem);
            tvItem.setText(name);
        }


        public void setItemPrice(double price)
        {

            TextView   tvItemPrice = (TextView) mView.findViewById(R.id.tvItemPrice);
            tvItemPrice.setText("R"+price);
        }
    }
}
