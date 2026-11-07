package ru.method.celebression.server;

import java.util.regex.Pattern;

public final class ServerTextPatternMatcher {
    public static final String alN = "owner|admin|moder|moderator|helper|media|streamer|legend|premium|prem|vip|elite|hero|king|lord|titan|yt|youtube|\u044e\u0442\u0443\u0431\u0435\u0440|\u0441\u0442\u0440\u0438\u043c\u0435\u0440|\u043c\u0435\u0434\u0438\u0430|\u0432\u0438\u043f|\u043f\u0440\u0435\u043c|\u044d\u043b\u0438\u0442\u0430|\u043b\u0435\u0433\u0435\u043d\u0434\u0430|\u0430\u0434\u043c\u0438\u043d|\u0445\u0435\u043b\u043f\u0435\u0440|\u043c\u043e\u0434\u0435\u0440";
    public static final Pattern alO = Pattern.compile("[\\p{L}\\p{N}_]");
    public static final Pattern alP = Pattern.compile("(?iu)((?:\u0434\u043e\u043d\u0430\u0442|\u043f\u0440\u0438\u0432\u0438\u043b\u0435\u0433\u0438\u044f|\u0440\u0430\u043d\u0433|rank|donate|prefix)\\s*[:=\\-\u00bb>]?\\s*)([\\[\\]\u2605\u2726\u2727\u269d\u269c\\p{L}\\p{N}_+\\- ]{2,32})");
    public static final Pattern alQ = Pattern.compile("(?iu)((?:\u043c\u043e\u043d\u0435\u0442(?:\u044b)?|\u043a\u043e\u0438\u043d(?:\u044b|\u043e\u0432)?|coins?|balance|\u0431\u0430\u043b\u0430\u043d\u0441)\\s*[:=\\-\u00bb>]?\\s*)(\\d[\\d\\s.,_]*)");
    public static final Pattern alR = Pattern.compile("(?iu)((?:\u0434\u043e\u043d\u0430\u0442|\u043f\u0440\u0438\u0432\u0438\u043b\u0435\u0433\u0438\u044f|\u0440\u0430\u043d\u0433|rank|donate|prefix)\\s*[:=\\-\u00bb>]?)\\s*([\\[\\]\u2605\u2726\u2727\u269d\u269c\\p{L}\\p{N}_+\\- ]{1,32})");
    public static final Pattern alS = Pattern.compile("(?iu)((?:\u0440\u0430\u043d\u0433|rank)\\s*[:=\\-\u00bb>]?)\\s*([\\[\\]\u2605\u2726\u2727\u269d\u269c\\p{L}\\p{N}_+\\- ]{1,32})");
    public static final Pattern alT = Pattern.compile("(?iu)^\\[?(?:\u0437\u0430\u0449\u0438\u0442\u0430|security|auth|login|\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0430\u0446\u0438\u044f|register|captcha|\u043a\u0430\u043f\u0447\u0430|\u043f\u0430\u0440\u043e\u043b\u044c|password)\\]?$");
    public static final Pattern alU = Pattern.compile("(?iu)(\\u0430\\u043d\\u0430\\u0440\\u0445\\u0438\\u044f|anarchy)([\\s#\\-]*)(\\d+)");
    public static final Pattern alV = Pattern.compile("(?iu)(\\u0433\\u0440\\u0438\\u0444|grief|grif)([\\s#\\-]*)(\\d+)");
    public static final Pattern alW = Pattern.compile("(?iu)(?:owner|admin|moder|moderator|helper|media|streamer|legend|premium|prem|vip|elite|hero|king|lord|titan|yt|youtube|\u044e\u0442\u0443\u0431\u0435\u0440|\u0441\u0442\u0440\u0438\u043c\u0435\u0440|\u043c\u0435\u0434\u0438\u0430|\u0432\u0438\u043f|\u043f\u0440\u0435\u043c|\u044d\u043b\u0438\u0442\u0430|\u043b\u0435\u0433\u0435\u043d\u0434\u0430|\u0430\u0434\u043c\u0438\u043d|\u0445\u0435\u043b\u043f\u0435\u0440|\u043c\u043e\u0434\u0435\u0440)[\\p{L}\\p{N}_+\\-]*");
    public static final Pattern alX = Pattern.compile(Pattern.quote("%player%"));
    public static final Pattern alY = Pattern.compile("(?i)^\\s*/(?:l|login|log|pas|pass|passwd|password|reg|register|authme|auth|changepassword|changepass|changepasswd|chpass|setpass|setpassword|newpass|resetpass|password2|repeat|confirmpass|2fa|totp|session|premium)(?=\\s|$|[:=])");
    public static final Pattern alZ = Pattern.compile("(?iu)(?:\\u043d\\u0438\\u043a|nick|name|player|\\u0438\\u0433\\u0440\\u043e\\u043a)\\s*[:=\\-\u0412\u00bb>]");
    public static final Pattern ama = Pattern.compile("(?:\u0413\u0420\u0418\u0424-|\u0413\u0420\u0418\u0424)");
    public static final Pattern amb = Pattern.compile("(?:\u0410\u043d\u0430\u0440\u0445\u0438\u044f-|\u0410\u043d\u0430\u0440\u0445\u0438\u044f)");

    private ServerTextPatternMatcher() {
    }
}

