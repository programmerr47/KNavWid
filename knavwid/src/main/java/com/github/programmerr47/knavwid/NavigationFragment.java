package com.github.programmerr47.knavwid;

import android.os.Bundle;
import android.view.*;
import android.view.animation.Animation;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation.OnTabSelectedListener;
import com.github.programmerr47.knavwid.layoutfactory.DummyLayoutFactory;
import com.github.programmerr47.knavwid.layoutfactory.LayoutFactory;
import com.github.programmerr47.knavwid.menu.MenuActions;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.aurelhubert.ahbottomnavigation.AHBottomNavigation.TitleState.ALWAYS_SHOW;
import static com.github.programmerr47.knavwid.AndroidUtils.bind;
import static com.github.programmerr47.knavwid.NavigationBuilder.NO_NAV_ICON;

public abstract class NavigationFragment extends Fragment implements OnTabSelectedListener {
    private static final LayoutFactory DUMMY_FACTORY = new DummyLayoutFactory(null);

    private NavigationBuilder<?> navigationBuilder;

    protected Toolbar toolbar;
    protected AHBottomNavigation bottomNavigation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        navigationBuilder = buildNavigation();
        return navigationBuilder.layoutFactory().produceLayout(inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        toolbar = bind(view, navigationBuilder.toolbarId);
        bottomNavigation = bind(view, navigationBuilder.bottomBarId);
        prepareNavigation();
    }

    protected void invalidateNavigation(NavigationBuilder newNavigation) {
        navigationBuilder = newNavigation;
        prepareNavigation();
    }

    private void prepareNavigation() {
        if (toolbar != null) {
            prepareToolbar(toolbar);
        }

        if (bottomNavigation != null) {
            prepareBottomNavigation(bottomNavigation);
        }
    }

    protected void prepareToolbar(Toolbar toolbar) {
        if (navigationBuilder.toolbarTitleRes != 0) {
            toolbar.setTitle(navigationBuilder.toolbarTitleRes);
        } else {
            toolbar.setTitle(navigationBuilder.toolbarTitle);
        }
        if (navigationBuilder.toolbarSubtitleRes != 0) {
            toolbar.setSubtitle(navigationBuilder.toolbarSubtitleRes);
        } else {
            toolbar.setSubtitle(navigationBuilder.toolbarSubtitle);
        }
        if (navigationBuilder.toolbarLogoRes != 0) {
            toolbar.setLogo(navigationBuilder.toolbarLogoRes);
        } else {
            toolbar.setLogo(navigationBuilder.toolbarLogo);
        }
        if (navigationBuilder.toolbarNavigationIcon == NO_NAV_ICON) {
            toolbar.setNavigationIcon(null);
            toolbar.setNavigationOnClickListener(null);
        } else {
            NavigationIcons.NavigationIcon navIcon = navigationBuilder.navigationDefaults().navigationIcons().fromType(navigationBuilder.toolbarNavigationIcon);
            toolbar.setNavigationIcon(navIcon.iconDrawable(toolbar.getContext()));
            toolbar.setNavigationOnClickListener(navigationBuilder.navigationDefaults().navigationIconListener());
        }


        Menu menu = toolbar.getMenu();
        if (menu != null) {
            menu.clear();
        }
        if (!navigationBuilder.menuRes.isEmpty()) {
            final MenuActions actions = navigationBuilder.menuActions.build();
            for (Integer menuRes : navigationBuilder.menuRes) {
                toolbar.inflateMenu(menuRes);
            }
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return actions.onMenuItemClick(item);
                }
            });
        }
    }

    protected void prepareBottomNavigation(AHBottomNavigation bottomNavigation) {
        NavigationItems navigationItems = navigationBuilder.navigationDefaults().navigationItems();
        bottomNavigation.removeAllItems();
        bottomNavigation.addItems(navigationItems.bottomNavigationItems());
        bottomNavigation.setCurrentItem(navigationItems.indexFromType(navigationBuilder.currentBottomBarItem), false);
        bottomNavigation.setOnTabSelectedListener(this);
        bottomNavigation.setTitleState(ALWAYS_SHOW);
        bottomNavigation.setColored(true);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation result = new Animation() {};
        result.setDuration(0);
        return result;
    }

    @Override
    public final boolean onTabSelected(int position, boolean wasSelected) {
        int itemType = navigationBuilder.navigationDefaults().navigationItems().get(position).type();
        return onTabTypeSelected(itemType, wasSelected);
    }

    public boolean onTabTypeSelected(int type, boolean wasSelected) {
        return true;
    }

    public void showBottomNavigation() {
        if (bottomNavigation != null) {
            bottomNavigation.setVisibility(VISIBLE);
        }
    }

    public void hideBottomNavigation() {
        if (bottomNavigation != null) {
            bottomNavigation.setVisibility(GONE);
        }
    }

    protected NavigationBuilder buildNavigation() {
        return new CustomLayoutNavigationBuilder(DUMMY_FACTORY);
    }
}
