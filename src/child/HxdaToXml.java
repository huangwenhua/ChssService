package child;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import coon.DBConnectionManager;

public class HxdaToXml {
public static void main(String[] args) {
	DBConnectionManager mang=new DBConnectionManager();
	Connection con=mang.getConnection();
	String sql="select * from DA_GR_HXDA";
	 PreparedStatement pspm;
	try {
		pspm = con.prepareStatement(sql);
		 ResultSet rs=pspm.executeQuery();
		 while (rs.next()){
			 //个人保健号
			 String GRBJH=rs.getString("GRBJH");
			 //姓名
			 String XM=rs.getString("XM");
			 //出生日期
			 String CSRQ=rs.getString("CSRQ");
			 //性别
			 String XB=rs.getString("XB");
			 //血型
			 String XXABO=rs.getString("XXABO");
			
			 
			 
			 //
			 
			 System.out.println(XM);
			 
		 }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 
}
}
