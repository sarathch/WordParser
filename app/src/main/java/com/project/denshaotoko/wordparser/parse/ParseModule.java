package com.project.denshaotoko.wordparser.parse;

import com.project.denshaotoko.wordparser.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link ParsePresenter}.
 */

@Module
public abstract class ParseModule {

    @ActivityScoped
    @Binds
    abstract ParseContract.Presenter parsePresenter(ParsePresenter presenter);
}
