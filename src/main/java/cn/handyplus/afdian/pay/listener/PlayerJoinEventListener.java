package cn.handyplus.afdian.pay.listener;

import cn.handyplus.lib.annotation.HandyListener;
import cn.handyplus.lib.util.HandyHttpUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * 玩家进入服务器事件.
 *
 * @author handy
 */
@HandyListener
public class PlayerJoinEventListener implements Listener {

    /**
     * op进入服务器发送更新提醒
     *
     * @param event 事件
     */
    @EventHandler
    public void onOpPlayerJoin(PlayerJoinEvent event) {
        HandyHttpUtil.checkVersion(event.getPlayer());
    }

}