package alpha.team.farmerseasylog;

import android.graphics.drawable.Icon;
import android.widget.ImageView;


/**
 * Created by jacrobertson on 14/03/16.
 */
public class Event {

    //Date date;
    String title;
    String desc;

    public Event (String title, String desc){
        //img = icon;
        this.title = title;
        this.desc = desc;

    }



    public String getTitle(){
        return title;

    }
    public String getDesc(){
        return desc;
    }


}
