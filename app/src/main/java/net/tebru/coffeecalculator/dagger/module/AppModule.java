package net.tebru.coffeecalculator.dagger.module;

import com.squareup.otto.Bus;

import net.tebru.coffeecalculator.lifecycle.fragment.InputFragment;
import net.tebru.coffeecalculator.lifecycle.fragment.ResultFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Class AppModule
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
}
