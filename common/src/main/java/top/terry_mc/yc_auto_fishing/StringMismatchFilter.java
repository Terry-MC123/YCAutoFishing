package top.terry_mc.yc_auto_fishing;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

public class StringMismatchFilter implements Filter {
    @Override
    public Result getOnMismatch() {
        return Result.NEUTRAL;
    }

    @Override
    public Result getOnMatch() {
        return Result.NEUTRAL;
    }

    public Result check(String s) {
        if (s.contains("Received passengers for unknown entity"))
            return Result.DENY;
        return Result.NEUTRAL;
    }

    @Override
    public Result filter(LogEvent event) {
        return this.check(event.getMessage().getFormattedMessage());
    }

    @Override
    public Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object... arg4) {
        return this.check(message);
    }

    @Override
    public Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4) {
        return this.check(message);
    }

    @Override
    public Result filter(Logger arg0, Level arg1, Marker arg2, Object message, Throwable arg4) {
        return this.check(message.toString());
    }

    @Override
    public Result filter(Logger arg0, Level arg1, Marker arg2, Message message, Throwable arg4) {
        return this.check(message.getFormattedMessage());
    }

    @Override
    public Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5) {
        return this.check(message);
    }

    @Override
    public Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6) {
        return this.check(message);
    }

    @Override
    public Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7) {
        return this.check(message);
    }

    @Override
    public Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8) {
        return this.check(message);
    }

    @Override
    public Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9) {
        return this.check(message);
    }

    @Override
    public Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10) {
        return this.check(message);
    }

    @Override
    public Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11) {
        return this.check(message);
    }

    @Override
    public Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12) {
        return this.check(message);
    }

    @Override
    public Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13) {
        return this.check(message);
    }

    @Override
    public State getState() {
        return State.STARTED;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public boolean isStopped() {
        return false;
    }
}