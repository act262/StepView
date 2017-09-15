package io.micro.stepviewdemo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.micro.stepview.StepView;
import io.micro.stepviewdemo.model.StepModel;

public class MainActivity extends AppCompatActivity {

    private List<StepModel> mDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mockData();

        RecyclerView recyclerView = findViewById(android.R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StepLineAdapter());
        recyclerView.addItemDecoration(new Divider(this));
    }

    private void mockData() {
        mDataSet = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            StepModel stepModel = new StepModel();
            stepModel.text = "This is text " + i;
            mDataSet.add(stepModel);
        }
    }

    private class StepLineAdapter extends RecyclerView.Adapter<StepHolder> {

        @Override
        public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_step, parent, false);
            return new StepHolder(inflate);
        }

        @Override
        public void onBindViewHolder(StepHolder holder, int position) {
            holder.bindView(mDataSet.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
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

    private class Divider extends DividerItemDecoration {
        public Divider(Context context) {
            super(context, VERTICAL);
            Drawable div = ContextCompat.getDrawable(context, R.drawable.line);
            setDrawable(div);
        }

    }
}

