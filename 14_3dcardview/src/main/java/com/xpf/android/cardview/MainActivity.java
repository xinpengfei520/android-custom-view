package com.xpf.android.cardview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xpf.android.cardview.view.CardAdapter;
import com.xpf.android.cardview.view.CardView;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements CardView.OnCardClickListener {

    private CardFragment cardFragment;
    private static final String[] LETTER_ARRAY = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        CardView cardView = findViewById(R.id.cardView1);
        cardView.setOnCardClickListener(this);
        cardView.setItemSpace(Utils.convertDpToPixelInt(this, 20));

        MyCardAdapter adapter = new MyCardAdapter(this);
        adapter.addAll(Arrays.asList(LETTER_ARRAY));
        cardView.setAdapter(adapter);

        FragmentManager manager = getSupportFragmentManager();
        cardFragment = new CardFragment();
        manager.beginTransaction().add(R.id.contentView, cardFragment).commit();
    }

    @Override
    public void onCardClick(final View view, final int position) {
        Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("text", LETTER_ARRAY[position % LETTER_ARRAY.length]);
        cardFragment.show(view, bundle);
    }

    public class MyCardAdapter extends CardAdapter<String> {

        public MyCardAdapter(Context context) {
            super(context);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        protected View getCardView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                convertView = inflater.inflate(R.layout.item_layout, parent, false);
            }
            TextView tv = convertView.findViewById(R.id.textView1);
            String text = getItem(position % LETTER_ARRAY.length);
            tv.setText(text);
            return convertView;
        }
    }

}
