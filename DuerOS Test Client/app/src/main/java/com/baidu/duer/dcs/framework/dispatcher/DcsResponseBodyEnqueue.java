
package com.baidu.duer.dcs.framework.dispatcher;

import com.baidu.duer.dcs.framework.DialogRequestIdHandler;
import com.baidu.duer.dcs.framework.message.AttachedContentPayload;
import com.baidu.duer.dcs.framework.message.DcsResponseBody;
import com.baidu.duer.dcs.framework.message.DialogRequestIdHeader;
import com.baidu.duer.dcs.framework.message.Directive;
import com.baidu.duer.dcs.framework.message.Header;
import com.baidu.duer.dcs.framework.message.Payload;
import com.baidu.duer.dcs.util.LogUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class DcsResponseBodyEnqueue {
    private static final String TAG = DcsResponseBodyEnqueue.class.getSimpleName();
    private final DialogRequestIdHandler dialogRequestIdHandler;
    private final Queue<DcsResponseBody> dependentQueue;
    private final Queue<DcsResponseBody> independentQueue;
    private final Queue<DcsResponseBody> incompleteResponseQueue;
    private final Map<String, AudioData> audioDataMap;

    public DcsResponseBodyEnqueue(DialogRequestIdHandler dialogRequestIdHandler,
                                  Queue<DcsResponseBody> dependentQueue,
                                  Queue<DcsResponseBody> independentQueue) {
        this.dialogRequestIdHandler = dialogRequestIdHandler;
        this.dependentQueue = dependentQueue;
        this.independentQueue = independentQueue;
        incompleteResponseQueue = new LinkedList<>();
        audioDataMap = new HashMap<>();
    }

    public synchronized void handleResponseBody(DcsResponseBody responseBody) {
        incompleteResponseQueue.add(responseBody);
        matchAudioDataWithResponseBody();
    }

    public synchronized void handleAudioData(AudioData audioData) {
        audioDataMap.put(audioData.contentId, audioData);
        matchAudioDataWithResponseBody();
    }

    private void matchAudioDataWithResponseBody() {
        for (DcsResponseBody responseBody : incompleteResponseQueue) {
            Directive directive = responseBody.getDirective();
            if (directive == null) {
                return;
            }

            Payload payload = responseBody.getDirective().payload;
            if (payload instanceof AttachedContentPayload) {
                AttachedContentPayload attachedContentPayload = (AttachedContentPayload) payload;
                String contentId = attachedContentPayload.getAttachedContentId();
                AudioData audioData = audioDataMap.remove(contentId);
                if (audioData != null) {
                    attachedContentPayload.setAttachedContent(contentId, audioData.dcsStream);
                }
            }
        }

        findCompleteResponseBody();
    }

    private void findCompleteResponseBody() {
        Iterator<DcsResponseBody> iterator = incompleteResponseQueue.iterator();
        while (iterator.hasNext()) {
            DcsResponseBody responseBody = iterator.next();
            Payload payload = responseBody.getDirective().payload;
            if (payload instanceof AttachedContentPayload) {
                AttachedContentPayload attachedContentPayload = (AttachedContentPayload) payload;

                if (!attachedContentPayload.requiresAttachedContent()) {
                    // The front most directive IS complete.
                    enqueueResponseBody(responseBody);
                    iterator.remove();
                } else {
                    break;
                }
            } else {
                // Immediately enqueue any directive which does not contain audio content
                enqueueResponseBody(responseBody);
                iterator.remove();
            }
        }
    }

    private void enqueueResponseBody(DcsResponseBody responseBody) {
        LogUtil.d(TAG, "DcsResponseBodyEnqueue-RecordThread:" + responseBody.getDirective().rawMessage);
        Header header = responseBody.getDirective().header;
        DialogRequestIdHeader dialogRequestIdHeader = (DialogRequestIdHeader) header;
        if (dialogRequestIdHeader.getDialogRequestId() == null) {
            independentQueue.add(responseBody);
        } else if (dialogRequestIdHandler.isActiveDialogRequestId(dialogRequestIdHeader.getDialogRequestId())) {
            dependentQueue.add(responseBody);
        }
    }
}
