package com.rcalencar.viewvisibilitycheck;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by rodrigo on 2/1/17.
 */

public class ViewVisibilityCheck {
    private View view;
    private boolean iAmVisible;
    private Runnable pendingRunnable;
    private ViewTreeObserver.OnScrollChangedListener onScrollChange;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayout;

    public static ViewVisibilityCheck newViewVisibilityCheck(View view, Runnable runnable) {
        ViewVisibilityCheck instance = new ViewVisibilityCheck(view);
        instance.addEventListeners();
        instance.runWhenVisible(runnable);
        return instance;
    }

    private ViewVisibilityCheck(View view){
        this.view = view;
    }

    public void destroy() {
        removeEventListeners();
    }

    public void runWhenVisible(Runnable message) {
        if (iAmVisible) {
            run(message);
        } else {
            setPendingRunnable(message);
        }
    }

    private void run(Runnable message) {
        view.post(message);
    }

    private void removeEventListeners() {
        view.getViewTreeObserver().removeOnScrollChangedListener(onScrollChange);
        view.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayout);
    }

    private void setPendingRunnable(Runnable message) {
        this.pendingRunnable = message;
    }

    private boolean hasPendingMessage() {
        return pendingRunnable != null;
    }

    private void addEventListeners() {
        onScrollChange = new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                updateVisibilityStatusAndResolvePendingMessage();
            }
        };

        onGlobalLayout = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                updateVisibilityStatusAndResolvePendingMessage();
            }
        };

        view.getViewTreeObserver().addOnScrollChangedListener(onScrollChange);
        view.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayout);
    }

    private void updateVisibilityStatusAndResolvePendingMessage() {
        updateVisibilityStatus();
        resolvePendingMessage();
    }

    private void resolvePendingMessage() {
        if (iAmVisible && hasPendingMessage()) {
            run(pendingRunnable);
            clearPendingMessage();
        }
    }

    private void updateVisibilityStatus() {
        Rect parentBorder = new Rect();
        View parent = (View) view.getParent();
        parent.getHitRect(parentBorder);

        iAmVisible = view.getGlobalVisibleRect(parentBorder);
    }

    private void clearPendingMessage() {
        setPendingRunnable(null);
    }
}