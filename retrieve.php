<?php
$db_name="ROOM";
$user="root";
$password="";
$server="localhost";
$con=mysqli_connect($server,$user,$password,$db_name);
    if(!$con){
	$arr['error']="ERROR CONNECTION";
     echo json_encode($arr);
	 exit(0);
	}
	else{
		$re=array();
		$re['Connection']="CONNECTION OK";
		$query="select * from Register ";
		$result=mysqli_query($con,$query);
		$num_rows=$result->num_rows;
		$base=array();
		if($num_rows>0){
			while($row=mysqli_fetch_array($result)){
              
             $re['success'][]=$row;
             $id=$row['id'];			 
             $re[$id]=base64_encode(file_get_contents($row['image']));
                 
               //  $base[$id]=$row['image'];			 
           //   $arr['success']['address']= $row['address'];
		//	  $array['success']['latitude']= $row['latitude'];
			//  $array['success']['longitude']= $row['longitude'];
			//  $array['success']['image']= $row['image'];                    			  
			}
          	 echo json_encode($re);
			 echo json_encode($base);
		}
		
		
		
	}
?>