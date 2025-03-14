package com.example.ecommerce.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ecommerce.ItemDomain;
import com.example.ecommerce.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DeleteProductAdapter extends ArrayAdapter<ItemDomain> {

    private final Context mContext;
    private final List<ItemDomain> itemList;
    private final DatabaseReference mDatabaseReference;

    public DeleteProductAdapter(Context context, List<ItemDomain> itemList) {
        super(context, R.layout.list_deleted, itemList);
        this.mContext = context;
        this.itemList = itemList;
        this.mDatabaseReference = FirebaseDatabase.getInstance().getReference("Items");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_deleted, parent, false);
        }

        ItemDomain item = itemList.get(position);

        TextView textViewProductName = convertView.findViewById(R.id.textviewProductDeleteName);
        TextView textViewProductPrice = convertView.findViewById(R.id.textviewProductDeletPrice);
        Button deleteBtn = convertView.findViewById(R.id.Deletebutton);

        textViewProductName.setText(item.getTitle());
        textViewProductPrice.setText("$" + item.getPrice());

        String itemId = item.getId();
        deleteBtn.setOnClickListener(v -> deleteProduct(itemId, position));

        return convertView;
    }

    private void deleteProduct(String itemId, int position) {
        mDatabaseReference.child(itemId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                itemList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(mContext, "Item deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Failed to delete item", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
