package geoquiz.android.bignerdranch.android.qr6;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus on 2018/4/20.
 */

public class Person  extends BmobObject {
    private String name;
    private String password;

    public String getsName() {
        return name;
    }

    public void setName(String sname) {
        this.name = sname;
    }

    public String getsPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
