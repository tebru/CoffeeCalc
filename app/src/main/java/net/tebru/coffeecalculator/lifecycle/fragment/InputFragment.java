package net.tebru.coffeecalculator.lifecycle.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.squareup.otto.Bus;

import net.tebru.coffeecalculator.R;
import net.tebru.coffeecalculator.event.InputTextChangedEvent;

import javax.inject.Inject;

/**
 * Class InputFragment
 *
 * Handles actions/rending of the input box
 */
public class InputFragment extends InjectableFragment implements TextWatcher {

    /**
     * An Otto event bus
     */
    @Inject Bus eventBus;

    /**
     * {@inheritDoc}
     */
    @Override public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    /**
     * Add listeners
     *
     * {@inheritDoc}
     */
    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // add an event listener to text box
        EditText input = (EditText) view.findViewById(R.id.amount_of_coffee);
        input.addTextChangedListener(this);
    }

    /**
     * Send text changed event
     *
     * {@inheritDoc}
     */
    @Override public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        this.eventBus.post(new InputTextChangedEvent(charSequence.toString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

    /**
     * {@inheritDoc}
     */
    @Override public void afterTextChanged(Editable editable) {}
}
