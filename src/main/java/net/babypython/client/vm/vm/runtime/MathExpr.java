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
import net.babypython.client.ui.util.Logable;

public class MathExpr extends NumberExpr {

    public static Object div(Object v1, Object v2) {
        if (v1 instanceof Integer)
            return IntOps.div((Integer) v1, v2);
        else if (v1 instanceof Double)
            return FloatOps.div((Double) v1, v2);
        Logable.err("MathExpr div", v1.getClass().getSimpleName(), v2.getClass().getSimpleName());
        return 0;
    }

    public static Object floorDiv(Object v1, Object v2) {
        if (v1 instanceof Integer)
            return IntOps.floorDiv((Integer) v1, v2);
        else if (v1 instanceof Double)
            return FloatOps.floorDiv((Double) v1, v2);
        Logable.err("MathExpr floorDiv", v1.getClass().getSimpleName(), v2.getClass().getSimpleName());
        return 0;
    }

    public static Object minus(Object v1, Object v2) {
        if (v1 instanceof Integer)
            return IntOps.minus((Integer) v1, v2);
        else if (v1 instanceof Double)
            return FloatOps.minus((Double) v1, v2);
        Logable.err("MathExpr minus", v1.getClass().getSimpleName(), v2.getClass().getSimpleName());
        return 0;
    }

    public static Object mod(Object v1, Object v2) {
        if (v1 instanceof Integer)
            return IntOps.mod((Integer) v1, v2);
        else if (v1 instanceof Double)
            return FloatOps.mod((Double) v1, v2);
        Logable.err("MathExpr mod", v1.getClass().getSimpleName(), v2.getClass().getSimpleName());
        return 0;
    }

    public static Object mult(Object v1, Object v2) {
        if (v1 instanceof Integer)
            return IntOps.mult((Integer) v1, v2);
        else if (v1 instanceof Double)
            return FloatOps.mult((Double) v1, v2);
        Logable.err("MathExpr mult", v1.getClass().getSimpleName(), v2.getClass().getSimpleName());
        return 0;
    }

    public static Object plus(Object v1, Object v2) {
        if (v1 instanceof Integer)
            return IntOps.plus((Integer) v1, v2);
        else if (v1 instanceof Double)
            return FloatOps.plus((Double) v1, v2);
        Logable.err("MathExpr plus", v1.getClass().getSimpleName(), v2.getClass().getSimpleName());
        return 0;
    }

    public static Object pow(Object v1, Object v2) {
        double value1 = FloatOps.coerce(v1);
        double value2 = FloatOps.coerce(v2);
        return Math.pow(value1, value2);
    }

}
