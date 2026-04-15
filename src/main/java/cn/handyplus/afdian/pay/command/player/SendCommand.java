package cn.handyplus.afdian.pay.command.player;

import cn.handyplus.afdian.pay.util.ConfigUtil;
import cn.handyplus.lib.command.IHandyCommandEvent;
import cn.handyplus.lib.core.StrUtil;
import cn.handyplus.lib.util.AssertUtil;
import cn.handyplus.lib.util.BaseUtil;
import cn.handyplus.lib.util.MessageUtil;
import cn.handyplus.lib.util.RgbTextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 发送赞助链接
 *
 * @author handy
 */
public class SendCommand implements IHandyCommandEvent {

    @Override
    public String command() {
        return "send";
    }

    @Override
    public String permission() {
        return "afDianPay.send";
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        AssertUtil.notTrue(args.length < 2, BaseUtil.getLangMsg("paramFailureMsg"));
        Player player = AssertUtil.notPlayer(sender, BaseUtil.getLangMsg("noPlayerFailureMsg"));
        String url = ConfigUtil.SHOP_CONFIG.getString(args[1] + ".url");
        if (StrUtil.isEmpty(url)) {
            String noShopName = BaseUtil.getLangMsg("noShopName").replace("${shop}", args[1]);
            MessageUtil.sendMessage(player, noShopName);
            return;
        }
        String price = ConfigUtil.SHOP_CONFIG.getString(args[1] + ".price");
        // 发送提醒消息
        String oneMsg = BaseUtil.getLangMsg("oneMsg").replace("${shop}", args[1]);
        RgbTextUtil message = RgbTextUtil.init(oneMsg);
        String twoMsg = BaseUtil.getLangMsg("twoMsg").replace("${shop}", args[1]).replace("${price}", price);
        message.addExtra(RgbTextUtil.init("     " + twoMsg).addClickUrl(url));
        message.addExtra(RgbTextUtil.init(BaseUtil.getLangMsg("threeMsg")));
        message.send(sender);
    }

}