package com.apocalypse.browser.nest.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;


public class UrlUtils {
	
    public static final String HTTP_PREFIX = "http://";
    public static final String PROTOCOL_MARK = "://";
	
	public static final String GOOD_IRI_CHAR = "a-zA-Z0-9";
	public static final String TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL =
            "(?:"
                    + "(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])"
                    + "|(?:biz|b[abdefghijmnorstvwyz])"
                    + "|(?:cat|com|cool|coop|c[acdfghiklmnoruvxyz])"
                    + "|d[ejkmoz]"
                    + "|(?:edu|e[cegrstu])"
                    + "|f[ijkmor]"
                    + "|(?:gov|g[abdefghilmnpqrstuwy])"
                    + "|h[kmnrtu]"
                    + "|(?:info|int|i[delmnoqrst])"
                    + "|(?:jobs|j[emop])"
                    + "|k[eghimnprwyz]"
                    + "|l[abcikrstuvy]"
                    + "|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])"
                    + "|(?:name|net|n[acefgilopruz])"
                    + "|(?:org|om)"
                    + "|(?:pro|p[aefghklmnrstwy])"
                    + "|qa"
                    + "|r[eosuw]"
                    + "|s[abcdeghijklmnortuvyz]"
                    + "|(?:tel|travel|t[cdfghjklmnoprtvwz])"
                    + "|u[agksyz]"
                    + "|v[aceginu]"
                    + "|w[fs]"
                    + "|(?:xn\\-\\-0zwm56d|xn\\-\\-11b5bs3a9aj6g|xn\\-\\-80akhbyknj4f|xn\\-\\-9t4b11yi5a|xn\\-\\-deba0ad|xn\\-\\-g6w251d|xn\\-\\-hgbk6aj7f53bba|xn\\-\\-hlcj6aya9esc7a|xn\\-\\-jxalpdlp|xn\\-\\-kgbechtv|xn\\-\\-zckzah)"
                    + "|y[etu]"
                    + "|z[amw]))";
	public static final Pattern WEB_URL = Pattern
        .compile(
                "((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)"
                        + "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_"
                        + "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?"
                        + "((?:(?:[" + GOOD_IRI_CHAR + "][" + GOOD_IRI_CHAR + "\\-]{0,64}\\.)+" // named
                                                                                                // host
                        + TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL
                        + "|(?:(?:25[0-5]|2[0-4]" // or ip address
                        + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]"
                        + "|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]"
                        + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                        + "|[1-9][0-9]|[0-9])))"
                        + "(?:\\:\\d{1,5})?)" // plus option port number
                        + "(\\/(?:(?:[" + GOOD_IRI_CHAR + "\\;\\/\\?\\:\\@\\&\\=\\#\\~" // plus
                                                                                        // option
                                                                                        // query
                                                                                        // params
                        + "\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?");
	
	

   public static boolean hasUrlInText(String text) {
        Pattern p = WEB_URL;
        Matcher m = p.matcher(text);
        while (m.find()) {
            if (acceptMatchUrl(m.group().toString(), 0, m.group().length())) {
                return true;
            }
        }
        return false;
    }
   
   public static String getUrlInText(String text) {
	   String url = null;
	   if (!TextUtils.isEmpty(text)) {
		   Pattern p = WEB_URL;
	       Matcher m = p.matcher(text);
	       while (m.find()) {
	           if (acceptMatchUrl(m.group().toString(), 0, m.group().length())) {
	               url = m.group().toString();
	               return url;
	           }
	       }
	   }
	  
	   return url;
   }
   
   /**
    * 指定的text是否为url
    * @param text
    * @return
    */
   public static boolean isUrl(String text) {
	   Pattern p = WEB_URL;
       Matcher m = p.matcher(text);
       if (m.find()) {
    	   if (m.group().toString().equals(text)) {
    		   return true;
    	   }
       }
       return false;
   }
   
   public static boolean acceptMatchUrl(CharSequence s, int start, int end) {
       if (start == 0) {
           return true;
       }

       if (s.charAt(start - 1) == '@') {
           return false;
       }

       return true;
   }
   
   public static String getHost(String url) {
	   String domain = null;
	   try {
		   URI u = new URI(url);
		   domain = u.getHost();
	   } catch (URISyntaxException e) {
			// TODO Auto-generated catch block
		   SimpleLog.e(e);
	   }
	   return domain;
   }
}
