/**
 * MIT License
 *
 * Copyright (c) 2022 Peter Fisk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.babypython.client.vm.util;

import com.google.gwt.i18n.client.NumberFormat;
import net.babypython.client.vm.containers.lists.StringList;
import net.babypython.client.vm.classes.special.objects.NilObject;

public class StringUtil {

    public static String argsToString(Object[] args) {
        return argsToString(args, " ");
    }

    public static String argsToString(Object[] args, String separator) {
        switch (args.length) {
            case 0:
                return "";
            case 1:
                return StringUtil.__str__(args[0]);
            default:
                StringList stringList = new StringList();
                for (int i = 0; i < args.length; i++)
                    stringList.add(StringUtil.__str__(args[i]));
                return String.join(separator, stringList);
        }
    }

    public static boolean isGlobalVariable(String varName) {
        if (varName.length() == 0)
            return false;
        return Character.isUpperCase(varName.charAt(0));
    }

    public static String __str__(Object value) {
        if (value == null)
            return NilObject.getInstance().toString();
        else if (value instanceof Double) {
            NumberFormat fmt = NumberFormat.getDecimalFormat();
            fmt.overrideFractionDigits(1, 4);
            return fmt.format((Double) value);
        } else if (value instanceof Boolean)
            return ((boolean) value) ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
        return value.toString();
    }

    public static String tagify(String str) {
        return str.toLowerCase().replaceAll(" ", "_").replaceAll("\\.", "_");
    }

    public static String toSnakeCase(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            Character ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                if (i > 0)
                    result += "_";
            }
            result += Character.toLowerCase(ch);
        }
        return result;
    }

    public static String format(String pattern, Object... args) {
        String workStr = pattern;
        for (int i = 0; i < args.length; i++) {
            String matchStr = "{" + i + "}";
            String replaceStr = args[i] == null ? "null" : args[i].toString();
            int index;
            while ((index = workStr.indexOf(matchStr)) >= 0) {
                String leftStr = workStr.substring(0, index);
                String rightStr = workStr.substring(index + matchStr.length());
                workStr = leftStr + replaceStr + rightStr;
            }
        }
        return workStr;
    }

    public static String padLeadingZeroes(int n) {
        return padLeadingZeroes(n, 3);
    }

    public static String padLeadingZeroes(int num, int n) {
        return padLeadingCharacter(num + "", "0", n);
    }

    public static String padLeadingCharacter(String s, String ch, int n) {
        while (s.length() < n)
            s = ch + s;
        return s;
    }

    public static String padSize(String s, int n) {
        return padTrailingCharacter(s, " ", n);
    }

    public static String padTrailingCharacter(String s, String ch, int n) {
        while (s.length() < n)
            s = s + ch;
        return s;
    }

    public static boolean startsWithLowerCase(String str) {
        return str.length() > 0 && Character.isLowerCase(str.charAt(0));
    }

    public static String stripLeading(String str, String prefix) {
        while (str.startsWith(prefix))
            if (str.length() == prefix.length())
                str = EMPTY_STRING;
            else
                str = str.substring(prefix.length());
        return str;
    }

    public static String stripTrailing(String str, String suffix) {
        while (str.lastIndexOf(suffix) == str.length() - suffix.length())
            if (str.length() == suffix.length())
                str = StringUtil.EMPTY_STRING;
            else
                str = str.substring(0, str.lastIndexOf(suffix));
        return str;
    }

    public final static String EMPTY_STRING = "";
    public final static String LIST_INDENT_PAD = "...";
}
