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
 * Created by yakuza on 13/04/16.
 */
public class MongoPortal {
    private MongoClientURI uri;
    private MongoClient client;
    private DBCollection table;
    public MongoPortal() {
        uri = new MongoClientURI("mongodb://admin:admin1@ds011291.mlab.com:11291/easylog");
        Log.v("Connection: ", uri.getURI());
    }

    public void addEntry(String herdNumber, String barNumber, String sex, String colour, String dateOfBirth, String breed,Boolean isOnForm) {
        try {
            client = new MongoClient(uri);
            Log.v("Connected: ", uri.getURI());

            DB db = client.getDB(uri.getDatabase());
            Log.v("Connection", "Got the database");
            if (isOnForm== true)
            {
                table = db.getCollection("onMovement");
            }
            else
            {
                table = db.getCollection("offMovement");
            }
            BasicDBObject document = new BasicDBObject();
            document.put("herdNumber", herdNumber);
            document.put("barNumber", barNumber);
            document.put("sex", sex);
            document.put("colour", colour);
            document.put("dateOfBirth", dateOfBirth);
            document.put("breed", breed);
            table.insert(document);
            client.close();
            Log.v("Connection: ", "Done");

        } catch (MongoException e) {

            Log.v("ERROR", e.getMessage());
        }
    }

}
