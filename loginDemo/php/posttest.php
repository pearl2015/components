<?php 
//http://localhost:8080/sample1/webservice2.php?json={%22UserName%22:1,%22FullName%22:2}
//$json=$_POST ['json'];
error_reporting(E_ALL ^ E_DEPRECATED);
// $json = '{"username": "dsvcjf","password":"ddfshfd"}';
// echo $json;
$json = file_get_contents('php://input');
//echo ($json = file_get_contents('php://input'));
$obj = json_decode($json);

 
//  //Save
// $con = mysql_connect('localhost','root','123456') or die('Cannot connect to the DB');
// mysql_select_db('test',$con);

$u=$obj->{'username'};
$p=$obj->{'password'}; 
 
//  $sql = "SELECT * FROM 'test' WHERE username = '$u' AND password = '$p'";
//  $result = mysql_query($sql, $con); 

// echo mysql_num_rows($result); 
// mysql_close($con);
 
// function insertUser($userContent){

//   /* grab the posts from the db */
//   //$query = "SELECT post_title, guid FROM wp_posts WHERE post_author = $user_id AND post_status = 'publish' ORDER BY ID DESC LIMIT $number_of_posts";
//   $u=$userContent->{'username'};
//   $p=$userContent->{'password'}; 
//   mysql_query("INSERT INTO `test` (username, password) VALUES ('$u','$p')");
// }
$mysqli = new mysqli("localhost", "root", "123456", "test");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
}
   
/* Select queries return a resultset */
if ($result = mysqli_query($mysqli, "SELECT username FROM test WHERE username = '$u' and  password = '$p'")) {
      
     $rows = mysqli_num_rows($result) ;
     
      echo json_encode(array('is_exit' => $rows));
 //echo json_encode(array('phone_number' => $rows), JSON_NUMERIC_CHECK);
    /* free result set */
    mysqli_free_result($result);
}
?> 