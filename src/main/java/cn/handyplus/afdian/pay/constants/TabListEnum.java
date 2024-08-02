package cn.handyplus.afdian.pay.constants;

import cn.handyplus.afdian.pay.util.ConfigUtil;
import cn.handyplus.lib.db.enums.DbTypeEnum;
import cn.handyplus.lib.util.BaseUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * tab提醒
 *
 * @author handy
 */
@Getter
@AllArgsConstructor
public enum TabListEnum {
    /**
     * 第一层提醒
     */
    FIRST(Arrays.asList("convert", "reload", "reward", "send", "look", "getIp", "done"), 0, null, 1),

    REWARD_TWO(Collections.singletonList(BaseUtil.getMsgNotColor("tabHelp.orderNumber")), 1, "reward", 2),
    LOOK_TWO(Collections.singletonList(BaseUtil.getMsgNotColor("tabHelp.orderNumber")), 1, "look", 2),
    DONE_TWO(Collections.singletonList(BaseUtil.getMsgNotColor("tabHelp.orderNumber")), 1, "done", 2),

    SEND_TWO(new ArrayList<>(), 1, "send", 2),

    CONVERT_ONE(DbTypeEnum.getEnum(), 1, "convert", 2),
    ;
    /**
     * 返回的List
     */
    private final List<String> list;
    /**
     * 识别的上个参数的位置
     */
    private final int befPos;
    /**
     * 上个参数的内容
     */
    private final String bef;
    /**
     * 这个参数可以出现的位置
     */
    private final int num;

    /**
     * 获取提醒
     *
     * @param args       参数
     * @param argsLength 参数长度
     * @return 提醒
     */
    public static List<String> returnList(String[] args, int argsLength) {
        List<String> completions = new ArrayList<>();
        for (TabListEnum tabListEnum : TabListEnum.values()) {
            // 过滤掉参数长度不满足要求的情况
            if (tabListEnum.getBefPos() - 1 >= args.length) {
                continue;
            }
            // 过滤掉前置参数不匹配的情况
            if (tabListEnum.getBef() != null && !tabListEnum.getBef().equalsIgnoreCase(args[tabListEnum.getBefPos() - 1])) {
                continue;
            }
            // 过滤掉参数长度不匹配的情况
            if (tabListEnum.getNum() != argsLength) {
                continue;
            }
            // 商品名称输出
            if (tabListEnum.equals(SEND_TWO)) {
                return new ArrayList<>(ConfigUtil.SHOP_CONFIG.getKeys(false));
            }
            return tabListEnum.getList();
        }
        return completions;
    }

}
