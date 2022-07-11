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
package net.babypython.client.ui.widgets.monaco;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import net.babypython.client.ui.constants.EditorConstants;
import net.babypython.client.ui.gwt.window.GwtWindow;
import net.babypython.client.ui.interfaces.IHandleLineChanged;
import net.babypython.client.ui.interfaces.IOnLoad;

public class PythonEditor extends HTML implements IHandleLineChanged {

    public PythonEditor(GwtWindow gwtWindow) {
        this(gwtWindow, null);
    }

    protected PythonEditor(GwtWindow gwtWindow, IOnLoad onLoadHandler) {
        super();
    }

    protected String defaultEditorLanguage() {
        return "python";
    }

    public void clear() {
        setValue("");
    }

    public void focus() {
        if (editor == null)
            return;
        nativeFocus(editor);
    }

    public JavaScriptObject getEditor() {
        return editor;
    }

    public String getValue() {
        return nativeGetValue(editor);
    }

    public void layout() {
        nativeLayout(editor);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Timer t = new Timer() {
            @Override
            public void run() {
                editor = nativeCreateEditor(defaultEditorLanguage(), getElement());
                layout();
                setTheme();
            }
        };
        t.schedule(100);
    }

    @Override
    public void onLineChanged(int lineNumber) {
        setCurrentLine(lineNumber);
    }

    public void refresh() {
        layout();
    }

    public void setCurrentLineAtEnd() {
        setCurrentLine(EditorConstants.LAST_LINE);
    }

    public void setCurrentLineAtStart() {
        setCurrentLine(EditorConstants.FIRST_LINE);
    }

    public void setCurrentLine(int lineNumber) {
        nativeRevealLineInCenter(editor, lineNumber);
        nativeSetCurrentLine(editor, lineNumber);
    }

    void setTheme() {
        nativeSetTheme();
    }

    public void setValue(String text) {
        nativeSetValue(editor, text);
    }

    native void nativeRevealLineInCenter(JavaScriptObject editor, int lineNumber) /*-{
        editor.revealLineInCenter(lineNumber);
    }-*/;

    native void nativeSetTheme() /*-{
        $wnd.monaco.editor.defineTheme('babyPython', {
	        base: 'vs',
	        inherit: true,
	        rules: [{ background: 'EDF9FA' }],
	        colors: {
		        'editor.foreground': '#000000',
		        'editor.background': '#EDF9FA',
		        'editorCursor.foreground': '#8B0000',
		        'editor.lineHighlightBackground': '#0000FF20',
		        'editorLineNumber.foreground': '#008800',
		        'editor.selectionBackground': '#88000030',
		        'editor.inactiveSelectionBackground': '#88000015'
	        }
        });
        $wnd.monaco.editor.setTheme('babyPython');
    }-*/;

    native void nativeSetCurrentLine(JavaScriptObject editor, int lineNumber) /*-{
        var pos = editor.getPosition();
        pos.lineNumber = lineNumber;
        editor.setPosition(pos);
    }-*/;

    native JavaScriptObject nativeCreateEditor(String language, JavaScriptObject element) /*-{
        var options = {
            value: '',
            automaticLayout: true,
            language: language,
            renderWhitespace: 'all',
            minimap: {enabled: false}
        };
        return $wnd.monaco.editor.create(element, options);
    }-*/;

    native void nativeFocus(JavaScriptObject editor) /*-{
    }-*/;

    native String nativeGetValue(JavaScriptObject editor) /*-{
        if (!editor)
            return '';
        return editor.getValue();
    }-*/;

    native void nativeLayout(JavaScriptObject editor) /*-{
        if (editor)
            editor.layout();
    }-*/;

    native void nativeRefresh(JavaScriptObject editor) /*-{
        editor.refresh();
    }-*/;

    native String nativeSetValue(JavaScriptObject editor, String text) /*-{
        if (editor)
            editor.setValue(text);
    }-*/;

    JavaScriptObject editor;
}
