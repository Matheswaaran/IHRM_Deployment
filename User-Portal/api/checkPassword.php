<?php
    include 'includes/dbconfig.php';
    include 'includes/SessionUtils.php';

    $password = $_POST['password'];

    $session = new SessionUtils();
    $response = array();

    if (isset($password)){
        if ($session->chkPassword($password)){
            $response['status'] = "success";
            $response['message'] = "Password is Correct.";
        }else{
            $response['status'] = "error";
            $response['message'] = "Incorrect Password.";
        }
    }else{
        $response['status'] = "error";
        $response['message'] = "Password is not set.";
    }

    echo json_encode($response);
?>