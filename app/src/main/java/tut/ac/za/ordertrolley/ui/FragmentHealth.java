package tut.ac.za.ordertrolley.ui;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import tut.ac.za.ordertrolley.R;
import tut.ac.za.ordertrolley.StoreCatalogueActivity;
import tut.ac.za.ordertrolley.classes.Item;
import tut.ac.za.ordertrolley.classes.Store;

/**
 * Created by ProJava on 1/25/2017.
 */

public class FragmentHealth extends Fragment {

    private List<Item> items;

    private RecyclerView rvStoreItem;
    private String key;
    private DatabaseReference db;
    private FirebaseRecyclerAdapter<Item,ViewItemHolder> adapter;
    private boolean isUpdate;
    private int position;
    private Store store;
    private ProgressBar progressBar;




    public FragmentHealth() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        items = new ArrayList<Item>();
        final View view = inflater.inflate(R.layout.fragment_store, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        rvStoreItem = (RecyclerView) view.findViewById(R.id.rvStoreItem);
        db= FirebaseDatabase.getInstance().getReference().child("Shop").child("ShopList").child(getKey()).child("itemList");

        Query query = db.orderByChild("catergory").equalTo("health & beaty");

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing_cart);
        rvStoreItem = (RecyclerView)  view.findViewById(R.id.rvStoreItem);
        rvStoreItem.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rvStoreItem.setLayoutManager(new GridLayoutManager(getActivity(),2));

        adapter = new FirebaseRecyclerAdapter<Item,ViewItemHolder>(Item.class,R.layout.item_view,ViewItemHolder.class,query) {
            @Override
            protected void populateViewHolder(ViewItemHolder viewHolder, final Item model, int position) {

                progressBar.setVisibility(View.GONE);
                viewHolder.setImage(model.getImageUrl());
                viewHolder.setItemName(model.getName());
                viewHolder.setItemPrice(model.getPrice());
                CardView cardView = viewHolder.getCardView();

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {





                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Do you want to add item to cart");
                        builder.setTitle("Cart");

                        View layout = LayoutInflater.from(getContext()).inflate(R.layout.item_inflate_view,null);

                        TextView tvItem = (TextView) layout.findViewById(R.id.tvItem);
                        TextView tvItemPrice = (TextView) layout.findViewById(R.id.tvItemPrice);
                        SimpleDraweeView ivItem = (SimpleDraweeView) layout.findViewById(R.id.ivItem);
                        Uri uri = Uri.parse(model.getImageUrl());
                        ivItem.setImageURI(uri);

                        tvItem.setText(model.getName());
                        tvItemPrice.setText("R"+ NumberFormat.getInstance().format(model.getPrice()));

                        builder.setView(layout);

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(isUpdate == false) {
                                    StoreCatalogueActivity.orderItems.add(model);
                                }else
                                {

                                    StoreCatalogueActivity.orderItems = getStore().getItemList();
                                    StoreCatalogueActivity.orderItems.set(getPosition(),model);
                                }
                            }
                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });


                        AlertDialog dialog = builder.show();
                        dialog.show();
                    }
                });
            }
        };


        rvStoreItem.setAdapter(adapter);




        // Inflate the layout for this fragment
        return view;
    }

    public void setItems(List<Item> items)
    {
        this.items = items;
    }





    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static class ViewItemHolder extends  RecyclerView.ViewHolder {

        private View mView;

        public ViewItemHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }


        public CardView getCardView() {

            CardView cdItem = (CardView) mView.findViewById(R.id.cdItem);
            return cdItem;
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

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
