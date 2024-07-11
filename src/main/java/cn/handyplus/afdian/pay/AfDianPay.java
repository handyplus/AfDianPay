package cn.handyplus.afdian.pay;

import cn.handyplus.afdian.pay.hook.PlaceholderUtil;
import cn.handyplus.afdian.pay.job.QueryOrderJob;
import cn.handyplus.afdian.pay.util.ConfigUtil;
import cn.handyplus.lib.InitApi;
import cn.handyplus.lib.constants.BaseConstants;
import cn.handyplus.lib.util.BaseUtil;
import cn.handyplus.lib.util.MessageUtil;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
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
        // 加载配置
        ConfigUtil.init();
        // 加载PlaceholderApi
        this.loadPlaceholder();
        // 加载PlayerPoints
        this.loadPlayerPoints();

        // 初始化
        initApi.initCommand("cn.handyplus.afdian.pay.command")
                .initListener("cn.handyplus.afdian.pay.listener")
                .enableSql("cn.handyplus.afdian.pay.entity")
                .addMetrics(17625)
                .checkVersion(ConfigUtil.CONFIG.getBoolean(BaseConstants.IS_CHECK_UPDATE));

        // 初始化定时任务
        QueryOrderJob.init();

        MessageUtil.sendConsoleMessage(ChatColor.GREEN + "已成功载入服务器!");
        MessageUtil.sendConsoleMessage(ChatColor.GREEN + "Author:handy WIKI: https://ricedoc.handyplus.cn/wiki/AfDianPay/README");
    }

    @Override
    public void onDisable() {
        InitApi.disable();
    }

    /**
     * 加载Placeholder
     */
    public void loadPlaceholder() {
        if (Bukkit.getPluginManager().getPlugin(BaseConstants.PLACEHOLDER_API) != null) {
            new PlaceholderUtil(this).register();
            USE_PAPI = true;
            MessageUtil.sendConsoleMessage(BaseUtil.getMsgNotColor("placeholderAPISucceedMsg"));
            return;
        }
        USE_PAPI = false;
        MessageUtil.sendConsoleMessage(BaseUtil.getMsgNotColor("placeholderAPIFailureMsg"));
    }

    /**
     * 加载PlayerPoints
     */
    private void loadPlayerPoints() {
        if (Bukkit.getPluginManager().getPlugin(BaseConstants.PLAYER_POINTS) != null) {
            final Plugin plugin = this.getServer().getPluginManager().getPlugin(BaseConstants.PLAYER_POINTS);
            PLAYER_POINTS = (PlayerPoints) plugin;
            MessageUtil.sendConsoleMessage(BaseUtil.getMsgNotColor("playerPointsSucceedMsg"));
            return;
        }
        MessageUtil.sendConsoleMessage(BaseUtil.getMsgNotColor("playerPointsFailureMsg"));
    }
}