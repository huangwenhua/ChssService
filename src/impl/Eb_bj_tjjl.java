/**
 * 
 */
package impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import util.DateUtil;
import Interface.BuildingXml;
import coon.JdbcPool;
import dictall.DictMap;

/**
 * <p>标题: Eb_bj_tjjl.java</p>
 * <p>业务描述:保健体检记录 生成xml</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author 王军
 * @date 2014年4月17日
 * @version V1.0 
 */

public class Eb_bj_tjjl implements BuildingXml{
	private static Logger logger = Logger.getLogger(Eb_bj_tjjl.class);
	@Override
	public Map<String, String> createxml(String startTime,String endTime) {
		
		Map<String, String> map = new HashMap<String, String>();
		Connection con = JdbcPool.getConnection();
		String sql = "select  LSH,GRBJH,SFJXJC,ZZJCYY,JCRQ,SZNL,YYFS,WYFS,MRMRCS,MRNFL,TJFS,TJSWDM,TJSWMC,MRTJFSCS,WSSD,YMJJL,SY,DBCS,MRPJSMSJ,SMCN,SMDH,HWHD,FXCS,FYCS,GMCS,FSJB,GLB,FSJBSM,TZ,SG,TW,QX1,QX2,CYS,QCS,XSEJBSC,XSETLSC,GGYCTZDM,GGYCTZMC,Y,KQ,PF,TL,XZTZ,FTZ,GPCZ,WSZQ,YG,KGJHDDM,KGJHDMC,NT,JX,DZFYDM,DZFYMC,DDST,XHDB,NCG,QTYCQKDM,QTYCQKMC,YYZK,TZPJ,SGPJ,SGCTZ,TWPJ,TREYSDM,TREYSMC,FP,YYBL,PX,GLB1,XTXXZB,SFTREQT,TREQT,SFTREGL,BJZDYCLDM,BJZDYCLMC,YYRQ,JCZ,JCZBM,JLZT,DJRQ,DJRY,DJRYBM,DJJGDM,DJJGMC,XGRQ,XGRY,XGRYBM,XGJGDM,XGJGMC from EB_BJ_TJJL where XGRQ>? and XGRQ<=?";
		PreparedStatement pspm = null;
		ResultSet rs = null;
		String xmlString = "";
		try {
			pspm = con.prepareStatement(sql);
			pspm.setString(1, startTime);
			pspm.setString(2, endTime);
			rs = pspm.executeQuery();
			while (rs.next()) {
				//流水号
				String LSH = rs.getString("LSH");
				// 个人保健号
				String GRBJH = rs.getString("GRBJH");
				//是否继续监测
				String SFJXJC = rs.getString("SFJXJC");
				//终止监测原因
				String str = rs.getString("ZZJCYY");
				String ZZJCYY = getIsNull(str); 
				//检查日期
				String JCRQ = rs.getString("JCRQ");
				JCRQ = convertDateToD8(JCRQ);
				//养育方式
				String 	str2 = rs.getString("YYFS");
				String YYFS = getIsNull(str2); 
				//喂养方式
				String WYFS = rs.getString("WYFS");
				//每日母乳次数
				String str3 = rs.getString("MRMRCS");
				String MRMRCS = getIsNull(str3); 
				//每日奶粉量(ml)
				String str4 = rs.getString("MRNFL");
				String MRNFL = getIsNull(str4); 
				//添加辅食
				String TJFS = rs.getString("TJFS");
				//添加食物代码
				String TJSWDM = rs.getString("TJSWDM");
				TJSWDM = getIsNull(TJSWDM);
				
				/**
				 * 2014-4-28    未做字典处理                   李研edit
				 */
				TJSWDM = getMultiple(TJSWDM);
				
				//每日添加辅食次数
				String MRTJFSCS = rs.getString("MRTJFSCS");
				//维生素D
				String WSSD = rs.getString("WSSD");
				//维生素D药名及剂量
				String YMJJL = rs.getString("YMJJL");
				//食欲
				String SY = rs.getString("SY");
				//大便次数
				String DBCS = rs.getString("DBCS");
				//每日平均睡眠时间
				String MRPJSMSJ = rs.getString("MRPJSMSJ");
				//睡眠吵闹或夜惊
				String SMCN = rs.getString("SMCN");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				SMCN = getSleep(SMCN);
				
				//睡眠多汗
				String SMDH= rs.getString("SMDH");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				SMDH = getSleep(SMDH);
				
				//户外活动
				String HWHD = rs.getString("HWHD");
				//本阶段腹泻次数
				String FXCS = rs.getString("FXCS");
				//本阶段肺炎次数
				String FYCS = rs.getString("FYCS");
				//感冒次数
				String GMCS = rs.getString("GMCS");
				//本阶段发生疾病
				String FSJB = rs.getString("FSJB");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				FSJB = getSleep(FSJB);
				
				//佝偻病
				String GLB = rs.getString("GLB");
				//本阶段发生疾病说明
				String FSJBSM = rs.getString("FSJBSM");
				//体重(kg)
				String TZ = rs.getString("TZ");
				//身高(cm)
				String SG = rs.getString("SG");
				//头围(cm)
				String TW = rs.getString("TW");
				//前囟1
				String QX1 = rs.getString("QX1");
				//前囟2
				String QX2 = rs.getString("QX2");
				//出牙数
				String CYS = rs.getString("CYS");
				//龋齿数
				String QCS = rs.getString("QCS");
				//新生儿疾病筛查
				String XSEJBSC = rs.getString("XSEJBSC");
				//新生儿听力筛查
				String XSETLSC = rs.getString("XSETLSC");
				//骨骼异常体征代码
				String GGYCTZDM = rs.getString("GGYCTZDM");
				//眼
				String Y = rs.getString("Y");
				//口腔
				String KQ = rs.getString("KQ");
				//皮肤
				String PF = rs.getString("PF");
				//听力
				String TL = rs.getString("TL");
				//心脏听诊
				String XZTZ = rs.getString("XZTZ");
				//肺听诊
				String FTZ = rs.getString("FTZ");
				//肝脾触诊
				String GPCZ = rs.getString("GPCZ");
				//外生殖器
				String WSZQ = rs.getString("WSZQ");
				//隐睾、疝
				String YG = rs.getString("YG");
				//髋关节活动代码
				String KGJHDDM = rs.getString("KGJHDDM");
				KGJHDDM = getIsNull(KGJHDDM);
				KGJHDDM = getMultiple(KGJHDDM);
				//脑瘫
				String NT = rs.getString("NT");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				NT = getSleep(NT);
				
				//畸形
				String JX = rs.getString("JX");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				JX = getSleep(JX);
				
				//DDST
				String DDST = rs.getString("DDST");
				//血红蛋白
				String XHDB = rs.getString("XHDB");
				//尿常规
				String NCG = rs.getString("NCG");
				//其他异常情况代码
				String QTYCQKDM = rs.getString("QTYCQKDM");
				//体重评价
				String TZPJ = rs.getString("TZPJ");
				//身高评价
				String SGPJ = rs.getString("SGPJ");
				//身高测体重
				String SGCTZ = rs.getString("SGCTZ");
				//头围评价
				String TWPJ = rs.getString("TWPJ");
				//体弱儿因素代码
				String TREYSMC = rs.getString("TREYSMC");
				TREYSMC = getIsNull(TREYSMC);
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				TREYSMC = getMultiple(TREYSMC);
				
				//肥胖
				String FP = rs.getString("FP");
				//营养不良
				String YYBL = rs.getString("YYBL");
				//贫血
				String PX = rs.getString("PX");
				//先天性心脏病
				String XTXXZB = rs.getString("XTXXZB");
				
				//是否体弱儿其他
				String SFTREQT = rs.getString("SFTREQT");
				//体弱儿其他
				String TREQT = rs.getString("TREQT");
				//是否纳入体弱儿管理
				String SFTREGL = rs.getString("SFTREGL");
				//保健指导与处理代码
				String BJZDYCLDM = rs.getString("BJZDYCLDM");
				BJZDYCLDM = getIsNull(BJZDYCLDM);
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				BJZDYCLDM = getMultiple(BJZDYCLDM);
				
				//预约日期
				String YYRQ = rs.getString("YYRQ");
				
				/**
				 * 2014-5-5    未做字典处理                   李研edit
				 */
				YYRQ = convertDateToD8(YYRQ);

				//检查者
				String JCZ = rs.getString("JCZ");
				//检查者编码
				String JCZBM = rs.getString("JCZBM");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				JCZBM = DictMap.getCzry(JCZBM);
				
				//登记机构代码
				String DJJGDM = rs.getString("DJJGDM");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				DJJGDM = DictMap.getZcjg(DJJGDM);
				
				//登记机构名称
				String DJJGMC = rs.getString("DJJGMC");
				//修改日期
				
				
				
				Document document = DocumentHelper.createDocument();	
				Element root = document.addElement("body"); 
				
				Element head = root.addElement("head"); 
				Element userid = head.addElement("userid");
				userid.addText("nb_fybj");
				Element password = head.addElement("password");
				password.addText("123");
				Element trans_no = head.addElement("trans_no");
				trans_no.addText("B04.01.01.04");
				
				Element request = root.addElement("resquest");
				Element trans_noChild = request.addElement("B04.01.01.04");
				Element eRPTNO = trans_noChild.addElement("RPTNO");
				eRPTNO.setText( getIsNull(GRBJH));
				Element eCHECK_SN = trans_noChild.addElement("CHECK_SN");
				eCHECK_SN.setText(getIsNull(LSH));
				Element eHEALTH_ID  = trans_noChild.addElement("HEALTH_ID");
				eHEALTH_ID.setText("");
				Element MONITOR  = trans_noChild.addElement("MONITOR");
				MONITOR.setText( getIsNull(SFJXJC));
				Element MONITOR_REASON  = trans_noChild.addElement("MONITOR_REASON");
				MONITOR_REASON.setText(ZZJCYY);
				Element MONITOR_DATE  = trans_noChild.addElement("MONITOR_DATE");
				MONITOR_DATE.setText(  getIsNull(JCRQ));
				Element CHECK_DATE  = trans_noChild.addElement("CHECK_DATE");
				CHECK_DATE.setText(JCRQ );
				Element REARING  = trans_noChild.addElement("REARING");
				REARING.setText( getIsNull(YYFS));
				
				Element eREARING  = trans_noChild.addElement("REARING");
				eREARING.setText( getIsNull(WYFS));
				Element eDIAGNOSIS  = trans_noChild.addElement("DAILY_MILK");
				eDIAGNOSIS.setText( getIsNull(MRMRCS));
				Element eCLOSED  = trans_noChild.addElement("DAILY_MILK_VOLUME");
				eCLOSED.setText( getIsNull(MRNFL));
				
				Element eCLOSED_DATE   = trans_noChild.addElement("ADD_FEEDING");
				eCLOSED_DATE.setText( getIsNull(TJFS) );
				Element eCLOSED_EMP_ID = trans_noChild.addElement("FEEDING_TIMES");
				eCLOSED_EMP_ID.setText( getIsNull(MRTJFSCS));
				Element eCLOSED_CONCLUSIONS   = trans_noChild.addElement("VITAMIN_D");
				eCLOSED_CONCLUSIONS.setText( getIsNull(WSSD));
				Element eCLOSE_ORG_CODE  = trans_noChild.addElement("VITAMIN_D_OTHER");
				eCLOSE_ORG_CODE.setText( getIsNull(YMJJL));
				Element eCREATE_EMP_NAME  = trans_noChild.addElement("ADD_FOOD");
				eCREATE_EMP_NAME.setText( getIsNull(TJSWDM));
				Element APPETITE   = trans_noChild.addElement("APPETITE");
				APPETITE.setText( getIsNull(SY) );
				Element STOOL_FREQUENCY   = trans_noChild.addElement("STOOL_FREQUENCY");
				STOOL_FREQUENCY.setText( getIsNull(DBCS) );
				Element SLEEP = trans_noChild.addElement("SLEEP");
				SLEEP.setText( getIsNull(MRPJSMSJ));
				Element SLEEP_NOISY   = trans_noChild.addElement("SLEEP_NOISY");
				SLEEP_NOISY.setText(getIsNull(SMCN));
				Element SLEEP_HYPERHIDROSIS  = trans_noChild.addElement("SLEEP_HYPERHIDROSIS");
				SLEEP_HYPERHIDROSIS.setText(getIsNull(SMDH));
				Element OUTDOOR_ACTIVITIES  = trans_noChild.addElement("OUTDOOR_ACTIVITIES");
				OUTDOOR_ACTIVITIES.setText(getIsNull(HWHD));
				Element DIARRHEA_TIMES   = trans_noChild.addElement("DIARRHEA_TIMES");
				DIARRHEA_TIMES.setText(getIsNull(FXCS));
				Element PNEUMONIA_TIMES   = trans_noChild.addElement("PNEUMONIA_TIMES");
				PNEUMONIA_TIMES.setText(getIsNull(FYCS));
				Element COLDS_TIMES = trans_noChild.addElement("COLDS_TIMES");
				COLDS_TIMES.setText(getIsNull(GMCS));
				Element DISEASE_OCCURS   = trans_noChild.addElement("DISEASE_OCCURS");
				DISEASE_OCCURS.setText(getIsNull(FSJB));
				Element DISEASE_OCCURS_OTHER  = trans_noChild.addElement("DISEASE_OCCURS_OTHER");
				DISEASE_OCCURS_OTHER.setText(getIsNull(FSJBSM));
				Element WEIGHT  = trans_noChild.addElement("WEIGHT");
				WEIGHT.setText(getIsNull(TZ));
				Element HEIGHT   = trans_noChild.addElement("HEIGHT");
				HEIGHT.setText( getIsNull(SG) );

				Element HC   = trans_noChild.addElement("HC");
				HC.setText(getIsNull(TW));
				Element BREGMA1 = trans_noChild.addElement("BREGMA1");
				BREGMA1.setText(getIsNull(QX1) );
				Element BREGMA2 = trans_noChild.addElement("BREGMA2");
				BREGMA2.setText(getIsNull(QX2));
				Element TEETH   = trans_noChild.addElement("TEETH");
				TEETH.setText(getIsNull(CYS));
				Element DENTAL_CARIES  = trans_noChild.addElement("DENTAL_CARIES");
				DENTAL_CARIES.setText(getIsNull(QCS));
				Element NEONATAL_SCREENING  = trans_noChild.addElement("NEONATAL_SCREENING");
				NEONATAL_SCREENING.setText(getIsNull(XSEJBSC));
				Element HEARING_SCREENING   = trans_noChild.addElement("HEARING_SCREENING");
				HEARING_SCREENING.setText(getIsNull(XSETLSC));
				Element SIGNS_SKELETAL   = trans_noChild.addElement("SIGNS_SKELETAL");
				SIGNS_SKELETAL.setText("");
				Element EYE = trans_noChild.addElement("EYE");
				EYE.setText(getIsNull(Y));
				Element ORAL   = trans_noChild.addElement("ORAL");
				ORAL.setText(getIsNull(KQ));
				Element SKIN  = trans_noChild.addElement("SKIN");
				SKIN.setText(getIsNull(PF));
				Element HEARING  = trans_noChild.addElement("HEARING");
				HEARING.setText(getIsNull(TL));
				Element CARDIAC_AUSCULTATION   = trans_noChild.addElement("CARDIAC_AUSCULTATION");
				CARDIAC_AUSCULTATION.setText(getIsNull(XZTZ));
				Element LUNG_AUSCULTATION   = trans_noChild.addElement("LUNG_AUSCULTATION");
				LUNG_AUSCULTATION.setText(getIsNull(FTZ));
				Element LIVER_SPLEEN = trans_noChild.addElement("LIVER_SPLEEN");
				LIVER_SPLEEN.setText(getIsNull(GPCZ));
				Element GENITALIA   = trans_noChild.addElement("GENITALIA");
				GENITALIA.setText(getIsNull(WSZQ));
				Element CRYPTORCHIDISM  = trans_noChild.addElement("CRYPTORCHIDISM");
				CRYPTORCHIDISM.setText(getIsNull(YG));
				Element HIP_JOINTS  = trans_noChild.addElement("HIP_JOINTS");
				HIP_JOINTS.setText(getIsNull(KGJHDDM));
				Element CP   = trans_noChild.addElement("CP");
				CP.setText(getIsNull(NT));
				Element DEFECTS   = trans_noChild.addElement("DEFECTS");
				DEFECTS.setText(getIsNull(JX));
				Element eDDST = trans_noChild.addElement("DDST");
				eDDST.setText(getIsNull(DDST) );
				Element HEMOGLOBIN   = trans_noChild.addElement("HEMOGLOBIN");
				HEMOGLOBIN.setText(getIsNull(XHDB));
				Element URINALYSIS  = trans_noChild.addElement("URINALYSIS");
				URINALYSIS.setText(getIsNull(NCG));
				Element OTHER_ANOMALIES  = trans_noChild.addElement("OTHER_ANOMALIES");
				OTHER_ANOMALIES.setText("");
				Element GUIDANCE_TREATMENT   = trans_noChild.addElement("GUIDANCE_TREATMENT");
				GUIDANCE_TREATMENT.setText("");//2014-5-5 出现20  30   等未知数据

				Element CONGENITAL_HEART   = trans_noChild.addElement("CONGENITAL_HEART");
				CONGENITAL_HEART.setText(getIsNull(XTXXZB));
				Element WEIGHT_EVAL = trans_noChild.addElement("WEIGHT_EVAL");
				WEIGHT_EVAL.setText(getIsNull(TZPJ));
				Element HEIGHT_EVAL   = trans_noChild.addElement("HEIGHT_EVAL");
				HEIGHT_EVAL.setText(getIsNull(SGPJ));
				Element HEAD_EVAL  = trans_noChild.addElement("HEAD_EVAL");
				HEAD_EVAL.setText(getIsNull(TWPJ));
				Element HEIGHT_MEASUREMENT  = trans_noChild.addElement("HEIGHT_MEASUREMENT");
				HEIGHT_MEASUREMENT.setText(getIsNull(SGCTZ));
				Element eCREATE_ORG_NAME   = trans_noChild.addElement("ANEMIA");
				eCREATE_ORG_NAME.setText(getIsNull(PX));

				Element RICKETS   = trans_noChild.addElement("RICKETS");
				RICKETS.setText(getIsNull(GLB));
				Element FAT = trans_noChild.addElement("FAT");
				FAT.setText(getIsNull(FP));
				Element MALNUTRITION   = trans_noChild.addElement("MALNUTRITION");
				MALNUTRITION.setText(getIsNull(YYBL));
				Element FRAIL_CHILDREN  = trans_noChild.addElement("FRAIL_CHILDREN");
				FRAIL_CHILDREN.setText(getIsNull(SFTREQT));
				Element FRAIL_CHILDREN_OTHER  = trans_noChild.addElement("FRAIL_CHILDREN_OTHER");
				FRAIL_CHILDREN_OTHER.setText(getIsNull(TREQT));
				Element FRAIL_CHILDREN_FLAG   = trans_noChild.addElement("FRAIL_CHILDREN_FLAG");
				FRAIL_CHILDREN_FLAG.setText(getIsNull(SFTREGL));

				Element INFANT_FACTORS   = trans_noChild.addElement("INFANT_FACTORS");
				INFANT_FACTORS.setText("");
				Element APPOINTMENT_DATE = trans_noChild.addElement("APPOINTMENT_DATE");
				APPOINTMENT_DATE.setText(getIsNull(YYRQ));
				Element CHECK_EMP_ID   = trans_noChild.addElement("CHECK_EMP_ID");
				CHECK_EMP_ID.setText(getIsNull(JCZBM));
				Element CHECK_ORG_CODE  = trans_noChild.addElement("CHECK_ORG_CODE");
				CHECK_ORG_CODE.setText(getIsNull(DJJGDM));
				Element CREATE_EMP_NAME  = trans_noChild.addElement("CREATE_EMP_NAME");
				CREATE_EMP_NAME.setText(getIsNull(JCZ));
				Element CREATE_ORG_NAME   = trans_noChild.addElement("CREATE_ORG_NAME");
				CREATE_ORG_NAME.setText(getIsNull(DJJGMC));
				
				Element eUPLOAD_TIME = trans_noChild.addElement("UPLOAD_TIME");
				eUPLOAD_TIME.setText(DateUtil.nowDT15());

				xmlString = document.getRootElement().asXML();
				//map.put(LSH, xmlString);
				map.put(getIsNull(GRBJH), xmlString);
			}
		}catch (SQLException e) {
			logger.error("fail to connect db");
		}finally{
			//2014-4-28   李研    添加链接关闭代码
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					pspm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}
		}
		
		return map;
	}
	

	
	public String getIsNull(String str){
		if(str == null){
			return "";
		}
		return str;
	}
	
	//终止监测原因  ZZJCYY
	//2014-4-28 check， ALL ERROR ， suggest   del
	public String getZZJCYY(String code) {
		String ZZJCYY = "";
		switch (code) {
		case "4":
			ZZJCYY = "葡萄胎";
			break;
		case "5":
			ZZJCYY = "异位妊娠";
			break;
		case "6":
			ZZJCYY = "自然流产(包括<28周死胎死产）";
			break;
		case "7":
			ZZJCYY = "<28周治疗性引产";
			break;
		default:
			break;
		}
		return ZZJCYY;
	}
	
	/**
	 * 
	* 方法名:          convertDateToD8
	* 方法功能描述:    将字符串日期转换为D8格式的日期
	* 
	 * @author 李研
	 */
	public String convertDateToD8(String strDate){
		
		if(strDate == null || "".equals(strDate)){
			return "";
		}
		
		String newDate = "";
		try {
			
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
			newDate = new SimpleDateFormat("yyyyMMdd").format(date);
		} catch (ParseException e) {
			logger.error("-----日期转换出错-------");
			e.printStackTrace();
		}
		return newDate;
	}
	
	/**
	 * 保健体检     睡眠吵闹或夜惊    匹配
	 * 保健体检     睡眠多汗    匹配
	 * 保健体检    本阶段发生疾病    匹配
	 * 保健体检     脑    瘫    匹配
	 * 保健体检   是否新发现畸形    匹配
	 * 保健体检     畸    形    匹配
	 * @author 李研
	 * 业务表   1   有   2  无
	 * 中心表   0   无   1  有   9  不详
	 */
	public String getSleep(String sleep){

		if("2".equals(sleep)){
			sleep="0";
		}
		
		return sleep;
	}
	
	/**
	 * 
	 * 方法名:          getMultiple
	 * 方法功能描述:    将多选串中的分隔符转换
	 * @author 李研
	 * @Create Date:   2014年4月17日 
	 */	
	public String getMultiple(String code) {

		if (code != null && !"".equals(code)) {
			code = code.replace("/", "|");
		}
		 
		if (code.endsWith("|")){
			code=code.substring(0, code.length()-1);
		}
		return code;
	}
	
	
	
	public static void main(String[] args) throws IOException {
//		Eb_bj_tjjl childCare = new Eb_bj_tjjl();
//		Map<String, String> result = childCare.createxml("2012-04-28 00:00:00.000", "2012-04-29 00:00:00.000");
//		@SuppressWarnings("rawtypes")
//		Iterator it = result.entrySet().iterator();
//		while (it.hasNext()) {
//			@SuppressWarnings("rawtypes")
//			Map.Entry entry = (Map.Entry) it.next();
//			Object key = entry.getKey();
//			Object value = entry.getValue();
//			System.out.println("key=" + key + " value=" + value);
//		}
		

	}
	
	
}
