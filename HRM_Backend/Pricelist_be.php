<?php
require 'config.php';
require 'tableconfig.php';
require 'utils.php';
require 'columns.php';
require 'default_code.php';

$db_name=DB_NAME;
$response=array();

$utils = new Utils();

$postdata = json_decode(file_get_contents("php://input"));
$option = $postdata->option;

$db_name = DB_NAME;
$table = Table_pricelist;

$conn = mysqli_connect(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);

if($conn){
  switch ($option) {
    case 'get':
      $query = "SELECT * FROM $db_name.$table";
      $result = mysqli_query($conn,$query);
      if($result){
        $pricelist = array();
        if(mysqli_num_rows($result) > 0){
          while ($row = mysqli_fetch_assoc($result)) {
            $values = array();
            $values[$COL_price_priceid] = $row[$COL_price_priceid];
            $values[$COL_price_createdate] = $row[$COL_price_createdate];
            $values[$COL_price_createtime] = $row[$COL_price_createtime];
            $values[$COL_price_pricelistname] = $row[$COL_price_pricelistname];
            $values[$COL_price_Pert] = $row[$COL_price_Pert];
            $values[$COL_price_AStatus] = $row[$COL_price_AStatus];
            $pricelist[] = $values;
          }
          $response = utils->getResponse1(Code_Success,"success","data fetched",$pricelist);
        }else{
          $response = $utils->getResponse(Code_Error,"error","no list found");
        }

      }
      break;

    default:
      # code...
      break;
  }
}else{
  $response = $utils->getResponse(Code_Conn_error,"error","error in connection");
}

echo json_encode($response);

?>
