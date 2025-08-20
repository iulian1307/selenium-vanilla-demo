package com.qaitsolutions.selenium.wrapper.wait;

import com.qaitsolutions.pframe.core.logging.Log;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Sleep {

    public static void forMilliseconds(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Log.warn("Sleep failed", e);
        }
    }

    public static void forSeconds(int seconds) {
        Log.info("Sleeping for seconds [%s]", seconds);
        forMilliseconds(seconds * 1000);
    }
}
