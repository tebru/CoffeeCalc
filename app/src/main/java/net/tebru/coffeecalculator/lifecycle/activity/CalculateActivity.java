package net.tebru.coffeecalculator.lifecycle.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import net.tebru.coffeecalculator.R;

import dagger.ObjectGraph;

/**
 * Class CalculateActivity
 *
 * The main activity
 */
public class CalculateActivity extends InjectableActivity {

    /**
     * The dagger object graph for activity
     */
    private ObjectGraph activityGraph;

    /**
     * {@inheritDoc}
     */
    @Override protected ObjectGraph getActivityGraph() {
        return this.activityGraph;
    }

    /**
     * {@inheritDoc}
     */
    @Override protected void setActivityGraph(ObjectGraph activityGraph) {
        this.activityGraph = activityGraph;
    }

    /**
     * {@inheritDoc}
     */
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
    }

    /**
     * {@inheritDoc}
     */
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
