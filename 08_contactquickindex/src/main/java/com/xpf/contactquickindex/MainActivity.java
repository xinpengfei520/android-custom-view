package com.xpf.contactquickindex;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends Activity {

    private ListView listView;
    private TextView tv_main_word;
    private IndexView iv_main_words;
    private Handler handler = new Handler();
    private ArrayList<Person> persons;
    private MyAdapter adapter;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        tv_main_word = (TextView) findViewById(R.id.tv_main_word);
        iv_main_words = (IndexView) findViewById(R.id.iv_main_words);
        //设置按下字母变化的监听
        iv_main_words.setOnTextChangeListener(new MyOnTextChangeListener());

        initData();
        //设置适配器
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return persons.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item_main, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_word = (TextView) convertView.findViewById(R.id.tv_word);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //根据位置得到对应的数据
            String word = persons.get(position).getPinyin().substring(0, 1);
            String name = persons.get(position).getName();
            viewHolder.tv_word.setText(word);
            viewHolder.tv_name.setText(name);

            if (position == 0) {
                viewHolder.tv_word.setVisibility(View.VISIBLE);
            } else {
                //前一条的首字母
                String wordPre = persons.get(position - 1).getPinyin().substring(0, 1);
                if (wordPre.equals(word)) {
                    viewHolder.tv_word.setVisibility(View.GONE);
                } else {
                    viewHolder.tv_word.setVisibility(View.VISIBLE);
                }
            }

            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_word;
        TextView tv_name;
    }

    class MyOnTextChangeListener implements IndexView.OnTextChangeListener {

        @Override
        public void onTextChange(String word) {
            updateWord(word);
            updateList(word);
        }
    }

    /**
     * A~Z
     *
     * @param word
     */
    private void updateList(String word) {
        for (int i = 0; i < persons.size(); i++) {
            String listWord = persons.get(i).getPinyin().substring(0, 1);
            if (word.equals(listWord)) {
                listView.setSelection(i);
                break;
            }
        }
    }

    private void updateWord(String word) {
        tv_main_word.setVisibility(View.VISIBLE);
        tv_main_word.setText(word);
        handler.removeCallbacksAndMessages(null);//把所有的消息移除
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //在主线程
                Log.e(TAG, Thread.currentThread().getName() + "---");
                tv_main_word.setVisibility(View.GONE);
            }
        }, 2000);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        persons = new ArrayList<>();
        persons.add(new Person("李晓飞"));
        persons.add(new Person("杨大脚"));
        persons.add(new Person("胡继群"));
        persons.add(new Person("刘畅"));

        persons.add(new Person("钟泽兴"));
        persons.add(new Person("尹革新"));
        persons.add(new Person("安传鑫"));
        persons.add(new Person("张骞壬"));

        persons.add(new Person("温松"));
        persons.add(new Person("李凤秋"));
        persons.add(new Person("刘甫"));
        persons.add(new Person("娄全超"));
        persons.add(new Person("张大大"));

        persons.add(new Person("王英杰"));
        persons.add(new Person("李振南"));
        persons.add(new Person("孙仁政"));
        persons.add(new Person("唐春雷"));
        persons.add(new Person("牛鹏伟"));
        persons.add(new Person("姜宇航"));

        persons.add(new Person("刘挺"));
        persons.add(new Person("张洪瑞"));
        persons.add(new Person("张建忠"));
        persons.add(new Person("侯亚帅"));
        persons.add(new Person("刘帅"));

        persons.add(new Person("乔竞飞"));
        persons.add(new Person("徐雨健"));
        persons.add(new Person("吴亮"));
        persons.add(new Person("王兆霖"));

        persons.add(new Person("阿三"));
        persons.add(new Person("周杰伦"));
        persons.add(new Person("的哥"));

        //排序
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });
    }
}
