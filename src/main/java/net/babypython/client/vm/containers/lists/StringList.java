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
package net.babypython.client.vm.containers.lists;

import net.babypython.client.vm.util.streams.StringStream;

import java.util.ArrayList;
import java.util.Collections;

public class StringList extends ArrayList<String> {

    public StringList() {
        super();
    }

    public StringList(String... args) {
        this();
        initializeValues(args);
    }

    public String asString() {
        return asString("");
    }

    public String asStringWithCommas() {
        return asString(",");
    }

    public String asStringWithSpaces() {
        return asString(" ");
    }

    public String asString(String separator) {
        StringStream stream = new StringStream();
        for (int i = 0; i < size(); i++) {
            stream.pr(get(i));
            if (i < size() - 1)
                stream.pr(separator);
        }
        return stream.toString();
    }

    void initializeValues(String... args) {
        for (String item : args)
            add(item);
    }

    public void sort() {
        Collections.sort(this);
    }
}
