package coon;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionManager {
     
  //  private String driverName = "oracle.jdbc.OracleDriver";//加载驱动程序
    //private String url = "jdbc:oracle:thin:@222.90.66.14:1521:oracle";//设置数据库连接串
    private String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String url="jdbc:sqlserver://192.168.5.4:1433;databaseName=Chss_New";
	private String user = "sa";//数据库登录用户名
    private String password = "admin2013!4";//数据库登录密码
    private static String message = "恭喜，数据库连接正常！";
    public Connection getConnection() {
        try {
            Class.forName(driverName);
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            message = "数据库连接失败！";
            return null;
        }
    }
    public static void main(String[] args) {
        try{
            DBConnectionManager dcm = new DBConnectionManager();
            dcm.getConnection();
            System.out.println(message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
