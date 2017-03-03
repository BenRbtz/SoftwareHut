package alpha.team.farmerseasylog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 * @author Ben Roberts
 */
public class easyRegister extends AppCompatActivity {
    private Button onAddBut,offAddBut;
    /**
     * initalises activity content
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewItems();
        buttonListeners();

    }

    /**
     * finds view items
     */
    public void findViewItems(){
        onAddBut = (Button) findViewById(R.id.onAddButton);
        offAddBut = (Button) findViewById(R.id.offAddButton);
    }

    /**
     * Adds listeners to buttons to open activities
     */
    public void buttonListeners() {
        onAddBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), onMovementForm.class));
            }
        });
        offAddBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), offMovementForm.class));
            }
        });
    }
}
