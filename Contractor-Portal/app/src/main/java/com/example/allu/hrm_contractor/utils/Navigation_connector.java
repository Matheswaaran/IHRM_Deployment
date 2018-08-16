package com.example.allu.hrm_contractor.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.allu.hrm_contractor.R;
import com.example.allu.hrm_contractor.UI.Activities.HomeActivity;

/**
 * Created by allu on 3/18/17.
 */

public class Navigation_connector {
    String Tag="Navigation connector";
    public int Id;
    public Context context;
    public Intent i;
    Utils utils;
    public Navigation_connector(int id,Context activity_context){
        this.context=activity_context;
        utils = new Utils(activity_context);
        i = new Intent(context,context.getClass());
        switch (id){
            case R.id.nav_home:
                utils.Goto(HomeActivity.class);
                break;
            case R.id.nav_workers:
                break;
            case R.id.nav_viewrequest:
                break;
            case R.id.nav_logout:
                break;
            default:
                utils.Goto(HomeActivity.class);
                break;
        }
    }
}
