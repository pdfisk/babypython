/**
 * MIT License
 * <p>
 * Copyright (c) 2022 Peter Fisk
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.babypython.client.ui.viewport.navbar;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.SimplePanel;
import net.babypython.client.constants.AppConstants;
import net.babypython.client.python.api.PythonApi;
import net.babypython.client.ui.constants.*;
import net.babypython.client.ui.gwt.widgets.GwtDockPanel;
import net.babypython.client.ui.session.SessionState;
import net.babypython.client.ui.util.DomUtils;
import net.babypython.client.ui.util.PwaUtil;
import net.babypython.client.ui.viewport.widgets.desktop.WindowManager;
import net.babypython.client.ui.viewport.widgets.navbar.*;
import net.babypython.client.ui.windows.projects.ProjectsWindow;
import net.babypython.client.ui.windows.session.login.LoginWindow;
import net.babypython.client.ui.windows.session.register.RegisterWindow;
import net.babypython.client.ui.windows.transcript.TranscriptWindow;
import net.babypython.client.ui.windows.workbench.WorkbenchWindow;
import net.babypython.client.vm.interfaces.ISessionState;

public class NavBar extends GwtDockPanel implements ISessionState {

    public NavBar() {
        super();
        pythonApi = getPythonApi();
        setHeight(DimensionConstants.NavBarHeight + "px");
        getElement().getStyle().setBackgroundColor(ColorConstants.NavBarBackgroundGray);
        getElement().getStyle().setColor(ColorConstants.NavBarTextColor);
        menuDockPanel = new GwtDockPanel();
        outerDockPanel = new GwtDockPanel();
        verticalDockPanel = new GwtDockPanel();
        addLogo();
        createCenterMenuBar();
        createInfoPanel();
        addCenterMenus();
        menuDockPanel.add(centerMenuBar);
        verticalDockPanel.addNorth(menuDockPanel, DimensionConstants.NavBarCenterMenuBarHeight);
        verticalDockPanel.addSouth(infoPanel, DimensionConstants.NavBarInfoPanelHeight);
        outerDockPanel.add(verticalDockPanel);
        addWest(new SimplePanel(), DimensionConstants.NavBarPaddingLeftRight);
        addEast(new SimplePanel(), DimensionConstants.NavBarPaddingLeftRight);
        add(outerDockPanel);
    }

    protected void addCenterMenus() {
        addDocsMenu();
        addToolsMenu();
        addForumMenu();
        addGitHubMenu();
        addYouTubeMenu();
        addDonateMenu();
        addLoginMenus();
    }

    protected PythonApi getPythonApi() {
        return PythonApi.getInstance();
    }

    protected void onProjects() {
        ProjectsWindow.getInstance().show();
    }

    protected void onWorkbench() {
        new WorkbenchWindow();
    }

    void addLoginMenus() {
        MenuBar loginMenuBar = new MenuBar();
        loginMenuBar.getElement().getStyle().setBackgroundImage(null);
        loginMenuBar.getElement().getStyle().setBackgroundColor(ColorConstants.NavBarBackgroundLightBlue);
        loginMenu = new LoginMenu(this);
        loginMenu.setAnimationEnabled(true);
        loginMenu.getElement().addClassName("subMenuStyle");
        loginMenuBar.addItem(new MenuItem("Login", loginMenu));
        menuDockPanel.addEast(loginMenuBar, DimensionConstants.NavBarLoginMenuSize);
    }

    void addLogo() {
        Logo logo = new Logo();
        logo.setUrl(ImageConstants.logoUrl);
        logo.getElement().getStyle().setMarginTop(2, Style.Unit.PX);
        outerDockPanel.addWest(logo, DimensionConstants.NavBarLogoWidth);
        SimplePanel spacer = new SimplePanel();
        outerDockPanel.addWest(spacer, 25);
    }

    protected void addDonateMenu() {
        MenuBar donateMenu = new MenuBar(true);
        donateMenu.setAnimationEnabled(true);

        if (AppConstants.SHOW_MERCHANDISE) {
            donateMenu.addItem("Merchandise", new Command() {
                @Override
                public void execute() {
                    onMerchandise();
                }
            });
        }

        if (AppConstants.SHOW_PATREON) {
            donateMenu.addSeparator();
            donateMenu.addItem("Patreon", new Command() {
                @Override
                public void execute() {
                    onPatreon();
                }
            });
        }

        centerMenuBar.addItem(new MenuItem("Donate", donateMenu));
    }

    protected void addDocsMenu() {
        centerMenuBar.addItem("Docs", new Command() {
            @Override
            public void execute() {
                onDocs();
            }
        });
    }

    protected void addForumMenu() {
        centerMenuBar.addItem(new MenuItem("Forum", new Command() {
            @Override
            public void execute() {
                onForum();
            }
        }));
    }

    protected void addGitHubMenu() {
        centerMenuBar.addItem(new MenuItem("GitHub", new Command() {
            @Override
            public void execute() {
                onGitHub();
            }
        }));
    }

    protected void addYouTubeMenu() {
        centerMenuBar.addItem(new MenuItem("YouTube", new Command() {
            @Override
            public void execute() {
                onYouTube();
            }
        }));
    }

    protected void addToolsMenu() {
        MenuBar toolsMenu = new MenuBar(true);
        toolsMenu.setAnimationEnabled(true);

        toolsMenu.addItem(ViewportConstants.ToolsMenuProjects, new Command() {
            @Override
            public void execute() {
                onProjects();
            }
        });

        toolsMenu.addItem(ViewportConstants.ToolsMenuWorkbench, new Command() {
            @Override
            public void execute() {
                onWorkbench();
            }
        });

        toolsMenu.addSeparator();

        toolsMenu.addItem(ViewportConstants.ToolsMenuTranscript, new Command() {
            @Override
            public void execute() {
                onTranscript();
            }
        });

        toolsMenu.addSeparator();

        MenuBar windowsMenu = new MenuBar(true);
        windowsMenu.setAnimationEnabled(true);

        windowsMenu.addItem("Minimize All Windows", new Command() {
            @Override
            public void execute() {
                onMinimizeAllWindows();
            }
        });

        windowsMenu.addItem("Restore All Windows", new Command() {
            @Override
            public void execute() {
                onRestoreAllWindows();
            }
        });

        windowsMenu.addSeparator();

        windowsMenu.addItem("Close All Windows", new Command() {
            @Override
            public void execute() {
                onCloseAllWindows();
            }
        });

        toolsMenu.addItem(new MenuItem("Windows", windowsMenu));

        centerMenuBar.addItem(new MenuItem(ViewportConstants.ToolsMenu, toolsMenu));
    }

    void createCenterMenuBar() {
        centerMenuBar = new MenuBar();
        centerMenuBar.getElement().getStyle().setBackgroundImage(null);
        centerMenuBar.getElement().getStyle().setBackgroundColor(ColorConstants.NavBarBackgroundLightBlue);
    }

    void createInfoPanel() {
        infoPanel = InfoPanel.getInstance();
    }

    @Override
    protected boolean isFullHeight() {
        return false;
    }

    void onJavaDoc() {
        DomUtils.openBrowserTab(UrlConstants.JavaDoc);
    }

    void onDocs() {
        DomUtils.openBrowserTab(UrlConstants.Docs);
    }

    void onFacebookBabyPython() {
        DomUtils.openBrowserTab(UrlConstants.Facebook);
    }

    void onTwitterBabyPython() {
        DomUtils.openBrowserTab(UrlConstants.Twitter);
    }

    public void onLogin() {
        new LoginWindow();
    }

    public void onRegister() {
        new RegisterWindow();
    }

    void onGitHub() {
        DomUtils.openBrowserTab(UrlConstants.GitHub);
    }

    void onYouTube() {
        DomUtils.openBrowserTab(UrlConstants.YouTube);
    }

    void onTranscript() {
        TranscriptWindow.getInstance().show();
    }

    void onMinimizeAllWindows() {
        WindowManager.getInstance().minimizeAllWindows();
    }

    void onRestoreAllWindows() {
        WindowManager.getInstance().restoreAllWindows();
    }

    void onCloseAllWindows() {
        WindowManager.getInstance().closeAllWindows();
    }

    int getNavBarCenterMenuBarWidth() {
        int width = DimensionConstants.NavBarCenterMenuBarWidth;
        if (isStandalone())
            width += DimensionConstants.NavBarFileMenuWidth;
        return width;
    }

    boolean isStandalone() {
        return PwaUtil.isStandalone();
    }

    void onMerchandise() {
        DomUtils.openBrowserTab(UrlConstants.Spring);
    }

    void onPatreon() {
        DomUtils.openBrowserTab(UrlConstants.Patreon);
    }

    void onForum() {
        DomUtils.openBrowserTab(UrlConstants.Forum);
    }

    void onShowVersion() {
        NavBarMessageBox.showMessage("Version: " + AppConstants.VERSION + " build: " + AppConstants.BUILD_NUMBER);
    }

    void onShowTimestamp() {
        NavBarMessageBox.showMessage(AppConstants.TIMESTAMP);
    }

    void onSupportGroup() {
        DomUtils.openBrowserTab(UrlConstants.Support);
    }

    void onQuora() {
        DomUtils.openBrowserTab(UrlConstants.Quora);
    }

    void onTwitter() {
        DomUtils.openBrowserTab(UrlConstants.Twitter);
    }

    @Override
    public void onSessionStateChanged(SessionState sessionState) {
//        loginMenu.onSessionStateChanged(sessionState);
    }

    protected MenuBar centerMenuBar;
    InfoPanel infoPanel;
    SessionMenuBar loginMenu;
    GwtDockPanel menuDockPanel;
    GwtDockPanel outerDockPanel;
    PythonApi pythonApi;
    GwtDockPanel verticalDockPanel;
}
