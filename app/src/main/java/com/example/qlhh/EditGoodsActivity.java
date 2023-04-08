package com.example.qlhh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlhh.databinding.ActivityEditGoodsBinding;

import java.util.List;

public class EditGoodsActivity extends AppCompatActivity {

    private Goods mGoods;

    ActivityEditGoodsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goods);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_goods);;

        binding.buttonDelete.setVisibility(View.GONE);

        mGoods = (Goods) getIntent().getParcelableExtra("selectedGoods");

        if (mGoods != null) {
            binding.setGoods(mGoods);

            binding.buttonDelete.setVisibility(View.VISIBLE);
        }

        binding.buttonSave.setOnClickListener(view -> {
            saveGoods();
        });


        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteGoods();
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelEdit();
            }
        });
    }

    private void saveGoods() {
        String name = binding.editGoodsName.getText().toString();
        String category = binding.editGoodsCategory.getText().toString();

        if (name.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Please enter both name and category", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mGoods == null) {
            mGoods = new Goods();
        }

        mGoods.setTenloai(name);
        mGoods.setTenloaikd(category);

        if (mGoods.getIdloai() > 0) {

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    // Perform long-running operation here, such as sending a network request
                    if (GoodsManager.getInstance().updateGoods(mGoods)) {
                        setResult(RESULT_OK);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Goods updated", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Failed to update goods", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    }
                    return null;
                }
            }.execute();
        } else {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    // Perform long-running operation here, such as sending a network request
                    if (GoodsManager.getInstance().addGoods(mGoods)) {
                        setResult(RESULT_OK);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Goods added", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Failed to add goods", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    }
                    return null;
                }
            }.execute();
        }
    }

    private void deleteGoods() {
        if (mGoods != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    // Perform long-running operation here, such as sending a network request
                    if (GoodsManager.getInstance().deleteGoods(mGoods)) {
                        setResult(RESULT_OK);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Goods deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Failed to delete goods", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    }
                    return null;
                }
            }.execute();
        }
    }

    private void cancelEdit() {
        setResult(RESULT_OK);
        finish();
    }
}