package alpha.team.farmerseasylog;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Set;

/**
 * @author Ben Roberts
 */
public class MongoPortal {
    private MongoClientURI uri;
    private MongoClient client;
    private DBCollection table;

    /**
     * on create Mongo uri
     */
    public MongoPortal() {
        uri = new MongoClientURI("mongodb://admin:admin1@ds011291.mlab.com:11291/easylog");
        Log.v("Connection: ", uri.getURI());
    }

    /**
     * Passes MovementForm data to database
     * @param herdNumber - contains herdNumber user entry from form
     * @param barNumber - contains barNumber user entry from form
     * @param sex - contains sex user entry from form
     * @param colour - contains colour user entry from form
     * @param dateOfBirth - contains dateOfBirth user entry from form
     * @param breed - contains breed user entry from form
     * @param isOnForm - contains which database collection to send form to
     */
    public void addEntry(String herdNumber, String barNumber, String sex, String colour,
                                                String dateOfBirth, String breed,Boolean isOnForm) {
        try {
            client = new MongoClient(uri); //creates database collection
            Log.v("Connected: ", uri.getURI());

            DB db = client.getDB(uri.getDatabase()); // get database
            Log.v("Connection", "Got the database");
            //checks which form it was sent from
            if (isOnForm== true)
            {
                table = db.getCollection("onMovement");//sets database collection has onMovement
            }
            else
            {
                table = db.getCollection("offMovement");//sets database collection has offMovement
            }
            BasicDBObject document = new BasicDBObject(); // creates a object to store form collection
            document.put("herdNumber", herdNumber); // inserts herdnumber along side field in object
            document.put("barNumber", barNumber);
            document.put("sex", sex);
            document.put("colour", colour);
            document.put("dateOfBirth", dateOfBirth);
            document.put("breed", breed);
            table.insert(document); // inserts document into database collection
            client.close(); //closes database connection
            Log.v("Connection: ", "Done");

        } catch (MongoException e) {

            Log.v("ERROR", e.getMessage());
        }
    }

}
