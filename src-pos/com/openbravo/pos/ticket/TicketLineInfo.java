//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2013 uniCenta
//    http://www.unicenta.com
//
//    This file is part of uniCenta oPOS
//
//    uniCenta oPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   uniCenta oPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.data.loader.SerializableWrite;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.StringUtils;
import java.io.*;
import java.util.Properties;

/**
 *
 * @author adrianromero
 */
public class TicketLineInfo implements SerializableWrite, SerializableRead, Serializable {

    private static final long serialVersionUID = 6608012948284450199L;
    private String m_sTicket;
    private int m_iLine;
    private double multiply;
    private double price;
    private TaxInfo tax;
    private Properties attributes;
    private String productid;
    private String attsetinstid;
    private String workshopTicket;
    private String collectedBy;
    private String notes;
    private double costPrice;
    private String reference;

    /** Creates new TicketLineInfo */
    public TicketLineInfo(String productid, double dMultiply, double dPrice, TaxInfo tax, Properties props, String workshopTicket, String collectedBy, String notes, double costPrice, String reference) {
        init(productid, null, dMultiply, dPrice, tax, props, workshopTicket, collectedBy, notes, costPrice, reference);
    }

    public TicketLineInfo(String productid, double dMultiply, double dPrice, TaxInfo tax, String workshopTicket, String collectedBy, String notes, double costPrice, String reference) {
        init(productid, null, dMultiply, dPrice, tax, new Properties(), workshopTicket, collectedBy, notes, costPrice, reference);
    }

    public TicketLineInfo(String productid, String productname, String producttaxcategory, double dMultiply, double dPrice, TaxInfo tax, String workshopTicket, String collectedBy, String notes, double costPrice, String reference) {
        Properties props = new Properties();
        props.setProperty("product.name", productname);
        props.setProperty("product.taxcategoryid", producttaxcategory);
        init(productid, null, dMultiply, dPrice, tax, props, workshopTicket, collectedBy, notes, costPrice, reference);
    }

    public TicketLineInfo(String productname, String producttaxcategory, double dMultiply, double dPrice, TaxInfo tax, String workshopTicket, String collectedBy, String notes, double costPrice, String reference) {

        Properties props = new Properties();
        props.setProperty("product.name", productname);
        props.setProperty("product.taxcategoryid", producttaxcategory);
        init(null, null, dMultiply, dPrice, tax, props, workshopTicket, collectedBy, notes, costPrice, reference);
    }

    public TicketLineInfo() {
        init(null, null, 0.0, 0.0, null, new Properties(), null, null, null, 0.0, null);
    }

    public TicketLineInfo(ProductInfoExt product, double dMultiply, double dPrice, TaxInfo tax, Properties attributes, String workshopTicket, String collectedBy, String notes, double costPrice) {

        String pid;

        if (product == null) {
            pid = null;
        } else {
            pid = product.getID();
// JDL 20.12.20 set product name to a default rather than blank    TO DO        
            attributes.setProperty("product.name", product.getName());
               attributes.setProperty("product.com", product.isCom() ? "true" : "false");
// ADDED JG 20.12.10 - Kitchen Print
	attributes.setProperty("product.kitchen", product.isKitchen() ? "true" : "false");
// ***
// ADDED JG 25.06.11 - IsService
	attributes.setProperty("product.service", product.isService() ? "true" : "false");
// ***      
// Added JDL 19.12.12 Variable Price Product
        attributes.setProperty("product.vprice", product.isVprice() ? "true" : "false");
//     
 
// Added JDL 09.02.132 
        attributes.setProperty("product.verpatrib", product.isVerpatrib() ? "true" : "false");
//

// Added JDL 09.04.13 - Amend JG 10 Oct 13
        if (product.getTextTip() != null) {
            attributes.setProperty("product.texttip", product.getTextTip());
        }
 
//
// Added JDL 25.05.13
        attributes.setProperty("product.warranty", product.getWarranty()? "true" : "false");        
//        
        
            if (product.getAttributeSetID() != null) {
                attributes.setProperty("product.attsetid", product.getAttributeSetID());
            }
            attributes.setProperty("product.taxcategoryid", product.getTaxCategoryID());
            if (product.getCategoryID() != null) {
                attributes.setProperty("product.categoryid", product.getCategoryID());
            }
        }
        init(pid, null, dMultiply, dPrice, tax, attributes, workshopTicket, collectedBy, notes, costPrice, product.m_sRef);
    }

    public TicketLineInfo(ProductInfoExt oProduct, double dPrice, TaxInfo tax, Properties attributes, String workshopTicket, String collectedBy, String notes, double costPrice) {
        this(oProduct, 1.0, dPrice, tax, attributes, workshopTicket, collectedBy, notes, costPrice);
    }

    public TicketLineInfo(TicketLineInfo line) {
        init(line.productid, line.attsetinstid, line.multiply, line.price, line.tax, (Properties) line.attributes.clone(), line.workshopTicket, line.collectedBy, line.notes, line.costPrice, line.reference);
    }

    private void init(String productid, String attsetinstid, double dMultiply, double dPrice, TaxInfo tax, Properties attributes, String workshopTicket, String collectedBy, String notes, double costPrice, String reference) {

        this.productid = productid;
        this.attsetinstid = attsetinstid;
        multiply = dMultiply;
        price = dPrice;
        this.tax = tax;
        this.attributes = attributes;
        this.workshopTicket = workshopTicket;
        this.collectedBy = collectedBy;
        this.notes = notes;
        this.costPrice = costPrice;
        this.reference = reference;

        m_sTicket = null;
        m_iLine = -1;
    }

    void setTicket(String ticket, int line) {
        m_sTicket = ticket;
        m_iLine = line;
    }

    @Override
    public void writeValues(DataWrite dp) throws BasicException {
        dp.setString(1, m_sTicket);
        dp.setInt(2, new Integer(m_iLine));
        dp.setString(3, productid);
        dp.setString(4, attsetinstid);

        dp.setDouble(5, new Double(multiply));
        dp.setDouble(6, new Double(price));

        dp.setString(7, tax.getId());
        try {
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            attributes.storeToXML(o, AppLocal.APP_NAME, "UTF-8");
            dp.setBytes(8, o.toByteArray());
        } catch (IOException e) {
            dp.setBytes(8, null);
        }
        
        //Charles Bell: write workshop data
        dp.setString(9, workshopTicket);
        dp.setString(10, collectedBy);
        dp.setString(11, notes);
        dp.setDouble(12, new Double(costPrice));
        dp.setString(13, reference);
    }

    @Override
    public void readValues(DataRead dr) throws BasicException {
        m_sTicket = dr.getString(1);
        m_iLine = dr.getInt(2).intValue();
        productid = dr.getString(3);
        attsetinstid = dr.getString(4);
        multiply = dr.getDouble(5);
        price = dr.getDouble(6);
        tax = new TaxInfo(
                dr.getString(7), 
                dr.getString(8), 
                dr.getString(9), 
                dr.getString(10), 
                dr.getString(11), 
                dr.getDouble(12), 
                dr.getBoolean(13), 
                dr.getInt(14));
        attributes = new Properties();
        try {
            byte[] img = dr.getBytes(15);
            if (img != null) {
                attributes.loadFromXML(new ByteArrayInputStream(img));
            }
        } catch (IOException e) {
        }
        workshopTicket = dr.getString(16);
        collectedBy = dr.getString(17);
        notes = dr.getString(18);
        costPrice = dr.getDouble(19);
        reference = dr.getString(20);
    }

    public TicketLineInfo copyTicketLine() {
        TicketLineInfo l = new TicketLineInfo();
        // l.m_sTicket = null;
        // l.m_iLine = -1;
        l.productid = productid;
        l.attsetinstid = attsetinstid;
        l.multiply = multiply;
        l.price = price;
        l.tax = tax;
        l.workshopTicket = workshopTicket;
        l.collectedBy = collectedBy;
        l.notes = notes;
        l.costPrice = costPrice;
        l.reference = reference;
        l.attributes = (Properties) attributes.clone();
        return l;
    }

    //Charles Bell: adding items for workshop tracking.
    public String getWorkshopTicket()
    {
        return workshopTicket;
    }
    
    public void setWorkshopTicket(String value)
    {
        workshopTicket = value;
    }
    
    public String getCollectedBy()
    {
        return collectedBy;
    }
    
    public void setCollectedBy(String value)
    {
        collectedBy = value;
    }
    
    public String getNotes()
    {
        return notes;
    }
    
    public void setNotes(String value)
    {
        notes = value;
    }
    
    public double getCostPrice()
    {
        return costPrice;
    }
    
    public void setCostPrice(double value)
    {
        costPrice = value;
    }
    
    //**
    
    public int getTicketLine() {
        return m_iLine;
    }

    public String getProductID() {
        return productid;
    }

    public String getProductName() {
        return attributes.getProperty("product.name");
    }

    public String getProductAttSetId() {
        return attributes.getProperty("product.attsetid");
    }

    public String getProductAttSetInstDesc() {
        return attributes.getProperty("product.attsetdesc", "");
    }

    public void setProductAttSetInstDesc(String value) {
        if (value == null) {
            attributes.remove(value);
        } else {
            attributes.setProperty("product.attsetdesc", value);
        }
    }
    

    public String getProductAttSetInstId() {
        return attsetinstid;
    }

    public void setProductAttSetInstId(String value) {
        attsetinstid = value;
    }

    public boolean isProductCom() {
        return "true".equals(attributes.getProperty("product.com"));
    }

    public String getProductTaxCategoryID() {
        return (attributes.getProperty("product.taxcategoryid"));
    }

    public void setProductTaxCategoryID(String taxID){
        attributes.setProperty("product.taxcategoryid",taxID);
    }
    
    public String getProductCategoryID() {
        return (attributes.getProperty("product.categoryid"));
    }

    public double getMultiply() {
        return multiply;
    }

    public void setMultiply(double dValue) {
        multiply = dValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double dValue) {
        price = dValue;
    }

    public double getPriceTax() {
        return price * (1.0 + getTaxRate());
    }

    public void setPriceTax(double dValue) {
        price = dValue / (1.0 + getTaxRate());
    }

    public TaxInfo getTaxInfo() {
        return tax;
    }

    public void setTaxInfo(TaxInfo value) {
        tax = value;
    }

    public String getProperty(String key) {
        return attributes.getProperty(key);
    }

    public String getProperty(String key, String defaultvalue) {
        return attributes.getProperty(key, defaultvalue);
    }

    public void setProperty(String key, String value) {
        attributes.setProperty(key, value);
    }

    public Properties getProperties() {
        return attributes;
    }

    public double getTaxRate() {
        return tax == null ? 0.0 : tax.getRate();
    }

    public double getSubValue() {
        return price * multiply;
    }

  
    
    public double getTax() {
        return price * multiply * getTaxRate();
    }

    public double getValue() {
        return price * multiply * (1.0 + getTaxRate());
    }

    public String printName() {
        //CB: putting in catch to ensure reference (e.g. GC0001) is printed on receipt.
        String productName = StringUtils.encodeXML(attributes.getProperty("product.name"));
        if (productName.startsWith(reference) == false)
        {
            return reference + " - " + productName;
        }
        return productName;
    }

    public String printMultiply() {
        return Formats.DOUBLE.formatValue(multiply);
    }

    public String printPrice() {
        return Formats.CURRENCY.formatValue(getPrice());
    }

    public String printPriceTax() {
        return Formats.CURRENCY.formatValue(getPriceTax());
    }

    public String printTax() {
        return Formats.CURRENCY.formatValue(getTax());
    }

    public String printTaxRate() {
        return Formats.PERCENT.formatValue(getTaxRate());
    }

    public String printSubValue() {
        return Formats.CURRENCY.formatValue(getSubValue());
    }

    public String printValue() {
        return Formats.CURRENCY.formatValue(getValue());
    }
// ADDED JG 20.12.10 - Kitchen Print
public boolean isProductKitchen() {
	return "true".equals(attributes.getProperty("product.kitchen"));
}
// ***
// ADDED JG 25.06.11 - Is Service
public boolean isProductService() {
	return "true".equals(attributes.getProperty("product.service"));
}
// Added JDL 19.12.12 - Variable price product
public boolean isProductVprice() {
	return "true".equals(attributes.getProperty("product.vprice"));
//

}

// Added JDL 09.02.13 for Chris
public boolean isProductVerpatrib() {
	return "true".equals(attributes.getProperty("product.verpatrib"));
//

}

// Added JDL 09.04.12 - Variable price product
public String printTextTip() {
	return attributes.getProperty("product.texttip");
//

}

// Added JDL 09.02.13 
public boolean isProductWarranty() {
	return "true".equals(attributes.getProperty("product.warranty"));
//

}


}
