package cn.handyplus.afdian.pay.command.admin;

import cn.handyplus.afdian.pay.entity.AfDianOrder;
import cn.handyplus.afdian.pay.service.AfDianOrderService;
import cn.handyplus.lib.command.IHandyCommandEvent;
import cn.handyplus.lib.util.AssertUtil;
import cn.handyplus.lib.util.BaseUtil;
import cn.handyplus.lib.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Optional;

/**
 * 强制修改订单为完成
 *
 * @author handy
 */
public class DoneCommand implements IHandyCommandEvent {

    @Override
    public String command() {
        return "done";
    }

    @Override
    public String permission() {
        return "afDianPay.done";
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // 参数是否正常
        AssertUtil.notTrue(args.length < 2, sender, BaseUtil.getMsgNotColor("paramFailureMsg"));
        Optional<AfDianOrder> afDianOrderOptional = AfDianOrderService.getInstance().findByOrder(args[1]);
        if (!afDianOrderOptional.isPresent()) {
            MessageUtil.sendMessage(sender, BaseUtil.getMsgNotColor("noOrder").replace("${order}", args[1]));
            return;
        }
        // 订单状态处理
        AfDianOrderService.getInstance().updateDone(afDianOrderOptional.get().getId());
        MessageUtil.sendMessage(sender, BaseUtil.getMsgNotColor("opDone").replace("${order}", args[1]));
    }

}