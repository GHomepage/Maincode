package com.baidu.duer.dcs.devicemodule.audioplayer.message;

import com.baidu.duer.dcs.framework.message.Payload;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.InputStream;
import java.io.Serializable;


public class PlayPayload extends Payload {
    public PlayBehavior playBehavior;
    public AudioItem audioItem;

    public enum PlayBehavior {
        REPLACE_ALL,
        ENQUEUE,
        REPLACE_ENQUEUED
    }

    public static class AudioItem implements Serializable {
        public String audioItemId;
        public Stream stream;
    }

    public static class Stream implements Serializable {
        public String url;
        public String token;
        public String expiryTime;
        public long offsetInMilliseconds;
        public String expectedPreviousToken;
        public String streamFormat;
        public boolean urlIsAContentId;
        public ProgressReport progressReport;
        @JsonIgnore
        public InputStream attachedContent;

        public boolean getProgressReportRequired() {
            return progressReport != null && progressReport.isRequired();
        }

        public void setUrl(String url) {
            urlIsAContentId = url.startsWith("cid");
            if (urlIsAContentId) {
                this.url = url.substring(4);
            } else {
                this.url = url;
            }
        }

        public boolean requiresAttachedContent() {
            return urlIsAContentId && !hasAttachedContent();
        }

        public boolean hasAttachedContent() {
            return attachedContent != null;
        }

        @JsonIgnore
        public InputStream getAttachedContent() {
            return attachedContent;
        }
    }

    public static class ProgressReport implements Serializable {
        public long progressReportDelayInMilliseconds;
        public long progressReportIntervalInMilliseconds;

        public boolean isRequired() {
            return progressReportDelayInMilliseconds > 0 || progressReportIntervalInMilliseconds > 0;
        }
    }
}