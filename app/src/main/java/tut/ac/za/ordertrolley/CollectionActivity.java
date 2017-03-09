package tut.ac.za.ordertrolley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import tut.ac.za.ordertrolley.classes.Constants;
import tut.ac.za.ordertrolley.classes.Order;

public class CollectionActivity extends AppCompatActivity {

    private TextView tvStoreName;
    private TextView tvDeliveryArea;
    private TextView tvStoreArea;
    private TextView tvDate;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra(Constants.ORDER);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvDeliveryArea = (TextView) findViewById(R.id.tvDeliveryArea);
        tvStoreArea=(TextView) findViewById(R.id.tvStoreArea);
        tvStoreName = (TextView) findViewById(R.id.tvStore);

        initialize();
    }

    public void initialize()
    {

        tvDate.setText(order.getDate());
        tvStoreName.setText(order.getStoreDistance().getStore().getName());
        tvStoreArea.setText(order.getStoreDistance().getStore().getAddress().getStreetName());
        tvDeliveryArea.setText(order.getStoreDistance().getDeliveryArea());
    }
}
