package alpha.team.farmerseasylog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Created by Ben Roberts
 */
public class toDoList extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();

        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        list.setAdapter(itemsAdapter);
        setupListViewListener();
        setupfab();

    }

    /**
     * adds button listener to fad button
     */
    private void setupfab(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            /**
             * opens alert dialog to insert text
             * @param v
             */
            @Override
            public void onClick(View v) {
                final EditText taskEditText = new EditText(toDoList.this);
                new AlertDialog.Builder(toDoList.this)
                        .setTitle("Add a new task")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                //checks to see if dialog text field is not empty
                                if (task.length() > 0) {
                                    itemsAdapter.add(task);// adds item to to do list
                                    writeItems();
                                    Toast.makeText(toDoList.this, "Task Added", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create().show();
            }
        });
    }
    private void setupListViewListener() {
        list.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        taskSelected(pos);
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }

                });
    }

    /**
     * create alert dialog to delete selected item
     * @param position
     */
    private void taskSelected(final int position) {

        new AlertDialog.Builder(toDoList.this)
                .setTitle("Do You Want To Delete Task?")
                .setMessage(itemsAdapter.getItem(position))
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create().show();
    }

    /**
     * reads items from text file and inserts into todo list
     */
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    /**
     * writes items from todo list into text file
     */
    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
