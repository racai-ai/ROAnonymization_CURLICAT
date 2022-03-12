<?php

require_once "string_utils.php";
require_once "anonymization.php";

function error($msg){
    die(json_encode(["status"=>"ERROR", "message"=>$msg]));
}


// Get the JSON contents
$json = file_get_contents('php://input');

// decode the json data
$data = json_decode($json,true);

if(!is_array($data) || !isset($data['text']))error("Wrong input format; missing text field");

if(isset($data['format']) && strcasecmp($data['format'],"text")!=0)error("Only text format is supported");


$text=$data['text'];

$anon=ANONYMIZATION_anonymize_text($text,true);
$anon=ANONYMIZATION_deanonymize_text($anon);

echo json_encode(["orignal_text"=>$text,"anonymized_text"=>$anon,"format"=>$data['format']]);
