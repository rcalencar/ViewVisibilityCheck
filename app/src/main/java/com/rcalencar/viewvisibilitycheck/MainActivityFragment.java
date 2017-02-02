package com.rcalencar.viewvisibilitycheck;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ViewVisibilityCheck check1;
    ViewVisibilityCheck check12;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final View v1 = rootView.findViewById(R.id.id1);
        check1 = ViewVisibilityCheck.newViewVisibilityCheck(v1, new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "View 1", Toast.LENGTH_LONG).show();
            }
        });

        final View v12 = rootView.findViewById(R.id.id12);
        check12 = ViewVisibilityCheck.newViewVisibilityCheck(v12, new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "View 12", Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        check1.destroy();
        check12.destroy();
        super.onStop();
    }
}
