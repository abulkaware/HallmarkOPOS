/*
 * John L changes
 */
package com.openbravo.pos.forms;

import java.util.HashMap;

/**
 *
 * @author John L July 2013
 */
public class Payments {
    private Double amount;
    private Double tendered;
    private HashMap paymentPaid;
    private HashMap paymentTendered;
    private HashMap rtnMessage;
    private String name;



public Payments() {
    paymentPaid =  new HashMap();
    paymentTendered =  new HashMap();
    rtnMessage = new HashMap();
     
    }

public void addPayment (String pName, Double pAmountPaid, Double pTendered, String rtnMsg){
        if (paymentPaid.containsKey(pName)){
            paymentPaid.put(pName,Double.parseDouble(paymentPaid.get(pName).toString()) + pAmountPaid);
            paymentTendered.put(pName,Double.parseDouble(paymentTendered.get(pName).toString()) + pTendered); 
            rtnMessage.put(pName, rtnMsg);
        }else {    
            paymentPaid.put(pName, pAmountPaid);
            paymentTendered.put(pName,pTendered);
            rtnMessage.put(pName, rtnMsg);
        }        
}


public Double getTendered (String pName){
    return(Double.parseDouble(paymentTendered.get(pName).toString()));
}

public Double getPaidAmount (String pName){
    return(Double.parseDouble(paymentPaid.get(pName).toString()));
}

public Integer getSize(){
    return (paymentPaid.size());
}

public String getRtnMessage(String pName){
    return (rtnMessage.get(pName).toString());
}

public String getFirstElement(){
    String rtnKey= paymentPaid.keySet().iterator().next().toString();
    return(rtnKey);
}

public void removeFirst (String pName){
   paymentPaid.remove(pName);
   paymentTendered.remove(pName);
   rtnMessage.remove(pName);
}

}