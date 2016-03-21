package com.engine.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理类
 * Created by gaylen on 2016/3/21.
 */
public class StringUtil {

    /**
     * 请选择
     */
    final static String PLEASE_SELECT = "请选择...";
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    @SuppressLint("SimpleDateFormat")
    private final static SimpleDateFormat dateFormater = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat(
            "yyyy-MM-dd");
    private final static Pattern trimer = Pattern
            .compile("[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\s]");
    /**
     * 震动
     */
    private static Vibrator mVibrator;

    private StringUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Date toDate(String sdate) {
        try {
            return new Date(sdate);
        } catch (Exception e) {
            return null;
        }
    }


    public static String getEdit(EditText ed) {
        return ed.getText() == null ? "" : ed.getText().toString();
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendlyTime(String sdate) {
        String timer = sdate.split(" ")[1];
        Date time = null;
        try {
            time = dateFormater.parse(sdate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (time == null) {
            return "";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.format(cal.getTime());
        String paramDate = dateFormater2.format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天" + timer;
        } else if (days == 2) {
            ftime = "前天" + timer;
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前" + timer;
        } else if (days > 10) {
            ftime = dateFormater2.format(time) + timer;
        }
        return ftime;
    }

    public static boolean isTopURL(String str) {
        // 转换为小写
        str = str.toLowerCase();
        String domainRules = "com.cn|net.cn|org.cn|gov.cn|com.hk|公司|中国|网络|com|net|org|int|edu|gov|mil|arpa|Asia|biz|info|name|pro|coop|aero|museum|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cf|cg|ch|ci|ck|cl|cm|cn|co|cq|cr|cu|cv|cx|cy|cz|de|dj|dk|dm|do|dz|ec|ee|eg|eh|es|et|ev|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gh|gi|gl|gm|gn|gp|gr|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|in|io|iq|ir|is|it|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|ml|mm|mn|mo|mp|mq|mr|ms|mt|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nt|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|pt|pw|py|qa|re|ro|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|st|su|sy|sz|tc|td|tf|tg|th|tj|tk|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|us|uy|va|vc|ve|vg|vn|vu|wf|ws|ye|yu|za|zm|zr|zw";
        String regex = "^((https|http|ftp|rtsp|mms)?://)"
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "(([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]+\\.)?" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "(" + domainRules + "))" // first level domain- .com or
                // .museum
                + "(:[0-9]{1,4})?" // 端口- :80
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher isUrl = pattern.matcher(str);
        return isUrl.matches();
    }

    /**
     * 判断字符串是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean isContainsChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.format(today);
            String timeDate = dateFormater2.format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 验证网址Url
     *
     * @param url 待验证的url字符串
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isUrl(String url) {
        String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        return match(regex, url);
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 将时间戳变成字符串
     *
     * @param sdate
     * @return
     */
    public static String toString(long sdate) {
        try {
            long lcc_time = Long.valueOf(sdate);
            String mDate = dateFormater.format(new Date(lcc_time * 1000L));
            return mDate;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */

    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * @param @param  input
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: createPhotos
     * @Description: 将list里面的地址连城一串
     * @author luyao
     * @date 2014年12月11日 下午6:07:50
     */
    public static String createPhotos(List<String> input) {
        String pathurl = "";
        if (input != null) {
            for (String url : input) {
                pathurl += (url + ",");
            }
        }
        return pathurl;
    }

    public static List<String> getListStr(String input) {
        input = input.trim();
        List<String> list = new ArrayList<>();
        String[] in = input.split(",");
        for (int index = 0; index < in.length; index++) {
            list.add(in[index]);
        }
        return list;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    public static String getFormattedSnippet(String snippet) {
        if (snippet != null) {
            snippet = snippet.trim();
            int index = snippet.indexOf('\n');
            if (index != -1) {
                snippet = snippet.substring(0, index);
            }
        }
        return snippet;
    }

    /**
     * 获取资源字符串
     */
    public static String getString(Context context, int strResourceId) {
        return context.getResources().getString(strResourceId);
    }

    /**
     * 随即生成字符串
     *
     * @param length 生成字符串长度
     * @return
     */
    public static String getRandomString(int length) { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 转String为utf-8编码
     *
     * @param key
     * @return
     */
    public static String toUtf8(String key) {
        String result = null;
        try {
            result = new String(key.getBytes("ISO8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 显示指定长度的String
     *
     * @param
     * @return
     */
    public static String toSub(String mStr, int mLength) {
        return mStr.substring(0, mLength) + "...";
    }

    /**
     * 根据指定字符格式化字符串（换行）
     *
     * @param data       需要格式化的字符串
     * @param formatChar 指定格式化字符
     * @return
     */
    public static String parseTxtFormat(String data, String formatChar) {
        StringBuffer backData = new StringBuffer();
        String[] txts = data.split(formatChar);
        for (int i = 0; i < txts.length; i++) {
            backData.append(txts[i]);
            backData.append("\n");
        }
        return backData.toString();
    }

    /**
     * @param @param  strImage
     * @param @return 设定文件
     * @return ArrayList<String> 返回类型
     * @throws
     * @Title: parseStringToList
     * @Description: 将字符串拼接的图片地址转为list《？》
     * @date 2014年12月17日 上午10:19:10
     */
    public static ArrayList<String> parseStringToList(String strImage) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            if (!isEmpty(strImage)) {
                String[] unstr = strImage.split(",");
                for (String url : unstr) {
                    list.add(url);
                }
            }
        } catch (Exception e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 格式化名字 用于保存图像，截取url的最后一段做为图像文件名
     *
     * @param url
     * @return
     */
    public static String formatName(String url) {
        if (url == null || "".equals(url)) {
            return url;
        }
        int start = url.lastIndexOf("/");
        int end = url.lastIndexOf(".");
        if (start == -1 || end == -1) {
            return url;
        }
        return url.substring(start + 1, end);
    }

    /**
     * 处理空字符串
     *
     * @param str
     * @return String
     */
    public static String doEmpty(String str) {
        return doEmpty(str, "");
    }

    /**
     * 处理空字符串
     *
     * @param str
     * @param defaultValue
     * @return String
     */
    public static String doEmpty(String str, String defaultValue) {
        if (str == null || str.equalsIgnoreCase("null")
                || str.trim().equals("") || str.trim().equals("－请选择－")) {
            str = defaultValue;
        } else if (str.startsWith("null")) {
            str = str.substring(4, str.length());
        }
        return str.trim();
    }

    public static boolean notEmpty(Object o) {
        return o != null && !"".equals(o.toString().trim())
                && !"null".equalsIgnoreCase(o.toString().trim())
                && !"undefined".equalsIgnoreCase(o.toString().trim())
                && !PLEASE_SELECT.equals(o.toString().trim());
    }

    /**
     * 判读是否是正确格式
     *
     * @param
     */

    public static boolean isFormat(String content, String format) {
        boolean isFormat;
        Pattern regex = Pattern.compile(format);
        Matcher matcher = regex.matcher(content);
        isFormat = matcher.matches();
        return isFormat;
    }

    /**
     * 判读是否是邮箱格式
     *
     * @param
     * @return
     */
    public static boolean checkEmail(String email) {
        return isFormat(
                email,
                "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    }

    /**
     * 判读是否是姓名格式
     *
     * @param
     * @return
     */
    public static boolean isName(String name) {
        return isFormat(name, "[\u4E00-\u9FA5]{2,50}");
    }

    /**
     * 判读是否是身份证格式
     *
     * @param idCard
     * @return
     */
    public static boolean isIdCard(String idCard) {
        // if (idCard.length() == 15) {
        // return isFormat(idCard,
        // "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
        // } else if (idCard.length() == 18) {
        // return isFormat(idCard,
        // "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");
        // }

        return isFormat(idCard,
                "^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X|x)?$");
        // return false;
    }

    public static boolean empty(Object o) {
        return o == null || "".equals(o.toString().trim())
                || "null".equalsIgnoreCase(o.toString().trim())
                || "undefined".equalsIgnoreCase(o.toString().trim())
                || PLEASE_SELECT.equals(o.toString().trim());
    }


    public static boolean decimal(Object o) {
        double n = 0;
        try {
            n = Double.parseDouble(o.toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return n > 0.0;
    }


    /**
     * 清除字符串中特殊字符包括空格
     *
     * @create 2014年5月13日 上午11:33:16 发布
     */
    public static String Trim(String str) {
        Matcher m = trimer.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return [0-9]{5,9}
     */
    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern
                    .compile("^((13[0-9])|(15[^4,\\D])|(14[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 判读是否是特殊格式
     *
     * @param content
     * @return
     */
    public static boolean isSpec(String content) {
        return isFormat(content, "^[a-zA-Z0-9\u4E00-\u9FA5]{1,50}$");
    }

    /**
     * 判读是否是座机号码
     *
     * @param content
     * @return
     */
    public static boolean isLandline(String content) {
        return isFormat(content, "^(0[0-9]{2,3})?([2-9][0-9]{6,7})?$");
    }

    /**
     * 判读是否含有特殊符号
     *
     * @param content
     * @return
     */
    public static boolean isContainsSpec(String content) {
        return isFormat(content, "^[\\u4e00-\\u9fa5A-Za-z0-9]*$"); // \\-\\_

    }

    /**
     * @param theString
     * @return String
     */
    public static String unicodeToUtf8(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    /**
     * 替换链接的域名
     */
    public static String toolsIPDo(String ip, String cokeIp) {
        //ip = "http://121.122.4.157:5055/";
        //cokeIp = "www.baidu.com";
        String[] fenjie = ip.split(":");
        String tou = ip.split("//")[0];
        if (fenjie.length > 2) {
            String wei = (ip.split("//")[1]).split(":")[1];
            ip = tou + "//" + cokeIp + ":" + wei;
        } else {
            if (ip.charAt(ip.length() - 1) == '/') {
                ip = tou + "//" + cokeIp + "/";
            } else {
                ip = tou + "//" + cokeIp;
            }
        }
        return ip;
    }

//    /**
//     * 将连接拆分并测试
//     */
//    public static String toolsSingleIp(String ip) {
//        //http://app_api.s8s8pc.com:5055
//        String[] maohao = ip.split(":");
//        String newUrl = maohao[1].replace("//", "");
//        return newUrl;
//    }

    /**
     * 截取字符串并加省略号
     * 根据最大指定长度
     */
    public static String getMaxStr(String str, int max) {
        if (str.length() > max) {
            str = str.substring(0, max).concat("...");
        }
        return str;
    }

    public static String getUrl(String ip, Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(ip);
        if (map != null && map.size() != 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                // 如果请求参数中有中文，需要进行URLEncoder编码 gbk/utf8
                try {
                    sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 转换下载信息提示
     *
     * @param total
     * @param current
     * @return
     */
    public static String convertDownloadMessage(long total, long current) {
        String unit;
        String max;
        String progress;
        if ((total / 1000) >= 1024) {
            unit = "MB";
            max = getSecondString((float) total / 1000 / 1024);
            progress = getSecondString((float) current / 1000 / 1024);
        } else {
            max = getNoPointString(total / 1000);
            progress = getNoPointString(current / 1000);
            unit = "KB";
        }
        return progress + unit + " / " + max + unit;
    }

    public static String getSecondString(float value) {
        String number = String.valueOf(value);
        return number.substring(0, number.indexOf(".") + 2);
    }

    public static String getNoPointString(float value) {
        String number = String.valueOf(value);
        return number.substring(0, number.indexOf("."));
    }
}
