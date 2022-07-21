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
package net.babypython.client.ui.constants;

import net.babypython.client.vm.util.structures.Point;

public class CommonWindowConstants {

    // auto, percents
    public static final String Auto = "auto";
    public static final String Fill = "100%";
    public static final int Percent15 = 15;
    public static final int Percent20 = 20;
    public static final int Percent25 = 25;
    public static final int Percent30 = 30;
    public static final int Percent50 = 50;
    public static final int Percent97 = 97;

    public static int percentRest(int pct) {
        return Percent97 - pct;
    }

    public static final String percentStr(int pct) {
        return pct + "%";
    }

    public static final String percentRestStr(int pct) {
        return percentStr(percentRest(pct));
    }

    // common
    public static final int DefaultBrowserWidth = 525;
    public static final int DefaultBrowserHeight = 375;
    public static final int DefaultCodeBrowserHeight = 425;
    public static final int DefaultInspectorWidth = 525;
    public static final int DefaultInspectorHeight = 375;
    public static final int DefaultWindowWidth = 325;
    public static final int DefaultWindowHeight = 275;

    // button bar
    public static final int ButtonBarHeight = 26;
    public static final int ButtonBarLeftPadding = 7;
    public static final int ButtonBarRightPadding = 7;
    public static final int ButtonBarSpacing = 7;
    public static final int ButtonBarTextBoxHeight = 20;
    public static final int ButtonBarTextBoxWidth = 125;
    public static final int ButtonBarTopPadding = 3;

    // dashboard window
    public static final String DashboardWindowHeader = "Dashboard";
    public static final int DashboardWindowWidthPct = 45;
    public static final int DashboardWindowHeightPct = 45;
    public static final int DashboardWindowLeftInset = 15;
    public static final int DashboardWindowRightInset = 5;
    public static final int DashboardWindowTopInset = 15;
    public static final int DashboardWindowBottomInset = 5;
    public static final Point DashboardWindowPosition = new Point(DashboardWindowLeftInset, DashboardWindowTopInset);

    // dictionary inspector window
    public static final String DictionaryInspectorWindowHeader = "Dictionary Inspector";
    public static final int DictionaryInspectorWindowWidth = DefaultInspectorWidth;
    public static final int DictionaryInspectorWindowHeight = DefaultInspectorHeight;

    // documentation window
    public static final String DocumentationWindowHeader = "Little Python Documentation";
    public static final int DocumentationWindowWidthPct = 90;
    public static final int DocumentationWindowHeightPct = 60;
    public static final Point DocumentationWindowPosition = new Point(15, 55);

    // google groups window
    public static final String GoogleGroupsWindowHeader = "Little Python Support Group";

    // info window
    public static final int InfoListPanelWidth = 250;

    // launcher window
    public static final String LauncherWindowTitle = "App Launcher";
    public static final int LauncherWindowWidth = 275;
    public static final int LauncherWindowHeight = 325;

    // list inspector window
    public static final String ListInspectorWindowHeader = "List Inspector";
    public static final int ListInspectorWindowWidth = DefaultInspectorWidth;
    public static final int ListInspectorWindowHeight = DefaultInspectorHeight;

    // login window
    public static final String LoginWindowHeader = "Login";
    public static final int LoginWindowWidth = 275;
    public static final int LoginWindowHeight = 132;
    public static final int LoginWindowLabelWidth = 60;
    public static final int LoginWindowWidgetAdjust = 3;

    // minimized window
    public static final int MinimizedWindowWidth = 200;
    public static final int MinimizedWindowHeight = 37;

    // module browser window
    public static final String ModuleBrowserWindowHeader = "Module Browser";
    public static final int ModuleBrowserWindowWidth = DefaultBrowserWidth;
    public static final int ModuleBrowserWindowHeight = DefaultCodeBrowserHeight;

    // modules browser window
    public static final String ModulesBrowserWindowHeader = "Modules Browser";
    public static final int ModulesBrowserWindowWidth = DefaultBrowserWidth;
    public static final int ModulesBrowserWindowHeight = DefaultBrowserHeight;

    // news window
    public static final String NewsWindowHeader = "News";
    public static final int NewsWindowWidthPct = 55;
    public static final int NewsWindowHeightPct = 45;
    public static final int NewsWindowTopInset = 15;
    public static final int NewsWindowBottomInset = 5;
    public static final int NewsWindowLeftInset = 5;
    public static final int NewsWindowRightInset = -15;
    public static final Point NewsWindowPosition = new Point(NewsWindowRightInset, NewsWindowTopInset);

    // policy window
    public static final String CookiePolicyWindowTitle = "Cookie Policy";
    public static final String CookiePolicyWindowFileName = "cookie_policy.html";
    public static final String PrivacyPolicyWindowTitle = "Privacy Policy";
    public static final String PrivacyPolicyWindowFileName = "privacy_policy.html";
    public static final int PolicyWindowWidth = 425;
    public static final int PolicyWindowHeight = 375;

    // projects
    public static final String ProjectsSharedProjectsWindowHeader = "Shared Projects";
    public static final int ProjectsWindowWidthPct = 70;
    public static final int ProjectsWindowHeightPct = 70;

    // register window
    public static final String RegisterWindowHeader = "Register";
    public static final int RegisterWindowWidth = 275;
    public static final int RegisterWindowHeight = 132;
    public static final int RegisterWindowLabelWidth = 60;
    public static final int RegisterWindowWidgetAdjust = 3;

    // scripts window
    public static final String LibraryScriptsWindowHeader = "Library Scripts";
    public static final String UserScriptsWindowHeader = "My Scripts";
    public static final int ScriptsWindowWidth = DefaultBrowserWidth;
    public static final int ScriptsWindowHeight = DefaultBrowserHeight;

    // split layout
    public static final int SplitLayoutSplitterWidth = 7;

    // transcript window
    public static final String TranscriptWindowHeader = "Transcript";
    public static final int TranscriptWindowWidth = 235;
    public static final int TranscriptWindowWidthWide = 375;
    public static final int TranscriptWindowWidthVeryWide = 525;
    public static final int TranscriptWindowHeight = 325;
    public static final Point TranscriptWindowPosition = new Point(15, 55);

    // welcome window
    public static final String WelcomeWindowTitle = "Welcome to Baby Python!";
    public static final int WelcomeWindowWidthPct = 70;
    public static final int WelcomeWindowHeightPct = 70;

    // workbench window
    public static final String WorkbenchWindowTitle = "Coding Workbench";
    public static final int WorkbenchWindowWidth = 625;
    public static final int WorkbenchWindowHeight = 425;

    // workspaces window
    public static final String WorkspacesWindowHeader = "Workspaces";

}
