package io.stoneoven.campfire.modules.chat.event;

import lombok.extern.slf4j.Slf4j;
import me.iqpizza6349.midnight.event.GeneralEvent;
import me.iqpizza6349.midnight.event.listener.AbstractEventListener;
import me.iqpizza6349.midnight.event.midnight.MidnightReadyEvent;

@Slf4j
@SuppressWarnings("unused")
public class BotReadyEventListener extends AbstractEventListener {

    @Override
    public void onEvent(GeneralEvent generalEvent) {
        if (generalEvent instanceof MidnightReadyEvent) {
            log.info("{} is ready to join!", generalEvent.getAuditing().getBotName());
        }
    }
}
