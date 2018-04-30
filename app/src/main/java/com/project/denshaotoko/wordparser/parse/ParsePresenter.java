package com.project.denshaotoko.wordparser.parse;

import com.project.denshaotoko.wordparser.dict.Dictionary;
import com.project.denshaotoko.wordparser.dict.DictionaryTrie;
import com.project.denshaotoko.wordparser.dict.TrieNode;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class ParsePresenter implements ParseContract.Presenter {

    private final DictionaryTrie mDictionaryTrie;

    @Nullable
    private ParseContract.View mParseView;

    @Inject
    ParsePresenter(DictionaryTrie dictionaryTrie){
        this.mDictionaryTrie = dictionaryTrie;
    }

    @Override
    public void takeView(ParseContract.View view) {
        mParseView = view;
        initDictionary();
    }

    @Override
    public void dropView() {
        mParseView = null;
    }

    @Override
    public void initDictionary() {

        if(mParseView!=null)
            mParseView.showProgressBar();

        mDictionaryTrie.initDictionary(new Dictionary.InitDictionaryCallBack() {
            @Override
            public void onDictionaryInit() {
                if(mParseView != null)
                {
                    mParseView.stopProgressBar();
                    mParseView.showStatusMessage("Dictionary Loaded!");
                }
            }

            @Override
            public void onInitException() {
                if(mParseView != null)
                {
                    mParseView.stopProgressBar();
                    mParseView.showStatusMessage("App might not work correctly!");
                }
            }
        });

    }

    @Override
    public void parseWordsFromDictionary(String input) {

        Set<String > wordSet = new LinkedHashSet<>();

        // dictionary is set in lower case to optimize Trie
        input = input.toLowerCase();

        for(int i=0; i< input.length(); i++){

            if(isSymb(input.charAt(i)))
                continue;

            StringBuilder sb = new StringBuilder();
            sb.append(input.charAt(i));

            TrieNode node = mDictionaryTrie.getLastTrieNode(sb.toString());

            // check if word
            if(mDictionaryTrie.isWord(node))
                wordSet.add(sb.toString());

            // check if prefix
            if(!mDictionaryTrie.isPrefix(node))
                continue;

            for(int j = i+1; j< input.length(); j++){

                if(isSymb(input.charAt(j)))
                    continue;

                sb.append(input.charAt(j));

                node = mDictionaryTrie.getLastTrieNode(sb.toString());

                // check if word
                if(mDictionaryTrie.isWord(node))
                    wordSet.add(sb.toString());

                // check if prefix
                if(!mDictionaryTrie.isPrefix(node))
                    break;
            }

        }

        if(mParseView!=null) {
            mParseView.updateWordList(new ArrayList<>(wordSet));
            if(wordSet.isEmpty()){
                mParseView.showStatusMessage("No valid english words present!");
            }
        }
    }

    private boolean isSymb(char c){
        return !Character.isLetter(c);
    }
}
