package com.sudhindra.spider_inductions_task_1.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sudhindra.spider_inductions_task_1.R;
import com.sudhindra.spider_inductions_task_1.adapters.MenuAdapter;
import com.sudhindra.spider_inductions_task_1.databinding.FragmentMenuBinding;
import com.sudhindra.spider_inductions_task_1.models.MenuItem;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;

    private NavController navController;

    // UI
    private ArrayList<MenuItem> menuItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        initMenu();
        buildRecyclerView();
    }

    private void initMenu() {
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.drawable.alarm_icon, "Alarm"));
        menuItems.add(new MenuItem(R.drawable.timer_icon, "Timer"));
        menuItems.add(new MenuItem(R.drawable.stopwatch_icon, "Stopwatch"));
    }

    private void buildRecyclerView() {
        MenuAdapter adapter = new MenuAdapter(menuItems);
        adapter.setListener(listener);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
    }

    private MenuAdapter.MenuAdapterListener listener = pos -> {
        switch (pos) {
            case 0:
                showAlarmScreen();
                break;
            case 1:
                showTimerScreen();
                break;
            case 2:
                showStopwatchScreen();
                break;
        }
    };

    private void showAlarmScreen() {
        navController.navigate(R.id.show_alarms);
    }

    private void showTimerScreen() {
        Toast.makeText(requireContext(), "Work in Progress", Toast.LENGTH_SHORT).show();
    }

    private void showStopwatchScreen() {
        Toast.makeText(requireContext(), "Work in Progress", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}