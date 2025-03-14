package com.example.ecommerce.admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ecommerce.ItemDomain;
import com.example.ecommerce.R;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<ItemDomain> {

    private final Context mContext;
    private final List<ItemDomain> productList;

    public ProductListAdapter(Context context, List<ItemDomain> productList) {
        super(context, R.layout.item_product_update, productList);
        this.mContext = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_product_update, parent, false);
        }

        ItemDomain item = productList.get(position);

        TextView nameTextView = convertView.findViewById(R.id.textviewProductName);
        TextView priceTextView = convertView.findViewById(R.id.textviewProductPrice);
        Button buttonUpdate = convertView.findViewById(R.id.updatebutton);

        nameTextView.setText(item.getTitle());
        priceTextView.setText("$" + item.getPrice());

        buttonUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, EditProductActivity.class);
            intent.putExtra("productId", item.getId());
            intent.putExtra("productName", item.getTitle());
            intent.putExtra("productDescription", item.getDescription());
            intent.putExtra("productPrice", item.getPrice());
            mContext.startActivity(intent);
        });

        return convertView;
    }
}
