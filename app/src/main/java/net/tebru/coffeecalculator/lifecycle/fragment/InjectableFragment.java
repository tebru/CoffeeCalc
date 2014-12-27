package net.tebru.coffeecalculator.lifecycle.fragment;

import android.app.Fragment;
import android.os.Bundle;

import net.tebru.coffeecalculator.lifecycle.activity.InjectableActivity;

/**
 * Class InjectableFragment
 *
 * Extending this class will inject fragments into the activities object graph upon
 * activity creation.  The activity must be an InjectableActivity.
 */
public class InjectableFragment extends Fragment{

    /**
     * Add to object graph
     *
     * {@inheritDoc}
     */
    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inject the fragment
        InjectableActivity activity = (InjectableActivity) this.getActivity();
        activity.inject(this);
    }
}
