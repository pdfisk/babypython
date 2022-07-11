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
package net.babypython.client.vm.process.simple;

import net.babypython.client.vm.constants.ProcessConstants;
import net.babypython.client.vm.process.scheduler.VmSchedulerProcess;

public class WorkspaceLoadProcess extends VmSchedulerProcess {

//    public static void schedule(AsyncCallback<WorkspaceRecord> asyncCallback) {
//        new WorkspaceLoadProcess(asyncCallback).schedule();
//    }
//
//    WorkspaceLoadProcess(AsyncCallback<WorkspaceRecord> asyncCallback) {
//        this.asyncCallback = asyncCallback;
//    }

    @Override
    protected String getName() {
        return ProcessConstants.SimpleProcess;
    }

//    void handleWorkspaceRecords(RecordList<WorkspaceRecord> recordList) {
//        boolean workspaceFound = false;
//        for (WorkspaceRecord record : recordList) {
//            if (record.getName() == WorkspaceConstants.LibraryWorkspaceName)
//                asyncCallback.onSuccess(record);
//            workspaceFound = true;
//            break;
//        }
//        if (!workspaceFound)
//            err("Workspace " + WorkspaceConstants.LibraryWorkspaceName + " was not found");
//    }

    @Override
    public void run() {
//        try {
//            WorkspacesStore.getInstance().loadFromDatabase(0, new AsyncCallback<RecordList<WorkspaceRecord>>() {
//                @Override
//                public void onFailure(Throwable caught) {
//                    err("WorkspaceLoadProcess", caught.getMessage());
//                }
//
//                @Override
//                public void onSuccess(RecordList<WorkspaceRecord> result) {
//                    handleWorkspaceRecords(result);
//                }
//            });
//        } catch (Exception e) {
//            err("Exception", e.getMessage());
//            asyncCallback.onFailure(e);
//        } finally {
//            setEnded();
//        }
    }

//    AsyncCallback<WorkspaceRecord> asyncCallback;
}
