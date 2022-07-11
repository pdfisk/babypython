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

import net.babypython.client.vm.constants.ProcessConstants;
import net.babypython.client.vm.process.events.VmProcessEvent;
import net.babypython.client.vm.process.events.VmProcessEventBus;
import net.babypython.client.vm.interfaces.IProcess;
import net.babypython.client.ui.util.Logable;

import java.util.Date;

public abstract class VmSchedulerProcess extends Logable implements Comparable<VmSchedulerProcess>, IProcess {

    public VmSchedulerProcess() {
        eventBus = VmProcessEventBus.getInstance();
        processState = VmProcessState.Stopped;
        id = idCounter++;
        isRescheduled = false;
        name = getName();
        totalTimesScheduled = 0;
        setWakeup(0);
    }

    public boolean canReschedule() {
        switch (getProcessState()) {
            case Ended:
            case Running:
            case Stopped:
            case Suspended:
                return false;
            default:
                return !isWaiting() && checkToTimesScheduled();
        }
    }

    boolean checkToTimesScheduled() {
        if (totalTimesScheduled <= ProcessConstants.VmProcessRescheduleLimit)
            return true;
        info("totalTimesScheduled", totalTimesScheduled, "exceeds limit", ProcessConstants.VmProcessRescheduleLimit);
        setEnded();
        return false;
    }

    @Override
    public int compareTo(VmSchedulerProcess vmProcess) {
        if (wakeupTime < vmProcess.wakeupTime)
            return -1;
        if (wakeupTime > vmProcess.wakeupTime)
            return 1;
        return 0;
    }

    void fireEvent() {
        eventBus.fireEvent(new VmProcessEvent(this));
    }

    protected abstract String getName();

    public String getMessage() {
        switch (getProcessState()) {
            case Ended:
                return messageEnded();
            case Resuming:
                return messageResuming();
            case Running:
                return messageRunning();
            case Scheduled:
                return messageScheduled();
            case Sleeping:
                return messageSleeping();
            case Started:
                return messageStarted();
            case Stopped:
                return messageStopped();
            case Suspended:
                return messageSuspended();
            case TimedOut:
                return messageTimedOut();
            default:
                return "no message for " + getProcessState();
        }
    }

    public VmProcessState getProcessState() {
        return processState;
    }

    public int getWakeup() {
        if (isEnded())
            return -1;
        return (int) (wakeupTime - (new Date()).getTime());
    }

    public boolean isEnded() {
        return processState == VmProcessState.Ended;
    }

    public boolean isResuming() {
        return processState == VmProcessState.Resuming;
    }

    public boolean isRunning() {
        return processState == VmProcessState.Running;
    }

    public boolean isScheduled() {
        return processState == VmProcessState.Scheduled;
    }

    public boolean isSleeping() {
        return processState == VmProcessState.Sleeping;
    }

    public boolean isStarted() {
        return processState == VmProcessState.Started;
    }

    public boolean isStopped() {
        return processState == VmProcessState.Stopped;
    }

    public boolean isSuspended() {
        return processState == VmProcessState.Suspended;
    }

    public boolean isTimedOut() {
        return processState == VmProcessState.TimedOut;
    }

    public boolean isWaiting() {
        return getWakeup() > 0;
    }

    public String messageEnded() {
        return id + " " + name + " ended";
    }

    public String messageResuming() {
        return id + " " + name + " resuming";
    }

    public String messageRunning() {
        return id + " " + name + " running";
    }

    public String messageScheduled() {
        return id + " " + name + " scheduled";
    }

    public String messageSleeping() {
        return id + " " + name + " sleeping";
    }

    public String messageStarted() {
        return id + " " + name + " started";
    }

    public String messageStopped() {
        return id + " " + name + " stopped";
    }

    public String messageSuspended() {
        return id + " " + name + " suspended";
    }

    public String messageTimedOut() {
        return id + " " + name + " timed out";
    }

    public void reset() {
    }

    public void resume() {
        reset();
        setResuming();
    }

    @Override
    public abstract void run();

    public void schedule() {
        VmScheduler.schedule(this);
    }

    public void setEnded() {
        processState = VmProcessState.Ended;
        fireEvent();
    }

    public void setResuming() {
        if (isEnded())
            return;
        processState = VmProcessState.Resuming;
        fireEvent();
    }

    public void setRunning() {
        if (isEnded())
            return;
        processState = VmProcessState.Running;
        fireEvent();
    }

    public void setScheduled() {
        if (isEnded())
            return;
        totalTimesScheduled++;
        processState = VmProcessState.Scheduled;
        fireEvent();
    }

    public void setSleeping() {
        if (isEnded())
            return;
        processState = VmProcessState.Sleeping;
        fireEvent();
    }

    public void setStarted() {
        if (isEnded())
            return;
        processState = VmProcessState.Started;
        fireEvent();
    }

    public void setStopped() {
        if (isEnded())
            return;
        processState = VmProcessState.Stopped;
        fireEvent();
    }

    public void setSuspended() {
        if (isEnded())
            return;
        processState = VmProcessState.Suspended;
        fireEvent();
    }

    public void setTimedOut() {
        if (isEnded())
            return;
        processState = VmProcessState.TimedOut;
        fireEvent();
    }

    public void setWakeup(int milliseconds) {
        if (isEnded())
            return;
        wakeupTime = (new Date()).getTime() + milliseconds;
    }

    public void sleep(int milliseconds) {
    }

    static int idCounter = 0;
    public String errorCondition;
    public VmProcessEventBus eventBus;
    public int id;
    public boolean isRescheduled;
    public String name;
    public VmProcessState processState;
    public int totalTimesScheduled;
    public long wakeupTime;
}
