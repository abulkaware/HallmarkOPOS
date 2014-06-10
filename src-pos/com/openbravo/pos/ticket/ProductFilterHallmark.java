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
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.ListQBFModelNumber;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.reports.ReportEditorCreator;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

public class ProductFilterHallmark extends javax.swing.JPanel implements ReportEditorCreator {
    
    private SentenceList m_sentcat;
    private SentenceList m_suppliers;
    private ComboBoxValModel m_CategoryModel;
    private ComboBoxValModel m_SupplierModel;

    /** Creates new form JQBFProduct */
    public ProductFilterHallmark() {

        initComponents();
    }
    
    public void init(AppView app) {
         
        DataLogicSales dlSales = (DataLogicSales) app.getBean("com.openbravo.pos.forms.DataLogicSales");
        
        // El modelo de categorias
        m_sentcat = dlSales.getCategoriesList();
        m_CategoryModel = new ComboBoxValModel();          
         
//        m_jCboName.setModel(new ListQBFModelNumber());
//        m_jCboPriceBuy.setModel(new ListQBFModelNumber());
//        m_jCboPriceSell.setModel(new ListQBFModelNumber());
        m_suppliers = dlSales.getSuppliersList();
        m_SupplierModel = new ComboBoxValModel();
        
        m_jCboPriceBuy.setModel(ListQBFModelNumber.getMandatoryNumber());
        m_jCboPriceSell.setModel(ListQBFModelNumber.getMandatoryNumber());
    }
    
    public void activate() throws BasicException {

        List catlist = m_sentcat.list();
        catlist.add(0, null);
        m_CategoryModel = new ComboBoxValModel(catlist);
        m_jCategory.setModel(m_CategoryModel);
        
        List suppList = m_suppliers.list();
        suppList.add(0, null);
        m_SupplierModel = new ComboBoxValModel(suppList);
        m_jSupplier.setModel(m_SupplierModel);
    }
    
    public SerializerWrite getSerializerWrite() {
        return new SerializerWriteBasic(
                new Datas[] {Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING});
    }

    public Component getComponent() {
        return this;
    }
   
    public Object createValue() throws BasicException {
        
        List<Object> comparators = new ArrayList<Object>();
        
        if (m_jDescription.getText() == null || m_jDescription.getText().equals(""))
        {
            comparators.add(QBFCompareEnum.COMP_NONE);
            comparators.add(null);
        }
        else
        {
            comparators.add(QBFCompareEnum.COMP_RE);
            comparators.add("%" + m_jDescription.getText() + "%");
        }
        
        if (m_jCboPriceBuy.getSelectedIndex() < 1)
        {
            comparators.add(QBFCompareEnum.COMP_NONE);
            comparators.add(null);
        }
        else
        {
            comparators.add(m_jCboPriceBuy.getSelectedItem());
            comparators.add(Formats.CURRENCY.parseValue(m_jPriceBuy.getText()));
        }
        
        if (m_jCboPriceSell.getSelectedIndex() < 1)
        {
            comparators.add(QBFCompareEnum.COMP_NONE);
            comparators.add(null);
        }
        else
        {
            comparators.add(m_jCboPriceSell.getSelectedItem());
            comparators.add(Formats.CURRENCY.parseValue(m_jPriceSell.getText()));
        }
        
        if (m_CategoryModel.getSelectedKey() == null)
        {
            comparators.add(QBFCompareEnum.COMP_NONE);
            comparators.add(null);
        }
        else
        {
            comparators.add(QBFCompareEnum.COMP_EQUALS);
            comparators.add(m_CategoryModel.getSelectedKey());
        }
        
        if (m_SupplierModel.getSelectedKey() == null)
        {
            comparators.add(QBFCompareEnum.COMP_NONE);
            comparators.add(null);
        }
        else
        {
            comparators.add(QBFCompareEnum.COMP_EQUALS);
            comparators.add(m_SupplierModel.getSelectedKey());
        }
        
        return comparators.toArray();
        
        /*
        return new Object[] {
                 ? QBFCompareEnum.COMP_NONE, null : QBFCompareEnum.COMP_RE, "%" + m_jDescription.getText() + "%",
                m_jSupplier.getSelectedItem(),
                m_jCboPriceBuy.getSelectedItem(), Formats.CURRENCY.parseValue(m_jPriceBuy.getText()),           
                m_jCboPriceSell.getSelectedItem(), Formats.CURRENCY.parseValue(m_jPriceSell.getText()),
                m_CategoryModel.getSelectedKey() == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_EQUALS, m_CategoryModel.getSelectedKey(),
                QBFCompareEnum.COMP_NONE, null         
            };
        
        if (m_jDescription.getText() == null || m_jDescription.getText().equals("")) {
            // Filtro por formulario
            return new Object[] {
                m_jSupplier.getSelectedItem(), m_jName.getText(),
                m_jCboPriceBuy.getSelectedItem(), Formats.CURRENCY.parseValue(m_jPriceBuy.getText()),           
                m_jCboPriceSell.getSelectedItem(), Formats.CURRENCY.parseValue(m_jPriceSell.getText()),
                m_CategoryModel.getSelectedKey() == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_EQUALS, m_CategoryModel.getSelectedKey(),
                QBFCompareEnum.COMP_NONE, null         
            };
        } else {            
            // Filtro por codigo de barras.
            return new Object[] {
                QBFCompareEnum.COMP_NONE, null,
                QBFCompareEnum.COMP_NONE, null,
                QBFCompareEnum.COMP_NONE, null,
                QBFCompareEnum.COMP_NONE, null,
                QBFCompareEnum.COMP_RE, "%" + m_jDescription.getText() + "%"
            };
        }*/
    } 
 
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        m_jSupplier = new javax.swing.JComboBox();
        m_jPriceBuy = new javax.swing.JTextField();
        m_jCboPriceBuy = new javax.swing.JComboBox();
        m_jCboPriceSell = new javax.swing.JComboBox();
        m_jPriceSell = new javax.swing.JTextField();
        m_jCategory = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        m_jDescription = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(755, 117));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(AppLocal.getIntString("label.productfilter"))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Department");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText(AppLocal.getIntString("label.prodpricesell")); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText(AppLocal.getIntString("label.prodpricebuy")); // NOI18N

        m_jSupplier.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        m_jPriceBuy.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        m_jCboPriceBuy.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        m_jCboPriceSell.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        m_jPriceSell.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        m_jCategory.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Supplier");

        m_jDescription.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Enter text:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 7, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(m_jSupplier, 0, 245, Short.MAX_VALUE)
                    .addComponent(m_jDescription)
                    .addComponent(m_jCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(m_jCboPriceBuy, 0, 126, Short.MAX_VALUE)
                    .addComponent(m_jCboPriceSell, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(m_jPriceBuy, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jPriceSell, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jCboPriceBuy, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jPriceBuy, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jCboPriceSell, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jPriceSell, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        jPanel1.getAccessibleContext().setAccessibleName(bundle.getString("label.productfilter")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
   
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox m_jCategory;
    private javax.swing.JComboBox m_jCboPriceBuy;
    private javax.swing.JComboBox m_jCboPriceSell;
    private javax.swing.JTextField m_jDescription;
    private javax.swing.JTextField m_jPriceBuy;
    private javax.swing.JTextField m_jPriceSell;
    private javax.swing.JComboBox m_jSupplier;
    // End of variables declaration//GEN-END:variables
    
}
