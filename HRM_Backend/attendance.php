<?php
require 'config.php';
require 'tableconfig.php';
require 'default_code.php';
require 'utils.php';

$postdata = json_decode(file_get_contents('php://input'));
$response = array();
$option = $postdata->option;

$db_name = DB_NAME;
$tablename = Table_emp_atten_table;
$utils = new Utils();

$conn = mysqli_connect(DB_HOST,DB_USERNAME, DB_PASSWORD, DB_NAME);
if($conn){
	switch ($option) {
		case 'entry':
			$eid = $postdata->eid;
			$entry = $postdata->entrytime;
			$date = $postdata->date;
			$sid = $postdata->sid;
			$sql = "INSERT INTO `HRM_Database`.`employee_attendance_table` (`atid`, `eid`, `sid`, `date`, `enter_time`, `exit_time`) VALUES (NULL, $eid, $sid, '$date', '$entry', '')";
			$result = mysqli_query($conn,$sql);
			if($result){
				$response = $utils->getResponse(Code_Success,"success","Attendance successfully inserted");
			}else{
				$response = $utils->getResponse(Code_query_error,"error","Error in updating the attendance");
			}
			break;
		case 'exit':
			$eid = $postdata->eid;
			$exit = $postdata->exittime;
			$date = $postdata->date;
			$sid = $postdata->sid;
			$sql = "UPDATE `HRM_Database`.`employee_attendance_table` SET `exit_time` = '$exit' WHERE `eid` = $eid AND `date` = '$date'";
			$result = mysqli_query($conn,$sql);
			if($result){
				$response = $utils->getResponse(Code_Success,"success","Attendance successfully updated");
			}else{
				$response = $utils->getResponse(Code_query_error,"error","Error in updating the attendance");
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