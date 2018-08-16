<?php
ini_set("allow_url_fopen", true);
require 'config.php';
require 'tableconfig.php';
require 'default_code.php';
require 'utils.php';


//SELECT * FROM employee_table JOIN allocated_employee_table ON employee_table.eid=allocated_employee_table.eid WHERE allocated_employee_table.rid = 3

$postdata = json_decode(file_get_contents('php://input'));
$response = array();
$option = $postdata->option;

$db_name = DB_NAME;
$table_name = Table_supervisor;
$table_emp_name = Table_employee;
$table_sup_request_name = Table_sup_requests;

//supervisor table
$COL_suid = "su_id";
$COL_sid = "sid";
$COL_name = "name";
$COL_email = "email";
$COL_password = "password";
$COL_aadhar_uid = "aadhar_uid";
$COL_aadhar_string = "aadhar_string";
$COL_gid = "gid";
$COL_created = "created";

//emp table
$COL_emp_eid = "eid";
$COL_emp_name = "name";
$COL_emp_cid = "cid";
$COL_emp_auth = "auth";
$COL_emp_aadhar_uid = "aadhar_uid";
$COL_emp_aadhar_string = "aadhar_string";
$COL_emp_skill = "skill";
$COL_emp_emptype = "emp_type";
$COL_emp_assigned = "assigned";
$COL_emp_created = "created";

//request table
//request table
$COL_req_rid = "rid";
$COL_req_name = "task_name";
$COL_req_su_id = "su_id";
$COL_req_cid = "cid";
$COL_req_sid = "sid";
$COL_req_date = "date";
$COL_req_skill1 = "skill_1";
$COL_req_skill2 = "skill_2";
$COL_req_skill3 = "skill_3";
$COL_req_skill4 = "skill_4";
$COL_req_alloc_skill1 = "alloc_skill_1";
$COL_req_alloc_skill2 = "alloc_skill_2";
$COL_req_alloc_skill3 = "alloc_skill_3";
$COL_req_alloc_skill4 = "alloc_skill_4";
$COL_req_req_date = "req_date";
$COL_req_alloc_time = "alloc_time";
$COL_req_c_response = "c_response";
$COL_req_created = "created";

$utils = new Utils();


$conn = mysqli_connect(DB_HOST,DB_USERNAME, DB_PASSWORD, DB_NAME);
if($conn){
  switch ($option) {
    case 'login':
      $name = $postdata->$COL_name;
      $password = $postdata->$COL_password;
      $sql = "SELECT * from $db_name.$table_name WHERE $COL_name ='$name'";
      $result = mysqli_query($conn,$sql);
      if($result){
        $row = mysqli_fetch_assoc($result);
        if($password == $row['password']){
          $data = array();
          $data[$COL_suid] = $row[$COL_suid];
          $data[$COL_sid] = $row[$COL_sid];
          $data[$COL_name] = $row[$COL_name];
          $data[$COL_email] = $row[$COL_email];
          $data[$COL_aadhar_uid] = $row[$COL_aadhar_uid];
          $data[$COL_aadhar_string] = $row[$COL_aadhar_string];
          $data[$COL_gid] = $row[$COL_gid];
          $data[$COL_created] = $row[$COL_created];
          $response = $utils->getResponse1(Code_Success,"success","User login successful",$data);
        }else{
          $response["status"] = "error";
          $response["code"] = Code_data_invalid;
          $response["message"] = "User login failed";
        }
      }else{
        $response["query"] = $sql;
        $response["status"]="error";
        $response["message"]="Failed to validate user.query not valid";
      }
      break;
    case 'signup':
      $name = $postdata->name;
      $sid = $postdata->sid;
      $email = $postdata->email;
      $password = $postdata->pass;
      $aadhar_uid = $postdata->aadharuid;
      $aadhar_string = $postdata->aadhar;
      $gid = $postdata->gid;
      $sql = "INSERT INTO $db_name.$table_name ('$COL_suid','$COL_sid','$COL_name','$COL_email','$COL_password','$COL_aadhar_uid','$COL_aadhar_string','$COL_gid','$COL_created') VALUES (NULL , $sid ,'$name' , '$email', '$password', $aadharuid ,'$aadhar_string', $gid, CURRENT_TIMESTAMP )";
      $result = mysqli_query($conn,$sql);
      if($result){
        $response["status"]="success";
        $response["message"]="New user created";
        $response["code"] = Code_Success;
      }else{
        $response["status"]="error";
        $response["message"]="Failed to create user.query not valid";
        $response["code"] = Code_query_error;
      }
      break;

    case 'create_request':
        $taskname = $postdata->task_name;
        $su_id = $postdata->su_id;
        $date = $postdata->date;
        $sid = $postdata->sid;
        $skill_1 = $postdata->skill_1;
        $skill_2 = $postdata->skill_2;
        $skill_3 = $postdata->skill_3;
        $skill_4 = $postdata->skill_4;
        $sql1 = "INSERT INTO `HRM_Database`.`super_req_table` (`rid`, `task_name`, `su_id`, `sid`, `date`, `skill_1`, `skill_2`, `skill_3`, `skill_4`, `alloc_skill_1`, `alloc_skill_2`, `alloc_skill_3`, `alloc_skill_4`, `alloc_time`, `req_date`, `c_response`, `created`) VALUES (NULL, '$taskname', $su_id , $sid,  '$date' , $skill_1, $skill_2, $skill_3, $skill_4, '0', '0', '0', '0', NULL, '$date', 0, CURRENT_TIMESTAMP)";
      //  $sql = "INSERT INTO $db_name.$table_sup_request_name ('$COL_req_rid' , '$COL_req_name' ,'$COL_req_su_id' ,'$COL_req_sid' , '$COL_req_date' , '$COL_req_skill1' , '$COL_req_skill2' , '$COL_req_skill3' , '$COL_req_skill4' , '$COL_req_alloc_skill1' , '$COL_req_alloc_skill2' , '$COL_req_alloc_skill3' , '$COL_req_alloc_skill4' , '$COL_req_alloc_time' , '$COL_req_c_response' , '$COL_req_created') VALUES (NULL, '$taskname' , $su_id, $sid, '$date' , $skill_1,$skill_2, $skill_3 , $skill_4 , 0,0,0,0,NULL,0,CURRENT_TIMESTAMP)";
        $result = mysqli_query($conn,$sql1);
        if($result){
          $response["status"]="success";
          $response["message"]="Response successfully created";
          $response["code"] = Code_Success;
        }else{
          $response["query"]=$sql1;
          $response["status"]="error";
          $response["message"]="Failed to create response";
          $response["code"] = Code_query_error;
        }
      break;
    case 'get_request':
        $sid = $postdata->su_id;
        $sql1 = "SELECT * FROM `super_req_table` WHERE `su_id` = $sid ";
        //$sql = "SELECT * FROM $db_name.$table_sup_request_name WHERE '$COL_req_su_id' = $sid";
        $result = mysqli_query($conn,$sql1);
        if($result){
          if(mysqli_num_rows($result) > 0){
            $data = array();
            while($row = mysqli_fetch_assoc($result)){
              $value=array();
              $value[$COL_req_rid]=$row[$COL_req_rid];
              $value[$COL_req_name]=$row[$COL_req_name];
              $value[$COL_req_su_id] = $row[$COL_req_su_id];
              $value[$COL_req_sid]=$row[$COL_req_sid];
              $value[$COL_req_date]=$row[$COL_req_date];
              $value[$COL_req_skill1]=$row[$COL_req_skill1];
              $value[$COL_req_skill2]=$row[$COL_req_skill2];
              $value[$COL_req_skill3]=$row[$COL_req_skill3];
              $value[$COL_req_skill4]=$row[$COL_req_skill4];
              $value[$COL_req_alloc_skill1]=$row[$COL_req_alloc_skill1];
              $value[$COL_req_alloc_skill2]=$row[$COL_req_alloc_skill2];
              $value[$COL_req_alloc_skill3]=$row[$COL_req_alloc_skill3];
              $value[$COL_req_alloc_skill4]=$row[$COL_req_alloc_skill4];
              $value[$COL_req_req_date] = $row[$COL_req_req_date];
              $value[$COL_req_alloc_time] = $row[$COL_req_alloc_time];
              $value[$COL_req_c_response]=$row[$COL_req_c_response];
              $value[$COL_req_created]=$row[$COL_req_created];
              $data[]=$value;
            }
            $response["status"]="success";
            $response["message"]="Response successfully fetched";
            $response["code"] = Code_Success;
            $response["data"] = $data;
          }else{
            $response["status"]="success";
            $response["message"]="No records found";
            $response["code"] = Code_Success;
          }
        }else{
          $response["status"]="error";
          $response["message"]="Failed to fetch requests";
          $response["code"] = Code_query_error;
        }
      break;

    case 'get_res_requests':
      $suid = $postdata->su_id;
      $sql1 = "SELECT * FROM `super_req_table` WHERE `su_id` = $suid AND `c_response` = 1";
      $result = mysqli_query($conn,$sql1);
      if($result){
        if(mysqli_num_rows($result) > 0){
          $data = array();
          while($row = mysqli_fetch_assoc($result)){
            $value=array();
            $value[$COL_req_rid]=$row[$COL_req_rid];
            $value[$COL_req_name]=$row[$COL_req_name];
            $value[$COL_req_su_id] = $row[$COL_req_su_id];
            $value[$COL_req_sid]=$row[$COL_req_sid];
            $value[$COL_req_date]=$row[$COL_req_date];
            $value[$COL_req_skill1]=$row[$COL_req_skill1];
            $value[$COL_req_skill2]=$row[$COL_req_skill2];
            $value[$COL_req_skill3]=$row[$COL_req_skill3];
            $value[$COL_req_skill4]=$row[$COL_req_skill4];
            $value[$COL_req_alloc_skill1]=$row[$COL_req_alloc_skill1];
            $value[$COL_req_alloc_skill2]=$row[$COL_req_alloc_skill2];
            $value[$COL_req_alloc_skill3]=$row[$COL_req_alloc_skill3];
            $value[$COL_req_alloc_skill4]=$row[$COL_req_alloc_skill4];
            $value[$COL_req_req_date] = $row[$COL_req_req_date];
            $value[$COL_req_alloc_time] = $row[$COL_req_alloc_time];
            $value[$COL_req_c_response]=$row[$COL_req_c_response];
            $value[$COL_req_created]=$row[$COL_req_created];
            $data[]=$value;
          }
          $response["status"]="success";
          $response["message"]="Response successfully fetched";
          $response["code"] = Code_Success;
          $response["data"] = $data;
        }else{
          $response["status"]="success";
          $response["message"]="No records found";
          $response["code"] = Code_Success;
        }
      }else{
        $response["status"]="error";
        $response["message"]="Failed to fetch requests";
        $response["code"] = Code_query_error;
      }
      break;

    case 'update_atten':
      $aid = $postdata->aid;
      $atten = $postdata->atten;
      $sql = "UPDATE `HRM_Database`.`allocated_employee_table` SET `atten` = '$atten' WHERE `allocated_employee_table`.`aid` = $aid";
      $result = mysqli_query($conn,$sql);
      if($result){
        $response["status"]="success";
        $response["message"]="Attendance successfully updated";
        $response["code"] = Code_Success;
      }else{
        $response["query"]=$sql1;
        $response["status"]="error";
        $response["message"]="Failed to update attendance";
        $response["code"] = Code_query_error;
      }
      # code...
      break;

    case 'get_res_workers':
      $rid = $postdata->rid;
      $sql = "SELECT * FROM employee_table JOIN allocated_employee_table ON employee_table.eid=allocated_employee_table.eid WHERE allocated_employee_table.rid = $rid ";
      $result = mysqli_query($conn,$sql);
      if($result){
        $data = array();
        if(mysqli_num_rows($result)>0){
          while ($row=mysqli_fetch_assoc($result)) {
                    $value=array();
                    $value[$COL_emp_eid]=$row[$COL_emp_eid];
                    $value[$COL_emp_cid] = $row[$COL_emp_cid];
                    $value[$COL_emp_name]=$row[$COL_emp_name];
                    $value[$COL_emp_auth]=$row[$COL_emp_auth];
                    $value[$COL_emp_aadhar_uid]=$row[$COL_emp_aadhar_uid];
                    $value[$COL_emp_aadhar_string]=$row[$COL_emp_aadhar_string];
                    $value[$COL_emp_skill]=$row[$COL_emp_skill];
                    $value[$COL_emp_emptype]=$row[$COL_emp_emptype];
                    $value[$COL_emp_assigned] = $row[$COL_emp_assigned];
                    $value[$COL_emp_created]=$row[$COL_emp_created];
                    $value["aid"] = $row["aid"];

                    $data[]=$value;
          }
          $response["data"]=$data;
          $response["status"]="success";
          $response["message"]="Data successful fetched";
          $response["code"] = Code_Success;
        }else{
          $response["status"]="success";
          $response["message"]="No records found";
          $response["code"] = Code_Success;
        }
      }else{
        $response["status"]="error";
        $response["message"]="Failed fetch data";
        $response["code"] = Code_query_error;
      }
      break;
    default:
      $response["status"] = "error";
      $response["message"] = "invalid request";
      $response["code"] = Code_data_invalid;
      break;
  }
}else{
  $response["status"] = "error";
  $response["message"] = "error in connection";
  $response["code"] = Code_Conn_error;
}
echo json_encode($response);
 ?>
