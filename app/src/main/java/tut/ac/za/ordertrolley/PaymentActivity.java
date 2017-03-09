package tut.ac.za.ordertrolley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tut.ac.za.ordertrolley.classes.Constants;
import tut.ac.za.ordertrolley.classes.Order;
import tut.ac.za.ordertrolley.classes.Payment;

public class PaymentActivity extends AppCompatActivity {
    private Order order;
    private Button btnCash;
    private Button btnCollect;
    private DatabaseReference db;
    private Payment payment;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        db = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra(Constants.ORDER);

        btnCollect = (Button) findViewById(R.id.btnCollect);
        btnCash = (Button) findViewById(R.id.btnCash);


    }


    public void onCash(View view)
    {

        payment = new Payment();
        payment.setAmountDue(Double.parseDouble(String.format("%.2f",order.getItemPrice() + order.getDeliveryFee())));
        payment.setAmountPaid(0);
        payment.setPaymentType("Cash");
        order.setPayment(payment);
      //  db.child("Order").child("OrderList").child(order.getUserID()).push().setValue(order);


       // Toast.makeText(PaymentActivity.this,"Order request made",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PaymentActivity.this,SlipActivity.class);
        intent.putExtra(Constants.ORDER,order);
        startActivity(intent);


    }



}
