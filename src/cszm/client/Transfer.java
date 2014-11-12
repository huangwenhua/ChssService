package cszm.client;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import dictall.DictMap;
import sys.CommonPara;
import sys.Content;
import util.DateUtil;
import util.EhcacheUtil;
import util.UnifieUtil;

public class Transfer {
	
	private static Logger logger = Logger.getLogger(Transfer.class);
	
	private static Map<String,Content>  mapCon = new HashMap<String,Content>();
	
	static{
		try{
			SAXReader reader = new SAXReader();    
			Document document = reader.read(new File(CommonPara.CSZM_CONTENT));
	        Element node = document.getRootElement();  
	        Iterator<Element> it = node.elementIterator();  
	        while (it.hasNext()) { 
	        	Content con = new Content();
	            Element e = it.next();              
	            String name = e.getName();
	            String idname = "";
	            String fun = "";
	 
	            List<Attribute> list = e.attributes();  
	            for (Attribute attr : list) {  
	                if(attr.getName().equals("id")){
	                	idname = attr.getValue();  
	                }
	                if(attr.getName().equals("fun")){
	                	fun = attr.getValue();  
	                }
	            }  
	            con.setName(name);
	            con.setId(idname);
	            con.setFun(fun);
	            mapCon.put(name, con);
	        }  
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
	
	
	public static String paser(String csbh,String cszmXml) throws Exception{
		Map<String,String> map = localVal(cszmXml);
		 // 创建saxReader对象  
        SAXReader reader = new SAXReader();         
        Document doc = reader.read(new File(CommonPara.CSZM_BODY));
        Element trans_noChild = (Element)doc.getRootElement().element("resquest").elementIterator().next();
	
        for (String key : mapCon.keySet()) {
        	String name = key;
        	String idname = mapCon.get(key).getId();
        	String fun = mapCon.get(key).getFun();
        	
        	Element elem = trans_noChild.addElement( name );
        	String text = idname.equals("")?"":map.get(idname);
            switch (fun)
            {
            case "nowDT15":
            	text = DateUtil.nowDT15();
            	break;
            case "DT15":
            	text = DT15(text);
            	break;
            case "zmzt":
            	text = zmzt(text);
            	break;
            case "mz":
            	text = mz(text);
            	break;
            case "cs":
            	text = cs(text);
            	break;
            case "csdfl":
            	text = csdfl(text);
            	break;
            case "zjlb":
            	text = zjlb(text);
            	break;
            case "gl":
            	text = gl(text);
            	break;
            case "xz":
            	text = xz(text);
            	break;
            default:
            	break;
            }
            elem.setText(text);
        }
        
//      Result result = new Result();
//      result.doResult( doc.getRootElement().asXML(), "T_CSZM", csbh);
        
        return doc.getRootElement().asXML();
	}
	
//	public static void paser(String csbh,String cszmXml) throws Exception{
//		Map<String,String> map = localVal(cszmXml);
//		 // 创建saxReader对象  
//        SAXReader reader = new SAXReader();  
//        
//        /**
//		Document doc = DocumentHelper.createDocument();	
//		Element root = doc.addElement("body"); 		
//		Element head = root.addElement("head"); 
//		Element userid = head.addElement("userid");
//		userid.addText("nb_fybj");
//		Element password = head.addElement("password");
//		password.addText("123");
//		Element trans_no = head.addElement("trans_no");
//		trans_no.addText("B04.01.01.06");	
//		Element request = root.addElement("resquest");
//		Element trans_noChild = request.addElement("B04.01.01.06");
//		**/
//        
//        Document doc = reader.read(new File("src/cszm_body.xml"));
//        Element trans_noChild = (Element)doc.getRootElement().element("resquest").elementIterator().next();
//		
//        // 通过read方法读取一个文件 转换成Document对象  
//        Document document = reader.read(new File("src/cszm_content.xml"));  
//        //获取根节点元素对象  
//        Element node = document.getRootElement(); 
//        // 当前节点下面子节点迭代器  
//        Iterator<Element> it = node.elementIterator();  
//        // 遍历  
//        while (it.hasNext()) {  
//            // 获取某个子节点对象  
//            Element e = it.next();  
//            String idname = "";
//            String fun = "";
//            // 对子节点进行遍历  
//            //System.out.println("当前节点的名称：：" + e.getName());  
//            // 获取当前节点的所有属性节点  
//            List<Attribute> list = e.attributes();  
//            for (Attribute attr : list) {  
//                if(attr.getName().equals("id")){
//                	 idname = attr.getValue();  
//                }
//                if(attr.getName().equals("fun")){
//                	fun = attr.getValue();  
//                }
//            }  
//            Element elem = trans_noChild.addElement( e.getName() );
//            String text = idname.equals("")?"":map.get(idname);
//            switch (fun)
//            {
//            case "nowDT15":
//            	text = DateUtil.nowDT15();
//            	break;
//            case "DT15":
//            	text = DT15(text);
//            	break;
//            case "zmzt":
//            	text = zmzt(text);
//            	break;
//            case "mz":
//            	text = mz(text);
//            	break;
//            case "cs":
//            	text = cs(text);
//            	break;
//            case "csdfl":
//            	text = csdfl(text);
//            	break;
//            case "zjlb":
//            	text = zjlb(text);
//            	break;
//            case "gl":
//            	text = gl(text);
//            	break;
//            case "xz":
//            	text = xz(text);
//            	break;
//            default:
//            	break;
//            }
//            elem.setText(text);
//        }  
//        
//        Result result = new Result();
//        result.doResult( doc.getRootElement().asXML(), "T_CSZM", csbh);
//	}
	
	private static Map<String,String> localVal(String cszmXml){
		Map<String,String> map = new HashMap<String,String>();
		Document docment = null;
		try {
			docment = DocumentHelper.parseText(cszmXml); // 将字符串转为XML
			Element rootElt = docment.getRootElement(); // 获取根节点
			Iterator iter = rootElt.elementIterator(); // 获取根节点下的子节点
			// 遍历response节点
			while (iter.hasNext()) {
				Element recordEle = (Element) iter.next();
				map.put(recordEle.getName(), recordEle.getText());
			}
		} catch (Exception e) {
			logger.error("parse xml is error :" + e.getMessage());
			e.printStackTrace();
		}
		return map;
	}
	
	public static void main(String[] args){
		EhcacheUtil.getInstance().initCache(); // 初始化ehcache缓存
		System.out.println("ss");
		try {
			Transfer.paser("B330000104","<Table><CSBH>B330000104</CSBH><XM>cdolph1 Blaine Charles David</XM><XB>1</XB><XBMC>男性</XBMC><SR>2013-11-04      </SR><SRS>12</SRS><SRF>12</SRF><MXM>cindy</MXM><MNL>28</MNL><MGJ>1</MGJ><MGJMC>中国</MGJMC><MMZ>1</MMZ><MMZMC>汉族</MMZMC><MQZJLB>1</MQZJLB><MQZJLBMC>居民身份证</MQZJLBMC><MQYXSFZJ>330125198501232759</MQYXSFZJ><MQDZMC>浙江省宁波市宁海县</MQDZMC><FXM>刘敏</FXM><FNL>28</FNL><FGJ>1</FGJ><FGJMC>中国</FGJMC><FMZ>1</FMZ><FMZMC>汉族</FMZMC><FQZJLB>1</FQZJLB><FQZJLBMC>居民身份证</FQZJLBMC><FQYXSFZJ>330726198501232759</FQYXSFZJ><FQDZMC>浙江省台州市临海市</FQDZMC><CSDZMC>浙江省丽水市莲都区</CSDZMC><CSDZBM>331102000000</CSDZBM><CSYZ>38</CSYZ><CS>1</CS><CSMC>自然产</CSMC><JSJGM>庆元县妇幼保健所</JSJGM><JSR>明天</JSR><CSDFL>3</CSDFL><CSDFLMC>妇幼保健院</CSDFLMC><ZMZT>1</ZMZT><QFRQ>2013-11-14</QFRQ><QFJGM>庆元县妇幼保健所</QFJGM><LBH>B330000100</LBH><DZLBDM>01</DZLBDM><HJSHENG>330000000000</HJSHENG><HJSHENGMC>浙江省</HJSHENGMC><HJSHIQU>331000000000</HJSHIQU><HJSHIQUMC>台州市</HJSHIQUMC><HJQUXIAN>331082000000</HJQUXIAN><HJQUXIANMC>临海市</HJQUXIANMC><HJJIEDAO/><HJJIEDAOMC/><HJDZMPH/><HJDZMC>浙江省台州市临海市</HJDZMC></Table>");
			Transfer.paser("B330000104","<Table><CSBH>B330000104</CSBH><XM>cdolph2 Blaine Charles David</XM><XB>1</XB><XBMC>男性</XBMC><SR>2013-11-04      </SR><SRS>12</SRS><SRF>12</SRF><MXM>cindy</MXM><MNL>28</MNL><MGJ>1</MGJ><MGJMC>中国</MGJMC><MMZ>1</MMZ><MMZMC>汉族</MMZMC><MQZJLB>1</MQZJLB><MQZJLBMC>居民身份证</MQZJLBMC><MQYXSFZJ>330125198501232759</MQYXSFZJ><MQDZMC>浙江省宁波市宁海县</MQDZMC><FXM>刘敏</FXM><FNL>28</FNL><FGJ>1</FGJ><FGJMC>中国</FGJMC><FMZ>1</FMZ><FMZMC>汉族</FMZMC><FQZJLB>1</FQZJLB><FQZJLBMC>居民身份证</FQZJLBMC><FQYXSFZJ>330726198501232759</FQYXSFZJ><FQDZMC>浙江省台州市临海市</FQDZMC><CSDZMC>浙江省丽水市莲都区</CSDZMC><CSDZBM>331102000000</CSDZBM><CSYZ>38</CSYZ><CS>1</CS><CSMC>自然产</CSMC><JSJGM>庆元县妇幼保健所</JSJGM><JSR>明天</JSR><CSDFL>3</CSDFL><CSDFLMC>妇幼保健院</CSDFLMC><ZMZT>1</ZMZT><QFRQ>2013-11-14</QFRQ><QFJGM>庆元县妇幼保健所</QFJGM><LBH>B330000100</LBH><DZLBDM>01</DZLBDM><HJSHENG>330000000000</HJSHENG><HJSHENGMC>浙江省</HJSHENGMC><HJSHIQU>331000000000</HJSHIQU><HJSHIQUMC>台州市</HJSHIQUMC><HJQUXIAN>331082000000</HJQUXIAN><HJQUXIANMC>临海市</HJQUXIANMC><HJJIEDAO/><HJJIEDAOMC/><HJDZMPH/><HJDZMC>浙江省台州市临海市</HJDZMC></Table>");

			Transfer.paser("B330000104","<Table><CSBH>B330000104</CSBH><XM>cdolph3 Blaine Charles David</XM><XB>1</XB><XBMC>男性</XBMC><SR>2013-11-04      </SR><SRS>12</SRS><SRF>12</SRF><MXM>cindy</MXM><MNL>28</MNL><MGJ>1</MGJ><MGJMC>中国</MGJMC><MMZ>1</MMZ><MMZMC>汉族</MMZMC><MQZJLB>1</MQZJLB><MQZJLBMC>居民身份证</MQZJLBMC><MQYXSFZJ>330125198501232759</MQYXSFZJ><MQDZMC>浙江省宁波市宁海县</MQDZMC><FXM>刘敏</FXM><FNL>28</FNL><FGJ>1</FGJ><FGJMC>中国</FGJMC><FMZ>1</FMZ><FMZMC>汉族</FMZMC><FQZJLB>1</FQZJLB><FQZJLBMC>居民身份证</FQZJLBMC><FQYXSFZJ>330726198501232759</FQYXSFZJ><FQDZMC>浙江省台州市临海市</FQDZMC><CSDZMC>浙江省丽水市莲都区</CSDZMC><CSDZBM>331102000000</CSDZBM><CSYZ>38</CSYZ><CS>1</CS><CSMC>自然产</CSMC><JSJGM>庆元县妇幼保健所</JSJGM><JSR>明天</JSR><CSDFL>3</CSDFL><CSDFLMC>妇幼保健院</CSDFLMC><ZMZT>1</ZMZT><QFRQ>2013-11-14</QFRQ><QFJGM>庆元县妇幼保健所</QFJGM><LBH>B330000100</LBH><DZLBDM>01</DZLBDM><HJSHENG>330000000000</HJSHENG><HJSHENGMC>浙江省</HJSHENGMC><HJSHIQU>331000000000</HJSHIQU><HJSHIQUMC>台州市</HJSHIQUMC><HJQUXIAN>331082000000</HJQUXIAN><HJQUXIANMC>临海市</HJQUXIANMC><HJJIEDAO/><HJJIEDAOMC/><HJDZMPH/><HJDZMC>浙江省台州市临海市</HJDZMC></Table>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


	/**
	 * 区域中心：	
	 * 		yyyyMMdd
	 * 
	 * 业务系统：
	 * 		yyyy-MM-dd
	 */
	private static String DT15(String sr){
		return UnifieUtil.convertDateToD8( UnifieUtil.null2(sr) );
	}
	
	/**
	区域中心：	
	证明状态	
	0-废证  1-正常
	2-补证  9-不详
	
	业务系统：
	出生证类型	
		1	正常
		2	补证
		3	换证
	 */
	private static String zmzt(String zmzt){
		String ret = "9";
		if(zmzt.equals("1"))
			ret = "1";
		else if(zmzt.equals("2"))
			ret = "2";
		else 
			ret = "9";
		return ret;
	}
	
	/**
	区域中心：
	编码	名称
		01	汉族
		02	蒙古族
		03	回族
		04	藏族
		05	维吾尔族
		06	苗族
		07	彝族
		08	壮族
		09	布依族
		10	朝鲜族
		11	满族
		12	侗族
		13	瑶族
		14	白族
		15	土家族
		16	哈尼族
		17	哈萨克族
		18	傣族
		19	黎族
		20	傈僳族
		21	佤族
		22	畲族
		23	高山族
		24	拉祜族
		25	水族
		26	东乡族
		27	纳西族
		28	景颇族
		29	柯尔克孜族
		30	土族
		31	达斡尔族
		32	仫佬族
		33	羌族
		34	布朗族
		35	撒拉族
		36	毛南族
		37	仡佬族
		38	锡伯族
		39	阿昌族
		40	普米族
		41	塔吉克族
		42	怒族
		43	乌孜别克族
		44	俄罗斯族
		45	鄂温克族
		46	德昂族
		47	保安族
		48	裕固族
		49	京族
		50	塔塔尔族
		51	独龙族
		52	鄂伦春族
		53	赫哲族
		54	门巴族
		55	珞巴族
		56	基诺族
		99	少数民族
	
	
	本地业务：
	民族代码	
		0	/
		1	汉族
		2	蒙古族
		3	回族
		4	藏族
		5	维吾尔族
		6	苗族
		7	彝族
		8	壮族
		9	布依族
		10	朝鲜族
		11	满族
		12	侗族
		13	瑶族
		14	白族
		15	土家族
		16	哈尼族
		17	哈萨克族
		18	傣族
		19	黎族
		20	傈僳族
		21	佤族
		22	畲族
		23	高山族
		24	拉祜族
		25	水族
		26	东乡族
		27	纳西族
		28	景颇族
		29	柯尔克孜族
		30	土族
		31	达斡尔族
		32	仫佬族
		33	羌族
		34	布朗族
		35	撒拉族
		36	毛南族
		37	仡佬族
		38	锡伯族
		39	阿昌族
		40	普米族
		41	塔吉克族
		42	怒族
		43	乌孜别克族
		44	俄罗斯族
		45	鄂温克族
		46	德昂族
		47	保安族
		48	裕固族
		49	京族
		50	塔塔尔族
		51	独龙族
		52	鄂伦春族
		53	赫哲族
		54	门巴族
		55	珞巴族
		56	基诺族
		57	亻革家人
		58	穿青族
		59	穿青人
		60	偈家人
		98	外国血统
		99	其他
	
	 */
	private static String mz(String mz){
		String ret = mz;
		if(mz.equals("0") || mz.equals("57") || mz.equals("58") || mz.equals("59") || mz.equals("60") || mz.equals("98"))
			ret = "99";
		return ret;
	}

	/**
	区域中心：
		1	头位阴道自然分娩	
		2	臀助产	
		3	臀牵引	
		4	胎头牵引	
		5	产钳	
		6	临产前剖宫产	
		7	临产后剖宫产	
		8	毁胎术	
		9	其他	
		99	不详	
	
	
	本地业务：
		1	自然产
		2	头吸
		3	产钳
		4	臀助产
		5	剖宫产
	 */
	
	private static String cs(String cs){
		String ret = "99";
		if(cs.equals("1"))
			ret = "1";
		else if(cs.equals("2"))
			ret  = "4";
		else if(cs.equals("3"))
			ret = "5";
		else if(cs.equals("4"))
			ret = "2";
		else if(cs.equals("5"))
			ret = "6";
		return ret;
	}
	
	/**
		区域中心：	
			1	医院
			2	妇幼保健院
			3	家庭
			9	其他
	
		本地业务：
		出生地分类	
			1	县级以上医院
			2	乡镇(街道)卫生院
			3	妇幼保健院
			4	家中
			5	途中
			99	其他
			100	不详
	 **/
	private static String csdfl(String csdfl){
		String ret = "9";
		if(csdfl.equals("1"))
			ret = "1";
		else if(csdfl.equals("2"))
			ret = "1";
		else if(csdfl.equals("3"))
			ret = "2";
		else if(csdfl.equals("4"))
			ret = "3";
		return ret;
	}
	
	/**
		区域中心：	
		值	值含义
		1	本县区
		2	本市区其他县区
		3	本省其他城市
		4	外省
		5	港澳台
		6 	外籍
		
		
		
		本地业务：
		1	本省
		2	本市
		3	本县
		4	外省
	
	**/

	
	/**
		区域中心：	
			01	居民身份证	
			02	港澳居民身份证	
			03	居民户口簿	
			04	护照	
			05	军官证	
			06	文职干部证	
			07	士兵证	
			08	驾驶执照	
			09	残疾证	
			10	医疗保险证	
			11	出生证明	
			12	家庭标识	
			13	住院病案	
			14	住院床位	
			15	门诊病历	
			16	死亡证明	
			99	其他标识	
		
		
		
		本地业务：
		有效身份证件类别	
			1	居民身份证
			2	护照
			3	军人身份证
			4	港澳居民来往内地通行证
			5	台湾居民来往大陆通行证
			6	中华人民共和国旅行证
	 **/		
	private static String zjlb(String zjlb){
		String ret = "99";
		if(zjlb.equals("1"))
			ret = "01";
		else if(zjlb.equals("2"))
			ret = "04";
		else if(zjlb.equals("3"))
			ret = "05";
		else if(zjlb.equals("4"))
			ret = "99";
		else if(zjlb.equals("5"))
			ret = "99";
		else if(zjlb.equals("6"))
			ret = "99";
		return ret;
	}
	
	
	/**
	区域中心：	
		004	阿富汗	阿富汗伊斯兰国 Islamic State of Afghanistan
		008	阿尔巴尼亚	阿尔马尼亚共和国 Republic of Albania
		010	南极洲	南极洲 Antarctica
		012	阿尔及利亚	阿尔及利亚民主人民共和国 Democratic People's Republic of Algeria
		016	美属萨摩亚	美属萨摩亚 American Samoa
		020	安道尔	安道尔公国 Principality of Andorra
		024	安哥拉	安哥拉共和国 Republic of Angola
		028	安提瓜和巴布达	安提瓜和巴布达 Antigua and Barbuda
		031	阿塞拜疆	阿塞拜疆共和国 Republic of Azerbaijan
		032	阿根廷	阿根廷共和国 Republic of Argentina
		036	澳大利亚	澳大利亚联邦 Commonwealth of Australia
		040	奥地利	奥地利共和国 Republic of Austria
		044	巴哈马	巴哈马联邦 Commonwealth of the Bahamas
		048	巴林	巴林国 State of Bahrain
		050	孟加拉国	孟加拉人民共和国 People's Republic of Bangladesh
		051	亚美尼亚	亚美尼亚共和国 Republic of Armenia
		052	巴巴多斯	巴巴多斯 Barbados
		056	比利时	比利时王国 Kingdom of belgium
		060	百慕大	百慕大群岛 Bermuda Islands
		064	不丹	不丹王国 Kingdom of Bhutan
		068	玻利维亚	玻利维亚共和国 Republic of Bolivia
		070	波黑	波斯尼亚和黑塞哥维那 Bosnia and Herzegovina
		072	博茨瓦纳	博茨瓦纳共和国 Republic of Botswana
		074	布维岛	布维岛 Bouvet Island
		076	巴西	巴西联邦共和国 Federative Republic of Brazil
		084	伯利兹	伯利兹 Belize
		086	英属印度洋领土	英属印度洋领土 British Indian Ocean Territory
		090	所罗门群岛	年罗门群岛 Solomon Islands
		092	英属维尔京群岛	英属维尔京群岛 British Virgin Islands
		096	文莱	文莱达鲁萨兰国 Brunei Darussalam
		100	保加利亚	保加利亚共和国 Republic ov Bulgaria
		104	缅甸	缅甸联邦 Union of Myanmar
		108	布隆迪	布隆迪共和国 Republic of Burundi
		112	白俄罗斯	白俄罗斯共和国 Republic of Belarus
		116	柬埔寨	柬埔寨王国 Kingdom of Cambodia
		120	喀麦隆	喀麦隆共和国 Republic of Cameroon
		124	加拿大	加拿大 Canada
		132	佛得角	佛得角共和国 Republic of Cape Verde
		136	开曼群岛	开曼群岛 Cayman Islands
		140	中非	中非共和国 Central African Republic
		144	斯里兰卡	斯里兰卡民主社会主义共和国 Democratic Socialist Republic of Srilanka
		148	乍得	乍得共和国 Republic of Chad
		152	智利	智利共和国 Republic of Chile
		156	中国	中华人民共和国 People's Republic of China
		158	台湾	中国台湾 Taiwan, Province of China
		162	圣诞岛	圣诞岛 Christmas Island
		166	科科斯(基林)群岛	科科斯(基林)群岛 Cocos(Keeling) Islands
		170	哥伦比亚	哥伦比亚共和国 Republic of Colombia
		174	科摩罗	科摩罗伊斯兰联邦共和国 Federal Islamic Republic of the Co-moros
		175	马约特	马约特 Mayotte
		178	刚果（布）	刚果共和国 Republic of Congo
		180	刚果（金）	刚果民主共和国 Democratic Republic of Congo
		184	库克群岛	库克群岛 Cook Islands
		188	哥斯达黎加	哥斯达黎加共和国 Republic of Costa Rica
		191	克罗地亚	克罗地亚共和国 Republic of Croatia
		192	古巴	古巴共和国 Republic of Cuba
		196	塞浦路斯	塞浦路斯共和国 Republic of Cyprus
		203	捷克	捷克共和国 Czech Republic
		204	贝宁	贝宁共和国 Republic of Benin
		208	丹麦	丹麦王国 Kingdom of Denmark
		212	多米尼克	多米尼克联邦 Commonwealth of Dominica
		214	多米尼加共和国	多米尼加共和国 Dominican Republic
		218	厄瓜多尔	厄瓜多尔共和国 Republic of Ecuador
		222	萨尔瓦多	萨尔瓦多共和国 Republic of El Salvador
		226	赤道几内亚	赤道几内亚共和国 Republic of Equatorial Guinea
		231	埃塞俄比亚	埃塞俄比亚 Ethiopia
		232	厄立特里亚	厄立特里亚国 State of Eritrea
		233	爱沙尼亚	爱沙尼亚共和国 Republic of Estonia
		234	法罗群岛	法罗群岛 Faroe Islands
		238	福克兰群岛(马尔维纳斯)	福克兰群岛(马尔维纳斯) Falkland Islands(Malvinas)
		239	南乔治亚岛和南桑德韦奇岛	南乔治亚岛和南桑德韦奇岛 South Georgia and South Sandwich Islands
		242	斐济	斐济共和国 Republic of Fiji
		246	芬兰	芬兰共和国 Republic of Finland
		250	法国	法兰西共和国 French Republic
		254	法属圭亚那	法属圭亚那 French Guiana
		258	法属波利尼西亚	法属波利尼西亚 French Polynesia
		260	法属南部领土	法属南部领土 French Southern Territories
		262	吉布提	吉布提共和国 Republic of Djibouti
		266	加蓬	加蓬共和国 Gabonese Republic
		268	格鲁吉亚	格鲁吉亚共和国 Republic of Georgia
		270	冈比亚Gambia	冈比亚共和国 Republic of Gambia
		276	德国	德意志联邦共和国 Federal Republic of Germany
		288	加纳	加纳共和国 Republic of Ghana
		292	直布罗陀	直布罗陀 Gibraltar
		296	基里巴斯	基里巴斯共和国 Republic of Kiribati
		300	希腊	希腊共和国 Hellenic Republic
		304	格陵兰	格陵兰 Greenland
		308	格林纳达	格林纳达 Grenada
		312	瓜德罗普	瓜德罗普 Guadeloupe
		316	关岛	关岛 Guam
		320	危地马拉	危地马拉共和国 Republic of Guatemala
		324	几内亚	几内亚共和国 Republic of Guinea
		328	圭亚那	圭亚那合作共和国 Cooperative Republic of Guyana
		332	海地	海地共和国 Republic of Haiti
		334	赫德岛和麦克唐纳岛	赫德岛和麦克唐纳岛 Heard islands and Mc Donald Islands
		336	梵蒂冈	梵蒂冈城国 Vatican City State
		340	洪都拉斯	洪都拉斯共和国 Republic of honduras
		344	香港	香港 Hong Kong
		348	匈牙利	匈牙利共和国 Republic of Hungary
		352	冰岛	冰岛共和国 Republic of Iceland
		356	印度	印度共和国 Republic of India
		360	印度尼西亚	印度尼西亚共和国 Republic of Indonesia
		364	伊朗	伊朗伊斯兰共和国 Islamic Rupublic of Iran
		368	伊拉克	伊拉克共和国 Republic of Iraq
		372	爱尔兰	爱尔兰 Ireland
		374	巴勒斯坦	巴勒斯坦国 State of Palestine
		376	以色列	以色列国 State of Israel
		380	意大利	意大利共和国 Republic of Italy
		384	科特迪瓦	科特迪瓦共和国 Republic of Cote d'Ivire
		388	牙买加	牙买加 Jamaica
		392	日本	日本国 Japan
		398	哈萨克斯坦	哈萨克斯坦共和国 Republic of Kazakhstan
		400	约旦	约旦哈希姆王国 Hashemite Kingdom of Jordan
		404	肯尼亚	肯尼亚共和国 Republic of Kenya
		408	朝鲜	朝鲜民主主义人民共和国 Democratic People's Republic of Ko-rea
		410	韩国	大韩民国 Republic of Korea
		414	科威特	科威特国 State of Kuwait
		417	吉尔吉斯斯坦	吉尔吉斯共和国 Kyrgyz Republic
		418	老挝	老挝人民民主共和国 Lao People's Democratic Republic
		422	黎巴嫩	黎巴嫩共和国 Republic of Lebanon
		426	莱索托	莱索托王国 Kingdom of Lesoto
		428	拉脱维亚	拉脱维亚共和国 Republic of Latvia
		430	利比里亚	利比里亚共和国 Republic of Liberia
		434	利比亚	大阿拉伯利比亚人民社会主义民众国 Great Socialist People's Libyan Arab jamahiriya
		438	列支敦士登	列支敦士登公国 Principality of Liechtenstein
		440	立陶宛	立陶宛共和国 Republic of Lithuania
		442	卢森堡	卢森堡大公国 Grand Duchy of Luxembourg
		446	澳门	澳门 Macau
		450	马达加斯加	马达加斯加共和国 Republic of Madagascar
		454	马拉维	马拉维共和国 Republic of Malawi
		458	马来西亚	马来西亚 Malaysia
		462	马尔代夫	马尔代夫共和国 Republic of maldives
		466	马里	马里共和国 Republic of Mali
		470	马耳他	马耳他共和国 Republic of Malta
		474	马提尼克	马提尼克 Martinique
		478	毛里塔尼亚	毛里求斯共和国 Republic of Mauritius
		480	毛里求斯	毛里求斯共和国 Republic of Mauritius
		484	墨西哥	墨西哥合众国 United States of Mexico
		492	摩纳哥	摩纳哥公国 Principality of Monaco
		496	蒙古	蒙古国 Mongolia
		498	摩尔多瓦	摩尔多瓦共和国 Republic of Moldova
		500	蒙特塞拉特	蒙特塞拉特 Montserrat
		504	摩洛哥	摩洛哥王国 Kingdom of Morocco
		508	莫桑比克	莫桑比克共和国 Republic of Mozambique
		512	阿曼	阿曼苏丹国 Sultanate of Oman
		516	纳米比亚	纳米比亚共和国 Republic of Namibia
		520	瑙鲁	瑙鲁共和国 Republic of Nauru
		524	尼泊尔	尼泊尔王国 Kingdom of Nepal
		528	荷兰	荷兰王国 Kingdom of the Netherlands
		530	荷属安的列斯	荷属安的列斯 Netherlands Antilles
		533	阿鲁巴	阿鲁巴 Aruba
		540	新喀里多尼亚	新喀里多尼亚 New Caledonia
		548	瓦努阿图	瓦努阿图共和国 Republic of Vanuatu
		554	新西兰	新西兰 New Zealand
		558	尼加拉瓜	尼加拉瓜共和国 Republic of Nicaragua
		562	尼日尔	尼日尔共和国 Republic of Niger
		566	尼日利亚	尼日利亚联邦共和国 Federal Republic of Nigeria
		570	纽埃	纽埃 Niue
		574	诺福克岛	诺福克岛 Norfolk Island
		578	挪威	挪威王国 Kingdom of Norway
		580	北马里亚纳	北马里亚纳自由联邦 Commonwealth of the Northern Marianas
		581	美国本土外小岛屿	美国本土外小岛屿 United States Minor Outlying Islands
		583	密克罗尼西亚联邦	密克罗尼西亚联邦 Federated States of Micronesia
		584	马绍尔群岛	马绍尔群岛共和国 Republic of the marshall Islands
		585	帕劳	帕劳共和国 Republic of Palau
		586	巴基斯坦	巴基斯坦伊斯兰共和国 Islamic Republic of Pakistan
		591	巴拿马	巴拿马共和国 Republic of Panama
		598	巴布亚新几内亚	巴布亚新几内亚独立国 Independent State of Papua New Guinea
		600	巴拉圭	巴拉圭共和国 Republic of Paraguay
		604	秘鲁	秘鲁共和国 Republic of Peru
		608	菲律宾	菲律宾共和国 Republic of the Philippines
		612	皮特凯恩群岛	皮竺凯恩群岛 Pitcairn Islands Group
		616	波兰	波兰共和国 Republic of Poland
		620	葡萄牙	葡萄牙共和国 Pirtuguese Republic
		624	几内亚比绍	几内亚比绍共和国 Republic of Guine-bissau
		626	东帝汶	东帝汶 East Timor
		630	波多黎各	波多黎各自由联邦 Commonwealth of Puerto Rico
		634	卡塔尔	卡塔尔国 State of Qatar
		638	留尼汪	留尼汪 Reunion
		642	罗马尼亚	罗马尼亚 Romania
		643	俄罗斯联邦	俄罗斯联邦 Russian Federation
		646	卢旺达	卢旺达共和国 Republic of Rwanda
		654	圣赫勒拿	对赫勒拿 Saint Helena
		659	圣基茨和尼维斯	圣革茨和尼维斯联邦 Federation of Saint Kitts and nevis
		660	安圭拉	安圭拉 Anguilla
		662	圣卢西亚	圣卢西亚 Saint Lucia
		666	圣皮埃尔和密克隆	圣皮埃尔和密克隆 Saint Pierre and Miquelon
		670	圣文森特和格林纳丁斯	圣文森特和格林纳丁斯 Saint Vincent and the Grenadines
		674	圣马力诺	圣马力诺共和国 Republic of San Marino
		678	圣多美和普林西比	圣多美和普林西比民主共和国 Democratic Republic of Sao Tome and Principe
		682	沙特阿拉伯	沙特阿拉伯王国 Kingdom of Saudi Arabia
		686	塞内加尔	塞内加尔共和国 Republic of Senegal
		690	塞舌尔	塞舌尔共和国 Republic of Seychelles
		694	塞拉利昂	塞拉利昂共和国 Republic of Sierra Leone
		702	新加坡	新加坡共和国 Republic of Singapore
		703	斯洛伐克	斯洛伐克共和国 Slovak Republic
		704	越南	越南社会主席共和国 Socialist Republic of Viet Nam
		705	斯洛文尼亚	斯洛文尼亚共和国 Republic of Slovenia
		706	索马里	索马里共和国 Somali Republic
		710	南非	南非共和国 Republic of South Africa
		716	津巴布韦	津巴布韦共和国 Republic of Zimbabwe
		724	西班牙	西班牙 Spain
		732	西撒哈拉	西撒哈拉 Western Sahara
		736	苏丹	苏丹共和国 Republic of the Sudan
		740	苏里南	苏里南共和国 Republic of Suriname
		744	斯瓦尔巴群岛	斯瓦尔巴群岛 Svalbard and Jan mayen islands
		748	斯威士兰	斯威士兰王国 Kingdom of Swaziland
		752	瑞典	瑞典王国 Kingdom of Sweden
		756	瑞士	瑞士联邦 Swiss Confederation
		760	叙利亚	阿拉伯叙利亚共和国 Syrian Arab Republic
		762	塔吉克斯坦	塔吉克斯坦共和国 Republic of Tajikistan
		764	泰国	泰王国 Kingdom of Thailand
		768	多哥	多哥共和国 Republic of Tago
		772	托克劳	托克劳 Tokelau
		776	汤加	汤加王国 Kingdom of Tonga
		780	特立尼达和多巴哥	特立尼达和多巴哥共和国 Republic of Trinidad and Tobago
		784	阿联酋	拉伯联合酋长国 United Arab Emirates
		788	突尼斯	突尼斯共和国 Republic of Tunisia
		792	土耳其	土耳其共和国 Republic of Turkey
		795	土库曼斯坦	土库曼斯坦 Turkmenistan
		796	特克斯科斯群岛	特克斯和凯科斯群岛 Turks and Caicos Islands
		798	图瓦卢	图瓦卢 Tuvalu
		800	乌干达	乌干达共和国 Republic of Uganda
		804	乌克兰	乌克兰 Ukraine
		807	前南马其顿	前南斯拉夫马其顿共和国 The Former Yugoslav Republic of Macedonia
		818	埃及	阿拉伯埃及共和国 Arab Republic of Egypt
		826	英国	大不列颠及北爱尔兰联合王国 United Kingdom of Great Britain and Northern ireland
		834	坦桑尼亚	坦桑尼亚联合共和国 United Republic of Tanzania
		840	美国	美利坚合众国 United States of America
		850	美属维尔京群岛	美属维尔京群岛 Virgin Islands of the United States
		854	布基纳法索	布基纳法索 Burkina Faso
		858	乌拉圭	乌拉圭东岸共和国 Oriental Republic of Uruguay
		860	乌兹别克斯坦	乌兹别克斯坦共和国 Republic of Uzbekistan
		862	委内瑞拉	委内瑞拉共和国 Republic of Venezuela
		876	瓦利斯和富图纳	瓦利斯和富图纳群岛 Wallis and Futuna
		882	萨摩亚	萨摩亚独立国 Independent State of Samoa
		887	也门	也门共和国 Republic of Yemen
		891	南斯拉夫	南斯拉夫联盟共和国 Federal Republic of Yugoslavia
		894	赞比亚	赞比亚共和国 Republic of Zambia
		
	本地业务：
		1	中国
		2	中国（台湾）
		3	中国（香港）
		4	中国（澳门）
		5	日本
		6	朝鲜
		7	韩国
		8	安提瓜和巴布达
		9	阿塞疆
		10	阿根廷
		11	澳大利亚
		12	奥地利
		13	巴哈马
		14	巴林
		15	孟加拉国
		16	亚美尼亚
		17	巴巴多斯
		18	比利时
		19	百慕大
		20	不丹
		21	玻利维亚
		22	波斯尼亚和黑塞哥维那
		23	博茨瓦纳
		24	布维岛
		25	巴西
		26	伯利兹
		27	英属印度洋领土
		28	所罗门群岛
		29	英属维尔京群岛
		30	文莱
		31	保加利亚
		32	缅甸
		33	布隆迪
		34	白俄罗斯
		35	柬埔寨
		36	喀麦隆
		37	加拿大
		38	佛得角
		39	开曼群岛
		40	中非
		41	斯里兰卡
		42	乍得
		43	智利
		44	阿富汗
		45	阿尔巴尼亚
		46	圣诞岛
		47	科科斯(基林)群岛
		48	哥伦比亚
		49	科摩罗
		50	马约特
		51	扎伊尔
		52	库克群岛
		53	哥斯达黎加
		54	克罗地亚
		55	古巴
		56	塞浦路斯
		57	捷克
		58	贝宁
		59	丹麦
		60	多米尼克
		61	多米尼加共和国
		62	厄瓜多尔
		63	萨尔瓦多
		64	赤道几内亚
		65	埃塞俄比亚
		66	厄立特里亚
		67	爱沙尼亚
		68	法罗群岛
		69	马尔维纳斯群岛(福克兰群岛)
		70	南乔治亚和南桑德韦奇群岛
		71	斐汶
		72	芬兰
		73	法国
		74	法属圭亚那
		75	法属波利尼西亚
		76	法属南部领土
		77	吉布提
		78	加蓬
		79	格鲁吉亚
		80	冈比亚
		81	德国
		82	加纳
		83	直布罗陀
		84	基里巴斯
		85	希腊
		86	格陵兰
		87	格林纳达
		88	瓜德罗普
		89	关岛
		90	危地马拉
		91	几内亚
		92	圭亚那
		93	海地
		94	赫德岛和麦克唐纳岛
		95	梵蒂冈
		96	洪都拉斯
		97	南极洲
		98	匈牙利
		99	冰岛
		100	印度
		101	印度尼西亚
		102	伊朗
		103	伊拉克
		104	爱尔兰
		105	巴勒斯坦
		106	以色列
		107	意大利
		108	科特迪瓦
		109	牙买加
		110	美属萨摩亚
		111	哈萨克斯坦
		112	约旦
		113	肯尼亚
		114	安道尔
		115	安哥拉
		116	科威特
		117	吉尔吉斯斯坦
		118	老挝
		119	黎巴嫩
		120	莱索托
		121	拉脱维亚
		122	利比里亚
		123	利比亚
		124	列支敦士登
		125	立陶宛
		126	卢森堡
		127	阿尔及利亚
		128	马达加斯加
		129	马拉维
		130	马来西亚
		131	马尔代夫
		132	马里
		133	马耳他
		134	马提尼克
		135	毛里塔尼亚
		136	毛里求斯
		137	墨西哥
		138	摩纳哥
		139	蒙古
		140	摩尔多瓦
		141	蒙特塞拉特
		142	摩洛哥
		143	莫桑比克
		144	阿曼
		145	纳米比亚
		146	瑙鲁
		147	尼泊尔
		148	荷兰
		149	荷属安的列斯
		150	阿鲁巴
		151	新喀里多尼亚
		152	瓦努阿图
		153	新西兰
		154	纽埃
		155	诺福克岛
		156	挪威
		157	北马里亚纳
		158	美属太平洋各群岛((包括：中途岛、约翰斯顿岛、豪兰岛、贝克岛和威克岛等)
		159	密克罗尼西亚
		160	马绍尔群岛
		161	贝劳
		162	巴基斯坦
		163	巴拿马
		164	巴布亚新几内亚
		165	巴拉圭
		166	秘鲁
		167	菲律宾
		168	皮特凯恩群岛
		169	波兰
		170	葡萄牙
		171	几内亚比招
		172	东帝汶
		173	波多黎各
		174	卡塔尔
		175	留尼汪
		176	罗马尼亚
		177	俄罗斯
		178	卢旺达
		179	圣赫勒拿
		180	圣基茨和尼维斯
		181	安圭拉
		182	圣卢西亚
		183	圣皮埃尔和密克隆
		184	圣文森特和格林纳丁斯
		185	圣马力诺
		186	圣多美和普林西比
		187	沙特阿拉伯
		188	塞内加尔
		189	塞舌尔
		190	塞拉利昂
		191	新加坡
		192	斯洛伐克
		193	越南
		194	斯洛文尼亚
		195	索马里
		196	南非
		197	津巴布韦
		198	西班牙
		199	西撒哈拉
		200	苏丹
		201	苏里南
		202	斯瓦尔巴群岛
		203	斯威士兰
		204	瑞典
		205	瑞士
		206	叙利亚
		207	塔吉克斯坦
		208	泰国
		209	多哥
		210	托克劳
		211	汤加
		212	特立尼达和多巴哥
		213	阿联酋
		214	突尼斯
		215	土耳其
		216	土库存曼斯坦
		217	特克斯和凯科斯群岛
		218	图瓦卢
		219	乌干达
		220	乌克兰
		221	马其顿
		222	埃及
		223	英国
		224	坦桑尼亚
		225	美国
		226	美属维尔京群岛
		227	布基纳法索
		228	乌拉圭
		229	乌兹别克斯坦
		230	委内瑞拉
		231	瓦利斯和富图纳群岛
		232	西萨摩亚
		233	也门
		234	南斯拉夫
		235	赞比亚
		236	尼日利亚
	 **/	
	private static String gl(String gl){
		String ret = gl;
		if(gl.equals("1"))
			ret = "156";
		else if(gl.equals("2"))
			ret = "158";
		else if(gl.equals("3"))
			ret = "344";
		else if(gl.equals("4"))
			ret = "446";
		return ret;
	}
	
	private static String xz(String xz){
		String ret = "";
		try {
			 ret = DictMap.getCszm(xz);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return ret;
	}
	

}

