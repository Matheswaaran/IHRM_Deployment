<?php
    class SessionUtils {
        public function __construct() {
            session_start();
            include 'dbconfig.php';
        }

        public function UserLogin($id,$username) {
            $_SESSION['user_id'] = $id;
            $_SESSION['user_username'] = $username;
        }

        public function Logout() {
            session_destroy();
            return true;
        }

        function encryptIt( $q ) {
            $cryptKey = 'qJB0rGtIn5UB1xG03efyCp';
            $qEncoded = base64_encode( mcrypt_encrypt( MCRYPT_RIJNDAEL_256, md5( $cryptKey ), $q, MCRYPT_MODE_CBC, md5( md5( $cryptKey ) ) ) );
            return( $qEncoded );
        }

        function decryptIt( $q ) {
            $cryptKey = 'qJB0rGtIn5UB1xG03efyCp';
            $qDecoded = rtrim( mcrypt_decrypt( MCRYPT_RIJNDAEL_256, md5( $cryptKey ), base64_decode( $q ), MCRYPT_MODE_CBC, md5( md5( $cryptKey ) ) ), "\0");
            return( $qDecoded );
        }

        function chkSession($userChk) {

            $db = mysqli_connect(DB_HOST,DB_USER,DB_PASS,DB_BASE);

            try{
                $ses_sql = mysqli_query($db,"SELECT * FROM users_table WHERE username = '$userChk'");
                $row = mysqli_fetch_array($ses_sql, MYSQL_ASSOC);
                $username = $row['name'];

                if (!isset($_SESSION['user_id']) && !isset($_SESSION['user_username'])){
                    return false;
                }else{
                    return true;
                }
            }catch (exception $e){
                return false;
            }
        }

        function isBlocked($userChk){

            $db = mysqli_connect(DB_HOST,DB_USER,DB_PASS,DB_BASE);

            try{
                $ses_sql = mysqli_query($db,"SELECT * FROM users_table WHERE username = '$userChk'");
                $row = mysqli_fetch_array($ses_sql, MYSQL_ASSOC);

                if ($row["blocked"] == 0){
                    return false;
                }else if ($row["blocked"] == 1){
                    return true;
                }
                return false;
            }catch (exception $e) {
                return false;
            }
        }

        function chkPassword($password) {
            $username = $_SESSION['user_username'];
            $id = $_SESSION['user_id'];
//            $password = $this->encryptIt($password);
            $db = mysqli_connect(DB_HOST,DB_USER,DB_PASS,DB_BASE) or die("Cannot connect Server");

            try{
                $pass_Sql = mysqli_query($db, "SELECT * FROM users_table WHERE username = '$username' AND password = '$password'");
                $pass_result = mysqli_num_rows($pass_Sql);
                $row = mysqli_fetch_array($pass_Sql, MYSQL_ASSOC);

                if ($pass_Sql){
                    if ($pass_result == 1){
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }catch (exception $e){
                return false;
            }
        }
    }
?>