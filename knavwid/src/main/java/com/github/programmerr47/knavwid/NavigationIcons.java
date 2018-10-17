package com.github.programmerr47.knavwid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import java.util.ArrayList;
import java.util.Collection;

import static androidx.core.graphics.drawable.DrawableCompat.setTint;
import static androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.create;
import static com.github.programmerr47.knavwid.AndroidUtils.color;

public class NavigationIcons extends ArrayList<NavigationIcons.NavigationIcon> {

    public NavigationIcons(int initialCapacity) {
        super(initialCapacity);
    }

    public NavigationIcons() {
        super();
    }

    public NavigationIcons(Collection<? extends NavigationIcon> c) {
        super(c);
    }

    public boolean contains(int type) {
        return fromType(type) != null;
    }

    @Nullable
    public NavigationIcon fromType(int type) {
        for (NavigationIcon icon : this) {
            if (icon.type == type) {
                return icon;
            }
        }

        return null;
    }

    public static final class NavigationIcon {
        private final int type;
        private final int drawableRes;
        private final Drawable drawable;

        public static NavigationIcon navigationIcon(int type, @DrawableRes int drawableRes) {
            return new NavigationIcon(type, drawableRes, null);
        }

        public static NavigationIcon navigationIcon(int type, @NonNull Drawable drawable) {
            return new NavigationIcon(type, 0, drawable);
        }

        private NavigationIcon(int type, @DrawableRes int drawableRes, Drawable drawable) {
            this.type = type;
            this.drawableRes = drawableRes;
            this.drawable = drawable;
        }

        public Drawable iconDrawable(@NonNull Context context) {
            if (drawable != null) {
                return drawable;
            } else {
                VectorDrawableCompat navIcon = create(context.getResources(), drawableRes, context.getTheme());
                setTint(navIcon, color(context, android.R.color.white));
                return navIcon;
            }
        }
    }
}
