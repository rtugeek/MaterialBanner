package com.freegeek.android.materialbanner.demo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    private interface OnClick{
        void onC();
    }

    private class A implements OnClick{

        @Override
        public void onC() {

        }
    }

    @Test
    public void addition_isCorrect() throws Exception {
        A a =new A();
    }
}