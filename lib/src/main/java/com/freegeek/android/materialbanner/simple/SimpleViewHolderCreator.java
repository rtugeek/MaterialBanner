package com.freegeek.android.materialbanner.simple;

import com.freegeek.android.materialbanner.holder.ViewHolderCreator;

/**
 * @author Jack Fu <rtugeek@gmail.com>
 * @date 2018/06/08
 */
public class SimpleViewHolderCreator implements ViewHolderCreator{

    @Override
    public SimpleHolder createHolder() {
        return new SimpleHolder();
    }

}
