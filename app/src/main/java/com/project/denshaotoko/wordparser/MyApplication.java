package com.project.denshaotoko.wordparser;

import android.support.annotation.VisibleForTesting;

import com.project.denshaotoko.wordparser.di.DaggerAppComponent;
import com.project.denshaotoko.wordparser.dict.DictionaryTrie;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class MyApplication extends DaggerApplication{

    @Inject
    DictionaryTrie dictionaryTrie;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    /**
     * Our Espresso tests need to be able to get an instance of the {@link DictionaryTrie}
     * so that we can delete all tasks before running each test
     */
    @VisibleForTesting
    public DictionaryTrie getDictionaryTrie() {
        return dictionaryTrie;
    }

}
