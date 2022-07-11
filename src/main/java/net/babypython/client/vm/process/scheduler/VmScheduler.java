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
package net.babypython.client.vm.process.scheduler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Timer;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import net.babypython.client.vm.process.events.VmProcessEvent;
import net.babypython.client.vm.process.events.VmProcessEventBus;
import net.babypython.client.vm.interfaces.IProcessState;
import net.babypython.client.ui.util.Logable;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class VmScheduler extends Logable implements IProcessState {

    public static void schedule(VmSchedulerProcess process) {
        getInstance().scheduleProcess(process);
    }

    void activateProcess(VmSchedulerProcess process) {
        if (process.isEnded())
            return;
        if (process.getWakeup() <= 0) {
            if (!activeProcesses.contains(process))
                activeProcesses.add(process);
        } else {
            if (!sleepingProcesses.contains(process)) {
                sleepingProcesses.add(process);
                if (wakeupTimer != null)
                    wakeupTimer.cancel();
                wakeupTimer = new Timer() {
                    @Override
                    public void run() {
                        activateSleepingProcess();
                    }
                };
                wakeupTimer.schedule(process.getWakeup());
            }
        }
        runNextProcess();
    }

    void activateSleepingProcess() {
        if (sleepingProcesses.size() == 0)
            return;
        VmSchedulerProcess process = sleepingProcesses.remove();
        process.setWakeup(0);
        activateProcess(process);
    }

    @EventHandler
    void onProcessEvent(VmProcessEvent event) {
        onProcessStateChanged(event.getProcess());
    }

    @Override
    public void onProcessStateChanged(VmSchedulerProcess process) {
        switch (process.getProcessState()) {
            case Resuming:
                activateProcess(process);
                break;
            case Running:
            case Scheduled:
            case Started:
//                info(process.getMessage());
                break;
            case Sleeping:
            case Stopped:
            case Suspended:
            case TimedOut:
                rescheduleOrRemoveCompletedProcess(process);
                break;
            case Ended:
                removeActiveProcess(process);
                break;
        }
    }

    public void removeActiveProcess(VmSchedulerProcess process) {
        if (activeProcesses.contains(process))
            activeProcesses.remove(process);
    }

    public void rescheduleProcess(VmSchedulerProcess process) {
        removeActiveProcess(process);
        if (!sleepingProcesses.contains(process))
            sleepingProcesses.add(process);
        setWakeupTimerDelay(process.getWakeup());
    }

    void rescheduleOrRemoveCompletedProcess(VmSchedulerProcess process) {
        switch (process.getProcessState()) {
            case Ended:
            case Stopped:
                removeActiveProcess(process);
                break;
            case Sleeping:
                rescheduleProcess(process);
                break;
            case Suspended:
                suspendProcess(process);
                break;
            case Scheduled:
            case TimedOut:
                if (process.canReschedule())
                    scheduleProcess(process);
                else
                    removeActiveProcess(process);
        }
    }

    void resumeNextSleepingProcess() {
        if (sleepingProcesses.size() == 0)
            return;
        VmSchedulerProcess process = sleepingProcesses.peek();
        int remainingTime = process.getWakeup();
        if (remainingTime > 100) {
            setWakeupTimerDelay(remainingTime);
            return;
        }
        process = sleepingProcesses.remove();
        if (sleepingProcesses.size() > 0) {
            VmSchedulerProcess nextProcess = sleepingProcesses.peek();
            setWakeupTimerDelay(nextProcess.getWakeup());
        }
        process.resume();
    }

    void runNextProcess() {
        if (activeProcesses.size() > 0)
            runFirstProcess();
    }

    void runFirstProcess() {
        VmSchedulerProcess firstProcess = activeProcesses.peek();
        int delay = firstProcess.getWakeup();
        if (delay <= 0) {
            try {
                activeProcesses.remove(firstProcess);
                runProcess(firstProcess);
            } catch (Exception e) {
            }
        } else
            info("trying to run a delayed process", firstProcess.getWakeup());
    }

    void runProcess(VmSchedulerProcess process) {
        if (!process.canReschedule())
            return;
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                if (!process.isEnded())
                    process.run();
            }
        });
    }

    void scheduleProcess(VmSchedulerProcess process) {
        process.setScheduled();
        activateProcess(process);
    }

    void setWakeupTimerDelay(int delayMilliseconds) {
        Scheduler.get().scheduleFixedDelay(new Scheduler.RepeatingCommand() {
            @Override
            public boolean execute() {
                resumeNextSleepingProcess();
                return false;
            }
        }, delayMilliseconds);
    }

    void suspendProcess(VmSchedulerProcess process) {
//        info("suspendProcess");
        removeActiveProcess(process);
        if (!suspendedProcesses.contains(process))
            suspendedProcesses.add(process);
    }

    private VmScheduler() {
        eventBinder.bindEventHandlers(this, VmProcessEventBus.getInstance());
        activeProcesses = new PriorityQueue<>();
        sleepingProcesses = new PriorityQueue<>();
        suspendedProcesses = new ArrayList<>();
    }

    public static VmScheduler getInstance() {
        if (instance == null)
            instance = new VmScheduler();
        return instance;
    }

    interface VmProcessorEventBinder extends EventBinder<VmScheduler> {
    }

    private final VmProcessorEventBinder eventBinder = GWT.create(VmProcessorEventBinder.class);

    PriorityQueue<VmSchedulerProcess> activeProcesses;
    PriorityQueue<VmSchedulerProcess> sleepingProcesses;
    ArrayList<VmSchedulerProcess> suspendedProcesses;
    Timer wakeupTimer;
    static VmScheduler instance;
}
