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
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.util.StringUtils;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author  adrianromero
 */
public class CustomersView extends javax.swing.JPanel implements EditorRecord {

    private static final long serialVersionUID = 1L;
    private Object m_oId;
    
    private List<CustomerTransaction> customerTransactionList;
    private TransactionTableModel transactionModel;
    
    private DirtyManager m_Dirty;
    private DataLogicSales dlSales;
        
    /** Creates new form CustomersView */
    public CustomersView(AppView app, DirtyManager dirty) {
        try {
            dlSales = (DataLogicSales) app.getBean("com.openbravo.pos.forms.DataLogicSales");
        
//        DataLogicSales dlSales = (DataLogicSales) app.getBean("com.openbravo.pos.forms.DataLogicSales");
        
        initComponents(); 
        
        m_Dirty = dirty;
        m_jTaxID.getDocument().addDocumentListener(dirty);
        m_jSearchkey.getDocument().addDocumentListener(dirty);
        m_jName.getDocument().addDocumentListener(dirty);        
        m_jNotes.getDocument().addDocumentListener(dirty);
        //txtMaxdebt.getDocument().addDocumentListener(dirty);
        m_jVisible.addActionListener(dirty);
        
        txtFirstName.getDocument().addDocumentListener(dirty);
        txtLastName.getDocument().addDocumentListener(dirty);
        txtEmail.getDocument().addDocumentListener(dirty);
        txtPhone.getDocument().addDocumentListener(dirty);
        txtPhone2.getDocument().addDocumentListener(dirty);
//        txtFax.getDocument().addDocumentListener(dirty);
        m_jImage.addPropertyChangeListener(dirty);
        
        txtAddress.getDocument().addDocumentListener(dirty);
        txtAddress2.getDocument().addDocumentListener(dirty);
 //           txtAddress3.getDocument().addDocumentListener(dirty);
        txtPostal.getDocument().addDocumentListener(dirty);
        txtCity.getDocument().addDocumentListener(dirty);
        txtRegion.getDocument().addDocumentListener(dirty);
        txtCountry.getDocument().addDocumentListener(dirty);
        chkReceiveSMS.addActionListener(dirty);
        chkReceivePost.addActionListener(dirty);
        chkReceiveEmail.addActionListener(dirty);
        
            init();
        } catch (Exception ex) {
            Logger.getLogger(CustomersView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init() {
        writeValueEOF(); 
    }
    
    @SuppressWarnings("unchecked")
    public void activate() throws BasicException {
        customerTransactionList = dlSales.getCustomersTransactionList();
    }
    
    @Override
    public void refresh() {
    }
    
    @Override
    public void writeValueEOF() {
        m_oId = null;
        m_jTaxID.setText(null);
        m_jSearchkey.setText(null);
        m_jName.setText(null);
        m_jNotes.setText(null);

        //txtMaxdebt.setText(null);
        //txtCurdebt.setText(null);
        //txtCurdate.setText(null);
        m_jVisible.setSelected(false);
        //jcard.setText(null);
        
        txtFirstName.setText(null);
        txtLastName.setText(null);
        txtEmail.setText(null);
        txtPhone.setText(null);
        txtPhone2.setText(null);
//        txtFax.setText(null);
        m_jImage.setImage(null);
        
        chkReceiveSMS.setSelected(true);
        chkReceiveEmail.setSelected(true);
        chkReceivePost.setSelected(true);
       
        txtAddress.setText(null);
        txtAddress2.setText(null);
//       txtAddress3.setText(null);
        txtPostal.setText(null);
        txtCity.setText(null);
        txtRegion.setText(null);
        txtCountry.setText(null);
        jTextSMS.setText(null);
        jTextSMS.setEnabled(false);
        
        m_jTaxID.setEnabled(false);
        m_jSearchkey.setEnabled(false);
        m_jName.setEnabled(false);

        m_jVisible.setEnabled(false);
        
        txtFirstName.setEnabled(false);
        txtLastName.setEnabled(false);
        txtEmail.setEnabled(false);
        txtPhone.setEnabled(false);
        txtPhone2.setEnabled(false);
        m_jImage.setEnabled(false);
        chkReceiveSMS.setEnabled(false);
        chkReceiveEmail.setEnabled(false);
        chkReceivePost.setEnabled(false);
       
        txtAddress.setEnabled(false);
        txtAddress2.setEnabled(false);
        txtPostal.setEnabled(false);
        txtCity.setEnabled(false);
        txtRegion.setEnabled(false);
        txtCountry.setEnabled(false);
        
        jTable1.setEnabled(false);
    } 
    
    @Override
    public void writeValueInsert() {
        m_oId = null;
        m_jTaxID.setText(null);
        m_jSearchkey.setText(null);
        m_jName.setText(null);
        m_jNotes.setText(null);
        m_jVisible.setSelected(true);
        
        txtFirstName.setText(null);
        txtLastName.setText(null);
        txtEmail.setText(null);
        txtPhone.setText(null);
        txtPhone2.setText(null);
        m_jImage.setImage(null);
       
        txtAddress.setText(null);
        txtAddress2.setText(null);
        txtPostal.setText(null);
        txtCity.setText(null);
        txtRegion.setText(null);
        txtCountry.setText(null);
        chkReceiveSMS.setSelected(true);
        chkReceiveEmail.setSelected(true);
        chkReceivePost.setSelected(true);
        
        jTextSMS.setText(null);
        jTextSMS.setEnabled(true);
        
        m_jTaxID.setEnabled(true);
        m_jSearchkey.setEnabled(true);
        m_jName.setEnabled(false);
        m_jNotes.setEnabled(true);
        m_jVisible.setEnabled(true);
               
        txtFirstName.setEnabled(true);
        txtLastName.setEnabled(true);
        txtEmail.setEnabled(true);
        txtPhone.setEnabled(true);
        txtPhone2.setEnabled(true);
        m_jImage.setEnabled(true);
        chkReceiveSMS.setEnabled(true);
        chkReceiveEmail.setEnabled(true);
        chkReceivePost.setEnabled(true);
       
        txtAddress.setEnabled(true);
        txtAddress2.setEnabled(true);
        txtPostal.setEnabled(true);
        txtCity.setEnabled(true);
        txtRegion.setEnabled(true);
        txtCountry.setEnabled(true);
        

        jTable1.setEnabled(false);
    }

    @Override
    public void writeValueDelete(Object value) {

        Object[] customer = (Object[]) value;
        m_oId = customer[0];
        m_jTaxID.setText((String) customer[1]);
        m_jSearchkey.setText((String) customer[2]);
        m_jName.setText((String) customer[3]);
        m_jNotes.setText((String) customer[4]);
        m_jVisible.setSelected(((Boolean) customer[5]).booleanValue());
       
        
        txtFirstName.setText(Formats.STRING.formatValue(customer[10]));
        txtLastName.setText(Formats.STRING.formatValue(customer[11]));
        txtEmail.setText(Formats.STRING.formatValue(customer[12]));
        txtPhone.setText(Formats.STRING.formatValue(customer[13]));
        txtPhone2.setText(Formats.STRING.formatValue(customer[14]));
//        txtFax.setText(Formats.STRING.formatValue(customer[15]));
       
        txtAddress.setText(Formats.STRING.formatValue(customer[16]));
        txtAddress2.setText(Formats.STRING.formatValue(customer[17]));
        txtPostal.setText(Formats.STRING.formatValue(customer[18]));
        txtCity.setText(Formats.STRING.formatValue(customer[19]));
        txtRegion.setText(Formats.STRING.formatValue(customer[20]));
        txtCountry.setText(Formats.STRING.formatValue(customer[21]));      
        
// JG 3 Oct 2013 - Customer Image
        m_jImage.setImage((BufferedImage) customer[23]);
        chkReceiveSMS.setSelected(((Boolean) customer[24]).booleanValue());
        chkReceiveEmail.setSelected(((Boolean) customer[25]).booleanValue());
        chkReceivePost.setSelected(((Boolean) customer[26]).booleanValue());
        
        m_jTaxID.setEnabled(false);
        m_jSearchkey.setEnabled(false);
        m_jName.setEnabled(false);
        m_jNotes.setEnabled(false);
        m_jVisible.setEnabled(false);
        
        txtFirstName.setEnabled(false);
        txtLastName.setEnabled(false);
        txtEmail.setEnabled(false);
        txtPhone.setEnabled(false);
        txtPhone2.setEnabled(false);
        m_jImage.setEnabled(true);
        chkReceiveSMS.setEnabled(false);
        chkReceiveEmail.setEnabled(false);
        chkReceivePost.setEnabled(false);
       
        txtAddress.setEnabled(false);
        txtAddress2.setEnabled(false);
        txtPostal.setEnabled(false);
        txtCity.setEnabled(false);
        txtRegion.setEnabled(false);
        txtCountry.setEnabled(false);
        
        jTextSMS.setEnabled(false);
        

// JG 3 Oct 2013 - for Transaction List table
        transactionModel = new TransactionTableModel(getTransactionOfName((String) customer[3]));
        jTable1.setModel(transactionModel);
        jTable1.setEnabled(false);
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] customer = (Object[]) value;
        m_oId = customer[0];
        m_jTaxID.setText((String) customer[1]);
        m_jSearchkey.setText((String) customer[2]);
        m_jName.setText((String) customer[3]);
        m_jNotes.setText((String) customer[4]);
        m_jVisible.setSelected(((Boolean) customer[5]).booleanValue());
        
        txtFirstName.setText(Formats.STRING.formatValue(customer[10]));
        txtLastName.setText(Formats.STRING.formatValue(customer[11]));
        txtEmail.setText(Formats.STRING.formatValue(customer[12]));
        txtPhone.setText(Formats.STRING.formatValue(customer[13]));
        txtPhone2.setText(Formats.STRING.formatValue(customer[14]));
       
        txtAddress.setText(Formats.STRING.formatValue(customer[16]));
        txtAddress2.setText(Formats.STRING.formatValue(customer[17]));
        txtPostal.setText(Formats.STRING.formatValue(customer[18]));
        txtCity.setText(Formats.STRING.formatValue(customer[19]));
        txtRegion.setText(Formats.STRING.formatValue(customer[20]));
        txtCountry.setText(Formats.STRING.formatValue(customer[21]));   
        

// JG 3 Oct 2013 - Customer image
        m_jImage.setImage((BufferedImage) customer[23]);
        
        chkReceiveSMS.setSelected(((Boolean) customer[24]).booleanValue());
        chkReceiveEmail.setSelected(((Boolean) customer[25]).booleanValue());
        chkReceivePost.setSelected(((Boolean) customer[26]).booleanValue());
        
        m_jTaxID.setEnabled(true);
        m_jSearchkey.setEnabled(true);
        m_jName.setEnabled(false);
        m_jNotes.setEnabled(true);

        m_jVisible.setEnabled(true);
               
        txtFirstName.setEnabled(true);
        txtLastName.setEnabled(true);
        txtEmail.setEnabled(true);
        txtPhone.setEnabled(true);
        txtPhone2.setEnabled(true);

        m_jImage.setEnabled(true);
        chkReceiveSMS.setEnabled(true);
        chkReceiveEmail.setEnabled(true);
        chkReceivePost.setEnabled(true);
       
        txtAddress.setEnabled(true);
        txtAddress2.setEnabled(true);
        txtPostal.setEnabled(true);
        txtCity.setEnabled(true);
        txtRegion.setEnabled(true);
        txtCountry.setEnabled(true);
        jTextSMS.setEnabled(true);
        
  
        transactionModel = new TransactionTableModel(getTransactionOfName((String) customer[3]));
        jTable1.setModel(transactionModel);
        jTable1.setEnabled(true);
    }
    
    @Override
    public Object createValue() throws BasicException {
        Object[] customer = new Object[27];
        customer[0] = m_oId == null ? UUID.randomUUID().toString() : m_oId;
        customer[1] = m_jTaxID.getText();
        customer[2] = m_jSearchkey.getText();
        customer[3] = m_jName.getText();
        customer[4] = m_jNotes.getText();
        customer[5] = Boolean.valueOf(m_jVisible.isSelected());
        customer[6] = ""; // Format to manage NULL values
        customer[7] = new Double(0.0);
        customer[8] = Formats.TIMESTAMP.parseValue(""); // not saved
        customer[9] = new Double(0.0); // not saved
        
        customer[10] = Formats.STRING.parseValue(txtFirstName.getText());
        customer[11] = Formats.STRING.parseValue(txtLastName.getText());
        customer[12] = Formats.STRING.parseValue(txtEmail.getText());
        customer[13] = Formats.STRING.parseValue(txtPhone.getText());
        customer[14] = Formats.STRING.parseValue(txtPhone2.getText());
//        customer[15] = Formats.STRING.parseValue(txtFax.getText());
       
        customer[16] = Formats.STRING.parseValue(txtAddress.getText());
        customer[17] = Formats.STRING.parseValue(txtAddress2.getText());
        customer[18] = Formats.STRING.parseValue(txtPostal.getText());
        customer[19] = Formats.STRING.parseValue(txtCity.getText());
        customer[20] = Formats.STRING.parseValue(txtRegion.getText());
        customer[21] = Formats.STRING.parseValue(txtCountry.getText()); 
        
        customer[22] = null; //tax category
// JG 3 Oct 2013 - Customer image
        customer[23] = m_jImage.getImage();
        customer[24] = Boolean.valueOf(chkReceiveSMS.isSelected());
        customer[25] = Boolean.valueOf(chkReceiveEmail.isSelected());
        customer[26] = Boolean.valueOf(chkReceivePost.isSelected());
        
        return customer;
    }   
    
    @Override
    public Component getComponent() {
        return this;
    }
    
// JG 3 Oct 2013 - Customer Transaction List
    private List<CustomerTransaction> getTransactionOfName(String name) {

        List<CustomerTransaction> customerList = new ArrayList<>();

        for (CustomerTransaction customerTransaction : customerTransactionList) {
            String customerName = customerTransaction.getCustomerName();
            if (customerName.equals(name)) {
                customerList.add(customerTransaction);
            }
        }
        return customerList;
    }

    class TransactionTableModel extends AbstractTableModel {

        List<CustomerTransaction> transactionList;
        String[] columnNames = {"TicketID", "Date", "Product", "Quantity", "Total"};

        public TransactionTableModel(List<CustomerTransaction> list) {
            transactionList = list;
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public int getRowCount() {
            return transactionList.size();
        }

        // this method is called to set the value of each cell
        @Override
        public Object getValueAt(int row, int column) {
            CustomerTransaction customerTransaction = transactionList.get(row);
            switch (column) {

                case 0:
                    return customerTransaction.getTicketId();                    
                case 1:
                    Date transactionDate = customerTransaction.getTransactionDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String formattedDate = sdf.format(transactionDate);
                    return formattedDate;
                case 2:
                    return customerTransaction.getProductName();
                case 3:
                    return customerTransaction.getUnit();
                case 4:
                    Double amount = customerTransaction.getTotal();
                    DecimalFormat df = new DecimalFormat("#.##");                    
                    String formattedAmount = df.format(amount);
                    return formattedAmount;
                default:
                    return "";
            }
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }
    }    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        m_jTaxID = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        m_jSearchkey = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        m_jName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        m_jVisible = new javax.swing.JCheckBox();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtPhone2 = new javax.swing.JTextField();
        chkReceiveSMS = new javax.swing.JCheckBox();
        chkReceiveEmail = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtCountry = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtAddress2 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtPostal = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtCity = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtRegion = new javax.swing.JTextField();
        chkReceivePost = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        m_jImage = new com.openbravo.data.gui.JImageEditor();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        m_jNotes = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextSMS = new javax.swing.JTextArea();
        jButtonSendSMS = new javax.swing.JButton();
        jButtonClearSMS = new javax.swing.JButton();

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText(AppLocal.getIntString("label.taxid")); // NOI18N
        jLabel7.setMaximumSize(new java.awt.Dimension(140, 25));
        jLabel7.setMinimumSize(new java.awt.Dimension(140, 25));
        jLabel7.setPreferredSize(new java.awt.Dimension(140, 25));

        m_jTaxID.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText(AppLocal.getIntString("label.custsurname")); // NOI18N

        m_jSearchkey.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText(AppLocal.getIntString("label.custfullname")); // NOI18N
        jLabel3.setMaximumSize(new java.awt.Dimension(140, 25));
        jLabel3.setMinimumSize(new java.awt.Dimension(140, 25));
        jLabel3.setPreferredSize(new java.awt.Dimension(140, 25));

        m_jName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText(AppLocal.getIntString("label.visible")); // NOI18N
        jLabel4.setMaximumSize(new java.awt.Dimension(140, 25));
        jLabel4.setMinimumSize(new java.awt.Dimension(140, 25));
        jLabel4.setPreferredSize(new java.awt.Dimension(140, 25));

        m_jVisible.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jVisible.setSelected(true);

        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTabbedPane1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTabbedPane1PropertyChange(evt);
            }
        });

        jPanel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel19.setText(AppLocal.getIntString("label.firstname")); // NOI18N
        jLabel19.setAlignmentX(0.5F);

        txtFirstName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtFirstName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLastNameKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel15.setText(AppLocal.getIntString("label.lastname")); // NOI18N

        txtLastName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtLastName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLastNameKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel16.setText(AppLocal.getIntString("label.email")); // NOI18N

        txtEmail.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel17.setText(AppLocal.getIntString("label.phone")); // NOI18N

        txtPhone.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel18.setText(AppLocal.getIntString("label.phone2")); // NOI18N

        txtPhone2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        chkReceiveSMS.setText(bundle.getString("label.marketingSMS")); // NOI18N

        chkReceiveEmail.setText(bundle.getString("label.marketingEmail")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPhone2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkReceiveSMS)
                            .addComponent(chkReceiveEmail))))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkReceiveEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhone2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkReceiveSMS))
                .addGap(89, 89, 89))
        );

        jTabbedPane1.addTab(AppLocal.getIntString("label.contact"), jPanel1); // NOI18N

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText(AppLocal.getIntString("label.address")); // NOI18N

        txtAddress.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel20.setText(AppLocal.getIntString("label.country")); // NOI18N

        txtCountry.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel21.setText(AppLocal.getIntString("label.address2")); // NOI18N

        txtAddress2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel22.setText(AppLocal.getIntString("label.postal")); // NOI18N

        txtPostal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel23.setText(AppLocal.getIntString("label.city")); // NOI18N

        txtCity.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel24.setText(AppLocal.getIntString("label.region")); // NOI18N

        txtRegion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        chkReceivePost.setText(bundle.getString("label.marketingPost")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAddress)
                            .addComponent(txtAddress2)
                            .addComponent(txtCity, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(txtRegion)
                            .addComponent(txtCountry))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkReceivePost)
                        .addContainerGap(78, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkReceivePost))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab(AppLocal.getIntString("label.location"), jPanel2); // NOI18N

        jPanel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jTable1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "TicketID", "Date", "Product", "Quantity", "Total"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setOpaque(false);
        jTable1.setRowHeight(20);
        jTable1.setShowHorizontalLines(false);
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(bundle.getString("label.Transactions"), jPanel4); // NOI18N

        m_jImage.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(m_jImage, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(m_jImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(bundle.getString("label.photo"), jPanel5); // NOI18N

        m_jNotes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(m_jNotes);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(AppLocal.getIntString("label.notes"), jPanel3); // NOI18N

        jTextSMS.setColumns(20);
        jTextSMS.setRows(5);
        jScrollPane3.setViewportView(jTextSMS);

        jButtonSendSMS.setText(bundle.getString("label.btnSendSMS")); // NOI18N
        jButtonSendSMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendSMS_actionPerformed(evt);
            }
        });

        jButtonClearSMS.setText("Clear");
        jButtonClearSMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearSMSActionPerformed(evt);
            }
        });
        jButtonClearSMS.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jButton_ClearSMS(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButtonClearSMS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSendSMS))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSendSMS)
                    .addComponent(jButtonClearSMS))
                .addGap(0, 38, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(bundle.getString("label.tabCustomerSMS"), jPanel6); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(m_jVisible, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(383, 383, 383))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(m_jTaxID, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(m_jSearchkey, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(m_jName, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(m_jSearchkey, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jTaxID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jVisible, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1PropertyChange

    private void sendSMS_actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendSMS_actionPerformed
        if (txtPhone2.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "No mobile number is set for this customer.");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to send this message?", "Send SMS?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {            
            if (com.openbravo.beans.SMSUtils.SendText(jTextSMS.getText(), txtPhone2.getText()))
            {
                JOptionPane.showMessageDialog(this, "SMS sent.");
                jTextSMS.setText(null);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Error sending SMS message.");
            }
        }
    }//GEN-LAST:event_sendSMS_actionPerformed

    private void jButton_ClearSMS(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jButton_ClearSMS
        jTextSMS.setText(null);
    }//GEN-LAST:event_jButton_ClearSMS

    private void jButtonClearSMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearSMSActionPerformed
        jTextSMS.setText("");
    }//GEN-LAST:event_jButtonClearSMSActionPerformed

    private void txtLastNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLastNameKeyTyped
        this.m_jName.setText(this.txtFirstName.getText() + " " + this.txtLastName.getText());
    }//GEN-LAST:event_txtLastNameKeyTyped
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkReceiveEmail;
    private javax.swing.JCheckBox chkReceivePost;
    private javax.swing.JCheckBox chkReceiveSMS;
    private javax.swing.JButton jButtonClearSMS;
    private javax.swing.JButton jButtonSendSMS;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextSMS;
    private com.openbravo.data.gui.JImageEditor m_jImage;
    private javax.swing.JTextField m_jName;
    private javax.swing.JTextArea m_jNotes;
    private javax.swing.JTextField m_jSearchkey;
    private javax.swing.JTextField m_jTaxID;
    private javax.swing.JCheckBox m_jVisible;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtAddress2;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextField txtCountry;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPhone2;
    private javax.swing.JTextField txtPostal;
    private javax.swing.JTextField txtRegion;
    // End of variables declaration//GEN-END:variables
    
}
