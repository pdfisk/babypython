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
package net.babypython.client.vm.vm.runtime;

import net.babypython.client.vm.vm.runtime.operations.FloatOps;
import net.babypython.client.vm.vm.runtime.operations.IntOps;
import net.babypython.client.vm.vm.runtime.operations.StringOps;

public class CompExpr extends NumberExpr {

    public static boolean eq(Object v1, Object v2) {
        if (v1 instanceof Integer)
            return IntOps.eq((Integer) v1, v2);
        else if (v1 instanceof Double)
            return FloatOps.eq((Double) v1, v2);
        else if (v1 instanceof String && v2 instanceof String)
            return StringOps.eq((String) v1, (String) v2);
        return false;
    }

    public static boolean ge(Object v1, Object v2) {
        if (v1 instanceof Integer)
            return IntOps.ge((Integer) v1, v2);
        else if (v1 instanceof Double)
            return FloatOps.ge((Double) v1, v2);
        else if (v1 instanceof String && v2 instanceof String)
            return StringOps.ge((String) v1, (String) v2);
        return false;
    }

    public static boolean gt(Object v1, Object v2) {
        if (v1 instanceof Integer)
            return IntOps.gt((Integer) v1, v2);
        else if (v1 instanceof Double)
            return FloatOps.gt((Double) v1, v2);
        else if (v1 instanceof String && v2 instanceof String)
            return StringOps.gt((String) v1, (String) v2);
        return false;
    }

    public static boolean le(Object v1, Object v2) {
        if (v1 instanceof Integer)
            return IntOps.le((Integer) v1, v2);
        else if (v1 instanceof Double)
            return FloatOps.le((Double) v1, v2);
        else if (v1 instanceof String && v2 instanceof String)
            return StringOps.le((String) v1, (String) v2);
        return false;
    }

    public static boolean lt(Object v1, Object v2) {
        if (v1 instanceof Integer)
            return IntOps.lt((Integer) v1, v2);
        else if (v1 instanceof Double)
            return FloatOps.lt((Double) v1, v2);
        else if (v1 instanceof String && v2 instanceof String)
            return StringOps.lt((String) v1, (String) v2);
        return false;
    }

    public static boolean neq(Object v1, Object v2) {
        if (v1 instanceof Integer)
            return !IntOps.eq((Integer) v1, v2);
        else if (v1 instanceof Double)
            return !FloatOps.eq((Double) v1, v2);
        else if (v1 instanceof String && v2 instanceof String)
            return !StringOps.eq((String) v1, (String) v2);
        return false;
    }

}
