
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="XmlText1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="XmlText2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "xmlText1",
    "xmlText2"
})
@XmlRootElement(name = "SetInfo")
public class SetInfo {

    @XmlElement(name = "XmlText1")
    protected String xmlText1;
    @XmlElement(name = "XmlText2")
    protected String xmlText2;

    /**
     * Gets the value of the xmlText1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlText1() {
        return xmlText1;
    }

    /**
     * Sets the value of the xmlText1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlText1(String value) {
        this.xmlText1 = value;
    }

    /**
     * Gets the value of the xmlText2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlText2() {
        return xmlText2;
    }

    /**
     * Sets the value of the xmlText2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlText2(String value) {
        this.xmlText2 = value;
    }

}
