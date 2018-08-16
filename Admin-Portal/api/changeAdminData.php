<?php
    include 'includes/SessionUtils.php';
    $session = new SessionUtils();
    $postdata = file_get_contents("php://input");
    $request = json_decode($postdata);
    $response = array();
    $action = $request->action;

    $db = mysqli_connect(DB_HOST,DB_USER,DB_PASS,DB_BASE);

    if (!$db){
        $response['status'] = "error";
        $response['message'] = "Error in Connection";
    }else {
        $gid = $request->gid;
        switch ($action) {
            case 'CHANGE_PASSWORD':
                $old = $request->old;
                $new = $request->new;
                $confirm = $request->confirm;

                $select_sql = "SELECT * FROM goverment_users WHERE gid = '$gid'";
                if($res = mysqli_query($db, $select_sql)){
                    if($row = mysqli_fetch_array($res)){
                        if($row["password"] != $old){
                            $response['status'] = "error";
                            $response['message'] = "Current Password wrong.";
                        } else if($row["password"] == $old){
                            $update_sql = "UPDATE goverment_users SET password = '$new' WHERE gid = '$gid'";
                            if(mysqli_query($db, $update_sql)){
                                $response['status'] = "success";
                                $response['message'] = "Password has been updated.";
                            } else {
                                $response['status'] = "error";
                                $response['message'] = "Error in update query execution.";
                            }
                        }
                    }
                }else {
                    $response['status'] = "error";
                    $response['message'] = "Error in select query execution.";
                }
                break;

            case 'CHANGE_EMAIL':
                $new = $request->new;
                $update_sql = "UPDATE goverment_users SET email = '$new' WHERE gid = '$gid'";
                if(mysqli_query($db, $update_sql)){
                    $response['status'] = "success";
                    $response['message'] = "Email Address has been updated.";
                } else {
                    $response['status'] = "error";
                    $response['message'] = "Error in update query execution.";
                }
                break;

            case 'CHANGE_PHONE':
                $new = $request->new;
                $update_sql = "UPDATE goverment_users SET email = '$new' WHERE gid = '$gid'";
                if(mysqli_query($db, $update_sql)){
                    $response['status'] = "success";
                    $response['message'] = "Phone Number has been updated.";
                } else {
                    $response['status'] = "error";
                    $response['message'] = "Error in update query execution.";
                }
                break;

            default:
                $response['status'] = "error";
                $response['message'] = "Default case.";
                break;
        }
    }
    echo json_encode($response);
?>