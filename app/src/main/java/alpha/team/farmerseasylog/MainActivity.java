package alpha.team.farmerseasylog;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author Ben Roberts & Jac Robertson
 */
public class MainActivity extends Activity {

    /**
     * Sets up activity content
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
    /**
     * sets up option menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Opens an instance of the calender activity
     * @param v
     */
    public void calendarButtonOnClick(View v) {
        startActivity(new Intent(getApplicationContext(), calendar.class));
    }

    /**
     * Opens an instance of the toDoList activity
     * @param v
     */
    public void toDoListButtonOnClick(View v) {
        startActivity(new Intent(getApplicationContext(), toDoList.class));
    }

    /**
     * Opens an instance of the weather activity
     * @param v
     */
    public void weatherButtonOnClick(View v) {
        startActivity(new Intent(getApplicationContext(), weather.class));
    }

    /**
     * Opens an instance of the easyRegister activity
     * @param v
     */
    public void easyRegisterButtonOnClick(View v) {
        startActivity(new Intent(getApplicationContext(), easyRegister.class));
    }

    /**
     * Action bar handler
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
