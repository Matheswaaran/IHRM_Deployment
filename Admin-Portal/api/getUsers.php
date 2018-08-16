<?php
    include 'includes/dbconfig.php';
    include 'includes/SessionUtils.php';
    $response = array();

    $db = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_BASE);

    if (!$db){
        $response["status"] = "error";
        $response["message"] = "Error in Connection";
    }else{
        $postdata = file_get_contents("php://input");
        $request = json_decode($postdata);
        $id = $request->user_id;

        $select_qry = "SELECT * FROM users_table WHERE uid = '$id'";
        if ($res = mysqli_query($db, $select_qry)) {
            $result = array();
            while ($row = mysqli_fetch_array($res)) {
                $user["uid"] = $row["uid"];
                $user["first_name"] = $row["first_name"];
                $user["last_name"] = $row["last_name"];
                $user["username"] = $row["username"];
                $user["aadhar_uid"] = $row["aadhar_uid"];
                $user["email_id"] = $row["email_id"];
                $user["address_1"] = $row["address_1"];
                $user["address_2"] = $row["address_2"];
                $user["district"] = $row["district"];
                $user["state"] = $row["state"];
                $user["pincode"] = $row["pincode"];
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
    }

    echo json_encode($response);
?>