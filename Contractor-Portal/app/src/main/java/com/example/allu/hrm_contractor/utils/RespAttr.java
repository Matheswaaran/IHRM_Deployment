package com.example.allu.hrm_contractor.utils;

/**
 * Created by allu on 3/25/17.
 */

public abstract class RespAttr {
    //contractor table
    public static String COL_con_cid = "cid";
    public static String COL_con_sid = "sid";
    public static String COL_con_name = "name";
    public static String COL_con_email = "email";
    public static String COL_con_password = "password";
    public static String COL_con_aadhar_uid = "aadhar_uid";
    public static String COL_con_aadhar_string = "aadhar_string";
    public static String COL_con_gid = "gid";
    public static String COL_con_created = "created";

    //supervisor table
    public static String COL_su_suid = "su_id";
    public static String COL_su_sid = "sid";
    public static String COL_su_name = "name";
    public static String COL_su_email = "email";
    public static String COL_su_password = "password";
    public static String COL_su_aadhar_uid = "aadhar_uid";
    public static String COL_su_aadhar_string = "aadhar_string";
    public static String COL_su_gid = "gid";
    public static String COL_su_created = "created";

//emp table
    public static String COL_emp_eid = "eid";
    public static String COL_emp_name = "name";
    public static String COL_emp_cid = "cid";
    public static String COL_emp_auth = "auth";
    public static String COL_emp_aadhar_uid = "aadhar_uid";
    public static String COL_emp_aadhar_string = "aadhar_string";
    public static String COL_emp_skill = "skill";
    public static String COL_emp_emptype = "emp_type";
    public static String COL_emp_created = "created";

//request table
    public static String COL_req_rid = "rid";
    public static String COL_req_name = "task_name";
    public static String COL_req_sid = "sid";
    public static String COL_req_su_id = "su_id";
    public static String COL_req_date = "date";
    public static String COL_req_skill1 = "skill_1";
    public static String COL_req_skill2 = "skill_2";
    public static String COL_req_skill3 = "skill_3";
    public static String COL_req_skill4 = "skill_4";
    public static String COL_req_alloc_skill1 = "alloc_skill_1";
    public static String COL_req_alloc_skill2 = "alloc_skill_2";
    public static String COL_req_alloc_skill3 = "alloc_skill_3";
    public static String COL_req_alloc_skill4 = "alloc_skill_4";
    public static String COL_req_reqdate = "req_date";
    public static String COL_req_alloc_time = "alloc_time";
    public static String COL_req_c_response = "c_response";
    public static String COL_req_created = "created";

}
