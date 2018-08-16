<?php
    include 'includes/SessionUtils.php';

    $session = new SessionUtils();
    $response = array();
    if ($session->Logout()){
        $response['status'] = "success";
        $response['message'] = "Logged out successfully.";
    }else{
        $response['status'] = "error";
        $response['message'] = "Logout unsuccessful.";
    }

    echo json_encode($response);
?>