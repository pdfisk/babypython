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
package net.babypython.client.vm.util.streams;

public class StringInputStream {
	private int index;
	private String src;

	public StringInputStream() {
		this("");
	}

	public StringInputStream(String str) {
		setSource(src);
	}

	public int nextChar() {
		int ch = peek();
		index++;
		return ch;
	}

	public int peek() {
		if (index >= src.length())
			return -1;
		else
			return src.charAt(index);
	}

	public int peek2() {
		index++;
		int ch = peek();
		index--;
		return ch;
	}

	public int prev() {
		if (index > 0) {
			index--;
			int ch = peek();
			index++;
			return ch;
		} else
			return -1;
	}

	public void setEof() {
		index = src.length() + 1;
	}
	
	public String getErrorString() {
		int beginIndex = Math.max(0, index - 5);
		int endIndex = Math.min(index + 5, src.length()-1);
		return src.substring(beginIndex, endIndex);
	}

	public void setSource(String src) {
		index = 0;
		this.src = src;
	}

}
