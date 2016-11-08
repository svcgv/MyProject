package com.example.administrator.myapplication.staticc;
/**
 * Created by Administrator on 2016/9/17.
 */
public class DefineStatic {
    //public final  static  String loachost="http://115.28.72.15:8080/indexget.php";
     public final static  String ip="http://192.168.1.102:8088";
     public  final  static  String LOGIN="login";
     public final  static  String RESIGSTER="resisger";
 /*  public final static  String  loachost="http://192.168.1.105:8088/indexget.php";
     public  final  static  String resigster="http://192.168.1.105:8088/androidtest/resigster.php";*/
//   public final static  String  loachost="http://115.28.72.15:8080/indexget.php";
 //  public  final  static  String resigster="http://115.28.72.15:8080/resigster.php";
     public  final  static  String resigster=DefineStatic.ip+"Admin/Index/resigter";
     public final static  String  loachost=DefineStatic.ip+"/MyProject/index.php/Admin/Index/my";
     public final  static  String jsonInfo=DefineStatic.ip+"/MyProject/index.php/Admin/Index/JSON";
//   public final  static String jsonInfo="http://192.168.1.101:8088/upload/json.php";
     public final  static  int  SUCCESS=1;
     public final  static  int  FEILD=0;
     public final  static  int Resisger_Success=2;
     public final  static  int Resisger_Field=3;
     public final  static  String UPLOAD=ip+"/MyProject/index.php/Admin/Index/upload";
     public final static  String PREFERCE="Share";
    public  final static  String UPLOADMOOD=ip+"/MyProject/index.php/Admin/Index/uploadMood";
    public  final  static  String INFO=ip+"/MyProject/index.php/Admin/Index/Myinfo";
    public  final  static  String INSERTCOMMENT=ip+"/MyProject/index.php/Admin/Index/insertcomment";
    public  final  static  String  GETCOMMENT=ip+"/MyProject/index.php/Admin/Index/querycomment";

}
