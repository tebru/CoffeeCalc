package net.tebru.coffeecalculator.lifecycle.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import net.tebru.coffeecalculator.R;
import net.tebru.coffeecalculator.domain.CoffeeCalculator;
import net.tebru.coffeecalculator.event.InputTextChangedEvent;

import javax.inject.Inject;

/**
 * Class ResultFragment
 *
 * Displays calculations
 */
public class ResultFragment extends InjectableFragment {

    /**
     * An Otto event bus
     */
    @Inject Bus eventBus;

    /**
     * Handles amount calculations
     */
    @Inject CoffeeCalculator calculator;

    /**
     * Whether calculations should be displayed
     */
    private boolean resultsVisible = false;

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
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    /**
     * Enables results if we have something to display
     *
     * {@inheritDoc}
     */
    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null == savedInstanceState) {
            return;
        }

        this.resultsVisible = savedInstanceState.getBoolean("resultsVisible");
    }


    /**
     * Registers with event bus
     *
     * {@inheritDoc}
     */
    @Override public void onResume() {
        super.onResume();

        eventBus.register(this);

        // if we should show results, display them
        if (true == this.resultsVisible) {
            this.enableResults();
        }
    }

    /**
     * Sets if results are visible
     *
     * {@inheritDoc}
     */
    @Override public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("resultsVisible", this.resultsVisible);
    }

    /**
     * Unregisters with event bus
     *
     * {@inheritDoc}
     */
    @Override public void onPause() {
        super.onPause();

        eventBus.unregister(this);
    }

    /**
     * Updates the view on text changed
     *
     * @param event The text changed event
     */
    @Subscribe public void updateView(InputTextChangedEvent event) {
        String text = event.getInputText();

        double amount;
        try {
            amount = Double.parseDouble(text);
        } catch (NumberFormatException exception) {
            // input is currently unprocessable, set default message
            this.disableResults();

            return;
        }

        this.calculator.setTargetYield(amount);
        this.enableResults();
    }

    /**
     * Toggles results off and displays message
     *
     * @throws UnsupportedOperationException
     */
    private void disableResults() throws UnsupportedOperationException {
        View view = this.getView();

        if (null == view) {
            throw new UnsupportedOperationException("View not found, must be available before disableResults() is called");
        }

        view.findViewById(R.id.coffee_amount_label).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.coffee_amount_result).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.bloom_amount_label).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.bloom_amount_result).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.water_amount_label).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.water_amount_result).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.no_results_found).setVisibility(View.VISIBLE);

        this.resultsVisible = false;
    }

    /**
     * Toggles results on and hides message
     *
     * @throws UnsupportedOperationException
     */
    private void enableResults() throws UnsupportedOperationException {
        View view = this.getView();

        if (null == view) {
            throw new UnsupportedOperationException("View not found, must be available before enableResults() is called");
        }

        TextView coffeeAmount = (TextView) view.findViewById(R.id.coffee_amount_result);
        TextView bloomAmount = (TextView) view.findViewById(R.id.bloom_amount_result);
        TextView waterAmount = (TextView) view.findViewById(R.id.water_amount_result);

        coffeeAmount.setText(this.calculator.getCoffeeAmount().toString() + 'g');
        bloomAmount.setText(this.calculator.getBloomAmount().toString() + 'g');
        waterAmount.setText(this.calculator.getWaterAmount().toString() + 'g');

        view.findViewById(R.id.coffee_amount_label).setVisibility(View.VISIBLE);
        view.findViewById(R.id.bloom_amount_label).setVisibility(View.VISIBLE);
        view.findViewById(R.id.water_amount_label).setVisibility(View.VISIBLE);
        coffeeAmount.setVisibility(View.VISIBLE);
        bloomAmount.setVisibility(View.VISIBLE);
        waterAmount.setVisibility(View.VISIBLE);
        view.findViewById(R.id.no_results_found).setVisibility(View.INVISIBLE);

        this.resultsVisible = true;
    }
}
