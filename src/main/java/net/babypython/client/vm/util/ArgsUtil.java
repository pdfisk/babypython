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

import net.babypython.client.vm.classes.special.objects.BlockObject;
import net.babypython.client.vm.vm.runtime.operations.BoolOps;
import net.babypython.client.vm.vm.runtime.operations.FloatOps;
import net.babypython.client.vm.vm.runtime.operations.IntOps;
import net.babypython.client.vm.vm.runtime.operations.StringOps;

public class ArgsUtil {

    public static BlockObject getBlockArg(Object[] args) {
        if (args.length > 0 && args[0] instanceof BlockObject)
            return (BlockObject) args[0];
        return null;
    }

    public static boolean getBooleanArg(Object[] args) {
        return getBooleanArg(0, args);
    }

    public static boolean getBooleanArg(int index, Object[] args) {
        if (args.length < index + 1)
            return false;
        return BoolOps.coerce(args[index]);
    }

    public static double getFloatArg(Object[] args) {
        return getFloatArg(0, args);
    }

    public static double getFloatArg(int index, Object[] args) {
        if (args.length < index + 1)
            return -1;
        return FloatOps.coerce(args[index]);
    }

    public static int getIntArg(Object[] args) {
        return getIntArg(0, args);
    }

    public static int getIntArg(int index, Object[] args) {
        if (args.length < index + 1)
            return -1;
        return IntOps.coerce(args[index]);
    }

    public static String getStringArg(Object[] args) {
        return getStringArg(0, args);
    }

    public static String getStringArg(int index, Object[] args) {
        if (args.length < index + 1)
            return "";
        return StringOps.coerce(args[index]);
    }

}
