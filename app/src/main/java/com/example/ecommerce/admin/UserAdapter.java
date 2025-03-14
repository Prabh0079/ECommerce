package com.example.ecommerce.admin;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private String currentUserEmail;

    public UserAdapter(List<User> userList, String currentUserEmail) {
        this.userList = userList;
        this.currentUserEmail = currentUserEmail;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);

        holder.txtUserName.setText(user.getName());
        holder.txtUserEmail.setText(user.getEmail());
        holder.txtUserPhone.setText(user.getPhone());

        // Highlight the current user
        if (user.getEmail().equals(currentUserEmail)) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFDDC1")); // Highlight color
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#1E1E1E")); // Default color
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName, txtUserEmail, txtUserPhone;

        public UserViewHolder(View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtUserEmail = itemView.findViewById(R.id.txtUserEmail);
            txtUserPhone = itemView.findViewById(R.id.txtUserPhone);
        }
    }
}
