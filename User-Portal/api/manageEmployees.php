<?php
    include 'includes/dbconfig.php';
    $postdata = file_get_contents("php://input");
    $request = json_decode($postdata);
    $actionError = false;
    $response = array();

    $db = mysqli_connect(DB_HOST,DB_USER,DB_PASS,DB_BASE);

    if (!$db){
        $response['status'] = "error";
        $response['message'] = "Error in Connection";
    } else {
        $employees = $request->employees;
        $action = $request->action;

        switch ($action) {
            case 'APPROVE_EMPLOYEES':
                foreach ($employees as $employee) {
                    $approve_sql = "UPDATE employee_table SET auth = '1' WHERE eid = '$employee->eid'";
                    if (mysqli_query($db, $approve_sql)){
                        $actionError = false;
                    }else{
                        $actionError = true;
                    }
                    if (!$actionError){
                        $response['status'] = "success";
                        $response['message'] = "Employees Approved Successfully";
                    } else{
                        $response['status'] = "error";
                        $response['message'] = "Error in query execution";
                    }
                }
                break;
            case 'DIS_APPROVE_EMPLOYEES':
                foreach ($employees as $employee) {
                    $disapprove_sql = "UPDATE employee_table SET auth = '0' WHERE eid = '$employee->eid'";
                    if (mysqli_query($db, $disapprove_sql)){
                        $actionError = false;
                    }else{
                        $actionError = true;
                    }
                    if (!$actionError){
                        $response['status'] = "success";
                        $response['message'] = "Employees Approved Successfully";
                    } else{
                        $response['status'] = "error";
                        $response['message'] = "Error in query execution";
                    }
                }
                break;
            default:
                $response['status'] = "error";
                $response['message'] = "Switch case error";
        }
    }
    echo json_encode($response);
?>