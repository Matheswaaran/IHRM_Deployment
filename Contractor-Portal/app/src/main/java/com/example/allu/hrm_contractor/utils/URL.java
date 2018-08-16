package com.example.allu.hrm_contractor.utils;

/**
 * Created by allu on 3/17/17.
 */

public abstract class URL {
    public static String IP = "api.projectihrm.com";
    public static String LOGIN_URL = "http://"+IP+"/HRM_Backend/";

    public static String URL_Contractors = LOGIN_URL+"contractor.php";
    public static String URL_Supervisor = LOGIN_URL+"supervisor.php";
    public static String URL_UPLOAD = LOGIN_URL+"fileupload.php";

    static String GCMServerKEY = "AAAAiSKKPTU:APA91bEY9szt-dmO67JQp5lFuMDJnnzIWhOIQNy5xmAjwBApMURt79fRU7ffQxbKwTUS4usPnP6y-wxEKjgYjCMUFdrCXZQxc6fbzLG8G6krbPzWov-YNVBIGZngYJsxUtPBtA3TjiAR";
}
