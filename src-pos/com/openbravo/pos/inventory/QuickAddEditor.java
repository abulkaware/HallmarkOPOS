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

package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.sales.TaxesLogic;
import com.openbravo.pos.ticket.CategoryInfo;
import com.openbravo.pos.ticket.ProductInfoExt;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author adrianromero
 */
public final class QuickAddEditor extends JPanel implements EditorRecord {

    
    private Object m_id;
    private DataLogicSales dlSales;

    /** Creates new form JEditProduct */
    public QuickAddEditor(DataLogicSales dlSales, DirtyManager dirty) {
        initComponents();
        
        this.dlSales = dlSales;
jProductCode.getDocument().addDocumentListener(new ProductCodeManager());
        
        writeValueEOF();
    }

    public void activate() throws BasicException {
        
    }

    @Override
    public void refresh() {
    }

    @Override
    public void writeValueEOF() {
        m_id = null;
        
    }
    @Override
    public void writeValueInsert() {

        m_id = UUID.randomUUID().toString();
        
   }
    @Override
    public void writeValueDelete(Object value) {

    }

    @Override
    public void writeValueEdit(Object value) {

    }

    @Override
    public Object createValue() throws BasicException {

        Object[] myprod = new Object[25];
        
        return myprod;
        
        
    }
    
    private boolean GetStockCode()
    {
        if (jProductCode.getText().length() == 6)
        {
            try
            {
                ProductInfoExt prod = dlSales.getProductInfoByReference(jProductCode.getText().toUpperCase());
                if (prod == null) {
                    JOptionPane.showMessageDialog(this, "Product id " + jProductCode.getText().toUpperCase() + " not found.");
                }
                else
                {
                    int dialogResult = JOptionPane.showConfirmDialog(this, "Add one " + prod.getName() + " to stock?");
                    if (dialogResult == JOptionPane.YES_OPTION)
                    {
                        
                        dlSales.addOneStock(prod.getID());
                        JOptionPane.showMessageDialog(this, "Stock level updated.");
                    }
                }
                Runnable doClearText = new Runnable() {
                    @Override
                    public void run() {
                        jProductCode.setText(null);
                    }
                };   
                SwingUtilities.invokeLater(doClearText);
            }
            catch (BasicException eData) {
            
            
            }
        }
        return false;
    }
    
    private class ProductCodeManager implements DocumentListener {
        @Override
        public void changedUpdate(DocumentEvent e) {
                      
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
              if (GetStockCode())
              {
                  jProductCode.setText(null);
              }
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
                      
        }
    }

    @Override
    public Component getComponent() {
        return this;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jProductCode = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        jLabel24.setText("jLabel24");

        jLabel27.setText("jLabel27");

        jLabel23.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        jLabel23.setText(bundle.getString("label.prodminmax")); // NOI18N
        jLabel23.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jProductCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jProductCodeActionPerformed(evt);
            }
        });

        jLabel1.setText("Stock Code");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProductCode, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(345, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jProductCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(326, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jProductCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jProductCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jProductCodeActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JTextField jProductCode;
    // End of variables declaration//GEN-END:variables
    
}
