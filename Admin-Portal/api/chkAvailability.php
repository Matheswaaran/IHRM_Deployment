<?php
  include 'includes/dbconfig.php';
  $postdata = file_get_contents("php://input");
  $request = json_decode($postdata);

  $data = $request->data;
  $type = $request->type;

  $response = array();

  $db = mysqli_connect(DB_HOST,DB_USER,DB_PASS,DB_BASE);

  if ($db){
    switch ($type) {
      case 'email':
        $chk_sql = "SELECT * FROM users_table WHERE email_id = '$data'";
        break;

      case 'username':
        $chk_sql = "SELECT * FROM users_table WHERE username = '$data'";
        break;

      default:
        $chk_sql = "";
        break;
    }


      if(mysqli_num_rows(mysqli_query($db, $chk_sql)) == 0){
        $response["status"] = "available";
      }else{
        $response["status"] = "not available";
      }
  }
  echo json_encode($response);
?>
