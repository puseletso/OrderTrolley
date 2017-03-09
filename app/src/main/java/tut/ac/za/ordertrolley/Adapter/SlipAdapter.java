package tut.ac.za.ordertrolley.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tut.ac.za.ordertrolley.R;
import tut.ac.za.ordertrolley.classes.Item;

/**
 * Created by ProJava on 2/9/2017.
 */

public class SlipAdapter extends RecyclerView.Adapter<SlipAdapter.SlipViewHolder> {

    private List<Item> itemList;
    private Context context;


    public SlipAdapter(Context context, List<Item> itemList)
    {
        this.context = context;
        this.itemList = itemList;
    }
    @Override
    public SlipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slip_view,parent,false);

        SlipViewHolder svh = new SlipViewHolder(view);
        return svh;
    }

    @Override
    public void onBindViewHolder(SlipViewHolder holder, int position) {

        Item item = itemList.get(position);


        holder.tvItemName.setText("Item: " + item.getName());
        holder.tvItemPrice.setText("Price: R"+item.getPrice() );
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class SlipViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItemName;
        private TextView tvItemPrice;
        public SlipViewHolder(View view) {
            super(view);

            tvItemName = (TextView) view.findViewById(R.id.tvItemName);
            tvItemPrice = (TextView) view.findViewById(R.id.tvItemPrice);

        }
    }
}
