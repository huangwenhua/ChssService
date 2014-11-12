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

public class Tretgl implements Bill {

	@Override
	public String createxml(String upload_time) {
		Connection con = JdbcPool.getConnection();
		String succFPZA = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_TRE_FPZA'" ;
		int  succretFPZA = 0;
		String errorFPZA= "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_TRE_FPZA'" ;
		int errorretFPZA = 0;
		int countFPZA= 0;
		
		String succFPSF = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_TRE_FPSF'" ;
		int  succretFPSF = 0;
		String errorFPSF = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_TRE_FPSF'" ;
		int errorretFPSF = 0;
		int countFPSF = 0;
		
		String succPXZA = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_TRE_PXZA'" ;
		int  succretPXZA = 0;
		String errorPXZA = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_TRE_PXZA'" ;
		int errorretPXZA = 0;
		int countPXZA = 0;
		
		String succPXSF = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_TRE_PXSF'" ;
		int  succretPXSF = 0;
		String errorPXSF = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_TRE_PXSF'" ;
		int errorretPXSF = 0;
		int countPXSF = 0;
		
		String succYYBLZA = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_TRE_YYBLZA'" ;
		int  succretYYBLZA = 0;
		String errorYYBLZA = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_TRE_YYBLZA'" ;
		int errorretYYBLZA = 0;
		int countYYBLZA = 0;
		
		String succYYBLSF = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_TRE_YYBLSF'" ;
		int  succretYYBLSF = 0;
		String errorYYBLSF = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_TRE_YYBLSF'" ;
		int errorretYYBLSF = 0;
		int countYYBLSF = 0;
		
		String succGLBZA = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_TRE_GLBZA'" ;
		int  succretGLBZA = 0;
		String errorGLBZA = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_TRE_GLBZA'" ;
		int errorretGLBZA = 0;
		int countGLBZA = 0;
		
		String succGLBSF = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_TRE_GLBSF'" ;
		int  succretGLBSF = 0;
		String errorGLBSF = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_TRE_GLBSF'" ;
		int errorretGLBSF = 0;
		int countGLBSF = 0;
		
		String succXTXXZBZA = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_TRE_XTXXZBZA'" ;
		int  succretXTXXZBZA = 0;
		String errorXTXXZBZA = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_TRE_XTXXZBZA'" ;
		int errorretXTXXZBZA = 0;
		int countXTXXZBZA = 0;
		
		String succXZBSF = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_TRE_XZBSF'" ;
		int  succretXZBSF = 0;
		String errorXZBSF = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_TRE_XZBSF'" ;
		int errorretXZBSF = 0;
		int countXZBSF = 0;
		
		String succQTZA = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_TRE_QTZA'" ;
		int  succretQTZA = 0;
		String errorQTZA = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_TRE_QTZA'" ;
		int errorretQTZA = 0;
		int countQTZA = 0;
		
		String succQTSF = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='EB_TRE_QTSF'" ;
		int  succretQTSF = 0;
		String errorQTSF = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='EB_TRE_QTSF'" ;
		int errorretQTSF = 0;
		int countQTSF = 0;
		
		PreparedStatement pspm = null;
		ResultSet rs = null;
		String xml = null;
		try {
			pspm = con.prepareStatement(succFPZA);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretFPZA = rs.getInt("count");
			}
			try {
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
			pspm = con.prepareStatement(errorFPZA);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretFPZA = rs.getInt("count");
			}
			countFPZA = succretFPZA + errorretFPZA;
			
			pspm = con.prepareStatement(succFPSF);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretFPSF = rs.getInt("count");
			}
			pspm = con.prepareStatement(errorFPSF);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretFPSF = rs.getInt("count");
			}
			countFPSF = succretFPSF + errorretFPSF;
			
			pspm = con.prepareStatement(succPXZA);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretPXZA = rs.getInt("count");
			}
			pspm = con.prepareStatement(errorPXZA);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretPXZA = rs.getInt("count");
			}
			countPXZA = succretPXZA + errorretPXZA;
			
			pspm = con.prepareStatement(succPXSF);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretPXSF = rs.getInt("count");
			}
			pspm = con.prepareStatement(errorPXSF);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretPXSF = rs.getInt("count");
			}
			countPXSF = succretPXSF + errorretPXSF;
			
			pspm = con.prepareStatement(succYYBLZA);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretYYBLZA = rs.getInt("count");
			}
			pspm = con.prepareStatement(errorYYBLZA);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretYYBLZA = rs.getInt("count");
			}
			countYYBLZA = succretYYBLZA + errorretYYBLZA;
			
			pspm = con.prepareStatement(succYYBLSF);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretYYBLSF = rs.getInt("count");
			}
			pspm = con.prepareStatement(errorYYBLSF);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretYYBLSF = rs.getInt("count");
			}
			countYYBLSF = succretYYBLSF + errorretYYBLSF;
			
			pspm = con.prepareStatement(succGLBZA);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretGLBZA = rs.getInt("count");
			}
			pspm = con.prepareStatement(errorGLBZA);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretGLBZA = rs.getInt("count");
			}
			countGLBZA = succretGLBZA + errorretGLBZA;
			
			pspm = con.prepareStatement(succGLBSF);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretGLBSF = rs.getInt("count");
			}
			pspm = con.prepareStatement(errorGLBSF);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretGLBSF = rs.getInt("count");
			}
			countGLBSF = succretGLBSF + errorretGLBSF;
			
			pspm = con.prepareStatement(succXTXXZBZA);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretXTXXZBZA = rs.getInt("count");
			}
			pspm = con.prepareStatement(errorXTXXZBZA);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretXTXXZBZA = rs.getInt("count");
			}
			countXTXXZBZA = succretXTXXZBZA + errorretXTXXZBZA;
			
			pspm = con.prepareStatement(succXZBSF);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretXZBSF = rs.getInt("count");
			}
			pspm = con.prepareStatement(errorXZBSF);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretXZBSF = rs.getInt("count");
			}
			countXZBSF = succretXZBSF + errorretXZBSF;
			
			pspm = con.prepareStatement(succQTZA);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretQTZA = rs.getInt("count");
			}
			pspm = con.prepareStatement(errorQTZA);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretQTZA = rs.getInt("count");
			}
			countQTZA = succretQTZA + errorretQTZA;
			
			pspm = con.prepareStatement(succQTSF);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succretQTSF = rs.getInt("count");
			}
			pspm = con.prepareStatement(errorQTSF);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorretQTSF = rs.getInt("count");
			}
			countQTSF = succretQTSF + errorretQTSF;
			
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
		
		String CHILD_FAT_COUNT = String.valueOf(countFPZA);
		String CHILD_FAT_VISITS_COUNT =  String.valueOf(countFPSF);
		String CHILD_ANEMIA_COUNT = String.valueOf(countPXZA);
		String CHILD_ANEMIA_VISITS_COUNT = String.valueOf(countPXSF);
		String CHILD_MP_COUNT = String.valueOf(countYYBLZA);
		String CHILD_MP_VISITS_COUNT = String.valueOf(countYYBLSF);
		String CHILD_RICKETS_COUNT = String.valueOf(countGLBZA);
		String CHILD_RICKETS_VISITS_COUNT = String.valueOf(countGLBSF);
		String CHILD_CH_COUNT = String.valueOf(countXTXXZBZA);
		String CHILD_CH_VISITS_COUNT = String.valueOf(countXZBSF);
		String CHILD_OTHER_COUNT = String.valueOf(countQTZA);
		String CHILD_OTHER_VISITS_COUNT = String.valueOf(countQTSF);
		
		Document document = DocumentHelper.createDocument();	
		Element root = document.addElement("body"); 
		
		Element head = root.addElement("head"); 
		Element userid = head.addElement("userid");
		userid.addText("nb_fybj");
		Element password = head.addElement("password");
		password.addText("123");
		Element trans_no = head.addElement("trans_no");
		trans_no.addText("Z00.00.00.10");
		
		Element request = root.addElement("resquest");
		Element trans_noChild = request.addElement("Z00.00.00.10");
		Element eORG_CODE = trans_noChild.addElement("ORG_CODE");
		eORG_CODE.setText(CommonPara.ORG_CODE); 
		Element eSMONTHDAY = trans_noChild.addElement("SMONTHDAY");
		eSMONTHDAY.setText(upload_time);
		Element eCHILD_FAT_COUNT = trans_noChild.addElement("CHILD_FAT_COUNT");
		eCHILD_FAT_COUNT.setText(CHILD_FAT_COUNT);
		Element eCHILD_FAT_VISITS_COUNT = trans_noChild.addElement("CHILD_FAT_VISITS_COUNT");
		eCHILD_FAT_VISITS_COUNT.setText(CHILD_FAT_VISITS_COUNT);
		Element eCHILD_ANEMIA_COUNT = trans_noChild.addElement("CHILD_ANEMIA_COUNT");
		eCHILD_ANEMIA_COUNT.setText(CHILD_ANEMIA_COUNT);
		Element eCHILD_ANEMIA_VISITS_COUNT = trans_noChild.addElement("CHILD_ANEMIA_VISITS_COUNT");
		eCHILD_ANEMIA_VISITS_COUNT.setText(CHILD_ANEMIA_VISITS_COUNT);
		Element eCHILD_MP_COUNT = trans_noChild.addElement("CHILD_MP_COUNT");
		eCHILD_MP_COUNT.setText(CHILD_MP_COUNT); 
		Element eCHILD_MP_VISITS_COUNT = trans_noChild.addElement("CHILD_MP_VISITS_COUNT");
		eCHILD_MP_VISITS_COUNT.setText(CHILD_MP_VISITS_COUNT);
		Element eCHILD_RICKETS_COUNT = trans_noChild.addElement("CHILD_RICKETS_COUNT");
		eCHILD_RICKETS_COUNT.setText(CHILD_RICKETS_COUNT);
		Element eCHILD_RICKETS_VISITS_COUNT = trans_noChild.addElement("CHILD_RICKETS_VISITS_COUNT");
		eCHILD_RICKETS_VISITS_COUNT.setText(CHILD_RICKETS_VISITS_COUNT);
		Element eCHILD_CH_COUNT = trans_noChild.addElement("CHILD_CH_COUNT");
		eCHILD_CH_COUNT.setText(CHILD_CH_COUNT);
		Element eCHILD_CH_VISITS_COUNT = trans_noChild.addElement("CHILD_CH_VISITS_COUNT");
		eCHILD_CH_VISITS_COUNT.setText(CHILD_CH_VISITS_COUNT);
		Element eCHILD_OTHER_COUNT = trans_noChild.addElement("CHILD_OTHER_COUNT");
		eCHILD_OTHER_COUNT.setText(CHILD_OTHER_COUNT);
		Element eCHILD_OTHER_VISITS_COUNT = trans_noChild.addElement("CHILD_OTHER_VISITS_COUNT");
		eCHILD_OTHER_VISITS_COUNT.setText(CHILD_OTHER_VISITS_COUNT);
		
		xml = document.getRootElement().asXML();
		return xml;
	}
	
	private static Log logger = LogFactory.getLog(Tretgl.class);

}
