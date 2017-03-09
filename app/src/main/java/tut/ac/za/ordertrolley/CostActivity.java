package tut.ac.za.ordertrolley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.List;

import tut.ac.za.ordertrolley.classes.Constants;
import tut.ac.za.ordertrolley.classes.Item;
import tut.ac.za.ordertrolley.classes.Order;
import tut.ac.za.ordertrolley.classes.Store_Distance;

public class CostActivity extends AppCompatActivity {

    private Store_Distance storeDistance;
    private TextView tvItemPrice;
    private TextView tvDistance;
    private Button btnSubmit;
    private TextView tvTotalPrice;
    private static final double PRICE_PER_KM = 1;
    private Order order;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);

        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        storeDistance = (Store_Distance) intent.getSerializableExtra(Constants.STORE_DISTANCE);
        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvItemPrice = (TextView) findViewById(R.id.tvItemPrice);
        tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        intialize();

    }


    private void intialize()
    {

        tvDistance.setText("Delivery Distance: "+String.format("%.3f",storeDistance.getTotalDistance())+" KM");
        tvItemPrice.setText("Total Item Price: R"+String.valueOf(totItemPrice()));
        tvTotalPrice.setText("Total Amount: R" +String.format("%.2f",totPrice()));

    }

    private double totItemPrice()
    {

        List<Item> items = storeDistance.getStore().getItemList();

        double totAmt = 0;
        for(Item item: items)
        {

            totAmt+=item.getPrice();
        }

        return totAmt;
    }


    private double totPrice()
    {

        double total =0;


        total = deliveryFee() + totItemPrice();

        return  total;
    }


    private double deliveryFee()
    {
       double amnt =0;
        double deliveryDistance = Double.parseDouble(String.format("%.3f",storeDistance.getTotalDistance()));
        amnt = deliveryDistance *  PRICE_PER_KM;
        return amnt;
    }


    public void onSubmit(View view)
    {

        order = new Order();
        order.setItemPrice(totItemPrice());
        order.setDeliveryFee(deliveryFee());
        order.setStoreDistance(storeDistance);
        order.setDate(String.valueOf(new Date()));
        order.setUserID(mAuth.getCurrentUser().getUid());

        order.setStoreDistance(storeDistance);
        Intent intent = new Intent(CostActivity.this,PaymentActivity.class);
        intent.putExtra(Constants.ORDER,order);
        startActivity(intent);
    }
}
