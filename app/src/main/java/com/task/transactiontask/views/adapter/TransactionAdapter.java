package com.task.transactiontask.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.task.transactiontask.R;
import com.task.transactiontask.databinding.CardViewLayoutBinding;
import com.task.transactiontask.model.Transaction;
import com.task.transactiontask.views.activity.TransactionDetailsActivity;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private Context mCtx;
    private Transaction transactionList;

    CardViewLayoutBinding binding;

    public TransactionAdapter(Context mCtx, Transaction transactionList) {
        this.mCtx = mCtx;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_view_layout, parent, false);

        return new TransactionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList;

        binding.title.setText("Transaction Details");
        binding.status.setText(transaction.getTransactionStatus());
        binding.amount.setText("   Rs. " + transaction.getAmount());

        binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx, TransactionDetailsActivity.class);
                intent.putExtra("transaction", transaction);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        CardViewLayoutBinding binding;

        public TransactionViewHolder(@NonNull CardViewLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }


}


