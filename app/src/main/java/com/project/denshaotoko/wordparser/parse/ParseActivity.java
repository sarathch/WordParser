package com.project.denshaotoko.wordparser.parse;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.denshaotoko.wordparser.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class ParseActivity extends DaggerAppCompatActivity implements ParseContract.View{

    @Inject
    ParsePresenter mParsePresenter;

    @BindView(R.id.et_input)
    EditText mInputText;

    @BindView(R.id.indeterminateProgressBar)
    ProgressBar progressBar;

    @BindView(R.id.rv_words)
    RecyclerView mRecyclerView;

    private WordsAdapter mWordsAdapter;
    private Animation mTranslateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // binding butter knife
        ButterKnife.bind(this);

        // set up the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mWordsAdapter = new WordsAdapter(new ArrayList<String>(0));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mWordsAdapter);

        // load translate animation
        mTranslateAnimation = AnimationUtils.loadAnimation(this,
                R.anim.anim_translate);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mParsePresenter.takeView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mParsePresenter.dropView();
    }

    @OnClick(R.id.bt_parse)
    public void startParser(){
        // invoke parser
        mParsePresenter.parseWordsFromDictionary(mInputText.getText().toString());

        // hide soft keyboard
        InputMethodManager inputManager = (InputMethodManager) getSystemService(ParseActivity.INPUT_METHOD_SERVICE);
        if(inputManager!=null && getCurrentFocus() !=null)
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }


    @Override
    public void updateWordList(List<String> words) {

        Log.v("size:",""+words.size());
        mWordsAdapter.refreshData(words);

        //start translate animation for rv
        mRecyclerView.startAnimation(mTranslateAnimation);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showStatusMessage(String msg) {

        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show();

    }

    public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.MyViewHolder>{

        List<String> wordsList;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case

            @BindView(R.id.tv_word)
            TextView wordView;

            public MyViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

        public WordsAdapter(List<String> wordsList) {
            this.wordsList = wordsList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_words, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

            holder.wordView.setText(wordsList.get(position));

        }

        public void refreshData(List<String> words) {

            if (wordsList != null){
                wordsList.clear();
                wordsList.addAll(words);
            }
            notifyDataSetChanged();

            // scroll to start of the list
            mRecyclerView.getLayoutManager().scrollToPosition(0);
        }

        @Override
        public int getItemCount() {
            return wordsList.size();
        }

    }
}
