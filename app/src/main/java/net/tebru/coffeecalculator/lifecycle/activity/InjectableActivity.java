package net.tebru.coffeecalculator.lifecycle.activity;

import android.app.Activity;
import android.os.Bundle;

import net.tebru.coffeecalculator.lifecycle.app.CoffeeApp;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Class InjectableActivity
 *
 * Extending this class will set up scaffolding to use per-activity object graphs
 * with Dagger
 */
abstract public class InjectableActivity extends Activity {

    /**
     * Get the activity graph
     *
     * @return The activity object graph
     */
    abstract protected ObjectGraph getActivityGraph();

    /**
     * Set the activity graph
     *
     * @param activityGraph The activity object graph
     */
    abstract protected void setActivityGraph(ObjectGraph activityGraph);

    /**
     * {@inheritDoc}
     */
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create a new object graph
        CoffeeApp app = (CoffeeApp) this.getApplication();
        ObjectGraph activityGraph = app.getApplicationGraph().plus(this.getModules().toArray());
        this.setActivityGraph(activityGraph);
    }

    /**
     * Eagerly clear the reference to the activity graph to allow it to be garbage collected as
     * soon as possible.
     *
     * {@inheritDoc}
     */
    @Override protected void onDestroy() {
        this.setActivityGraph(null);

        super.onDestroy();
    }

    /**
     * A list of modules to use for the individual activity graph. Subclasses can override this
     * method to provide additional modules provided they call and include the modules returned by
     * calling {@code super.getModules()}.
     */
    protected List<Object> getModules() {
        return new ArrayList<Object>();
    }

    /**
     * Inject object into activity graph
     *
     * @param object The object to inject into activity graph
     */
    public void inject(Object object) {
        this.getActivityGraph().inject(object);
    }
}
