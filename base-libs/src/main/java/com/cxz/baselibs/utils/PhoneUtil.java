package com.cxz.baselibs.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * @author chenxz
 * @date 2018/9/18
 * @desc
 */
public class PhoneUtil {

    private static final String TAG = "PhoneUtil";

    private int[] countryCode = new int[]{
            86, 93, 355, 213, 376,//中国,阿富汗,阿尔巴尼亚,阿尔及利亚,安道尔,
            244, 1264, 1268, 54, 374,//安哥拉,安圭拉岛(英),安提瓜和巴布达,阿根廷,亚美尼亚,
            297, 247, 61, 43, 994,//阿鲁巴岛,阿森松(英),澳大利亚,奥地利,阿塞拜疆,
            1242, 973, 880, 1246, 375,//巴哈马国,巴林,孟加拉国,巴巴多斯,白俄罗斯,
            32, 501, 229, 1441, 975,//比利时,伯利兹,贝宁,百慕大群岛(英),不丹,
            591, 267, 55, 673, 359,//玻利维亚,博茨瓦纳,巴西,文莱,保加利亚,
            226, 257, 237, 1, 34,//布基纳法索,布隆迪,喀麦隆,加拿大,加那利群岛(西),
            238, 235, 236, 56, 1345,//佛得角,乍得,中非,智利,开曼群岛(英),
            619164, 619162, 57, 1767, 269,//圣诞岛,科科斯岛,哥伦比亚,多米尼加联邦,科摩罗,
            242, 682, 506, 385, 53,//刚果,科克群岛(新),哥斯达黎加,克罗地亚,古巴,
            357, 420, 45, 246, 298,//塞浦路斯,捷克,丹麦,迪戈加西亚,法罗群岛,
            299, 253, 1809, 593, 20,//格陵兰岛,吉布提,多米尼加共和国,厄瓜多尔,埃及,
            503, 240, 372, 251, 291,//萨尔瓦多,赤道几内亚,爱沙尼亚,埃塞俄比亚,厄立特里亚,
            500, 679, 358, 33, 594,//福克兰群岛,斐济,芬兰,法国,法属圭亚那,
            241, 220, 995, 49, 233,//加蓬,冈比亚,格鲁吉亚,德国,加纳,
            350, 30, 1473, 1671, 502,//直布罗陀(英),希腊,格林纳达,关岛(美),危地马拉,
            245, 590, 224, 592, 509,//几内亚比绍,瓜得罗普岛(法),几内亚,圭亚那,海地,
            504, 36, 354, 353, 91,//洪都拉斯,匈牙利,冰岛,爱尔兰,印度,
            62, 98, 964, 972, 39,//印度尼西亚,伊朗,伊拉克,以色列,意大利,
            225, 1876, 81, 962, 855,//科特迪瓦,牙买加,日本,约旦,柬埔寨,
            7, 254, 996, 686, 850,//哈萨克斯坦,肯尼亚,吉尔吉斯斯坦,基里巴斯,朝鲜,
            965, 856, 371, 961, 266,//科威特,老挝,拉脱维亚,黎巴嫩,莱索托,
            231, 218, 4175, 370, 352,//利比里亚,利比亚,列支敦士登,立陶宛,卢森堡,
            261, 265, 60, 960, 223,//马达加斯加,马拉维,马来西亚,马尔代夫,马里,
            356, 1670, 692, 596, 230,//马耳他,马里亚纳群岛,马绍尔群岛,马提尼克(法),毛里求斯,
            269, 222, 691, 52, 1808,//马约特岛,毛里塔尼亚,密克罗尼西亚,墨西哥,中途岛(美),
            373, 377, 212, 258, 95,//摩尔多瓦,摩纳哥,摩洛哥,莫桑比克,缅甸,
            389, 976, 264, 674, 977,//马其顿共和国,蒙古,纳米比亚,瑙鲁,尼泊尔,
            31, 64, 505, 227, 234,//荷兰,新西兰,尼加拉瓜,尼日尔,尼日利亚,
            683, 672, 47, 968, 92,//纽埃岛(新),诺福克岛(澳),挪威,阿曼,巴基斯坦,
            680, 507, 595, 51, 63,//帕劳,巴拿马,巴拉圭,秘鲁,菲律宾,
            48, 351, 35196, 35191, 1787,//波兰,葡萄牙,马德拉群岛(萄),亚速尔群岛(萄),波多黎各(美),
            974, 262, 40, 7, 250,//卡塔尔,留尼旺岛(法),罗马尼亚,俄罗斯,卢旺达,
            684, 685, 378, 966, 221,//东萨摩亚(美),西萨摩亚,圣马力诺,沙特阿拉伯,塞内加尔,
            248, 232, 65, 421, 386,//塞舌尔,塞拉利昂,新加坡,斯洛伐克,斯洛文尼亚,
            677, 27, 252, 82, 34,//所罗门群岛(英),南非,索马里,韩国,西班牙,
            94, 290, 1758, 1784, 249,//斯里兰卡,圣赫勒拿,圣卢西亚,圣文森特岛(英),苏丹,
            597, 268, 46, 41, 963,//苏里南,斯威士兰,瑞典,瑞士,叙利亚,
            7, 255, 66, 228, 690,//塔吉克斯坦,坦桑尼亚,泰国,多哥,托克劳群岛(新),
            676, 216, 90, 993, 688,//汤加,突尼斯,土耳其,土库曼斯坦,图瓦卢,
            256, 44, 380, 598, 1,//乌干达,英国,乌克兰,乌拉圭,美国(本土),
            1808, 907, 998, 678, 3906698,//夏威夷,阿拉斯加,乌兹别克斯坦,瓦努阿图,梵蒂冈,
            58, 84, 1284, 1340, 1808,//委内瑞拉,越南,维尔京群岛(英),维京京群岛(美),威克岛(美),
            967, 381, 243, 260, 263,//也门,南斯拉夫,扎伊尔,赞比亚,津巴布韦,
            259, 969, 689, 675, 1681,//桑给巴尔,原民主也门地区,法属波里尼西亚,巴布亚新几内亚,瓦里斯和富士那群岛,
            852, 853, 239, 306, 971,//香港,澳门,圣多美和普林西比,(马尔维纳斯群岛),阿拉伯联合酋长国,
            689, 967, 387, 1649, 1868,//波利尼西亚,原阿拉伯也门地区,波斯尼亚和黑塞哥维那,特克斯和凯科斯群岛,特立尼达和多巴哥,
            508, 64672, 599, 687, 886,//圣皮埃尔岛及密克隆岛,南极,荷属安的列斯群岛,新喀里多尼亚群岛(法),台湾,
            1664, 1869,//蒙特塞拉特岛(英),圣克里斯托弗和尼维斯,
    };


    /**
     * 判断国家区号，忽略+
     */
    private boolean isCorrectCountryCode(final String code) {
        try {
            String copyCode = code;
            if (code.startsWith("+")) {
                // 如果只有一个+号，明显地，相当于正确！
                if (code.length() == 1) {
                    return true;
                }
                copyCode = copyCode.substring(1);
            }
            int int_code = Integer.parseInt(copyCode);
            for (int i = 0; i < countryCode.length; i++) {
                if (int_code == countryCode[i]) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return false;
    }

    /**
     * 查询避免由于区号引起拿不到联系人姓名的问题。（部分手机）
     */
    public String getContentUserName(Context context, String number) {
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
        Log.d(TAG, "get All phone Name!");
        String getName = null;
        String getNumber = null;
        try {
            for (; cursor != null && cursor.moveToNext(); ) {
                getName = cursor.getString(0);
                getNumber = cursor.getString(1);
                if (getNumber == null) {
                    continue;
                }
                //  \\D的原因是部分手机在电话号码能够填写*#+-/等符号！
                getNumber = getNumber.replaceAll("[\\D]", "");
                Log.d(TAG, "raw name is " + getName + "   raw number is " + getNumber);
                // 完全相等，优先级最高的情况。
                if (getNumber.equals(number)) {
                    Log.d(TAG, "equals! get the Correct Data! name is " + getName);
                    cursor.close();
                    return getName;
                } else if (getNumber.endsWith(number)) {
                    //如果存储的是+86，+***，来电没有+86，+***的情况。
                    String code = getNumber.substring(0, getNumber.length() - number.length());
                    Log.d(TAG, "getNumber.endsWith(number)! code is " + code);
                    if (isCorrectCountryCode(code)) {
                        Log.d(TAG, "right! " + getName);
                        cursor.close();
                        return getName;
                    }
                } else if (number.endsWith(getNumber)) {
                    // 如果储存的是没有+86，+***，来电是+86，+***的情况
                    String code = getNumber.substring(0, number.length() - getNumber.length());
                    Log.d(TAG, "number.endsWith(getNumber)! code is " + code);
                    if (isCorrectCountryCode(code)) {
                        Log.d(TAG, "right! " + getName);
                        cursor.close();
                        return getName;
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        } finally {
            try {
                cursor.close();
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
        return number;
    }

}
