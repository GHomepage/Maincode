
package com.baidu.duer.dcs.devicemodule.alerts;

public interface AlertHandler {
    void startAlert(String alertToken);

    void stopAlert(String alertToken);
}
