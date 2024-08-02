package cn.handyplus.afdian.pay.util;

import cn.handyplus.afdian.pay.constants.AfDianPayConstants;
import cn.handyplus.lib.constants.BaseConstants;
import cn.handyplus.lib.util.HandyConfigUtil;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;

/**
 * 配置
 *
 * @author handy
 */
public class ConfigUtil {
    public static FileConfiguration CONFIG, LANG_CONFIG, SHOP_CONFIG;

    /**
     * 初始化加载文件
     */
    public static void init() {
        // 加载config
        CONFIG = HandyConfigUtil.loadConfig();
        LANG_CONFIG = HandyConfigUtil.loadLangConfig(CONFIG.getString("language"));
        SHOP_CONFIG = HandyConfigUtil.load("shop.yml");
        // 处理配置
        AfDianPayConstants.AFDIAN_TOKEN = CONFIG.getString("afDianToken");
        AfDianPayConstants.AFDIAN_USER_ID = CONFIG.getString("afDianUserId");
        upConfig();
    }

    /**
     * 升级节点处理
     *
     * @since 1.0.4
     */
    private static void upConfig() {
        // 1.0.4 添加拉取控制
        HandyConfigUtil.setPathIsNotContains(CONFIG, "onlyReward", false, Arrays.asList("是否只发奖励", "多子服连接同一个mysql的时候", "一个主服设置为false表示拉订单并发奖励(这个主服例如登陆服，玩家都必须进去过)", "其他子服都设置为true表示只发奖励不拉订单，避免重复拉取"), "config.yml");
        // 1.1.1 可以自己控制域名
        HandyConfigUtil.setPathIsNotContains(CONFIG, "afDianUrl", "afdian.net", null, "config.yml");
        CONFIG = HandyConfigUtil.load("config.yml");
        HandyConfigUtil.setPathIsNotContains(BaseConstants.LANG_CONFIG, "configNotShopName", "&8[&c✘&8] &7OP没有配置 &a${shop} &7这个商品名称", null, "languages/" + CONFIG.getString("language") + ".yml");
        // 1.1.3 新增设置订单完成指令
        HandyConfigUtil.setPathIsNotContains(BaseConstants.LANG_CONFIG, "opDone", "&8[&a✔&8] &7设置订单 &a${order} &7状态为已完成", null, "languages/" + CONFIG.getString("language") + ".yml");
        BaseConstants.LANG_CONFIG = HandyConfigUtil.loadLangConfig(CONFIG.getString("language"), true);
    }

}