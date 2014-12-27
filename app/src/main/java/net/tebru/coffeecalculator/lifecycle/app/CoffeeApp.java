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
     * Get the application object graph
     *
     * @return The application object graph
     */
    public ObjectGraph getApplicationGraph()
    {
        return this.objectGraph;
    }
}
