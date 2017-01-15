<?php

header('Content-Type:application/json');
$db_name="ROOM";
$user="root";
$password="";
$server="localhost";
$con=mysqli_connect($server,$user,$password,$db_name);
if(!$con){
	$arr['error']['hello'] = "NO INTERNET CONNECTION";
	echo json_encode($arr);
	exit(0);
}
else{
	$arr['success']['Connection']="Connected successfully";
	if(isset($_POST['address']) && isset($_POST['latitude']) && isset($_POST['longitude']) && isset($_POST['image'])){
		
		$addres=$_POST['address'];
		$latitude=$_POST['latitude'];
		$longitude=$_POST['longitude'];
		$image=$_POST['image'];
		$id=0;
		$query1="select id from Register ";
		$result1=mysqli_query($con,$query1);
		while($row=mysqli_fetch_array($result1)){
			$id=$row['id'];
		}
		$path="images/$id.png";
		$query="insert into Register (address,latitude,longitude,image) values ('$addres','$latitude','$longitude','$path') ;";
		$result=mysqli_query($con,$query);
		if($result == 1){
			file_put_contents($path,base64_decode($image));
		     $arr['success']['register']="Successfully registered";
		}
		else{
			 $arr['success']['register']="Sorry an error occured..Not registered";
		}
		
		
	}
	echo json_encode($arr);
	//mysqli_close($con);
}
?>