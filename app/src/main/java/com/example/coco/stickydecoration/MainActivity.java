package com.example.coco.stickydecoration;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gavin.com.library.StickyDecoration;
import com.gavin.com.library.listener.GroupListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView mRv;
    RecyclerView.Adapter mAdapter;
    List<City> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //模拟数据
        dataList.addAll(CityUtils.getCityList());
//        dataList.addAll(CityUtils.getCityList());

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRv.setLayoutManager(manager);
        //使用StickyDecoration
        StickyDecoration decoration = StickyDecoration.Builder
                .init(new GroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //组名回调
                        if (dataList.size() > position) {
                            //获取城市对应的省份
                            return dataList.get(position).getProvince();
                        }
                        return null;
                    }
                })
                .setGroupBackground(Color.parseColor("#48BDFF"))    //背景色
                .setGroupHeight(DencityUtils.dip2px(this, 35))       //高度
                .setDivideColor(Color.parseColor("#CCCCCC"))        //分割线颜色
                .setDivideHeight(DencityUtils.dip2px(this, 1))       //分割线高度 (默认没有分割线)
                .setGroupTextColor(Color.BLACK)                     //字体颜色
                .setGroupTextSize(DencityUtils.sp2px(this, 15))      //字体大小
                .setTextSideMargin(DencityUtils.dip2px(this, 10))    // 边距   靠左时为左边距  靠右时为右边距
                .isAlignLeft(false)                                 //靠右显示  （默认靠左）
                .build();
        mRv.addItemDecoration(decoration);

        mAdapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                return new Holder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
                Holder holder = (Holder) viewHolder;
                holder.mTextView.setText(dataList.get(position).getName());
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }
        };
        mRv.setAdapter(mAdapter);

    }
    static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView mTextView;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
