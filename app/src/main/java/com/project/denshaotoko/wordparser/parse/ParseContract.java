package com.project.denshaotoko.wordparser.parse;

import com.project.denshaotoko.wordparser.BasePresenter;
import com.project.denshaotoko.wordparser.BaseView;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface ParseContract {

    interface View extends BaseView<Presenter>{

        void updateWordList(List<String> words);

        void showProgressBar();

        void stopProgressBar();

        void showStatusMessage(String msg);
    }

    interface Presenter extends BasePresenter<View>{

        void initDictionary();

        void parseWordsFromDictionary(String input);
    }
}
