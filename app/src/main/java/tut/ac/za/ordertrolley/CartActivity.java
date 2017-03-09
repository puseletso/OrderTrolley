package tut.ac.za.ordertrolley;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import tut.ac.za.ordertrolley.Adapter.StoreCatalogueAdapter;
import tut.ac.za.ordertrolley.classes.Constants;
import tut.ac.za.ordertrolley.classes.Store;
import tut.ac.za.ordertrolley.ui.SpacesItemDecoration;

public class CartActivity extends AppCompatActivity {

    private Store store;
    private RecyclerView rvCart;
    private StoreCatalogueAdapter adapter;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        store = (Store) intent.getSerializableExtra(Constants.STORE);
        rvCart = (RecyclerView) findViewById(R.id.rvCart);
        key = intent.getStringExtra(Constants.KEY);

        getSupportActionBar().setTitle(store.getName()+"(Cart)");
        adapter = new StoreCatalogueAdapter(this, store.getItemList(), true,key,store);
        rvCart.setLayoutManager(new GridLayoutManager(this, 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing_cart);
        rvCart.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rvCart.setAdapter(adapter);
        adapter.notifyDataSetChanged();




    }


    public void onSubmit(View view)
    {
        store.setItemList(StoreCatalogueActivity.orderItems);
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setTitle("Payment Options")
                .setMessage("Do you want to your items to be deliveried?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Intent intent = new Intent(CartActivity.this,GoogleMyAreaActivity.class);
                intent.putExtra(Constants.STORE,store);
                startActivity(intent);

            }
        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(CartActivity.this,CollectionOptionActivity.class);
                        intent.putExtra(Constants.STORE,store);
                        startActivity(intent);
                    }
                });

        Dialog dialog = builder.create();
        dialog.show();

    }








}


