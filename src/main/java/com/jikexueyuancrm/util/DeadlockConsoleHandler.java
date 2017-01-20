package com.jikexueyuancrm.util;

import java.lang.management.ThreadInfo;
import java.util.Map;

public class DeadlockConsoleHandler implements DeadlockHandler {
 
  private String clazzName;

public DeadlockConsoleHandler(String name) {
		this.clazzName=name;
	}

@Override
  public void handleDeadlock(final ThreadInfo[] deadlockedThreads) {
	  
	  //可以替换成发送邮件
    if (deadlockedThreads != null) {
      System.err.println("Deadlock detected!");
 
      Map<Thread, StackTraceElement[]> stackTraceMap = Thread.getAllStackTraces();
      for (ThreadInfo threadInfo : deadlockedThreads) {
 
        if (threadInfo != null) {
 
          for (Thread thread : Thread.getAllStackTraces().keySet()) {
 
            if (thread.getId() == threadInfo.getThreadId()) {
              System.err.println(threadInfo.toString().trim());
 
              for (StackTraceElement ste : thread.getStackTrace()) {
                  System.err.println("t" + ste.toString().trim());
              }
            }
          }
        }
      }
    }
    
    //发送通知邮件
    EMailUtils.sendMail("jethai@126.com","jethai", "csrhit7293380", new String[]{"yuanhai@miduchina.com","jethai@126.com","934033381@qq.com"}, "dead lock", clazzName, null, null);
    
  }
}