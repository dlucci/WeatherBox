package com.dlucci.weatherbox.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by derlucci on 5/18/15.
 */

/**
 * Since RecyclerView does not have an onItemClickListener (yet!), this is
 * an OnItemClickListener implementation I found on the InterBlag
 * (http://stackoverflow.com/questions/24471109/recyclerview-onclick/26196831#26196831)
 *
 * I do not claim to have written this but have taken it from the url listed.
 */
public class RecyclerViewItemClick implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener clickListener;

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }

    GestureDetector gestureDetector;

    public RecyclerViewItemClick(Context context, OnItemClickListener listener){
        clickListener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
           @Override public boolean onSingleTapUp(MotionEvent e){
               return true;
           }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && clickListener != null && gestureDetector.onTouchEvent(e)){
            clickListener.onItemClick(childView, rv.getChildPosition(childView));
            return true;
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) { }
}
