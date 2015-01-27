package bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import sys.CommonPara;
import coon.JdbcPool;
import Interface.Bill;


/**
 * 	儿童保健对账接口 
 * 
 *
 */
public class Etbj implements Bill{

	@Override
	public String createxml(String upload_time) {
		Connection con = JdbcPool.getConnection();
		String succYBQK = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_BJ_YBQK'" ;
		int  succretYBQK = 0;
		String errorYBQK = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_BJ_YBQK'" ;
		int errorretYBQK = 0;
		int countYBQK = 0;
		
		String succXSEFS = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_BJ_XSEFS'" ;
		int  succretXSEFS = 0;
		String errorXSEFS = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_BJ_XSEFS'" ;
		int errorretXSEFS = 0;
		int countXSEFS = 0;
		
		String succXSECSQK = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='FB_YCF_XSECSQK'" ;
		int  succretXSECSQK = 0;
		String errorXSECSQK = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='FB_YCF_XSECSQK'" ;
		int errorretXSECSQK = 0;
		int countXSECSQK = 0;
		
		String succTJJL = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_BJ_TJJL'" ;
		int  succretTJJL = 0;
		String errorTJJL = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_BJ_TJJL'" ;
		int errorretTJJL = 0;
		int countTJJL = 0;
		
		String succZSJKXJ = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_BJ_ZSJKXJ'" ;
		int  succretZSJKXJ = 0;
		String errorZSJKXJ = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_BJ_ZSJKXJ'" ;
		int errorretZSJKXJ = 0;
		int countZSJKXJ = 0;
		
		
		PreparedStatement pspm = null;
		ResultSet rs = null;
		String xml = null;
		try {
			pspm = con.prepareStatement(succYBQK);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretYBQK = rs.getInt("count");
				succretYBQK = succretYBQK +1;
			}
			try {
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
			pspm = con.prepareStatement(errorYBQK);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretYBQK = rs.getInt("count");
			}
//			countYBQK = succretYBQK + errorretYBQK;
			countYBQK = succretYBQK;
			
			pspm = con.prepareStatement(succXSEFS);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretXSEFS = rs.getInt("count");
				succretXSEFS = succretXSEFS + 1;
			}
			pspm = con.prepareStatement(errorXSEFS);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretXSEFS = rs.getInt("count");
			}
//			countXSEFS = succretXSEFS + errorretXSEFS;	
			countXSEFS = succretXSEFS ;
			
			pspm = con.prepareStatement(succXSECSQK);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretXSECSQK = rs.getInt("count");
				succretXSECSQK = succretXSECSQK + 1;
			}
			pspm = con.prepareStatement(errorXSECSQK);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretXSECSQK = rs.getInt("count");
			}
//			countXSECSQK = succretXSECSQK + errorretXSECSQK;
			countXSECSQK = succretXSECSQK;
			
			pspm = con.prepareStatement(succTJJL);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretTJJL = rs.getInt("count");
				succretTJJL =succretTJJL + 1;
			}
			pspm = con.prepareStatement(errorTJJL);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretTJJL = rs.getInt("count");
			}
//			countTJJL = succretTJJL + errorretTJJL;
			countTJJL = succretTJJL;
			
			pspm = con.prepareStatement(succZSJKXJ);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretZSJKXJ = rs.getInt("count");
				succretZSJKXJ= succretZSJKXJ + 1;
			}
			pspm = con.prepareStatement(errorZSJKXJ);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretZSJKXJ = rs.getInt("count");
			}
//			countZSJKXJ = succretZSJKXJ + errorretZSJKXJ;
			countZSJKXJ = succretZSJKXJ;
			
		} catch (SQLException e) {
			logger.error("fail to connect db：" + e.getMessage());
		}finally{
			try {
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				try {
					if (pspm != null) {
						pspm.close();
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					try {
						if (con != null) {
							con.close();
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

				}

			}		
		}
		
		String CHILD_BASEINFO_COUNT = String.valueOf(countYBQK);
		String CHILD_NEWBORN_COUNT = String.valueOf(countXSEFS);
		String PG_NEWBORN_COUNT = String.valueOf(countXSECSQK);
		String CHILD_CHECK_COUNT = String.valueOf(countTJJL);
		String CHILD_YEARS_COUNT = String.valueOf(countZSJKXJ);
		
		Document document = DocumentHelper.createDocument();	
		Element root = document.addElement("body"); 
		
		Element head = root.addElement("head"); 
		Element userid = head.addElement("userid");
		userid.addText("nb_fybj");
		Element password = head.addElement("password");
		password.addText("123");
		Element trans_no = head.addElement("trans_no");
		trans_no.addText("Z00.00.00.09");
		
		Element request = root.addElement("resquest");
//		Element trans_noChild = request.addElement("Z00.00.00.09");
		Element eORG_CODE = request.addElement("ORG_CODE");
		eORG_CODE.setText(CommonPara.ORG_CODE); 
		Element eSMONTHDAY = request.addElement("SMONTHDAY");
		eSMONTHDAY.setText(upload_time);
		
		Element eCHILD_BASEINFO_COUNT = request.addElement("CHILD_BASEINFO_COUNT");
		eCHILD_BASEINFO_COUNT.setText(CHILD_BASEINFO_COUNT);
		Element eCHILD_NEWBORN_COUNT = request.addElement("CHILD_NEWBORN_COUNT");
		eCHILD_NEWBORN_COUNT.setText(CHILD_NEWBORN_COUNT);
		Element ePG_NEWBORN_COUNT = request.addElement("PG_NEWBORN_COUNT");
		ePG_NEWBORN_COUNT.setText(PG_NEWBORN_COUNT);
		Element eCHILD_CHECK_COUNT = request.addElement("CHILD_CHECK_COUNT");
		eCHILD_CHECK_COUNT.setText(CHILD_CHECK_COUNT);
		Element eCHILD_YEARS_COUNT = request.addElement("CHILD_YEARS_COUNT");
		eCHILD_YEARS_COUNT.setText(CHILD_YEARS_COUNT);

		xml = document.getRootElement().asXML();
		return xml;
	}
	
	private static Log logger = LogFactory.getLog(Etbj.class);

}
