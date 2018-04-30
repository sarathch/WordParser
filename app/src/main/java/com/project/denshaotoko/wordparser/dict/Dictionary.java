package com.project.denshaotoko.wordparser.dict;

import android.support.annotation.NonNull;

/**
 * Entry point for dictionary
 */
public interface Dictionary {

    interface InitDictionaryCallBack {

        void onDictionaryInit();

        void onInitException();
    }

    void initDictionary(@NonNull InitDictionaryCallBack callBack);

    void insert(String word);

    boolean isWord(TrieNode node);

    boolean isPrefix(TrieNode node);

    TrieNode getLastTrieNode(String s);

}
