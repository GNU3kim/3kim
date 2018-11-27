
<?php

$con=mysqli_connect("localhost","root","","taxi");

if(mysqli_connect_errno($con))
{
	echo "Failed to connect to MySQL:" . mysqli_connect_error();
}

mysqli_set_charset($con,"utf8");

$res=mysqli_query($con,"select*from list ORDER BY `time` ASC, `min` ASC "); //order by 로 정렬

$result=array();


while($row=mysqli_fetch_array($res))
{
	array_push($result,array('dep'=>$row[0],'des'=>$row[1],'time'=>$row[2],'min'=>$row[3],'addr'=>$row[4],'say'=>$row[5],'phone'=>$row[6]));
}


echo json_encode(array("result"=>$result),JSON_UNESCAPED_UNICODE);
mysqli_close($con);
?>