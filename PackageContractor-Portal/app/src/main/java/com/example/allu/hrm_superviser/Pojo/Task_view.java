package com.example.allu.hrm_superviser.Pojo;

/**
 * Created by allu on 3/21/17.
 */

public class Task_view {
    public int Color_ID,ImageId;

    public void setIntentClass(Class intentClass) {
        IntentClass = intentClass;
    }

    public Class IntentClass;
    public String TaskName;

    public Task_view(int color_ID, int imageId, String taskName) {
        Color_ID = color_ID;
        ImageId = imageId;
        TaskName = taskName;
    }
}
