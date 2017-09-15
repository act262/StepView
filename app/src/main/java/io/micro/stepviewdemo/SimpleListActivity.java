package io.micro.stepviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.micro.stepview.StepView;
import io.micro.stepviewdemo.model.StepModel;

public class SimpleListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<StepModel> mDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);

        mockData();

        recyclerView = findViewById(android.R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new VerticalAdapter());
    }

    private void mockData() {
        mDataSet = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            StepModel stepModel = new StepModel();
            stepModel.text = "This is text " + i;
            mDataSet.add(stepModel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_orientation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.vertical:
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new VerticalAdapter());
                break;
            case R.id.horizontal:
                recyclerView.setLayoutManager(
                        new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(new HorizontalAdapter());
                break;
        }
        return true;
    }

    private abstract class StepLineAdapter extends RecyclerView.Adapter<StepHolder> {

        @Override
        public void onBindViewHolder(StepHolder holder, int position) {
            holder.bindView(mDataSet.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

    }

    private class VerticalAdapter extends StepLineAdapter {
        @Override
        public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_simple_list_step_vertical, parent, false);
            return new StepHolder(inflate);
        }
    }

    private class HorizontalAdapter extends StepLineAdapter {

        @Override
        public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_simple_list_step_horizontal, parent, false);
            return new StepHolder(inflate);
        }
    }

    private class StepHolder extends RecyclerView.ViewHolder {
        private StepView stepView;
        private TextView textView;

        StepHolder(View itemView) {
            super(itemView);
            stepView = itemView.findViewById(R.id.stepView);
            textView = itemView.findViewById(R.id.text);
        }

        void bindView(StepModel stepModel) {
            textView.setText(stepModel.text);
        }
    }
}
