<?php
    include 'includes/dbconfig.php';
    $postdata = file_get_contents("php://input");
    $request = json_decode($postdata);
    $response = array();

    $db = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_BASE);

    if (!$db){
        $response["status"] = "error";
        $response["message"] = "Error in Connection";
    } else {
        switch ($request->action){
            case 'GET_EMPLOYEES':
                $select_qry = "SELECT * FROM employee_table";
                if ($res = mysqli_query($db, $select_qry)){
                    $result = array();
                    while ($row = mysqli_fetch_array($res)){
                        $employee["eid"] = $row["eid"];
                        $employee["name"] = $row["name"];
                        $employee["cid"] = $row["cid"];
                        $employee["auth"] = $row["auth"];
                        $employee["aadhar_uid"] = $row["aadhar_uid"];
                        $employee["aadhar_string"] = $row["aadhar_string"];
                        $employee["skill"] = $row["skill"];
                        $employee["emp_type"] = $row["emp_type"];
                        $result[] = $employee;
                    }
                    $response["records"] = $result;
                }else{
                    $response["status"] = "error";
                    $response["message"] = "Error in Query Execution";
                }
                break;
            case 'GET_USERS':
                $select_qry = "SELECT * FROM users_table";
                if ($res = mysqli_query($db, $select_qry)) {
                    $result = array();
                    while ($row = mysqli_fetch_array($res)) {
                        $user["uid"] = $row["uid"];
                        $user["name"] = $row["first_name"] . " " . $row["last_name"];
                        $user["username"] = $row["username"];
                        $user["aadhar_uid"] = $row["aadhar_uid"];
                        $user["email_id"] = $row["email_id"];
                        $user["address"] = $row["address_1"] . " , " . $row["address_2"] . " , " . $row["district"] . " , " . $row["state"] . " - " . $row["pincode"];
                        $user["contact_no"] = $row["contact_no"];
                        $user["blocked"] = $row["blocked"];
                        $result[] = $user;
                    }
                    $response["records"] = $result;
                    $response["status"] = "success";
                    $response["message"] = "Users fetched successfuly";
                } else {
                    $response["status"] = "error";
                    $response["message"] = "Error in Query Execution";
                }
                break;
            case 'GET_SITES':
                $select_qry = "SELECT * FROM site_table";
                if ($res = mysqli_query($db, $select_qry)){
                    $result = array();
                    while ($row = mysqli_fetch_array($res)){
                        $site["sid"] = $row["sid"];
                        $site["name"] = $row["name"];
                        $site["address"] = $row["address"] . " , " . $row["district"] . " , " . $row["state"] . " - " . $row["pincode"];
                        $site["type"] = $row["type"];
                        $site["gid"] = $row["gid"];
                        $result[] = $site;
                    }
                    $response["records"] = $result;
                }else{
                    $response["status"] = "error";
                    $response["message"] = "Error in Query Execution";
                }
                break;
            case 'GET_PKG_CONTRACTORS':
                $select_qry = "SELECT * FROM package_contractors";
                if ($res = mysqli_query($db, $select_qry)){
                    $result = array();
                    while ($row = mysqli_fetch_array($res)){
                        $contractor["pkg_id"] = $row["pkg_id"];
                        $contractor["name"] = $row["name"];
                        $contractor["sid"] = $row["sid"];
                        $contractor["email"] = $row["email"];
                        $contractor["aadhar_uid"] = $row["aadhar_uid"];
                        $contractor["aadhar_string"] = $row["aadhar_string"];
                        $contractor["uid"] = $row["uid"];
                        $result[] = $contractor;
                    }
                    $response["records"] = $result;
                }else{
                    $response["status"] = "error";
                    $response["message"] = "Error in Query Execution";
                }
                break;
            case 'GET_CONTRACTORS':
                $select_qry = "SELECT * FROM contract_users";
                if ($res = mysqli_query($db, $select_qry)){
                    $result = array();
                    while ($row = mysqli_fetch_array($res)){
                        $contractor["cid"] = $row["cid"];
                        $contractor["name"] = $row["name"];
                        $contractor["sid"] = $row["sid"];
                        $contractor["email"] = $row["email"];
                        $contractor["aadhar_uid"] = $row["aadhar_uid"];
                        $contractor["aadhar_string"] = $row["aadhar_string"];
                        $contractor["uid"] = $row["uid"];
                        $result[] = $contractor;
                    }
                    $response["records"] = $result;
                }else{
                    $response["status"] = "error";
                    $response["message"] = "Error in Query Execution";
                }
                break;
            case 'GET_EMPLOYEE_ATTENDANCE':
                $select_qry = "SELECT * FROM employee_attendance_table";
                if ($res = mysqli_query($db, $select_qry)){
                    $result = array();
                    while ($row = mysqli_fetch_array($res)){
                        $attendance["atid"] = $row["atid"];
                        $attendance["eid"] = $row["eid"];
                        $attendance["sid"] = $row["sid"];
                        $attendance["date"] = $row["date"];
                        $attendance["enter_time"] = $row["enter_time"];
                        $attendance["exit_time"] = $row["exit_time"];
                        $result[] = $attendance;
                    }
                    $response["records"] = $result;
                }else{
                    $response["status"] = "error";
                    $response["message"] = "Error in Query Execution";
                }
                break;
            case 'GET_PKG_CONTRACTORS_REQUEST':
                $select_qry = "SELECT * FROM super_req_table";
                if ($res = mysqli_query($db, $select_qry)){
                    $result = array();
                    while ($row = mysqli_fetch_array($res)){
                        $pkg_request["rid"] = $row["rid"];
                        $pkg_request["task_name"] = $row["task_name"];
                        $pkg_request["su_id"] = $row["su_id"];
                        $pkg_request["sid"] = $row["sid"];
                        $pkg_request["cid"] = $row["cid"];
                        $pkg_request["date"] = $row["date"];
                        $pkg_request["skill_1"] = $row["skill_1"];
                        $pkg_request["skill_2"] = $row["skill_2"];
                        $pkg_request["skill_3"] = $row["skill_3"];
                        $pkg_request["skill_4"] = $row["skill_4"];
                        $pkg_request["alloc_skill_1"] = $row["alloc_skill_1"];
                        $pkg_request["alloc_skill_2"] = $row["alloc_skill_2"];
                        $pkg_request["alloc_skill_3"] = $row["alloc_skill_3"];
                        $pkg_request["alloc_skill_4"] = $row["alloc_skill_4"];
                        $pkg_request["alloc_time"] = $row["alloc_time"];
                        $pkg_request["req_date"] = $row["req_date"];
                        $pkg_request["c_response"] = $row["c_response"];
                        $result[] = $pkg_request;
                    }
                    $response["records"] = $result;
                }else{
                    $response["status"] = "error";
                    $response["message"] = "Error in Query Execution";
                }
                break;
            case 'GET_ALL':
                $select_contract = "SELECT * FROM contract_users";
                $select_pkg_contract = "SELECT * FROM package_contractors";
                $select_sites = "SELECT * FROM site_table";
                $select_user = "SELECT * FROM users_table";
                $select_employees = "SELECT * FROM employee_table";
                $data = array();
                if ($res = mysqli_query($db, $select_contract)){
                    $result = array();
                    while ($row = mysqli_fetch_array($res)){
                        $contractor["cid"] = $row["cid"];
                        $contractor["name"] = $row["name"];
                        $contractor["sid"] = $row["sid"];
                        $contractor["email"] = $row["email"];
                        $contractor["aadhar_uid"] = $row["aadhar_uid"];
                        $contractor["aadhar_string"] = $row["aadhar_string"];
                        $contractor["uid"] = $row["uid"];
                        $result[] = $contractor;
                    }
                    $data["contractors"] = $result;
                }else{
                    $response["status"] = "error";
                    $response["message"] = "Error in Query Execution";
                }
                if ($res = mysqli_query($db, $select_pkg_contract)){
                    $result = array();
                    while ($row = mysqli_fetch_array($res)){
                        $contractor["pkg_id"] = $row["pkg_id"];
                        $contractor["name"] = $row["name"];
                        $contractor["sid"] = $row["sid"];
                        $contractor["email"] = $row["email"];
                        $contractor["aadhar_uid"] = $row["aadhar_uid"];
                        $contractor["aadhar_string"] = $row["aadhar_string"];
                        $contractor["uid"] = $row["uid"];
                        $result[] = $contractor;
                    }
                    $data["pkg_contractors"] = $result;
                }else{
                    $response["status"] = "error";
                    $response["message"] = "Error in Query Execution";
                }
                if ($res = mysqli_query($db, $select_sites)){
                    $result = array();
                    while ($row = mysqli_fetch_array($res)){
                        $site["sid"] = $row["sid"];
                        $site["name"] = $row["name"];
                        $site["address"] = $row["address"] . " , " . $row["district"] . " , " . $row["state"] . " - " . $row["pincode"];
                        $site["type"] = $row["type"];
                        $site["gid"] = $row["gid"];
                        $result[] = $site;
                    }
                    $data["sites"] = $result;
                }else{
                    $response["status"] = "error";
                    $response["message"] = "Error in Query Execution";
                }
                if ($res = mysqli_query($db, $select_user)) {
                    $result = array();
                    while ($row = mysqli_fetch_array($res)) {
                        $user["uid"] = $row["uid"];
                        $user["name"] = $row["first_name"] . " " . $row["last_name"];
                        $user["username"] = $row["username"];
                        $user["aadhar_uid"] = $row["aadhar_uid"];
                        $user["email_id"] = $row["email_id"];
                        $user["address"] = $row["address_1"] . " , " . $row["address_2"] . " , " . $row["district"] . " , " . $row["state"] . " - " . $row["pincode"];
                        $user["contact_no"] = $row["contact_no"];
                        $user["blocked"] = $row["blocked"];
                        $result[] = $user;
                    }
                    $data["users"] = $result;
                } else {
                    $response["status"] = "error";
                    $response["message"] = "Error in Query Execution";
                }
                if ($res = mysqli_query($db, $select_employees)){
                    $result = array();
                    while ($row = mysqli_fetch_array($res)){
                        $employee["eid"] = $row["eid"];
                        $employee["name"] = $row["name"];
                        $employee["cid"] = $row["cid"];
                        $employee["auth"] = $row["auth"];
                        $employee["aadhar_uid"] = $row["aadhar_uid"];
                        $employee["aadhar_string"] = $row["aadhar_string"];
                        $employee["skill"] = $row["skill"];
                        $employee["emp_type"] = $row["emp_type"];
                        $result[] = $employee;
                    }
                    $data["employees"] = $result;
                }else{
                    $response["status"] = "error";
                    $response["message"] = "Error in Query Execution";
                }
                $response["records"] = $data;
                break;
            default:
                $response['status'] = "error";
                $response['message'] = "Switch case error";
                break;
        }
    }

    echo json_encode($response);
?>