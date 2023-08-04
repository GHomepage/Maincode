
package com.baidu.duer.dcs.devicemodule.playbackcontroller;

public class ApiConstants {
    public static final String NAMESPACE = "ai.dueros.device_interface.playback_controller";
    public static final String NAME = "PlaybackControllerInterface";

    public static final class Events {
        public static final class NextCommandIssued {
            public static final String NAME = NextCommandIssued.class.getSimpleName();
        }

        public static final class PreviousCommandIssued {
            public static final String NAME = PreviousCommandIssued.class.getSimpleName();
        }

        public static final class PlayCommandIssued {
            public static final String NAME = PlayCommandIssued.class.getSimpleName();
        }

        public static final class PauseCommandIssued {
            public static final String NAME = PauseCommandIssued.class.getSimpleName();
        }
    }
}
