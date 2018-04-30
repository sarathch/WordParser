package com.project.denshaotoko.wordparser.dict;

public class TrieNode {

    TrieNode[] arr;
    boolean isEnd;

    // Initialize to array size 26: ' a - z'
    public TrieNode() {

        this.arr = new TrieNode[26];

    }
}
