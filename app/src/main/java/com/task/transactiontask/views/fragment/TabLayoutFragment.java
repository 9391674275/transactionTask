package com.task.transactiontask.views.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.task.transactiontask.R;
import com.task.transactiontask.databinding.TabLayoutBinding;
import com.task.transactiontask.model.Transaction;
import com.task.transactiontask.views.adapter.TransactionAdapter;

public class TabLayoutFragment extends Fragment {

    private Transaction transactions;
    private String status;
    private TransactionAdapter adapter;

    TabLayoutBinding tabLayoutBinding;

    public TabLayoutFragment() {
        // Required empty public constructor
    }

    public static TabLayoutFragment newInstance(Transaction transactions, String status) {
        TabLayoutFragment fragment = new TabLayoutFragment();
        fragment.transactions = transactions;
        fragment.status = status;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tabLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.tab_layout, container, false);

        tabLayoutBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TransactionAdapter(getContext(), transactions);

        tabLayoutBinding.recyclerview.setAdapter(adapter);

        return tabLayoutBinding.getRoot();
    }

}
