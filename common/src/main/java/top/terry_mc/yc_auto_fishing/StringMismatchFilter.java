package top.terry_mc.yc_auto_fishing;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

public class StringMismatchFilter extends AbstractFilter {
    private final String text;

    public StringMismatchFilter(final String text, final Result onMatch, final Result onMismatch) {
        super(onMatch, onMismatch);
        this.text = text;
    }

    @Override
    public Result filter(
            final Logger logger, final Level level, final Marker marker, final String msg, final Object... params) {
        return filter(logger.getMessageFactory().newMessage(msg, params).getFormattedMessage());
    }

    @Override
    public Result filter(
            final Logger logger, final Level level, final Marker marker, final Object msg, final Throwable t) {
        return filter(logger.getMessageFactory().newMessage(msg).getFormattedMessage());
    }

    @Override
    public Result filter(
            final Logger logger, final Level level, final Marker marker, final Message msg, final Throwable t) {
        return filter(msg.getFormattedMessage());
    }

    @Override
    public Result filter(final LogEvent event) {
        return filter(event.getMessage().getFormattedMessage());
    }

    private Result filter(final String msg) {
        return msg.contains(this.text) ? onMatch : onMismatch;
    }

    @Override
    public Result filter(
            final Logger logger, final Level level, final Marker marker, final String msg, final Object p0) {
        return filter(logger.getMessageFactory().newMessage(msg, p0).getFormattedMessage());
    }

    @Override
    public Result filter(
            final Logger logger,
            final Level level,
            final Marker marker,
            final String msg,
            final Object p0,
            final Object p1) {
        return filter(logger.getMessageFactory().newMessage(msg, p0, p1).getFormattedMessage());
    }

    @Override
    public Result filter(
            final Logger logger,
            final Level level,
            final Marker marker,
            final String msg,
            final Object p0,
            final Object p1,
            final Object p2) {
        return filter(logger.getMessageFactory().newMessage(msg, p0, p1, p2).getFormattedMessage());
    }

    @Override
    public Result filter(
            final Logger logger,
            final Level level,
            final Marker marker,
            final String msg,
            final Object p0,
            final Object p1,
            final Object p2,
            final Object p3) {
        return filter(logger.getMessageFactory().newMessage(msg, p0, p1, p2, p3).getFormattedMessage());
    }

    @Override
    public Result filter(
            final Logger logger,
            final Level level,
            final Marker marker,
            final String msg,
            final Object p0,
            final Object p1,
            final Object p2,
            final Object p3,
            final Object p4) {
        return filter(
                logger.getMessageFactory().newMessage(msg, p0, p1, p2, p3, p4).getFormattedMessage());
    }

    @Override
    public Result filter(
            final Logger logger,
            final Level level,
            final Marker marker,
            final String msg,
            final Object p0,
            final Object p1,
            final Object p2,
            final Object p3,
            final Object p4,
            final Object p5) {
        return filter(logger.getMessageFactory()
                .newMessage(msg, p0, p1, p2, p3, p4, p5)
                .getFormattedMessage());
    }

    @Override
    public Result filter(
            final Logger logger,
            final Level level,
            final Marker marker,
            final String msg,
            final Object p0,
            final Object p1,
            final Object p2,
            final Object p3,
            final Object p4,
            final Object p5,
            final Object p6) {
        return filter(logger.getMessageFactory()
                .newMessage(msg, p0, p1, p2, p3, p4, p5, p6)
                .getFormattedMessage());
    }

    @Override
    public Result filter(
            final Logger logger,
            final Level level,
            final Marker marker,
            final String msg,
            final Object p0,
            final Object p1,
            final Object p2,
            final Object p3,
            final Object p4,
            final Object p5,
            final Object p6,
            final Object p7) {
        return filter(logger.getMessageFactory()
                .newMessage(msg, p0, p1, p2, p3, p4, p5, p6, p7)
                .getFormattedMessage());
    }

    @Override
    public Result filter(
            final Logger logger,
            final Level level,
            final Marker marker,
            final String msg,
            final Object p0,
            final Object p1,
            final Object p2,
            final Object p3,
            final Object p4,
            final Object p5,
            final Object p6,
            final Object p7,
            final Object p8) {
        return filter(logger.getMessageFactory()
                .newMessage(msg, p0, p1, p2, p3, p4, p5, p6, p7, p8)
                .getFormattedMessage());
    }

    @Override
    public Result filter(
            final Logger logger,
            final Level level,
            final Marker marker,
            final String msg,
            final Object p0,
            final Object p1,
            final Object p2,
            final Object p3,
            final Object p4,
            final Object p5,
            final Object p6,
            final Object p7,
            final Object p8,
            final Object p9) {
        return filter(logger.getMessageFactory()
                .newMessage(msg, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9)
                .getFormattedMessage());
    }

    @Override
    public String toString() {
        return text;
    }

}
