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

package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.ticket.TicketLineInfo;

/**
 *
 * @author adrianromero
 */
public class JProductLineEdit extends javax.swing.JDialog {
    
    private TicketLineInfo returnLine;
    private TicketLineInfo m_oLine;
    private boolean m_bunitsok;
    private boolean m_bpriceok;
            
    /** Creates new form JProductLineEdit */
    private JProductLineEdit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }
    /** Creates new form JProductLineEdit */
    private JProductLineEdit(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
    }
    
    private TicketLineInfo init(AppView app, TicketLineInfo oLine) throws BasicException {
        // Inicializo los componentes
        initComponents();

        if (oLine.getTaxInfo() == null) {
            throw new BasicException(AppLocal.getIntString("message.cannotcalculatetaxes"));
        }

        m_oLine = new TicketLineInfo(oLine);
        m_bunitsok = true;
        m_bpriceok = true;
        
        m_jName.setEditable(false);
        m_jExternalRef.setEditable(false);
        //m_jName.setEnabled(m_oLine.getProductID() == null && app.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
        //CB: don't edit raw price
        //m_jPrice.setEnabled(app.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
        m_jPriceTax.setEnabled(app.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
        
        m_jName.setText(m_oLine.getProperty("product.name"));
        m_jUnits.setDoubleValue(oLine.getMultiply());
        
        m_jPriceTax.setDoubleValue(oLine.getPriceTax());
        
        m_jName.addPropertyChangeListener("Edition", new RecalculateName());
        m_jUnits.addPropertyChangeListener("Edition", new RecalculateUnits());
        m_jPriceTax.addPropertyChangeListener("Edition", new RecalculatePriceTax());

        m_jUnits.addEditorKeys(m_jKeys);
        
        m_jPriceTax.addEditorKeys(m_jKeys);
        
        m_jWTicket.setText(oLine.getWorkshopTicket());
        m_jWTicket.addEditorKeys(m_jKeys);
        m_jWTicket.addPropertyChangeListener("Edition", new StoreWorkshopTicket());
        
        m_jCollectedBy.setText(oLine.getCollectedBy());
        m_jCollectedBy.addEditorKeys(m_jKeys);
        m_jCollectedBy.addPropertyChangeListener("Edition", new StoreCollectedBy());
        
        m_jNotes.setText(oLine.getNotes());
        m_jNotes.addEditorKeys(m_jKeys);
        m_jNotes.addPropertyChangeListener("Edition", new StoreNotes());
        
        m_jUnits.activate();
        
        printTotals();
        setExternalRef(m_oLine.getPrice());

        getRootPane().setDefaultButton(m_jButtonOK);   
        returnLine = null;
        setVisible(true);

      
        return returnLine;
    }
    
    private void setExternalRef(Object value)
    {
        try
        {
            long extRefPence = Math.round((double)value * 100);
            m_jExternalRef.setText("MIG-" + String.valueOf(extRefPence));     
        }
        catch (Exception ex)
        {
            m_jExternalRef.setText("MIG-0");
        }    
    }
    
    
    private void printTotals() {
        
        if (m_bunitsok && m_bpriceok) {
            m_jTotal.setText(m_oLine.printValue());
            m_jButtonOK.setEnabled(true);
       } else {
            m_jTotal.setText(null);
            m_jButtonOK.setEnabled(false);
        }
    }
    
    private class StoreWorkshopTicket implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt)
        {
            m_oLine.setWorkshopTicket(m_jWTicket.getText());
        }
    }
    
    private class StoreCollectedBy implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt)
        {
            m_oLine.setCollectedBy(m_jCollectedBy.getText());
        }
    }
    
    private class StoreNotes implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt)
        {
            m_oLine.setNotes(m_jNotes.getText());
        }
    }
    
    private class RecalculateUnits implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            Double value = m_jUnits.getDoubleValue();
            if (value == null || value == 0.0) {
                m_bunitsok = false;
            } else {
                m_oLine.setMultiply(value);
                m_bunitsok = true;                
            }

            printTotals();
        }
    }
    
    
    private class RecalculatePriceTax implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {

            Double value = m_jPriceTax.getDoubleValue();
            if (value == null || value == 0.0) {
                // m_jPriceTax.setValue(m_oLine.getPriceTax());
                m_bpriceok = false;
            } else {
                m_oLine.setPriceTax(value);
                m_bpriceok = true;
            }

            printTotals();
        }
    }   
    
    private class RecalculateName implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            m_oLine.setProperty("product.name", m_jName.getText());
        }
    }   
    
    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        } else if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        } else {
            return getWindow(parent.getParent());
        }
    }       
    
    public static TicketLineInfo showMessage(Component parent, AppView app, TicketLineInfo oLine) throws BasicException {
         
        Window window = getWindow(parent);
        
        JProductLineEdit myMsg;
        if (window instanceof Frame) { 
            myMsg = new JProductLineEdit((Frame) window, true);
        } else {
            myMsg = new JProductLineEdit((Dialog) window, true);
        }
        return myMsg.init(app, oLine);
    }        

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        m_jUnits = new com.openbravo.editor.JEditorDouble();
        m_jPriceTax = new com.openbravo.editor.JEditorCurrency();
        jLabel6 = new javax.swing.JLabel();
        m_jTotal = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        m_jWTicket = new com.openbravo.editor.JEditorString();
        m_jCollectedBy = new com.openbravo.editor.JEditorString();
        m_jNotes = new com.openbravo.editor.JEditorString();
        m_jScrollPane = new javax.swing.JScrollPane();
        m_jName = new javax.swing.JTextArea();
        m_jExternalRef = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        m_jButtonCancel = new javax.swing.JButton();
        m_jButtonOK = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        m_jKeys = new com.openbravo.editor.JEditorKeys();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(AppLocal.getIntString("label.editline")); // NOI18N

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText(AppLocal.getIntString("label.prodpricebuy")); // NOI18N
        jPanel2.add(jLabel1);
        jLabel1.setBounds(10, 100, 90, 25);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText(AppLocal.getIntString("label.units")); // NOI18N
        jPanel2.add(jLabel2);
        jLabel2.setBounds(10, 140, 90, 25);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText(AppLocal.getIntString("label.prodpriceselltaxeach")); // NOI18N
        jPanel2.add(jLabel3);
        jLabel3.setBounds(10, 170, 100, 25);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText(AppLocal.getIntString("label.item")); // NOI18N
        jPanel2.add(jLabel4);
        jLabel4.setBounds(10, 10, 90, 25);

        m_jUnits.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel2.add(m_jUnits);
        m_jUnits.setBounds(130, 140, 240, 25);

        m_jPriceTax.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel2.add(m_jPriceTax);
        m_jPriceTax.setBounds(130, 170, 240, 25);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText(AppLocal.getIntString("label.notes")); // NOI18N
        jPanel2.add(jLabel6);
        jLabel6.setBounds(10, 290, 90, 25);

        m_jTotal.setBackground(javax.swing.UIManager.getDefaults().getColor("TextField.disabledBackground"));
        m_jTotal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        m_jTotal.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jTotal.setOpaque(true);
        m_jTotal.setPreferredSize(new java.awt.Dimension(150, 25));
        m_jTotal.setRequestFocusEnabled(false);
        jPanel2.add(m_jTotal);
        m_jTotal.setBounds(130, 200, 210, 25);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText(AppLocal.getIntString("label.totalcash")); // NOI18N
        jPanel2.add(jLabel8);
        jLabel8.setBounds(10, 200, 90, 25);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText(AppLocal.getIntString("label.workshopTicket")); // NOI18N
        jPanel2.add(jLabel9);
        jLabel9.setBounds(10, 230, 90, 25);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText(AppLocal.getIntString("label.collectedBy")); // NOI18N
        jPanel2.add(jLabel10);
        jLabel10.setBounds(10, 260, 90, 25);

        m_jWTicket.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        m_jWTicket.setText("");
        jPanel2.add(m_jWTicket);
        m_jWTicket.setBounds(100, 230, 270, 25);

        m_jCollectedBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel2.add(m_jCollectedBy);
        m_jCollectedBy.setBounds(100, 260, 270, 25);

        m_jNotes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel2.add(m_jNotes);
        m_jNotes.setBounds(100, 290, 270, 70);

        m_jName.setColumns(20);
        m_jName.setLineWrap(true);
        m_jName.setRows(5);
        m_jName.setWrapStyleWord(true);
        m_jName.setAutoscrolls(false);
        m_jScrollPane.setViewportView(m_jName);

        jPanel2.add(m_jScrollPane);
        m_jScrollPane.setBounds(100, 10, 244, 84);

        m_jExternalRef.setMinimumSize(new java.awt.Dimension(100, 25));
        jPanel2.add(m_jExternalRef);
        m_jExternalRef.setBounds(100, 100, 240, 30);

        jPanel5.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        m_jButtonCancel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/cancel.png"))); // NOI18N
        m_jButtonCancel.setText(AppLocal.getIntString("Button.Cancel")); // NOI18N
        m_jButtonCancel.setFocusPainted(false);
        m_jButtonCancel.setFocusable(false);
        m_jButtonCancel.setMargin(new java.awt.Insets(8, 16, 8, 16));
        m_jButtonCancel.setRequestFocusEnabled(false);
        m_jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jButtonCancelActionPerformed(evt);
            }
        });
        jPanel1.add(m_jButtonCancel);

        m_jButtonOK.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jButtonOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/ok.png"))); // NOI18N
        m_jButtonOK.setText(AppLocal.getIntString("Button.OK")); // NOI18N
        m_jButtonOK.setFocusPainted(false);
        m_jButtonOK.setFocusable(false);
        m_jButtonOK.setMargin(new java.awt.Insets(8, 16, 8, 16));
        m_jButtonOK.setRequestFocusEnabled(false);
        m_jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jButtonOKActionPerformed(evt);
            }
        });
        jPanel1.add(m_jButtonOK);

        jPanel5.add(jPanel1, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));
        jPanel4.add(m_jKeys);

        jPanel3.add(jPanel4, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.EAST);

        setSize(new java.awt.Dimension(580, 453));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void m_jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jButtonCancelActionPerformed

        dispose();

    }//GEN-LAST:event_m_jButtonCancelActionPerformed

    private void m_jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jButtonOKActionPerformed

        returnLine = m_oLine;
        
        dispose();

    }//GEN-LAST:event_m_jButtonOKActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JButton m_jButtonCancel;
    private javax.swing.JButton m_jButtonOK;
    private com.openbravo.editor.JEditorString m_jCollectedBy;
    private javax.swing.JTextField m_jExternalRef;
    private com.openbravo.editor.JEditorKeys m_jKeys;
    private javax.swing.JTextArea m_jName;
    private com.openbravo.editor.JEditorString m_jNotes;
    private com.openbravo.editor.JEditorCurrency m_jPriceTax;
    private javax.swing.JScrollPane m_jScrollPane;
    private javax.swing.JLabel m_jTotal;
    private com.openbravo.editor.JEditorDouble m_jUnits;
    private com.openbravo.editor.JEditorString m_jWTicket;
    // End of variables declaration//GEN-END:variables
    
}
