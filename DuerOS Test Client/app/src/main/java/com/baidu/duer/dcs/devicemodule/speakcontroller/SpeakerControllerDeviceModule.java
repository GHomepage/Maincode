
package com.baidu.duer.dcs.devicemodule.speakcontroller;

import com.baidu.duer.dcs.devicemodule.speakcontroller.message.AdjustVolumePayload;
import com.baidu.duer.dcs.devicemodule.speakcontroller.message.MuteChangedPayload;
import com.baidu.duer.dcs.devicemodule.speakcontroller.message.SetMutePayload;
import com.baidu.duer.dcs.devicemodule.speakcontroller.message.SetVolumePayload;
import com.baidu.duer.dcs.devicemodule.speakcontroller.message.VolumeStatePayload;
import com.baidu.duer.dcs.devicemodule.system.HandleDirectiveException;
import com.baidu.duer.dcs.devicemodule.system.HandleDirectiveException.ExceptionType;
import com.baidu.duer.dcs.framework.BaseDeviceModule;
import com.baidu.duer.dcs.framework.BaseMultiChannelMediaPlayer;
import com.baidu.duer.dcs.framework.IMessageSender;
import com.baidu.duer.dcs.framework.message.ClientContext;
import com.baidu.duer.dcs.framework.message.Directive;
import com.baidu.duer.dcs.framework.message.Event;
import com.baidu.duer.dcs.framework.message.Header;
import com.baidu.duer.dcs.framework.message.MessageIdHeader;
import com.baidu.duer.dcs.framework.message.Payload;


public class SpeakerControllerDeviceModule extends BaseDeviceModule {
    private final BaseMultiChannelMediaPlayer.ISpeakerController speakerController;

    public SpeakerControllerDeviceModule(BaseMultiChannelMediaPlayer.ISpeakerController speakerController,
                                         IMessageSender messageSender) {
        super(ApiConstants.NAMESPACE, messageSender);
        this.speakerController = speakerController;
    }

    @Override
    public ClientContext clientContext() {
        String namespace = getNameSpace();
        String name = ApiConstants.Events.VolumeState.NAME;
        Header header = new Header(namespace, name);
        long volume = (long) (speakerController.getVolume() * 100.0F);
        boolean mute = speakerController.getMute();
        VolumeStatePayload payload = new VolumeStatePayload(volume, mute);
        return new ClientContext(header, payload);
    }

    @Override
    public void handleDirective(Directive directive) throws HandleDirectiveException {
        Header header = directive.header;
        String name = header.getName();
        Payload payload = directive.getPayload();
        if (name.equals(ApiConstants.Directives.AdjustVolume.NAME)) {
            if (payload instanceof AdjustVolumePayload) {
                AdjustVolumePayload adjustVolumePayload = (AdjustVolumePayload) directive.getPayload();
                float increment = (float) adjustVolumePayload.getVolume() / 100.0F;
                float volume = speakerController.getVolume() + increment;
                volume = Math.min(1.0F, Math.max(volume, -1.0F));
                setVolume(volume);
            }
        } else if (name.equals(ApiConstants.Directives.SetVolume.NAME)) {
            if (payload instanceof SetVolumePayload) {
                SetVolumePayload setVolumePayload = (SetVolumePayload) payload;
                float volume = (float) setVolumePayload.getVolume() / 100.0F;
                this.setVolume(volume);
            }
        } else if (name.equals(ApiConstants.Directives.SetMute.NAME)) {
            if (payload instanceof SetMutePayload) {
                SetMutePayload setMutePayload = (SetMutePayload) payload;
                setMute(setMutePayload.getMute());
            }
        } else {
            String message = "SpeakerController cannot handle the directive";
            throw new HandleDirectiveException(ExceptionType.UNSUPPORTED_OPERATION, message);
        }
    }

    @Override
    public void release() {

    }

    private void setVolume(float volume) {
        speakerController.setVolume(volume);
        messageSender.sendEvent(volumeChangedEvent());
    }

    private void setMute(boolean mute) {
        speakerController.setMute(mute);
        messageSender.sendEvent(muteChangedEvent());
    }

    private Event volumeChangedEvent() {
        String nameSpace = getNameSpace();
        String name = ApiConstants.Events.VolumeChanged.NAME;
        MessageIdHeader header = new MessageIdHeader(nameSpace, name);
        VolumeStatePayload payload = new VolumeStatePayload(getVolume(), isMuted());
        return new Event(header, payload);
    }

    private Event muteChangedEvent() {
        String nameSpace = getNameSpace();
        String name = ApiConstants.Events.MuteChanged.NAME;
        MessageIdHeader header = new MessageIdHeader(nameSpace, name);
        MuteChangedPayload payload = new MuteChangedPayload(getVolume(), isMuted());
        return new Event(header, payload);
    }

    private long getVolume() {
        return (long) (speakerController.getVolume() * 100.0F);
    }

    private boolean isMuted() {
        return speakerController.getMute();
    }
}
