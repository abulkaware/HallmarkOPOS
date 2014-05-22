//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2013 uniCenta & previous Openbravo POS works
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

package com.openbravo.pos.customers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.pos.forms.DataLogicSales;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SupplierStockItem {

    String supplierId;
    String productCode;
    String supplierCode;
    String description;
    int stockLevel;

    public SupplierStockItem() {
    }

    public SupplierStockItem(String siId, String productCode, String supplierCode, String desc, int stockLevel) {
        this.supplierId = siId;
        this.productCode = productCode;
        this.description = desc;
        this.stockLevel = stockLevel;
        this.supplierCode = supplierCode;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String siId) {
        this.supplierId = siId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int level) {
        this.stockLevel = level;
    }

    public static SerializerRead getSerializerRead() {
        return new SerializerRead() {

            @Override
            public Object readValues(DataRead dr) throws BasicException {

                String supplierId = dr.getString(1);
                String productCode = dr.getString(2);
                String supplierCode = dr.getString(3);
                String description = dr.getString(4);
                int stockLevel = dr.getInt(5);

                return new SupplierStockItem(supplierId, productCode, supplierCode, description, stockLevel);
            }
        };
    }
}
