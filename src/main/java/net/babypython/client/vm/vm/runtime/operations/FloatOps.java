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
package net.babypython.client.vm.vm.runtime.operations;

import net.babypython.client.ui.util.Logable;

public class FloatOps extends Logable {
    
    public static double coerce(Object object) {
        if (object instanceof Integer)
            return (double) (int) object;
        else if (object instanceof Long)
            return (double) (long) object;
        else if (object instanceof Float)
            return (double) (float) object;
        else if (object instanceof Double)
            return (double) object;
        else if (object instanceof String) {
            try {
                return Double.parseDouble(object.toString());
            }
            catch (Exception e) {
                err("FloatOps coerce error", e.getMessage());
            }
        }
        return 0;
    }

    public static Object div(double arg1, Object arg2) {
        if (arg2 instanceof Integer) {
            int intArg2 = (int) arg2;
            if (intArg2 == 0)
                return divZeroError(arg2);
            return arg1 / intArg2;
        }
        else if (arg2 instanceof Double) {
            double doubleArg2 = (double) arg2;
            if (doubleArg2 == 0)
                return divZeroError(arg2);
            return arg1 / doubleArg2;
        }
        else if (arg2 instanceof Long) {
            double longArg2 = (long) arg2;
            if (longArg2 == 0)
                return divZeroError(arg2);
            return arg1 / longArg2;
        }
        else if (arg2 instanceof Float) {
            float floatArg2 = (long) arg2;
            if (floatArg2 == 0)
                return divZeroError(arg2);
            return arg1 / floatArg2;
        }
        err("IntOps div error", arg2);
        return 0;
    }

    static Object divZeroError(Object arg2) {
        return err("FloatOps divZeroError", arg2);
    }

    public static boolean eq(double arg1, Object arg2) {
        if (arg2 instanceof Integer)
            return arg1 == (int) arg2;
        else if (arg2 instanceof Double)
            return arg1 == (double) arg2;
        else if (arg2 instanceof Long)
            return arg1 == (long) arg2;
        else if (arg2 instanceof Float)
            return arg1 == (float) arg2;
        err("FloatOps eq error", arg2);
        return false;
    }

    public static Object floorDiv(double arg1, Object arg2) {
        if (arg2 instanceof Integer) {
            int intArg2 = (int) arg2;
            if (intArg2 == 0)
                return divZeroError(arg2);
            return Math.floor(arg1 / intArg2);
        }
        else if (arg2 instanceof Double) {
            double doubleArg2 = (double) arg2;
            if (doubleArg2 == 0)
                return divZeroError(arg2);
            return Math.floor(arg1 / doubleArg2);
        }
        else if (arg2 instanceof Long) {
            double longArg2 = (long) arg2;
            if (longArg2 == 0)
                return divZeroError(arg2);
            return Math.floor(arg1 / longArg2);
        }
        else if (arg2 instanceof Float) {
            float floatArg2 = (long) arg2;
            if (floatArg2 == 0)
                return divZeroError(arg2);
            return Math.floor(arg1 / floatArg2);
        }
        err("FloatOps floorDiv error", arg2);
        return 0;
    }

    public static boolean ge(double arg1, Object arg2) {
        if (arg2 instanceof Integer)
            return arg1 >= (int) arg2;
        else if (arg2 instanceof Double)
            return arg1 >= (double) arg2;
        else if (arg2 instanceof Long)
            return arg1 >= (long) arg2;
        else if (arg2 instanceof Float)
            return arg1 >= (float) arg2;
        err("FloatOps ge error", arg2);
        return false;
    }

    public static boolean gt(double arg1, Object arg2) {
        if (arg2 instanceof Integer)
            return arg1 > (int) arg2;
        else if (arg2 instanceof Double)
            return arg1 > (double) arg2;
        else if (arg2 instanceof Long)
            return arg1 > (long) arg2;
        else if (arg2 instanceof Float)
            return arg1 > (float) arg2;
        err("FloatOps gt error", arg2);
        return false;
    }

    public static boolean le(double arg1, Object arg2) {
        if (arg2 instanceof Integer)
            return arg1 <= (int) arg2;
        else if (arg2 instanceof Double)
            return arg1 <= (double) arg2;
        else if (arg2 instanceof Long)
            return arg1 <= (long) arg2;
        else if (arg2 instanceof Float)
            return arg1 <= (float) arg2;
        err("FloatOps le error", arg2);
        return false;
    }

    public static boolean lt(double arg1, Object arg2) {
        if (arg2 instanceof Integer)
            return arg1 < (int) arg2;
        else if (arg2 instanceof Double)
            return arg1 < (double) arg2;
        else if (arg2 instanceof Long)
            return arg1 < (long) arg2;
        else if (arg2 instanceof Float)
            return arg1 < (float) arg2;
        err("FloatOps lt error", arg2);
        return false;
    }

    public static Object minus(double arg1, Object arg2) {
        if (arg2 instanceof Integer)
            return arg1 - (int) arg2;
        else if (arg2 instanceof Double)
            return arg1 - (double) arg2;
        else if (arg2 instanceof Long)
            return arg1 - (long) arg2;
        else if (arg2 instanceof Float)
            return arg1 - (float) arg2;
        err("FloatOps minus error", arg2);
        return 0;
    }

    public static Object mod(double arg1, Object arg2) {
        if (arg2 instanceof Integer) {
            int intArg2 = (int) arg2;
            if (intArg2 == 0)
                return divZeroError(arg2);
            return arg1 % intArg2;
        }
        else if (arg2 instanceof Double) {
            double doubleArg2 = (double) arg2;
            if (doubleArg2 == 0)
                return divZeroError(arg2);
            return arg1 % doubleArg2;
        }
        else if (arg2 instanceof Long) {
            double longArg2 = (long) arg2;
            if (longArg2 == 0)
                return divZeroError(arg2);
            return arg1 % longArg2;
        }
        else if (arg2 instanceof Float) {
            float floatArg2 = (long) arg2;
            if (floatArg2 == 0)
                return divZeroError(arg2);
            return arg1 % floatArg2;
        }
        err("FloatOps mod error", arg2);
        return 0;
    }

    public static Object mult(double arg1, Object arg2) {
        if (arg2 instanceof Integer)
            return arg1 * (int) arg2;
        else if (arg2 instanceof Double)
            return arg1 * (double) arg2;
        else if (arg2 instanceof Long)
            return arg1 * (long) arg2;
        else if (arg2 instanceof Float)
            return arg1 * (float) arg2;
        err("IntOps mult error", arg2);
        return 0;
    }

    public static Object plus(double arg1, Object arg2) {
        if (arg2 instanceof Integer)
            return arg1 + (int) arg2;
        else if (arg2 instanceof Double)
            return arg1 + (double) arg2;
        else if (arg2 instanceof Long)
            return arg1 + (long) arg2;
        else if (arg2 instanceof Float)
            return arg1 + (float) arg2;
        err("FloatOps plus error", arg2);
        return 0;
    }
    
}
