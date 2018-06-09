package geoquiz.android.bignerdranch.android.qr6;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus on 2018/4/20.
 */

public class qr_code extends BmobObject {
    private String text;

    public void setText(String text){
        this.text = text;
    }
}
