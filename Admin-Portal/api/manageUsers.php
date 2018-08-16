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
        $users = $request->users;
        $action = $request->action;

        switch ($action){
            case 'REMOVE_USERS':
                foreach ($users as $user){
                    $remove_sql = "DELETE FROM users_table WHERE uid = '$user->uid'";
                    if (mysqli_query($db, $remove_sql)){
                        $actionError = false;
                    }else{
                        $actionError = true;
                    }
                }

                if (!$actionError){
                    $response['status'] = "success";
                    $response['message'] = "Users Deleted Successfully";
                } else{
                    $response['status'] = "error";
                    $response['message'] = "Error in query execution";
                }
                break;
            case 'BLOCK_USERS':
                foreach ($users as $user){
                    $remove_sql = "UPDATE users_table SET blocked = 1 WHERE uid = '$user->uid'";
                    if (mysqli_query($db, $remove_sql)){
                        $actionError = false;
                    }else{
                        $actionError = true;
                    }
                }

                if (!$actionError){
                    $response['status'] = "success";
                    $response['message'] = "Users Blocked Successfully";
                } else{
                    $response['status'] = "error";
                    $response['message'] = "Error in query execution";
                }
                break;
            case 'UNBLOCK_USERS':
                foreach ($users as $user){
                    $remove_sql = "UPDATE users_table SET blocked = 0 WHERE uid = '$user->uid'";
                    if (mysqli_query($db, $remove_sql)){
                        $actionError = false;
                    }else{
                        $actionError = true;
                    }
                }

                if (!$actionError){
                    $response['status'] = "success";
                    $response['message'] = "Users Unblocked Successfully";
                } else{
                    $response['status'] = "error";
                    $response['message'] = "Error in query execution";
                }
                break;
            default:
                $response['status'] = "error";
                $response['message'] = "Switch case error";
        }
    }

    echo json_encode($response);

?>