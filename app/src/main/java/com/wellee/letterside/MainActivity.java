package com.wellee.letterside;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<NameBean> mNameBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initView() {
        LetterSide letterSide = findViewById(R.id.letter_side);
        final TextView tvLetter = findViewById(R.id.tv_letter);

        final RecyclerView rv = findViewById(R.id.rv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(new RvAdapter(mNameBeans));

        final List<String> letters = new ArrayList<>();
        for (NameBean nameBean : mNameBeans) {
            String letter = nameBean.getLetter();
            letters.add(letter);
        }

        letterSide.setOnTouchItemListener(new LetterSide.OnTouchItemListener() {
            @Override
            public void onTouchItem(String letter, boolean isTouching) {
                if (isTouching) {
                    tvLetter.setText(letter);
                    tvLetter.setVisibility(View.VISIBLE);
                    int position = letters.indexOf(letter);
                    if (position >= 0) {
                        rv.stopScroll();
                        layoutManager.scrollToPositionWithOffset(position, 0);
                    }
                } else {
                    tvLetter.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initData() {
        mNameBeans = new ArrayList<>();
        List<String> names = AssetsUtils.readAssetsTxt(this, "sys_user.txt");
        for (String name : names) {
            NameBean nameBean = new NameBean();
            nameBean.setName(name);
            String s = PinyinUtils.chineseToPinyin(name);
            char c = s.toUpperCase().toCharArray()[0];
            if (c >= 'A' && c <= 'Z') {
                nameBean.setLetter(String.valueOf(c));
            } else {
                nameBean.setLetter("#");
            }
            mNameBeans.add(nameBean);
        }
        Collections.sort(mNameBeans, new Comparator<NameBean>() {
            @Override
            public int compare(NameBean o1, NameBean o2) {
                return o1.getLetter().compareTo(o2.getLetter());
            }
        });
    }
}
