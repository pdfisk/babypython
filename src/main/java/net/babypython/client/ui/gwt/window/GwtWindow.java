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
package net.babypython.client.ui.gwt.window;

import net.babypython.client.vm.classes.special.classes.WindowButtonClass;
import net.babypython.client.vm.classes.special.objects.WindowButtonObject;
import net.babypython.client.vm.classes.special.objects.WindowObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.*;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import net.babypython.client.ui.constants.ColorConstants;
import net.babypython.client.ui.constants.DimensionConstants;
import net.babypython.client.ui.constants.StyleConstants;
import net.babypython.client.ui.constants.CommonWindowConstants;
import net.babypython.client.vm.events.session.SessionEvent;
import net.babypython.client.vm.events.session.SessionEventBus;
import net.babypython.client.ui.interfaces.IAfterResize;
import net.babypython.client.ui.interfaces.IMouseEvents;
import net.babypython.client.vm.interfaces.ISessionState;

import net.babypython.client.vm.vm.context.Context;
import net.babypython.client.vm.vm.interfaces.ICallMethod;
import net.babypython.client.ui.session.SessionState;
import net.babypython.client.ui.viewport.widgets.desktop.WindowManager;
import net.babypython.client.vm.util.FormatUtil;
import net.babypython.client.ui.util.Logable;
import net.babypython.client.ui.util.WindowUtil;
import net.babypython.client.vm.util.structures.Point;
import net.babypython.client.ui.gwt.window.widgets.*;

import java.util.ArrayList;

public class GwtWindow extends PopupPanel implements IAfterResize, ICallMethod, IMouseEvents, ISessionState {
    protected static final int EDGE_WIDTH = DimensionConstants.GwtWindowEdgeWidth;
    protected static final int HEADER_BAR_HEIGHT = DimensionConstants.GwtWindowHeaderBarHeight;
    protected static final int BUTTON_BAR_HEIGHT = CommonWindowConstants.ButtonBarHeight;

    protected static final int MINIMUM_WINDOW_HEIGHT = HEADER_BAR_HEIGHT + 2 * EDGE_WIDTH;
    protected static final int MINIMUM_WINDOW_WIDTH = 100;

    protected static final String DECORATOR_COLOR = ColorConstants.WindowDecoratorMauve;

    protected static final String BUTTON_BAR_COLOR = DECORATOR_COLOR;
    protected static final String CONTENT_WIDGET_COLOR = "white";
    protected static final String CORNER_WIDGET_COLOR = DECORATOR_COLOR;
    protected static final String SIDE_WIDGET_COLOR = DECORATOR_COLOR;
    protected static final String TABLE_WIDGET_COLOR = "transparent";
    protected static final String HEADER_BAR_COLOR = DECORATOR_COLOR;
    protected static final String WINDOW_WIDGET_COLOR = "transparent";

    public GwtWindow() {
        super(false, false);
        addClass(this, StyleConstants.WindowGrayStyle);
        createAndPopulateTable();
        initializeAfterLayout();
        setTitle(defaultTitle());
        setBackgroundColor(this, WINDOW_WIDGET_COLOR);
        sinkEvents(Event.ONCLICK);
        setModal(defaultModal());
        if (defaultPosition() != null)
            setLocation(defaultPosition());
        else
            center();
        if (defaultAutoShow())
            show();
    }

    public void addClass(Widget widget, String className) {
        widget.getElement().addClassName(className);
    }

    public WindowButtonObject addButton(String label) {
        if (buttonBar == null)
            return null;
        WindowButtonObject windowButtonObject = (WindowButtonObject) WindowButtonClass.getInstance().createInstance();
        windowButtonObject.setGwtWindowButton(buttonBar.addButton(label));
        return windowButtonObject;
    }

    public void afterResize() {
        if (contentWidget != null && contentWidget instanceof IAfterResize)
            ((IAfterResize) contentWidget).afterResize();
    }

    public void close() {
        hide();
        removeFromParent();
    }

    protected boolean defaultAutoShow() {
        return true;
    }

    protected Widget defaultContent() {
        return new SimplePanel();
    }

    public int defaultContentHeight() {
        return defaultHeight() - HEADER_BAR_HEIGHT - BUTTON_BAR_HEIGHT - 2 * EDGE_WIDTH;
    }

    public int defaultContentWidth() {
        return defaultWidth() - 2 * EDGE_WIDTH;
    }

    protected int defaultHeight() {
        return 325;
    }

    public boolean defaultHideOnClose() {
        return false;
    }

    public boolean defaultShowCloseButton() {
        return true;
    }

    public boolean defaultShowMaximizeButton() {
        return true;
    }

    public boolean defaultShowMinimizeButton() {
        return true;
    }

    protected boolean defaultModal() {
        return false;
    }

    protected Point defaultPosition() {
        return null;
    }

    protected String defaultTitle() {
        return "a Window";
    }

    protected int defaultWidth() {
        return 375;
    }

    @Override
    public Object callMethod(Context context, String methodName, ArrayList<Object> args) {
        switch (methodName) {
            case "close":
                hideOrClose();
                return null;
            default:
                return FormatUtil.format("method {0} not implemented in {1}", methodName, getClass().getSimpleName());
        }
    }

    private void createAndPopulateTable() {
        table = new FlexTable();
        cellFormatter = table.getCellFormatter();
        setWindowSize(defaultWidth(), defaultHeight());
        setBackgroundColor(table, TABLE_WIDGET_COLOR);

        table.setBorderWidth(0);
        table.setCellPadding(0);
        table.setCellSpacing(0);

        createTopLeftCorner();
        createTopEdge();
        createTopRightCorner();
        createLeftEdge();

        createContentFrame();

        createRightEdge();
        createBottomLeftCorner();
        createBottomEdge();
        createBottomRightCorner();

        setWidget(table);
    }

    void createBottomEdge() {
        bottomEdgeWidget = setWidgetRowColWidthHeight(2, 1, -1, EDGE_WIDTH, createEdgeWidget(GwtWindowEdge.SideLocation.BOTTOM));
    }

    void createLeftEdge() {
        leftEdgeWidget = setWidgetRowColWidthHeight(1, 0, EDGE_WIDTH, -1, createEdgeWidget(GwtWindowEdge.SideLocation.LEFT));
    }

    void createContentFrame() {
        contentFrameWidget = setWidgetRowColWidthHeight(1, 1, -1, -1, createContentFrameWidget());
    }

    void createRightEdge() {
        rightEdgeWidget = setWidgetRowColWidthHeight(1, 2, EDGE_WIDTH, -1, createEdgeWidget(GwtWindowEdge.SideLocation.RIGHT));
    }

    void createTopEdge() {
        topEdgeWidget = setWidgetRowColWidthHeight(0, 1, -1, EDGE_WIDTH, createEdgeWidget(GwtWindowEdge.SideLocation.TOP));
    }

    void createBottomLeftCorner() {
        bottomLeftCornerWidget = setWidgetRowColWidthHeight(2, 0, EDGE_WIDTH, EDGE_WIDTH, createCornerWidget(GwtWindowCorner.CornerLocation.BOTTOM_LEFT));
        addClass(bottomLeftCornerWidget, "bottomLeftCorner");
    }

    void createBottomRightCorner() {
        bottomRightCornerWidget = setWidgetRowColWidthHeight(2, 2, EDGE_WIDTH, EDGE_WIDTH, createCornerWidget(GwtWindowCorner.CornerLocation.BOTTOM_RIGHT));
        addClass(bottomRightCornerWidget, "bottomRightCorner");
    }

    void createTopLeftCorner() {
        topLeftCornerWidget = setWidgetRowColWidthHeight(0, 0, EDGE_WIDTH, EDGE_WIDTH, createCornerWidget(GwtWindowCorner.CornerLocation.TOP_LEFT));
        addClass(topLeftCornerWidget, "topLeftCorner");
    }

    void createTopRightCorner() {
        topRightCornerWidget = setWidgetRowColWidthHeight(0, 2, EDGE_WIDTH, EDGE_WIDTH, createCornerWidget(GwtWindowCorner.CornerLocation.TOP_RIGHT));
        addClass(topRightCornerWidget, "topRightCorner");
    }

    GwtWindowContentFrame createContentFrameWidget() {
        GwtWindowContentFrame contentFrame = new GwtWindowContentFrame(this, CONTENT_WIDGET_COLOR);
        headerBar = createHeaderBarWidget();
        addClass(headerBar, "headerBarBorders");
        contentFrame.addNorth(headerBar, HEADER_BAR_HEIGHT);

        if (hasButtonBar()) {
            buttonBar = createButtonBarWidget();
            addClass(buttonBar, "buttonBarBorders");
            contentFrame.addSouth(buttonBar, BUTTON_BAR_HEIGHT);
        }

        contentWidget = defaultContent();
        contentWidget.setWidth("100%");
        contentWidget.setHeight("100%");
        contentWidget.sinkEvents(Event.ONDBLCLICK);

        contentHolder = new SimplePanel();
        contentFrame.add(contentHolder);
        setContent(defaultContent());

        return contentFrame;
    }

    GwtWindowHeaderBar createHeaderBarWidget() {
        GwtWindowHeaderBar headerBar = new GwtWindowHeaderBar(this);
        headerBar.setHeight(HEADER_BAR_HEIGHT + "px");
        headerBar.getElement().getStyle().setBackgroundColor(HEADER_BAR_COLOR);
        return headerBar;
    }

    GwtWindowButtonBar createButtonBarWidget() {
        GwtWindowButtonBar buttonBar = defaultButtonBar();
        buttonBar.setHeight(BUTTON_BAR_HEIGHT + "px");
        buttonBar.getElement().getStyle().setBackgroundColor(BUTTON_BAR_COLOR);
        return buttonBar;
    }

    GwtWindowEdge createEdgeWidget(GwtWindowEdge.SideLocation sideLocation) {
        return new GwtWindowEdge(this, sideLocation, EDGE_WIDTH, SIDE_WIDGET_COLOR);
    }

    GwtWindowCorner createCornerWidget(GwtWindowCorner.CornerLocation location) {
        return new GwtWindowCorner(this, location, CORNER_WIDGET_COLOR);
    }

    protected GwtWindowButtonBar defaultButtonBar() {
        return new GwtWindowButtonBar(this);
    }

    public static void err(Object... args) {
        Logable.err(args);
    }

    protected void initializeAfterLayout() {
    }

    public int getHeight() {
        return table.getElement().getClientHeight();
    }

    public String getTitle() {
        return headerBar.getTitle();
    }

    public int getWidth() {
        return table.getElement().getClientWidth();
    }

    public int getZIndex() {
        return zIndex;
    }

    protected boolean hasButtonBar() {
        return true;
    }

    public void hideOrClose() {
        if (defaultHideOnClose())
            toggleVisible();
        else
            close();
    }

    public static void info(Object... args) {
        Logable.info(args);
    }

    public static String infoClass(Object arg) {
        return Logable.infoClass(arg);
    }

    public void maximizeOrRestore() {
        if (isMaximized) {
            restoreSizeAndLocation();
            isMaximized = false;
        } else {
            saveSizeAndLocation();
            setLocation(new Point(0, 0));
            setWindowSize(WindowUtil.getPctScreenWidth(100), WindowUtil.getPctScreenHeight(100));
            isMaximized = true;
            afterResize();
        }
    }

    public void minimize() {
        if (isMinimized)
            return;
        minimizeOrRestore();
    }

    public void minimizeOrRestore() {
        if (isMinimized) {
            WindowManager.getInstance().removeMinimizedWindow(this);
            restoreSizeAndLocation();
            isMinimized = false;
        } else {
            saveSizeAndLocation();
            if (buttonBar != null)
                buttonBar.setVisible(false);
            setWindowSize(CommonWindowConstants.MinimizedWindowWidth, CommonWindowConstants.MinimizedWindowHeight);
            isMinimized = true;
            afterResize();
            WindowManager.getInstance().addMinimizedWindow(this);
        }
    }

    public void moveToFront() {
        WindowManager.getInstance().moveToFront(this);
    }

    @Override
    public void onBrowserEvent(Event event) {
        switch (DOM.eventGetType(event)) {
            case Event.ONCLICK:
                moveToFront();
                break;
            default:
                super.onBrowserEvent(event);
                break;
        }
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        sessionHandlerRegistration = eventBinder.bindEventHandlers(this, SessionEventBus.getInstance());
        WindowManager.getInstance().addActiveWindow(this);
    }

    public void onButtonClicked(String actionTag) {
        Logable.info("onButtonClicked", actionTag);
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        sessionHandlerRegistration.removeHandler();
        WindowManager.getInstance().removeActiveWindow(this);
    }

    public void onMenuItemClicked(String actionTag) {
        Logable.info("onMenuItemClicked", actionTag);
    }

    @Override
    public void onMouseMove(Event event) {
    }

    @Override
    public void onMouseUp(Event event) {
    }

    public void onHeaderDoubleClick() {
        if (isMinimized)
            minimizeOrRestore();
        else if (isMaximized)
            maximizeOrRestore();
    }

    @EventHandler
    public void onSessionStateChanged(SessionEvent event) {
        onSessionStateChanged(event.getSessionState());
    }

    @Override
    public void onSessionStateChanged(SessionState sessionState) {
    }

    public void removeClass(Widget widget, String className) {
        widget.getElement().removeClassName(className);
    }

    public void restore() {
        if (!isMinimized)
            return;
        minimizeOrRestore();
    }

    protected void restoreSizeAndLocation() {
        if (savedHeight < 0 || savedWidth < 0)
            return;
        if (buttonBar != null)
            buttonBar.setVisible(true);
        setWindowSize(savedWidth, savedHeight);
        afterResize();
        setLocation(savedLocation);
        savedHeight = savedWidth = -1;
        moveToFront();
    }

    protected void saveSizeAndLocation() {
        savedHeight = getHeight();
        savedWidth = getWidth();
        savedLocation = new Point(getPopupLeft(), getPopupTop());
    }

    void setBackgroundColor(Widget widget, String bgColor) {
        widget.getElement().getStyle().setBackgroundColor(bgColor);
    }

    public void setTitle(String title) {
        headerBar.setTitle(title);
    }

    public void setWindowPosition(int left, int top) {
        setPopupPosition(left, top);
    }

    public boolean setWindowSize(int width, int height) {
        if (width < MINIMUM_WINDOW_WIDTH || height < MINIMUM_WINDOW_HEIGHT)
            return false;
        table.setPixelSize(width, height);
        return true;
    }

    public void setLocation(Point p) {
        setWindowPosition(p.x, p.y);
    }

    public void setContent(Widget widget) {
        contentWidget = widget;
        contentWidget.setWidth("100%");
        contentWidget.setHeight("100%");
        contentHolder.setWidget(contentWidget);
    }

    public void setHeight(int height) {
        setHeight(height + "px");
    }

    Widget setWidgetRowColWidthHeight(int row, int col, int width, int height, Widget widget) {
        table.setWidget(row, col, widget);
        String heightStr = height >= 0 ? height + "px" : "100%";
        String widthStr = width >= 0 ? height + "px" : "100%";
        setCellWidthHeight(row, col, widthStr, heightStr);
        return widget;
    }

    public void setWidth(int width) {
        setWidth(width + "px");
    }

    void setCellWidthHeight(int row, int col, String width, String height) {
        cellFormatter.setWidth(row, col, width);
        cellFormatter.setHeight(row, col, height);
    }

    public void setZIndex() {
        setZIndexStyle(zIndex);
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
        setZIndexStyle(this.zIndex);
    }

    public void setZIndexStyle(int zIndex) {
        getElement().getStyle().setZIndex(zIndex);
    }

    @Override
    public void show() {
        show(true);
    }

    public void show(boolean moveToFront) {
        super.show();
        if (moveToFront)
            moveToFront();
    }

    public void toggleVisible() {
        if (isVisible())
            hide();
        else
            show();
    }

    interface SessionEventBinder extends EventBinder<GwtWindow> {
    }

    private final GwtWindow.SessionEventBinder eventBinder = GWT.create(GwtWindow.SessionEventBinder.class);

    Widget bottomEdgeWidget;
    Widget bottomLeftCornerWidget;
    Widget bottomRightCornerWidget;
    protected GwtWindowButtonBar buttonBar;
    HTMLTable.CellFormatter cellFormatter;
    Widget contentFrameWidget;
    SimplePanel contentHolder;
    Widget contentWidget;
    protected GwtWindowHeaderBar headerBar;
    protected boolean isMaximized = false;
    protected boolean isMinimized = false;
    Widget leftEdgeWidget;
    Widget rightEdgeWidget;
    protected int savedHeight = -1;
    protected Point savedLocation;
    protected int savedWidth = -1;
    HandlerRegistration sessionHandlerRegistration;
    FlexTable table;
    Widget topEdgeWidget;
    Widget topLeftCornerWidget;
    Widget topRightCornerWidget;
    public WindowObject windowObject;
    int zIndex;
}
