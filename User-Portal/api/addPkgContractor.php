<?php
    include 'includes/dbconfig.php';
    $postdata = file_get_contents("php://input");
    $request = json_decode($postdata);

    $pkg_id = $request->pkg_id;
    $name = $request->name;
    $sid = $request->sid;
    $email = $request->email;
    $password = $request->password;
    $aadhar_uid = $request->aadhar_uid;
    $aadhar_string = $request->aadhar_string;
    $uid = $request->uid;
    $response = array();

    $db = mysqli_connect(DB_HOST,DB_USER,DB_PASS,DB_BASE);

    if (!$db){
        $response['status'] = "error";
        $response['message'] = "Error in Connection";
    }else{
        $insert_sql = "INSERT INTO package_contractors(pkg_id, name, sid, email, password, aadhar_uid, aadhar_string, uid) VALUES ($pkg_id,'$name', $sid, '$email', '$password', $aadhar_uid, '$aadhar_string', $uid)";
        $response['query'] = $insert_sql;
        if (mysqli_query($db,$insert_sql)){
            $response['status'] = "success";
            $response['message'] = "Package Contractor added successfully";
        }else{
            $response['status'] = "error";
            $response['message'] = "Error in Query Execution";
        }
    }

    echo json_encode($response);
?>