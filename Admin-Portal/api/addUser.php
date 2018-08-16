<?php
    include 'includes/SessionUtils.php';
    $session = new SessionUtils();
    $postdata = file_get_contents("php://input");
    $request = json_decode($postdata);

    $first_name = $request->first_name;
    $last_name = $request->last_name;
    $username = $request->username;
    $email = $request->email;
    $aadhar = $request->aadhar;
    $password = $request->password;
    $address_1 = $request->address_1;
    $address_2 = $request->address_2;
    $dist = $request->district;
    $state = $request->state_name;
    $pincode = $request->pincode;
    $contact_no = $request->contact_no;
    $gid = $request->gid;
//    $password = $session->encryptIt($password);
    $response = array();

    $db = mysqli_connect(DB_HOST,DB_USER,DB_PASS,DB_BASE);

    if (!$db){
        $response['status'] = "error";
        $response['message'] = "Error in Connection";
    }else{
        $insert_sql = "INSERT INTO users_table(first_name, last_name, username, email_id, aadhar_uid, address_1, address_2, district, state, pincode, contact_no, password, gid) VALUES ('$first_name', '$last_name', '$username', '$email', '$aadhar', '$address_1', '$address_2', '$dist', '$state', '$pincode', '$contact_no', '$password', '$gid')";

        if (mysqli_query($db,$insert_sql)){
            $response['status'] = "success";
            $response['message'] = "User added successfully";
        }else{
            $response['status'] = "error";
            $response['message'] = "Error in Query Execution";
        }
    }

    echo json_encode($response);
?>