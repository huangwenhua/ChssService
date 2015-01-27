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
 * 妇女专项业务相关数据的对账
 *
 */
public class Fyzx  implements Bill{

	@Override
	public String createxml(String upload_time) {
		Connection con = JdbcPool.getConnection();
		String succYKJC = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='FB_YCF_YKJC'" ;
		int  succretYKJC = 0;
		String errorYKJC = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='FB_YCF_YKJC'" ;
		int errorretYKJC = 0;
		int countYKJC = 0;
		
		String succCCCJ = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='FB_YCF_CCCJ'" ;
		int  succretCCCJ = 0;
		String errorCCCJ = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='FB_YCF_CCCJ'" ;
		int errorretCCCJ = 0;
		int countCCCJ = 0;
		
		String succCQJC = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='FB_YCF_CQJC'" ;
		int  succretCQJC = 0;
		String errorCQJC = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='FB_YCF_CQJC'" ;
		int errorretCQJC = 0;
		int countCQJC = 0;
		
		String succCQSC = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='FB_YCF_CQSC'" ;
		int  succretCQSC = 0;
		String errorCQSC = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='FB_YCF_CQSC'" ;
		int errorretCQSC = 0;
		int countCQSC = 0;
		
		String succCSQK = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='FB_YCF_CSQK'" ;
		int  succretCSQK = 0;
		String errorCSQK = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='FB_YCF_CSQK'" ;
		int errorretCSQK = 0;
		int countCSQK = 0;
		
		String succCHFS = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='FB_YCF_CHFS'" ;
		int  succretCHFS = 0;
		String errorCHFS = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='FB_YCF_CHFS'" ;
		int errorretCHFS = 0;
		int countCHFS = 0;
		
		String succXSEFS = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='FB_YCF_XSEFS'" ;
		int  succretXSEFS = 0;
		String errorXSEFS = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='FB_YCF_XSEFS'" ;
		int errorretXSEFS = 0;
		int countXSEFS = 0;
		
		String succCHJC = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='FB_YCF_CHJC'" ;
		int  succretCHJC = 0;
		String errorCHJC = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='FB_YCF_CHJC'" ;
		int errorretCHJC = 0;
		int countCHJC = 0;
		
		
		PreparedStatement pspm = null;
		ResultSet rs = null;
		String xml = null;
		try {
			pspm = con.prepareStatement(succYKJC);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretYKJC = rs.getInt("count");
				succretYKJC = succretYKJC + 1;
			}
			try {
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
			pspm = con.prepareStatement(errorYKJC);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretYKJC = rs.getInt("count");
			}
//			countYKJC = succretYKJC + errorretYKJC;
			countYKJC = succretYKJC ;
			
			pspm = con.prepareStatement(succCCCJ);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretCCCJ = rs.getInt("count");
				succretCCCJ = succretCCCJ + 1;
			}
			pspm = con.prepareStatement(errorCCCJ);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretCCCJ = rs.getInt("count");
			}
//			countCCCJ = succretCCCJ + errorretCCCJ;
			countCCCJ = succretCCCJ ;
			
			pspm = con.prepareStatement(succCQJC);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretCQJC = rs.getInt("count");
				succretCQJC = succretCQJC + 1;
			}
			pspm = con.prepareStatement(errorCQJC);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretCQJC = rs.getInt("count");
			}
//			countCQJC = succretCQJC + errorretCQJC;
			countCQJC = succretCQJC ;
			
			pspm = con.prepareStatement(succCQSC);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretCQSC = rs.getInt("count");
				succretCQSC = succretCQSC + 1;
			}
			pspm = con.prepareStatement(errorCQSC);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretCQSC = rs.getInt("count");
			}
//			countCQSC = succretCQSC + errorretCQSC;
			countCQSC = succretCQSC;
			
			pspm = con.prepareStatement(succCSQK);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretCSQK = rs.getInt("count");
				succretCSQK = succretCSQK + 1;
			}
			pspm = con.prepareStatement(errorCSQK);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretCSQK = rs.getInt("count");
			}
//			countCSQK = succretCSQK + errorretCSQK;
			countCSQK = succretCSQK ;
			
			pspm = con.prepareStatement(succCHFS);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretCHFS = rs.getInt("count");
				succretCHFS = succretCHFS + 1;
			}
			pspm = con.prepareStatement(errorCHFS);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretCHFS = rs.getInt("count");
			}
//			countCHFS = succretCHFS + errorretCHFS;
			countCHFS = succretCHFS ;
			
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
			countXSEFS = succretXSEFS;
			
			pspm = con.prepareStatement(succCHJC);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretCHJC = rs.getInt("count");
				succretCHJC = succretCHJC + 1;
			}
			pspm = con.prepareStatement(errorCHJC);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretCHJC = rs.getInt("count");
			}
//			countCHJC = succretCHJC + errorretCHJC;
			countCHJC = succretCHJC;
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
		
		String PG_BASEINFO_COUNT = String.valueOf(countYKJC);
		String PG_INITCHECK_COUNT = String.valueOf(countCCCJ);
		String PG_PRENATAL_COUNT = String.valueOf(countCQJC);
		String PG_SCREENING_COUNT = String.valueOf(countCQSC);
		String PG_YIELD_COUNT = String.valueOf(countCSQK);
		String PG_AFTER_COUNT = String.valueOf(countCHFS);
		String PG_AFTER_CHILD_COUNT = String.valueOf(countXSEFS);
		String PG_CHECK42_COUNT = String.valueOf(countCHJC);
		
		Document document = DocumentHelper.createDocument();	
		Element root = document.addElement("body"); 
		
		Element head = root.addElement("head"); 
		Element userid = head.addElement("userid");
		userid.addText("nb_fybj");
		Element password = head.addElement("password");
		password.addText("123");
		Element trans_no = head.addElement("trans_no");
		trans_no.addText("Z00.00.00.08");
		
		Element request = root.addElement("resquest");
//		Element trans_noChild = request.addElement("Z00.00.00.08");
		Element eORG_CODE = request.addElement("ORG_CODE");
		eORG_CODE.setText(CommonPara.ORG_CODE); 
		Element eSMONTHDAY = request.addElement("SMONTHDAY");
		eSMONTHDAY.setText(upload_time);
		Element ePG_BASEINFO_COUNT = request.addElement("PG_BASEINFO_COUNT");
		ePG_BASEINFO_COUNT.setText(PG_BASEINFO_COUNT);
		Element ePG_INITCHECK_COUNT = request.addElement("PG_INITCHECK_COUNT");
		ePG_INITCHECK_COUNT.setText(PG_INITCHECK_COUNT);
		Element ePG_PRENATAL_COUNT = request.addElement("PG_PRENATAL_COUNT");
		ePG_PRENATAL_COUNT.setText(PG_PRENATAL_COUNT);
		Element ePG_SCREENING_COUNT = request.addElement("PG_SCREENING_COUNT");
		ePG_SCREENING_COUNT.setText(PG_SCREENING_COUNT);
		Element ePG_YIELD_COUNT = request.addElement("PG_YIELD_COUNT");
		ePG_YIELD_COUNT.setText(PG_YIELD_COUNT);
		Element ePG_AFTER_COUNT = request.addElement("PG_AFTER_COUNT");
		ePG_AFTER_COUNT.setText(PG_AFTER_COUNT);
		Element ePG_AFTER_CHILD_COUNT = request.addElement("PG_AFTER_CHILD_COUNT");
		ePG_AFTER_CHILD_COUNT.setText(PG_AFTER_CHILD_COUNT);
		Element ePG_CHECK42_COUNT = request.addElement("PG_CHECK42_COUNT");
		ePG_CHECK42_COUNT.setText(PG_CHECK42_COUNT);
		
		xml = document.getRootElement().asXML();
		
		return xml;
	}
	
	private static Log logger = LogFactory.getLog(Fyzx.class);

}
