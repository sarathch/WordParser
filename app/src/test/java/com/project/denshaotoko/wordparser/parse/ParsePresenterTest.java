package com.project.denshaotoko.wordparser.parse;

import com.project.denshaotoko.wordparser.dict.Dictionary;
import com.project.denshaotoko.wordparser.dict.DictionaryTrie;
import com.project.denshaotoko.wordparser.dict.TrieNode;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ParsePresenterTest {

    @Mock
    private DictionaryTrie mDictionaryTrie;

    @Mock
    private ParseContract.View mParseView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<Dictionary.InitDictionaryCallBack> initDictionaryCallBackArgumentCaptor;

    private ParsePresenter mParsePresenter;

    @Before
    public void setupParsePresenter() {

        MockitoAnnotations.initMocks(this);

        mParsePresenter = new ParsePresenter(mDictionaryTrie);
        mParsePresenter.takeView(mParseView);

    }

    @Test
    public void initDictionary_updateViews(){

        mParsePresenter.initDictionary();

        verify(mParseView, times(2)).showProgressBar();

        // invoke once more on takeView
        verify(mDictionaryTrie, times(2)).initDictionary(initDictionaryCallBackArgumentCaptor.capture());
        initDictionaryCallBackArgumentCaptor.getValue().onDictionaryInit();

        verify(mParseView, times(1)).stopProgressBar();

        ArgumentCaptor<String> showMessageArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(mParseView).showStatusMessage(showMessageArgumentCaptor.capture());
        System.out.println(showMessageArgumentCaptor.getAllValues());
        assertTrue(showMessageArgumentCaptor.getValue().equals( "Dictionary Loaded!"));

    }

    @Test
    public void parseWordsFromDictionary_validString(){

        String input = "apple";

        mParsePresenter.parseWordsFromDictionary(input);

        verify(mDictionaryTrie,times(5)).getLastTrieNode(anyString());

        verify(mDictionaryTrie, times(5)).isWord(any(TrieNode.class));

        verify(mDictionaryTrie, times(5)).isPrefix(any(TrieNode.class));

    }

    @Test
    public void parseWordsFromDictionary_symbolsSkipped(){

        String input = "a$.l/ e";

        mParsePresenter.parseWordsFromDictionary(input);

        verify(mDictionaryTrie,times(3)).getLastTrieNode(anyString());

        verify(mDictionaryTrie, times(3)).isWord(any(TrieNode.class));

        verify(mDictionaryTrie, times(3)).isPrefix(any(TrieNode.class));

    }

    @Test
    public void parseWordsFromDictionary_showToastOnEmpty(){

        String input = "a$.l/ e";

        mParsePresenter.parseWordsFromDictionary(input);

        verify(mDictionaryTrie,times(3)).getLastTrieNode(anyString());

        verify(mDictionaryTrie, times(3)).isWord(any(TrieNode.class));

        verify(mDictionaryTrie, times(3)).isPrefix(any(TrieNode.class));

        verify(mParseView).showStatusMessage(anyString());
    }

}
