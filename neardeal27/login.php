<?php
session_start();
$user="root";
$pass="";
$host="localhost";
$dbnamne="near_deal27";

$username = $_REQUEST['username'];
$password = $_REQUEST['password'];

/*$username = 'i';
$password = 'a';
*/
//create connection
$conn = new mysqli($host, $user, $pass, $dbnamne);

//check connection
if($conn->connect_error){
    die("connection failed" .$com->connect_error);
}

$sql = "SELECT * FROM user WHERE username='{$username}' AND password='{$password}' LIMIT 1";

$result = $conn->query($sql);
$user = array();

while($row =$result->fetch_assoc()){
    $user = $row;
}

if (!empty($user)) {
    # code..
echo json_encode(array(
    'success' => true,
    'user' => $user
),JSON_NUMERIC_CHECK);
}else{
    # code...
echo json_encode(array(
    'success' => true,
    'user' => [
           'id'=>0,
           'username'=>'', 
           'password'=>''
    ]
),JSON_NUMERIC_CHECK);
}
