package com.sudhindra.spider_inductions_task_1.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sudhindra.spider_inductions_task_1.databinding.MenuItemBinding;
import com.sudhindra.spider_inductions_task_1.models.MenuItem;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuItemViewHolder> {

    private ArrayList<MenuItem> menuItems;
    private MenuAdapterListener listener;

    public MenuAdapter(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void setListener(MenuAdapterListener listener) {
        this.listener = listener;
    }

    public interface MenuAdapterListener {
        void onItemClick(int pos);
    }

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {

        private MenuItemBinding binding;

        public MenuItemViewHolder(@NonNull MenuItemBinding menuItemBinding, MenuAdapterListener listener) {
            super(menuItemBinding.getRoot());
            binding = menuItemBinding;

            binding.menuCard.setOnClickListener(view -> listener.onItemClick(getAdapterPosition()));
        }

        public void setDetails(MenuItem menuItem) {
            binding.setMeuItem(menuItem);
        }
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MenuItemBinding binding = MenuItemBinding.inflate(inflater, parent, false);
        return new MenuItemViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        holder.setDetails(menuItems.get(position));
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }
}
