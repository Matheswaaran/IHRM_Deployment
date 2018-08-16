<?php
    include 'includes/SessionUtils.php';

//    $message = $_GET["msg"];

    $session = new SessionUtils();
    $response = array();

    $response['user_id'] = $_SESSION["user_id"];
    $response['user_username'] = $_SESSION["user_username"];
    $response['user_blocked'] = $_SESSION["user_blocked"];
    if ($session->chkSession($_SESSION['user_username'])){
        if ($session->isBlocked($_SESSION['user_username'])){
            $response['status'] = "error";
            $response['message'] = "You have blocked out of the portal. Contact the administrator.";
        }else {
            $response['status'] = "success";
            $response['message'] = "Session already exists. Make sure to logout when you leave.";
        }
    }else{
        $response['status'] = "error";
        $response['message'] = "Session doesn't exists. Login to Continue.";
    }

    echo json_encode($response);
?>