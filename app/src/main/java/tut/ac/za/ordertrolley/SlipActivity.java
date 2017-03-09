package tut.ac.za.ordertrolley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tut.ac.za.ordertrolley.Adapter.SlipAdapter;
import tut.ac.za.ordertrolley.classes.Constants;
import tut.ac.za.ordertrolley.classes.Order;
import tut.ac.za.ordertrolley.ui.SpacesItemDecoration;

public class SlipActivity extends AppCompatActivity {

    private Order order;
    private RecyclerView rvSlip;
    private SlipAdapter adapter;
    private DatabaseReference db;
    private TextView  tvTotalSlip,tvDeliveryFee;
    private Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slip);

        getSupportActionBar().setTitle("Slip");
        Intent intent = getIntent();
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing_cart);
        order = (Order) intent.getSerializableExtra(Constants.ORDER);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        rvSlip = (RecyclerView) findViewById(R.id.rvSlip);
        rvSlip.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rvSlip.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseDatabase.getInstance().getReference();
        tvTotalSlip = (TextView) findViewById(R.id.tvTotalSlip);
        tvDeliveryFee = (TextView) findViewById(R.id.tvDeliveryFee);
        btnSubmit = (Button)  findViewById(R.id.btnSubmit);
        adapter = new SlipAdapter(this,order.getStoreDistance().getStore().getItemList());

       rvSlip.setAdapter(adapter);

       initialize();

    }

    private void initialize() {
        tvDeliveryFee.setText("Delivery Fee R:" + String.format("%.2f", order.getDeliveryFee()));
        double amnt = order.getDeliveryFee() + order.getItemPrice();
        tvTotalSlip.setText("Total: R" + String.format("%.2f",amnt));
    }

  public void onSubmit(View view)
    {

        db.child("Order").child("OrderList").child(order.getUserID()).push().setValue(order);
        Toast.makeText(SlipActivity.this,"Order request made",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SlipActivity.this,MainActivity.class);
        startActivity(intent);

    }





}
