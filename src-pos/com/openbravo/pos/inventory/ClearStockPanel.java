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
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.panels.JPanelTable;

/**
 *
 * @author adrianromero
 */
public class ClearStockPanel extends JPanelTable {
    
    private ClearStockEditor jeditor;    
    
    /** Creates a new instance of JPanelDiaryEditor */
    public ClearStockPanel() {
    }
    
    @Override
    protected void init() {
        jeditor = new ClearStockEditor(app, dirty); 
    }
    
    @Override
    public ListProvider getListProvider() {
        return null;
    }
    
    @Override
    public SaveProvider getSaveProvider() {
        return null;      
    }
    
    @Override
    public EditorRecord getEditor() {
        return jeditor;
    }
    
    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.ClearStock");
    }     
    
        
    @Override
    public void activate() throws BasicException {
        jeditor.activate(); // primero activo el editor 
        super.activate();   // segundo activo el padre        
    } 
}
