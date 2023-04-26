package com.task.transactiontask.views.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.task.transactiontask.R;
import com.task.transactiontask.databinding.ActivityMainBinding;
import com.task.transactiontask.model.Transaction;
import com.task.transactiontask.views.fragment.TabLayoutFragment;
import com.task.transactiontask.views.adapter.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Transaction> transactions;
    ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        setupViewPager();

        activityMainBinding.tabLayout.setupWithViewPager(activityMainBinding.pager);
    }

    public void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        transactions = getTransactionsFromJson();

        adapter.addFragment(TabLayoutFragment.newInstance(transactions.get(0), "Success"), "Success");
        adapter.addFragment(TabLayoutFragment.newInstance(transactions.get(1), "Pending"), "Pending");
        adapter.addFragment(TabLayoutFragment.newInstance(transactions.get(2), "Failed"), "Failed");

        activityMainBinding.pager.setAdapter(adapter);
    }

    public List<Transaction> getTransactionsFromJson() {
        String jsonString = "[{\"TransactionStatus\":\"Success\",\"Amount\":\"100.00\",\"TransactionDate\":\"23-08-2022 08:00:00\",\"Narration\":\"Amount paid for Recharge payment\"},{\"TransactionStatus\":\"Pending\",\"Amount\":\"10.00\",\"TransactionDate\":\"25-09-2022 11:0450:00\",\"Narration\":\"Amount paid for Government bill payment\"},{\"TransactionStatus\":\"Failed\",\"Amount\":\"110.00\",\"TransactionDate\":\"24-10-2022 06:54:00\",\"Narration\":\"Amount paid for DTH payment\"}]";

        List<Transaction> transactions = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String transactionStatus = jsonObject.getString("TransactionStatus");
                String amount = jsonObject.getString("Amount");
                String transactionDate = jsonObject.getString("TransactionDate");
                String narration = jsonObject.getString("Narration");
                transactions.add(new Transaction(transactionStatus, amount, transactionDate, narration));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return transactions;
    }

}