package com.task.transactiontask.views.activity;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.task.transactiontask.R;
import com.task.transactiontask.databinding.ActivityDetailLayoutBinding;
import com.task.transactiontask.model.Transaction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TransactionDetailsActivity extends AppCompatActivity {

    Transaction transaction;
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 41;
    int pageHeight = 1120;
    int pagewidth = 792;
    ActivityDetailLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_layout);

        transaction = (Transaction) getIntent().getSerializableExtra("transaction");

        binding.tvStatus.setText("Status: " + transaction.getTransactionStatus());
        binding.tvAmount.setText("Amount Rs: " + transaction.getAmount());
        binding.tvDate.setText("Date: " + transaction.getTransactionDate());
        binding.tvNarration.setText("Description: " + transaction.getNarration());

        binding.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        ActivityCompat.requestPermissions(TransactionDetailsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                READ_STORAGE_PERMISSION_REQUEST_CODE);

                    } else {

                        generatePDF(transaction.getTransactionDate(), transaction.getAmount(), transaction.getTransactionStatus(), transaction.getNarration());

                    }
                }


            }
        });
    }


    private void generatePDF(String transactionDate, String amount, String transactionStatus, String narration) {
        PdfDocument pdfDocument = new PdfDocument();
        Paint heading_title = new Paint();
        Paint elements = new Paint();
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
        Canvas canvas = myPage.getCanvas();

        heading_title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));


        heading_title.setTextSize(20);

        heading_title.setColor(ContextCompat.getColor(TransactionDetailsActivity.this, R.color.purple_200));

        elements.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));


        elements.setTextSize(15);

        elements.setColor(ContextCompat.getColor(TransactionDetailsActivity.this, R.color.black));

        canvas.drawText("Description: " + narration, 209, 110, elements);
        canvas.drawText("Amount Rs. " + amount, 209, 90, elements);
        canvas.drawText("Transaction date: " + transactionDate, 209, 70, elements);
        canvas.drawText("Status: " + transactionStatus, 209, 50, elements);
        canvas.drawText("Transaction Details: ", 209, 20, heading_title);

        elements.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        elements.setColor(ContextCompat.getColor(TransactionDetailsActivity.this, R.color.purple_200));
        elements.setTextSize(15);

        elements.setTextAlign(Paint.Align.CENTER);

        pdfDocument.finishPage(myPage);

        String timeWindow = System.currentTimeMillis() + "";
        String lastFourDigits = "";
        if (timeWindow.length() > 4) {
            lastFourDigits = timeWindow.substring(timeWindow.length() - 4);
        } else {
            lastFourDigits = timeWindow;
        }
//storage/emulated/0/Android/data/com.task.transactiontask/files/Movies/transaction1528.pdf

        // File file = new File(Environment.getExternalStorageDirectory(), "transaction" + lastFourDigits + ".pdf");
        String absolutePath = getFilePath(lastFourDigits);
        File file = new File(absolutePath);


        try {
            pdfDocument.writeTo(new FileOutputStream(file));

            Toast.makeText(TransactionDetailsActivity.this, "PDF generated in \n"+ absolutePath, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }

    private String getFilePath(String digits) {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(directory, "transaction" + digits + ".pdf");
        return file.getAbsolutePath();
    }


}