/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.bws.site;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

/**
 *
 * @author sku202
 */
class SmartRobot extends Robot {

    public SmartRobot() throws AWTException {
        super();
    }

    /*public void pressEnter() 
     {
     keyPress(KeyEvent.VK_ENTER);
     delay(50);
     keyRelease(KeyEvent.VK_ENTER);
     } */
    public void pasteClipboard() {
        keyPress(KeyEvent.VK_CONTROL);
        keyPress(KeyEvent.VK_A);
        keyPress(KeyEvent.VK_V);
        delay(50);
        keyRelease(KeyEvent.VK_CONTROL);
    }

    public void type(String text) {
        clearClipboard();
        writeToClipboard(text);
        pasteClipboard();
    }

    private void writeToClipboard(String s) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection(s);          
        clipboard.setContents(transferable, null);
    }

    private void clearClipboard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection("");          
        clipboard.setContents(transferable, null);
    }
}
