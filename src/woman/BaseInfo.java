package woman;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import coon.DBConnectionManager;

public class BaseInfo {
public String getBaseInfo(){
	 DBConnectionManager dcm = new DBConnectionManager();
     Connection conn=dcm.getConnection();
	 String sql="select * from mchealth.t_child_info where cid='402881ea42db76120142db77b7460001'";
	 PreparedStatement pspm;
	 String name="";
	try {
		pspm = conn.prepareStatement(sql);
	
	 ResultSet rs=pspm.executeQuery();
	 while(rs.next()){
		name= rs.getString("newborn_name");
	 }
	 rs.close();
	 pspm.close();
	 conn.close();
	} catch (SQLException e) {
	
		e.printStackTrace();
	}
	return "<body>"+name+"</body>";
	
}
public static void main(String[] args) {
	BaseInfo bs=new BaseInfo();
	System.out.println(bs.getBaseInfo());
}
}
