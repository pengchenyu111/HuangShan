package com.example.huangshan.fragment;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.huangshan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Field;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    String TAG="Notification";
    boolean isKeyUp=false;


    public NotificationFragment() {
        // Required empty public constructor
    }


    public void searchViewSetting(SearchView mSearchView) {
        int searchPlateId = mSearchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = mSearchView.findViewById(searchPlateId);
//       mSearchView.setBackground();
        if (searchPlate != null) {
            int searchTextId = searchPlate.getContext().getResources()
                    .getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText != null) {
                searchText.setTextColor(Color.GRAY);
                searchText.setHintTextColor(Color.GRAY);
            }
            try {
                Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
                mCursorDrawableRes.setAccessible(true);
                mCursorDrawableRes.set(searchText, R.drawable.cursor_color);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View textView=inflater.inflate(R.layout.fragment_notification,null);

        FloatingActionButton circle_add=(FloatingActionButton) textView.findViewById(R.id.circle_add);

        SearchView searchView = (SearchView) textView.findViewById(R.id.search_view);
        searchViewSetting(searchView);




        circle_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        return textView;
    }

}
