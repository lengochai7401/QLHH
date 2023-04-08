package com.example.qlhh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.qlhh.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd;
    private RecyclerView rvGoodsList;
    private GoodsAdapter goodsAdapter;
    private GoodsManager goodsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        rvGoodsList = findViewById(R.id.rvGoodsList);

        // Create GoodsManager instance
        goodsManager = GoodsManager.getInstance();

        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvGoodsList.setLayoutManager(layoutManager);
        goodsAdapter = new GoodsAdapter(this, new ArrayList<Goods>());
        rvGoodsList.setAdapter(goodsAdapter);

        // Get goods list in a separate thread
        GetAllGoodsTask getAllGoodsTask = new GetAllGoodsTask();
        getAllGoodsTask.execute();

        // Set up click listeners for buttons


        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditGoodsActivity.class);
            startActivityForResult(intent, GoodsAdapter.EDIT_GOODS_REQUEST_CODE);
        });

    }

    private class GetAllGoodsTask extends AsyncTask<Void, Void, List<Goods>> {
        @Override
        protected List<Goods> doInBackground(Void... voids) {
            return goodsManager.getAllGoods();
        }

        @Override
        protected void onPostExecute(List<Goods> goodsList) {
            super.onPostExecute(goodsList);
            goodsAdapter.setData(goodsList);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GoodsAdapter.EDIT_GOODS_REQUEST_CODE && resultCode == RESULT_OK) {
            new GetAllGoodsTask().execute();

        }
    }
}
