/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bad;
import java.io.IOException;
import javax.swing.JFrame;
/**
 *
 * @author David
 */
public class Bad {

    public Bad(){
    
    }
    
   public static void main(String[] args) throws IOException {
        Breaking variable;
        variable = new Breaking();
        variable.setVisible(true);
        variable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
