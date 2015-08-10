/**
 * Created by IntelliJ IDEA.
 * User: dgonneau
 * Date: 3 févr. 2006
 * Time: 14:59:52
 * To change this template use File | Settings | File Templates.
 */
package com.accor.asa.commun.services.mail.poolthread;

public class ExecutorState {
   public static final ExecutorState RUNNING = new ExecutorState("RUNNING");
   public static final ExecutorState PAUSED = new ExecutorState("PAUSED");
   public static final ExecutorState TERMINATING = new ExecutorState("TERMINATING");
   public static final ExecutorState TERMINATED = new ExecutorState("TERMINATED");
   public static final ExecutorState SHUTDOWN_IN_PROGRESS = new ExecutorState("SHUTDOWN_IN_PROGRESS");
   public static final ExecutorState SHUTDOWN = new ExecutorState("SHUTDOWN");

   private final String myName; // for debug only

   private ExecutorState(String name) {
      myName = name;
   }

   public String toString() {
      return myName;
   }
}
