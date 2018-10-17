package com.github.programmerr47.knavwid.layoutfactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;

public final class IdLayoutFactory implements LayoutFactory {
    private final int layoutId;

    public IdLayoutFactory(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public View produceLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(layoutId, container, false);
    }
}
