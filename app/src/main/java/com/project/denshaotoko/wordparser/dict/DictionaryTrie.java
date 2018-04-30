package com.project.denshaotoko.wordparser.dict;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.inject.Inject;

/**
 * Dictionary implementation to store English words
 * <p>
 *     Implemented a Trie data structure to get O(1) word and prefix lookup
 * </p>
 */
public class DictionaryTrie implements Dictionary{

    private TrieNode root;

    Context mContext;

    @Inject
    DictionaryTrie(Context context){
        root =new TrieNode();
        this.mContext = context;
    }

    @Override
    public void initDictionary(@NonNull InitDictionaryCallBack callBack) {

        // thread reads file and loads into Trie
        new Thread(new ReadFile(callBack)).start();
    }

    /**
     * Method inserts word into Dictionary
     * @param word - word in dictionary
     */
    @Override
    public void insert(String word) {

        TrieNode curr = root;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            int index = c-'a';
            if(curr.arr[index]==null){
                // create new node. char entry doesn't exist.
                TrieNode temp = new TrieNode();
                curr.arr[index]=temp;
                curr = temp;
            }else{
                // go to char index
                curr=curr.arr[index];
            }
        }
        // mark last char node.
        curr.isEnd=true;
    }

    /**
     * Method looks up if word exists in the dictionary
     * @param node - node of last char
     * @return - exists? true : false
     */
    @Override
    public boolean isWord(TrieNode node) {

        if(node != null && node.isEnd)
            return true;

        return false;
    }

    /**
     * Method looks up if word is a prefix in the dictionary
     * @param node - node of last char
     * @return - Prefix? true:false
     */
    @Override
    public boolean isPrefix(TrieNode node) {
        return !(node == null);
    }

    /**
     * Method traverses trie to search for a word
     * @param s -   input string
     * @return LastNode
     */
    @Override
    public TrieNode getLastTrieNode(String s){

        TrieNode curr = root;

        for(int i=0; i<s.length(); i++){
            char c= s.charAt(i);
            int index = c-'a';

            if(curr.arr[index] != null){
                curr = curr.arr[index];
            }else{
                return null;
            }
        }

        // handle empty string
        if(curr == root)
            return null;

        return curr;
    }

    /**
     * Runnable class to read the text file
     */

    private class ReadFile implements Runnable {

        InitDictionaryCallBack mInitDictionaryCallback;
        Handler handler = new Handler();

        public ReadFile(InitDictionaryCallBack initDictionaryCallBack) {
            this.mInitDictionaryCallback = initDictionaryCallBack;
        }

        public void run() {

            try {
                // link dictionary .txt
                InputStream is = mContext.getAssets().open("words_english.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String st;
                while ((st = br.readLine()) != null) {

                    // remove two letter abbr and words with non alphabet
                    if(st.matches("[a-z]*") && st.length() > 2) {
                        insert(st);
                    }

                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mInitDictionaryCallback.onDictionaryInit();
                    }
                });

            }catch (Exception e){

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mInitDictionaryCallback.onInitException();
                    }
                });
            }
        }
    }

}
