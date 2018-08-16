<?php
// Enabling error reporting
error_reporting(-1);
ini_set('display_errors', 'On');

require_once __DIR__ . '/firebase.php';
require_once __DIR__ . '/push.php';

$firebase = new Firebase();
$push = new Push();



// notification title
$title = 'test title';

// notification message
$arr = array();
$arr["test1"] = "allu";
$arr["test2"] = "allu2";
$message = $arr;

// push type - single user / topic
$push_type = 'topic';

// whether to include to image or not
$include_image = FALSE;


$push->setTitle($title);
$push->setMessage($message);
$push->setIsBackground(FALSE);


$json = '';
$response = '';

if ($push_type == 'topic') {
    $json = $push->getPush();
    $response = $firebase->sendToTopic('global', $json);
} else if ($push_type == 'individual') {
    $json = $push->getPush();
    $regId = isset($_GET['regId']) ? $_GET['regId'] : '';
    $response = $firebase->send($regId, $json);
}

echo $response."message sent";
?>
