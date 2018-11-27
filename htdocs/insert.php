<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
 
        $dep=$_POST['dep'];
        $des=$_POST['des'];
		$time=$_POST['time'];
		$min=$_POST['min'];
		$addr=$_POST['addr'];
		$say=$_POST['say'];
		$phone=$_POST['phone'];

        if(empty($dep)){
            $errMSG = "출발지를 입력하세요";
        }
        else if(empty($des)){
            $errMSG = "도착지를 입력하세요";
        }
		else if(empty($time)){
            $errMSG = "시간을 입력하세요";
        }
		else if(empty($min)){
            $errMSG = "분을 입력하세요";
        }
		else if(empty($addr)&&empty($phone)){
            $errMSG = "전화번호 또는 카카오톡 ID를 입력하세요";
        }
		

        if(!isset($errMSG)) 

        {
            try{
                
                $stmt = $con->prepare('INSERT INTO list(dep,des,time,min,addr,say,phone) VALUES(:dep, :des, :time, :min, :addr, :say, :phone)');
                $stmt->bindParam(':dep', $dep);
                $stmt->bindParam(':des', $des);
				$stmt->bindParam(':time', $time);
				$stmt->bindParam(':min', $min);
				$stmt->bindParam(':addr', $addr);
				$stmt->bindParam(':say', $say);
				$stmt->bindParam(':phone', $phone);

                if($stmt->execute())
                {
                    $successMSG = "추가했습니다.";
                }
                else
                {
                    $errMSG = "추가 에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }

    }

?>


<?php 
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;


    if (!$android)
    {
?>
    <html>
       <body>

            <form action="<?php $_PHP_SELF ?>" method="POST">
                dep: <input type = "text" name = "dep" />
                des: <input type = "text" name = "des" />
				time: <input type = "text" name = "time" />
				min: <input type = "text" name = "min" />
				addr: <input type = "text" name = "addr" />
				say: <input type = "text" name = "say" />
				phone: <input type = "text" name = "phone" />
                <input type = "submit" name = "submit" />
            </form>
       
       </body>
    </html>

<?php 
    }
?>