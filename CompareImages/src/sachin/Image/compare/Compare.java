/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachin.Image.compare;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.im4java.core.CompareCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ArrayListErrorConsumer;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sku202
 */
public class Compare {

    CompareCmd compare = new CompareCmd();

    public static void main(String... ad) {
        File f = new File("C:\\Users\\sku202\\Desktop\\Current\\ScreenShotCapture\\output\\nexxus.com\\PROD");
        File[] fls = f.listFiles((File pathname) -> pathname.isFile() && pathname.getName().contains(".json"));
        JSONObject json = new Compare().go(new JSONManager().createComparingJSON(HelperClass.readJsonFromFile(fls[0].getAbsolutePath()), HelperClass.readJsonFromFile(fls[1].getAbsolutePath())));
        System.out.println(json);
    }

    public JSONObject go(JSONObject json) {
        try {
            new File(json.getString("diff")).mkdirs();
        } catch (JSONException ex) {
            Logger.getLogger(Compare.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < json.length(); i++) {
            try {
                String name = Integer.toString(i + 1);
                JSONObject js = json.getJSONObject(name);
                File file = new File(json.getString("diff"), name + ".png");
                js.put("matched", compareImages(js.getString("pic1"), js.getString("pic2"), file.getAbsolutePath()));
                js.put("differences", file.getAbsolutePath());
            } catch (JSONException ex) {
                Logger.getLogger(Compare.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return json;
    }

    private boolean compareImages(String exp, String cur, String diff) {
        IMOperation cmpOp = new IMOperation();
        cmpOp.metric("ae");
        cmpOp.addImage(exp);
        cmpOp.addImage(cur);
        cmpOp.addImage(diff);
        ArrayListErrorConsumer outputConsumer = new ArrayListErrorConsumer();
        compare.setErrorConsumer(outputConsumer);
        try {
            compare.run(cmpOp);
            return true;
        } catch (Exception ex) {
            List<String> stringArrayList = outputConsumer.getOutput();
            System.out.println(stringArrayList);
            return false;
        }
    }
}
