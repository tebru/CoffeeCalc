package net.tebru.coffeecalculator.dagger.module;

import com.squareup.otto.Bus;

import net.tebru.coffeecalculator.domain.CoffeeCalculator;
import net.tebru.coffeecalculator.lifecycle.fragment.InputFragment;
import net.tebru.coffeecalculator.lifecycle.fragment.ResultFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Class CalculateActivityModule
 *
 * Dagger providers
 */
@Module(
    injects = {
        InputFragment.class,
        ResultFragment.class
    }
)
public class AppModule {

    /**
     * Provides a singleton event bus
     *
     * @return Bus
     */
    @Provides @Singleton Bus provideEventBus() {
        return new Bus();
    }

    /**
     * Provide a calculator
     *
     * We need this object to persist through pause/resumes
     *
     * @return A coffee calculator
     */
    @Provides @Singleton CoffeeCalculator provideCoffeeCalculator() {
        return new CoffeeCalculator();
    }
}
