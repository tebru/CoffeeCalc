package net.tebru.coffeecalculator.lifecycle.app;

import android.app.Application;

import net.tebru.coffeecalculator.dagger.module.AppModule;

import dagger.ObjectGraph;

/**
 * Class CoffeeApp
 *
 * Subclassed Application to register dagger object graph
 */
public class CoffeeApp extends Application {

    /**
     * The dagger ObjectGraph
     */
    private ObjectGraph objectGraph;

    /**
     * Create object graph
     *
     * {@inheritDoc}
     */
    @Override public void onCreate() {
        super.onCreate();

        this.objectGraph = ObjectGraph.create(new AppModule());
    }

    /**
     * Inject an object
     *
     * @param object The object to inject
     */
    public void inject(Object object) {
        this.objectGraph.inject(object);
    }
}
