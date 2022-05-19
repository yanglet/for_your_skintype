package com.project.fyst.global.aop.trace.logtrace;


import com.project.fyst.global.aop.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);
    void end(TraceStatus status);
    void exception(TraceStatus status, Exception e);
}
