<?php
CLASS Utils{
  private $response;
  function __construct() {
    $response = array();
  }
  public function getResponse($code,$status,$message){
    $response["status"]= $status;
    $response["message"]= $message;
    $response["code"] = $code;
    return $response;
  }

  public function getResponse1($code,$status,$message,$data){
    $response["status"]= $status;
    $response["message"]= $message;
    $response["code"] = $code;
    $response["data"] = $data;
    return $response;
  }
}
 ?>
