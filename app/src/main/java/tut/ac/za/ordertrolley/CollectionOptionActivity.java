package tut.ac.za.ordertrolley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;

import tut.ac.za.ordertrolley.classes.Constants;
import tut.ac.za.ordertrolley.classes.Item;
import tut.ac.za.ordertrolley.classes.Order;
import tut.ac.za.ordertrolley.classes.Payment;
import tut.ac.za.ordertrolley.classes.Store;
import tut.ac.za.ordertrolley.classes.Store_Distance;

public class CollectionOptionActivity extends AppCompatActivity {

    private Order order;
    private Button btnCollect;
    private DatabaseReference db;
    private Payment payment;
    private FirebaseAuth mAuth;
    private Store store;
    private double totAmnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_option);

        db = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        store= (Store) intent.getSerializableExtra(Constants.STORE);
        totAmnt =itemPrice(store.getItemList());

        btnCollect = (Button) findViewById(R.id.btnCollect);


        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Store_Distance store_distance = new Store_Distance();
                store_distance.setStore(store);
                order = new Order();
                order.setDate(String.valueOf(new Date()));
                order.setStoreDistance(store_distance);
                payment = new Payment();
                payment.setAmountDue(totAmnt);
                payment.setAmountPaid(0);
                payment.setPaymentType("Payment at Store");
                order.setPayment(payment);

                db.child("Order").child("OrderList").child(mAuth.getCurrentUser().getUid()).push().setValue(order);

                Toast.makeText(CollectionOptionActivity.this,"Order request made",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CollectionOptionActivity.this,CollectionActivity.class);
                intent.putExtra(Constants.ORDER,order);
                startActivity(intent);

            }
        });
    }

    private double itemPrice(List<Item> itemList)
    {
        double amnt = 0;

        for(Item item: itemList)
        {
            amnt+=item.getPrice();

        }

        return  amnt;
    }


}
