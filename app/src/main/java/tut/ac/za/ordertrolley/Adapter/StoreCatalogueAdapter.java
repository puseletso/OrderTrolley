package tut.ac.za.ordertrolley.Adapter;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.NumberFormat;
import java.util.List;

import tut.ac.za.ordertrolley.R;
import tut.ac.za.ordertrolley.StoreCatalogueActivity;
import tut.ac.za.ordertrolley.classes.Constants;
import tut.ac.za.ordertrolley.classes.Item;
import tut.ac.za.ordertrolley.classes.Store;

/**
 * Created by ProJava on 1/25/2017.
 */

public class StoreCatalogueAdapter extends RecyclerView.Adapter<StoreCatalogueAdapter.StoreViewHolder> {

    private static List<Item> items;
    private Context context;
    private boolean cart;
    private boolean isUpdate;
    private String key;
    private Store store;



    public StoreCatalogueAdapter(Context context, List<Item> items, boolean cart)
    {
        this.context = context;
        this.items = items;
        this.cart = cart;
    }


    public StoreCatalogueAdapter(Context context, List<Item> items, boolean cart,String key,Store store)
    {
        this.context = context;
        this.items = items;
        this.cart = cart;
        this.key = key;
        this.store= store;
    }




    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(cart== false) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, null);

        }else
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_view, null);
        }
        StoreViewHolder svh = new StoreViewHolder(view);
        return svh;
    }

    @Override
    public void onBindViewHolder(StoreViewHolder holder, final int position) {


        Item item = items.get(position);
        Uri uri = Uri.parse(item.getImageUrl());
        holder.ivItem.setImageURI(uri);

        holder.tvItem.setText(item.getName());
        holder.tvItemPrice.setText("R"+NumberFormat.getInstance().format(item.getPrice()));
        if(cart == false) {




            holder.cdItem.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    final Item item = items.get(position);



                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to add item to cart");
                    builder.setTitle("Cart");

                    View layout = LayoutInflater.from(context).inflate(R.layout.item_inflate_view,null);

                    TextView tvItem = (TextView) layout.findViewById(R.id.tvItem);
                    TextView tvItemPrice = (TextView) layout.findViewById(R.id.tvItemPrice);
                    SimpleDraweeView ivItem = (SimpleDraweeView) layout.findViewById(R.id.ivItem);
                    Uri uri = Uri.parse(item.getImageUrl());
                    ivItem.setImageURI(uri);

                    tvItem.setText(item.getName());
                    tvItemPrice.setText("R"+NumberFormat.getInstance().format(item.getPrice()));

                    builder.setView(layout);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            StoreCatalogueActivity.orderItems.add(item);
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
        }else
        {



            holder.cdItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CharSequence[] options = {"Delete","Update"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);




                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            switch (i)
                            {
                                case 0:



                                 items.remove(position);


                                    notifyDataSetChanged();

                                    break;

                                case 1:

                                    isUpdate = true;
                                    Intent intent = new Intent(context,StoreCatalogueActivity.class);
                                    intent.putExtra(Constants.UPDATE,isUpdate);
                                    intent.putExtra(Constants.STORE,store);
                                    intent.putExtra(Constants.KEY,key);
                                    intent.putExtra(Constants.POSITION,position);
                                    context.startActivity(intent);

                                    break;
                            }

                        }
                    });


                    Dialog dialog = builder.create();
                    dialog.show();

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView ivItem;
        private TextView tvItem;
        private TextView tvItemPrice;
        private CardView cdItem;
        public StoreViewHolder(View view) {
            super(view);

            ivItem = (SimpleDraweeView) view.findViewById(R.id.ivItem);
            tvItem = (TextView) view.findViewById(R.id.tvItem);
            tvItemPrice = (TextView) view.findViewById(R.id.tvItemPrice);
            cdItem = (CardView) view.findViewById(R.id.cdItem);



        }
    }


    public static List<Item> getItems()
    {

        List<Item> itemList = items;
        return itemList;
    }




}



