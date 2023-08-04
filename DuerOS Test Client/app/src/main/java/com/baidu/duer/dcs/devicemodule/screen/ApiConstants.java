package com.baidu.duer.dcs.devicemodule.screen;



public class ApiConstants {
    public static final String NAMESPACE = "ai.dueros.device_interface.screen";
    public static final String NAME = "ScreenInterface";
    public static final class Events {
        public static final class LinkClicked {
            public static final String NAME = LinkClicked.class.getSimpleName();
        }
    }


    public static final class Directives {

        public static final class HtmlView {
            public static final String NAME = HtmlView.class.getSimpleName();
        }

        public static final class RenderVoiceInputText {
            public static final String NAME = RenderVoiceInputText.class.getSimpleName();
        }

        public static final class RenderCard {
            public static final String NAME = RenderCard.class.getSimpleName();
        }

        public static final class RenderHint {
            public static final String NAME = RenderHint.class.getSimpleName();
        }
    }
}
