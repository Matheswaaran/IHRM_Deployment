<?php
require 'config.php';
require 'tableconfig.php';
require 'default_code.php';
require 'utils.php';

$postdata = json_decode(file_get_contents("php://input"));
$option = $postdata->option;

$response = array();
$response["postdata"] = $option;


$db_name = DB_NAME;
$table_name = Table_contractor;
$table_emp_name = Table_employee;
$table_sup_request_name = Table_sup_requests;

//contractor table
$COL_cid = "cid";
$COL_sid = "sid";
$COL_name = "name";
$COL_email = "email";
$COL_password = "password";
$COL_aadhar_uid = "aadhar_uid";
$COL_aadhar_string = "aadhar_string";
$COL_gid = "gid";
$COL_gcmtoken = "GCMtoken";
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
        $name = $postdata->name;
        $password = $postdata->password;
        $sql = "SELECT * from $db_name.$table_name WHERE $COL_name='$name'";
        $result = mysqli_query($conn,$sql);
        if($result){
          $row = mysqli_fetch_assoc($result);
          if($password == $row['password']){
            $data = array();
            $data[$COL_cid] = $row[$COL_cid];
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
            $response["query"] = $sql;
            $response["code"] = Code_data_invalid;
            $response["message"] = "User login failed";
          }
        }else{
          $response["status"]="error";
          $response["message"]="Failed to validate user.query not valid";
        }
      break;

    case 'signup':
        $name = $postdata->name;
        $email = $postdata->email;
        $password = $postdata->password;
        $aadhar_uid = $postdata->aadhar_uid;
        $aadhar_string = $postdata->aadhar_string;
        $gid = $postdata->gid;
        $sql = "INSERT INTO $db_name.$table_name (`$COL_cid`,`$COL_name`,`$COL_email`,`$COL_password`,`$COL_emp_aadhar_uid`,`$COL_emp_aadhar_string`,`$COL_emp_skill`,`$COL_emp_emptype`) VALUES (NULL , `$name` , `$email`, `$password`, $aadhar_uid,`$aadhar_string`, $gid, CURRENT_TIMESTAMP )";
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
    case 'updateGCMtoken':
      $cid = $postdata->cid;
      $gcm = $postdata->gcm;
      $sql = "UPDATE INTO $db_name.$table_name SET `$COL_gcmtoken` = `$gcm`";
      $result = mysqli_query($conn,$sql);
      if($result){
        $response = $utils->getResponse(Code_Success,"success","GCM token successfully updated");
      }else{
        $response = $utils->getResponse(Code_query_error,"error","Error in updating the gcm token");
      }
      break;

    case 'create_emp':
        $cid = $postdata->cid;
        $name = $postdata->name;
        $aadhar_uid = $postdata->aadhar_uid;
        $aadhar_string = $postdata->aadhar_string;
        $skill = $postdata->skill;
        $emptype = $postdata->emp_type;
        $sql = "INSERT INTO $db_name.$table_emp_name (`$COL_emp_eid`,`$COL_emp_name`,`$COL_emp_cid`,`$COL_emp_auth`,`$COL_emp_aadhar_uid`,`$COL_emp_aadhar_string`,`$COL_emp_skill`,`$COL_emp_emptype`,`$COL_emp_assigned`,`$COL_emp_created` ) VALUES(NULL , '$name' , $cid, 0, $aadhar_uid,'$aadhar_string', $skill, $emptype, 0,CURRENT_TIMESTAMP )";
        $result = mysqli_query($conn,$sql);
        if($result){
          $response["status"]="success";
          $response["name"] = $name;
          $response["aadhar_uid"] = $aadhar_uid;
          $response["message"]="New employee created";
          $response["code"] = Code_Success;
        }else{
          $response["status"]="error";
          $response["query"] = $sql;
          $response["result"] = $result;
          $response["message"]="Failed to create employee";
          $response["code"] = Code_query_error;
        }
      break;

    case 'get_emp':
        $cid = $postdata->cid;
        $sql = "SELECT * FROM $db_name.$table_emp_name WHERE $COL_cid = $cid";
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

      case 'get_emp_par':
        $name = $postdata->name;
        $aadhar_uid = $postdata->aadhar_uid;
        $sql = "SELECT * FROM $db_name.$table_emp_name WHERE $COL_name = $name AND $COL_";
        $result = mysqli_query($conn,$sql);
        if($result){
          $data = array();
          if(mysqli_num_rows($result)>0){
            $row=mysqli_fetch_assoc($result);
            $response["cid"] = $row[$COL_cid]; 
            $response["data"]=$value;
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

      case 'get_auth_emp':
          $cid = $postdata->cid;
          $sql = "SELECT * FROM $db_name.$table_emp_name WHERE $COL_cid = $cid AND $COL_emp_auth = 1";
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

      case 'get_unassigned_emp':
          $cid = $postdata->cid;
          $sql = "SELECT * FROM $db_name.$table_emp_name WHERE $COL_cid = $cid AND $COL_emp_assigned = 0";
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

    case 'get_assigned_emp':
          $cid = $postdata->cid;
          $sql = "SELECT * FROM $db_name.$table_emp_name WHERE $COL_cid = $cid AND $COL_emp_assigned = 1";
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



    case 'get_request':
      $cid = $postdata->cid;
      $sql = "SELECT * FROM $db_name.$table_sup_request_name";
      $result = mysqli_query($conn,$sql);
      if($result){
        $data = array();
        if(mysqli_num_rows($result)>0){
          while ($row=mysqli_fetch_assoc($result)) {
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
          $response["data"]=$data;
          $response["status"]="success";
          $response["query"] = $sql;
          $response["message"]="Data successful fetched";
          $response["code"] = Code_Success;
        }else{
          $response["status"]="success";
          $response["query"] = $sql;
          $response["message"]="No records found";
          $response["code"] = Code_Success;
        }
      }else{
        $response["status"]="error";
        $response["message"]="Failed fetch data";
        $response["code"] = Code_query_error;
      }
      break;
    case 'update_response':
      $cid = $postdata->cid;
      $rid = $postdata->rid;
      $skill1 = $postdata->alloc_skill_1;
      $skill2 = $postdata->alloc_skill_2;
      $skill3 = $postdata->alloc_skill_3;
      $skill4 = $postdata->alloc_skill_4;
      $sql = "SELECT $COL_req_c_response FROM $db_name.$table_sup_request_name WHERE '$COL_req_rid' = $rid";
      $result = mysqli_query($conn,$sql);
      if($result){
        $row = mysqli_fetch_assoc($result);
        $c_res = $row[$COL_req_c_response];
        if($c_res == 0 || $c_res == 2){
          $update1 = "UPDATE `HRM_Database`.`super_req_table` SET `alloc_skill_1` = '$skill1', `alloc_skill_2` = '$skill2', `alloc_skill_3` = '$skill3', `alloc_skill_4` = '$skill4', `c_response` = '1' WHERE `super_req_table`.`rid` = $rid";
          $update = "UPDATE $db_name.$table_sup_request_name SET '$COL_req_c_response' = 1, '$COL_req_alloc_skill1' = $skill1 ,'$COL_req_alloc_skill2' = $skill2 ,'$COL_req_alloc_skill3' = $skill3 ,'$COL_req_alloc_skill4' = $skill4 WHERE '$COL_req_rid' = $rid";
          $update_result = mysqli_query($conn,$update1);
          if($update_result){
            $response["status"]="success";
            $response["message"]="Your Response is successfully submitted";
            $response["code"] = Code_Success;
          }else{
            $response["status"]="error";
            $response["in"] = $update1;
            $response["message"]="Unable to update your response";
            $response["code"] = Code_query_error;
          }
        }else{
          $response["status"]="success";
          $response["message"]="Request is already responded";
          $response["code"] = Code_Success;
        }
      }else{
        $response["status"]="error";
        $response["message"]="Failed fetch data";
        $response["code"] = Code_query_error;
      }
      break;
    case 'viewresponse':
      $cid = $postdata->cid;
      $sql = "SELECT * FROM $db_name.$table_sup_request_name WHERE '$COL_req_cid' = $cid ";
      $result = mysqli_query($conn,$sql);
      if($result){
        $data = array();
        if(mysqli_num_rows($result)>0){
          while ($row=mysqli_fetch_assoc($result)) {
                    $value=array();
                    $value[$COL_req_rid]=$row[$COL_req_rid];
                    $value[$COL_req_name]=$row[$COL_req_name];
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
                    $value[$COL_req_c_response]=$row[$COL_req_c_response];
                    $value[$COL_req_created]=$row[$COL_req_created];
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

    case 'allocate_employee':
      $rid = $postdata->rid;
      $eid = $postdata->eid;
      $date = $postdata->date;
      $sql = "INSERT INTO `HRM_Database`.`allocated_employee_table` (`aid`, `rid`, `eid`, `date`, `atten`) VALUES (NULL, '$rid', '$eid', '$date', '0')";
      $result = mysqli_query($conn,$sql);
      if($result){
        $response = $utils->getResponse(Code_Success,"success","Employee allocated");
      }else{
        $response = $utils->getResponse(Code_query_error,"error","Error in allocating employee");
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
