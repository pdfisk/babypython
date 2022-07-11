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

public class LogicExpr {

    public static Object and(Object v1, Object v2) {
        if (!(isBoolean(v1) && isBoolean(v2)))
            return null;
        boolean value1 = (boolean) v1;
        boolean value2 = (boolean) v2;
        return value1 && value2;
    }

    public static Object not(Object v1) {
        if (!(isBoolean(v1)))
            return null;
        boolean value1 = (boolean) v1;
        return !value1;
    }

    public static Object or(Object v1, Object v2) {
        if (!(isBoolean(v1) && isBoolean(v2)))
            return null;
        boolean value1 = (boolean) v1;
        boolean value2 = (boolean) v2;
        return value1 || value2;
    }

    static boolean isBoolean(Object value) {
        return value instanceof Boolean;
    }

}
