package com.github.programmerr47.knavwid.layoutfactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;

public final class DummyLayoutFactory implements LayoutFactory {
    private final View view;

    public DummyLayoutFactory(View view) {
        this.view = view;
    }

    @Override
    public View produceLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        return view;
    }
}
