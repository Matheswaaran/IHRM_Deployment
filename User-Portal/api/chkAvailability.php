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
      case 'CONTRACTOR_CHK_EMAIL':
          $chk_sql = "SELECT * FROM contract_users WHERE email = '$data'";
          break;

      case 'PKG_CONTRACTOR_CHK_EMAIL':
        $chk_sql = "SELECT * FROM package_contractors WHERE email = '$data'";
        break;

      case 'CONTRACTOR_CHK_USERNAME':
        $chk_sql = "SELECT * FROM package_contractors WHERE name = '$data'";
        break;

      case 'PKG_CONTRACTOR_CHK_USERNAME':
          $chk_sql = "SELECT * FROM contract_users WHERE name = '$data'";
          break;

      case 'PKG_CONTRACTOR_CHK_ID':
          $chk_sql = "SELECT * FROM package_contractors WHERE pkg_id = '$data'";
          break;

      case 'CONTRACTOR_CHK_ID':
          $chk_sql = "SELECT * FROM contract_users WHERE cid = '$data'";
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
