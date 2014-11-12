package util;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * @description 解析xml字符串
 */
public class DuXMLDoc {

    public void readStringXml(String xml) {
        Document doc = null;
        try {


            doc = DocumentHelper.parseText(xml); // 将字符串转为XML


            Element rootElt = doc.getRootElement(); // 获取根节点

            System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称


            Iterator iter = rootElt.elementIterator("response"); // 获取根节点下的子节点response

            
            // 遍历response节点

            while (iter.hasNext()) {
            	Element recordEle = (Element) iter.next();
                String ret_code = recordEle.elementTextTrim("ret_code"); // 拿到response节点下的子节点ret_code值

                System.out.println("ret_code:" + ret_code);
                
                String ret_info = recordEle.elementTextTrim("ret_info"); // 拿到response节点下的子节点ret_info值

                System.out.println("ret_info:" + ret_info);
               
              
                Iterator iters = recordEle.elementIterator("DIC_INFOR"); // 获取子节点response下的子节点DIC_INFOR


                // 遍历response节点下的DIC_INFOR节点

                while (iters.hasNext()) {

                    Element itemEle = (Element) iters.next();

                    String DIC_TYPE = itemEle.elementTextTrim("DIC_TYPE"); // 

                    String DIC_NAME = itemEle.elementTextTrim("DIC_NAME");

                    System.out.println("DIC_TYPE:" + DIC_TYPE);
                    System.out.println("DIC_NAME:" + DIC_NAME);
                }
				 Iterator iterss = recordEle.elementIterator("DIC_INFOR_LIST"); // 获取子节点response下的子节点DIC_INFOR_LIST


                // 遍历response节点下的DIC_INFOR_LIST节点

                while (iterss.hasNext()) {

                    Element itemEle = (Element) iterss.next();

                    String DIC_KEY = itemEle.elementTextTrim("DIC_KEY"); // 

                    String DIC_VALUE = itemEle.elementTextTrim("DIC_VALUE");

                    System.out.println("DIC_KEY:" + DIC_KEY);
                    System.out.println("DIC_VALUE:" + DIC_VALUE);
                }
            }
          
            
        } catch (DocumentException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    


}