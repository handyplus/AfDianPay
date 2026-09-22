package cn.handyplus.afdian.pay.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * 购买商品发奖励事件
 *
 * @author handy
 */
public class BuyShopGiveOutRewardsEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Integer afDianOrderId;

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public BuyShopGiveOutRewardsEvent(Integer afDianOrderId) {
        this.afDianOrderId = afDianOrderId;
    }

    /**
     * 获取爱发电订单ID
     *
     * @return 爱发电订单ID
     */
    public Integer getAfDianOrderId() {
        return afDianOrderId;
    }

}