package com.project.denshaotoko.wordparser.dict;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * This is used by Dagger. provides {@link DictionaryTrie}
 */
@Module
public class DictionaryModule {

    @Provides
    DictionaryTrie providesDictionary(Context context){
        return new DictionaryTrie(context);
    }
}
