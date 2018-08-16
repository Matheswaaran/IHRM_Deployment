<?php
    include 'includes/SessionUtils.php';
    $session = new SessionUtils();
    $postdata = file_get_contents("php://input");
    $request = json_decode($postdata);

    $sid = $request->sid;
    $name = $request->name;
    $address = $request->address;
    $district = $request->district;
    $state = $request->state;
    $pincode = $request->pincode;
    $type = $request->type;
    $gid = $request->gid;
    $response = array();

    $db = mysqli_connect(DB_HOST,DB_USER,DB_PASS,DB_BASE);

    if (!$db){
        $response['status'] = "error";
        $response['message'] = "Error in Connection";
    }else{
        $insert_sql = "INSERT INTO site_table (sid, name, address, district, state, pincode, type, gid) VALUES ('$sid', '$name', '$address', '$district', '$state', '$pincode', '$type', '$gid')";
        if (mysqli_query($db, $insert_sql)){
            $response['status'] = "success";
            $response['message'] = "Site added successfully";
        }else{
            $response['status'] = "error";
            $response['message'] = "Error in Query Execution";
        }
    }

    echo json_encode($response);
?>
