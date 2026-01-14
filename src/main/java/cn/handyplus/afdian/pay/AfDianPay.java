package cn.handyplus.afdian.pay;

import cn.handyplus.afdian.pay.hook.PlaceholderUtil;
import cn.handyplus.afdian.pay.job.QueryOrderJob;
import cn.handyplus.afdian.pay.util.AfDianUtil;
import cn.handyplus.afdian.pay.util.ConfigUtil;
import cn.handyplus.lib.InitApi;
import cn.handyplus.lib.constants.HookPluginEnum;
import cn.handyplus.lib.util.BaseUtil;
import cn.handyplus.lib.util.HookPluginUtil;
import cn.handyplus.lib.util.MessageUtil;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 主类
 *
 * @author handy
 */
public class AfDianPay extends JavaPlugin {
    public static AfDianPay INSTANCE;
    public static PlayerPoints PLAYER_POINTS;
    public static boolean USE_PAPI;

    @Override
    public void onEnable() {
        INSTANCE = this;
        // 初始化
        InitApi initApi = InitApi.getInstance(this);
        // 加载 配置
        ConfigUtil.init();
        // 加载 PlaceholderApi
        USE_PAPI = HookPluginUtil.hook(HookPluginEnum.PLACEHOLDER_API);
        if (USE_PAPI) {
            new PlaceholderUtil(this).register();
        }
        // 加载 PlayerPoints
        HookPluginUtil.hookToPlugin(HookPluginEnum.PLAYER_POINTS).ifPresent(value -> PLAYER_POINTS = (PlayerPoints) value);

        // 初始化
        initApi.initCommand("cn.handyplus.afdian.pay.command")
                .initListener("cn.handyplus.afdian.pay.listener")
                .enableSql("cn.handyplus.afdian.pay.entity")
                .addMetrics(17625)
                .checkVersion();

        // 初始化域名
        AfDianUtil.init();

        // 初始化定时任务
        QueryOrderJob.init();

        MessageUtil.sendConsoleMessage(ChatColor.GREEN + "已成功载入服务器!");
        MessageUtil.sendConsoleMessage(ChatColor.GREEN + "Author:handy WIKI: https://ricedoc.handyplus.cn/wiki/AfDianPay/README");
    }

    @Override
    public void onDisable() {
        InitApi.disable();
    }

}