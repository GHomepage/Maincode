
package com.baidu.duer.dcs.devicemodule.voiceoutput;

public class ApiConstants {
    public static final String NAMESPACE = "ai.dueros.device_interface.voice_output";
    public static final String NAME = "VoiceOutputInterface"; //namespace和name确定这是什么指令

    public static final class Events {

        public static final class SpeechStarted {
            public static final String NAME = SpeechStarted.class.getSimpleName();
        }

        public static final class SpeechFinished {
            public static final String NAME = SpeechFinished.class.getSimpleName();
        }

        public static final class SpeechState {
            public static final String NAME = SpeechState.class.getSimpleName();
        }
    }



    public static final class Directives {

        public static final class Speak {
            public static final String NAME = Speak.class.getSimpleName();
        }
    }
}
