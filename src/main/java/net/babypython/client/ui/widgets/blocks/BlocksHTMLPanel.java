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
package net.babypython.client.ui.widgets.blocks;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.web.bindery.event.shared.binder.EventHandler;
import net.babypython.client.ui.events.resize.ResizeEvent;
import net.babypython.client.ui.interfaces.IAfterResize;

public class BlocksHTMLPanel extends HTML implements IAfterResize, RequiresResize {

    public BlocksHTMLPanel(String workspaceName) {
        super();
        this.workspaceName = workspaceName;
        setWidth("100%");
        setHeight("100%");
    }

    @Override
    public void afterResize() {
        onResize();
    }

    public String asJsonString() {
        return nativeSaveWorkspace(workspace);
    }

    public String generate() {
        if (workspace == null)
            return "";
        return nativeGenerate(workspace);
    }

    public void loadWorkspace(String json64) {
        nativeLoadWorkspace(workspace, json64);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Timer t = new Timer() {
            @Override
            public void run() {
                workspace = nativeInitializeEditor(getElement());
                onResize();
            }
        };
        t.schedule(300);
    }

    @EventHandler
    public void onProcessEvent(ResizeEvent event) {
        onResize();
    }

    public void onResize() {
        if (workspace != null)
            nativeOnResize(workspace);
    }

    native String nativeGenerate(JavaScriptObject workspace) /*-{
        return $wnd.Blockly.Python.workspaceToCode(workspace);
    }-*/;

    native JavaScriptObject nativeInitializeEditor(JavaScriptObject element) /*-{
        var options = {
            toolbox : $wnd.Blockly.Toolboxes.STANDARD_TOOLBOX,
            collapse : true,
            comments : true,
            disable : true,
            maxBlocks : Infinity,
            trashcan : true,
            horizontalLayout : false,
            toolboxPosition : 'start',
            css : true,
            media : 'blockly/media/',
            rtl : false,
            scrollbars : true,
            sounds : true,
            oneBasedIndex : true
        };
        return $wnd.Blockly.inject(element, options);
    }-*/;

    native void nativeLoadWorkspace(JavaScriptObject workspace, String json64) /*-{
        var data = $wnd.JSON.parse($wnd.atob(json64));
        $wnd.Blockly.serialization.workspaces.load(data, workspace);
    }-*/;

    native void nativeOnResize(JavaScriptObject workspace) /*-{
        $wnd.Blockly.svgResize(workspace);
    }-*/;

    native String nativeSaveWorkspace(JavaScriptObject workspace) /*-{
        return btoa($wnd.JSON.stringify($wnd.Blockly.serialization.workspaces.save(workspace)));
    }-*/;

    JavaScriptObject workspace;
    String workspaceName;
}
