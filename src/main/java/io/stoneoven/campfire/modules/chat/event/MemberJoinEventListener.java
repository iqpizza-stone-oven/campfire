package io.stoneoven.campfire.modules.chat.event;

import lombok.extern.slf4j.Slf4j;
import me.iqpizza6349.midnight.event.GeneralEvent;
import me.iqpizza6349.midnight.event.chat.join.MemberChannelJoinEvent;
import me.iqpizza6349.midnight.event.listener.AbstractEventListener;
import me.iqpizza6349.midnight.model.channel.Channel;

@Slf4j
@SuppressWarnings("unused")
public class MemberJoinEventListener extends AbstractEventListener {

    @Override
    public void onEvent(GeneralEvent generalEvent) {
        if (generalEvent instanceof MemberChannelJoinEvent event) {
            Channel channel = event.getChannel();
            log.info("{} has join to channel {}", event.getMember().getUsername(),
                    channel.getTopic());
            channel.sendMessage("Hello, " + event.getMember().getUsername());
        }
    }
}
