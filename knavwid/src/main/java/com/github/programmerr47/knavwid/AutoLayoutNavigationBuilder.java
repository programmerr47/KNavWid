package com.github.programmerr47.knavwid;

import android.view.View;
import com.github.programmerr47.knavwid.layoutfactory.DummyLayoutFactory;
import com.github.programmerr47.knavwid.layoutfactory.IdLayoutFactory;
import com.github.programmerr47.knavwid.layoutfactory.LayoutFactory;
import com.github.programmerr47.knavwid.layoutfactory.NavigationLayoutFactory;

public final class AutoLayoutNavigationBuilder extends NavigationBuilder<AutoLayoutNavigationBuilder> {
    private boolean includeToolbar;
    private boolean includeBottomBar;

    public static AutoLayoutNavigationBuilder navigation(int id) {
        return new AutoLayoutNavigationBuilder(new IdLayoutFactory(id));
    }

    public static AutoLayoutNavigationBuilder navigation(View view) {
        return new AutoLayoutNavigationBuilder(new DummyLayoutFactory(view));
    }

    public AutoLayoutNavigationBuilder(LayoutFactory layoutFactory) {
        super(layoutFactory, NavigationDefaults.NavigationDefaultsHolder.navigationDefaults());
    }

    @Override
    protected AutoLayoutNavigationBuilder getThis() {
        return this;
    }

    @Override
    public LayoutFactory layoutFactory() {
        return new NavigationLayoutFactory(includeToolbar, includeBottomBar, super.layoutFactory());
    }

    public AutoLayoutNavigationBuilder includeToolbar() {
        this.includeToolbar = true;
        return this;
    }

    public AutoLayoutNavigationBuilder excludeToolbar() {
        this.includeToolbar = false;
        return this;
    }

    public AutoLayoutNavigationBuilder includeBottomNavigation() {
        this.includeBottomBar = true;
        return this;
    }

    public AutoLayoutNavigationBuilder excludeBottomNavigation() {
        this.includeBottomBar = false;
        return this;
    }

    public CustomLayoutNavigationBuilder custom() {
        return new CustomLayoutNavigationBuilder(super.layoutFactory());
    }
}
