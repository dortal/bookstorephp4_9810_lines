package com.accor.asa.commun.services.mail.poolthread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Ce pool de thread à quelques particularité en comparaison avec un ThreadPoolExecutor :
 * <li> Il est posible de le mettre en pause (les thread seront quand même instancié avec les mails donnés au pool de thread jusqu'a concurence du maximum autorisé mais il ne se lanceront pas, les autres mails arrivant dans le pool seront empilés dans la queue)
 * <li> Il surveille la taille de la queue et garde en mémoire la taille maximum et l'instant auquel cela s'est produit
 * <li> Il a un etat ExecutorState qui peut etre soit running, paused, in shutdown, teminated, ... etc
 * <li> Il a une procédure de shutdown qui mets à jour l'état avec l'ExecutorState adéquat
 * <li> Il posséde un mode debug
 * <li> Il surveille la taille des mail en mode debug
 * <li> Il peut service de garant de la taille maximum de la queue via la methode isFull qui se base sur la propriété maxCapacity
 * <li> Il garde en mémoire l'heure de son démarrage et peut donner le laps de temps qui s'est écoulé depuis
 * Created by IntelliJ IDEA.
 * User: dgonneau
 * Date: 2 févr. 2006
 * Time: 11:14:27
 * To change this template use File | Settings | File Templates.
 */
public class PausableThreadPoolExecutor extends ThreadPoolExecutor {
   private boolean _isPaused;
   private Date _dateLaunched;
   private int _nbMaxInQueue;
   private Date _dateWasAtMaxInQueue;
   private ReentrantLock _pauseLock = new ReentrantLock();
   private Condition _unpaused = _pauseLock.newCondition();
   private ExecutorState _state;
   private int _waitShutdown;
   private SimpleDateFormat _dateFormat;
   private int _maxCapacity;
   private int _biggestMailSize;
   private boolean _debug;


   /**
    * Override du contructeur de la classe super
    * on initialise quelques données qui sont spécifiques à la classe PausableThreadPoolExecutor
    * @param corePoolSize : taille du code de thread
    * @param maximumPoolSize : nombre de thread total maximum
    * @param keepAliveTime : keepalive time des thread créés en sus du code
    * @param unit : Time unit du keepAliveTime
    * @param workQueue : workingQueue associée à l'executor
    * @param maxCapacity : capacité maximale de la queue, gérée programaticalement
    * @param waitShutdown : temps en seconde d'attente pour un clean shutdown
    * @param debug : indique que le pool de thread est en mode debug, influe sur le travail effectué par le beforeExectute
    */
   public PausableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue workQueue,int maxCapacity,int waitShutdown, boolean debug) {
      super(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue);
      _dateLaunched = new Date();
      _nbMaxInQueue = 0;
      _maxCapacity = maxCapacity;
      _waitShutdown = waitShutdown;
      _state = ExecutorState.RUNNING;
      _dateFormat = new SimpleDateFormat("EEEE dd/MM/yyyy 'a' HH:mm:ss");
      _debug = debug;
   }

   /**
    * override du beforeExecute qui gère la mise en pause
    * surveille également les tailles de mails si en mode debug
    * surveille également la taille de la queue
    * @param t
    * @param r
    */
   protected void beforeExecute(Thread t, Runnable r) {
     super.beforeExecute(t, r);
     _pauseLock.lock();
     try {
       while (_isPaused) _unpaused.await();
     } catch (InterruptedException ie) {
       t.interrupt();
     } finally {
       _pauseLock.unlock();
     }
      int current_size = super.getQueue().size();
      if (current_size>_nbMaxInQueue){
         _nbMaxInQueue=current_size;
         _dateWasAtMaxInQueue = new Date();
      }
      int current_mailsize;
      if (_debug) {
         current_mailsize = ((MailSenderTask)r).getMail().getMemorySize();
         if (current_mailsize > _biggestMailSize){
            _biggestMailSize =  current_mailsize;
         }
      }
   }

   /**
    * permet de savoir si le queue est full par programmation dans le cas ou on utilise une queue non boundée (oh le bel anglicisme)
    * @return true si la queue est égale ou supérieur à la maxCapacity
    */
   public boolean isFull(){
      return super.getQueue().size() >= _maxCapacity;
   }

   /**
    * permet de changer "à chaud" la capacité maximal de la queue
    * @param capacity : la capacité maximale de la queue du pool de thread
    */
   public void setMaxCapacity(int capacity){
      _maxCapacity = capacity;
   }

   /**
    * permet de récupérer la capacité maximum que peut prendre la queue
    * à utiliser avec isFull en dehors de cette classe pour ne pas envoyer de mails si isFull = true
    * @return la capacité maximum
    */
   public int getMaxCapacity(){
      return _maxCapacity;
   }

   /**
    * indique si l'executor est en pause
    * @return true si en pause
    */
   public boolean isPaused(){
      return _isPaused;
   }

   /**
    * indique si l'executor est en fonctionnement normal
    * @return  true si pas en pause
    */
   public boolean isRunning(){
      return !_isPaused;
   }

   /**
    * permet de retrouver la date à laquelle le Pool de thread à été instancié
    * @return  la date de d'instanciation
    */
   public String getDateLaunched(){
      try{
         if (_dateLaunched==null) return "Pas encore demarre";
         return _dateFormat.format(_dateLaunched);
      } catch (Exception e){
         return "Pas encore demarre";
      }
   }

   /**
    * permet de retrouver le nombre maximum qui a été vu dans la queue depuis le démarrage du pool
    * @return  le max de la queue
    */
   public int getNbMaxInQueue(){
      return _nbMaxInQueue;
   }

   /**
    * Permet de retrouver la plus grande taille de mail détectée en mode DEBUG
    * @return la taille maximum détectée
    */
   public int getBiggestMailSize(){
      return _biggestMailSize;
   }

   /**
    * Permet de retrouver le temps, en secondes, pendant lequel on attend la complétion du clean shutdown
    * @return le temps en secondes
    */
   public int getWaitShutdown(){
      return _waitShutdown;
   }

   /**
    * Permet de modifier "à chaud" le temps d'attente pour un clean shutdown
    * @param waitShutdown
    */
   public void setWaitShutdown(int waitShutdown){
      _waitShutdown = waitShutdown;
   }

   /**
    * Renvoie la date à laquelle la queue à été la plus remplie
    * @return la date formatée en EEEE dd/MM/yyyy '&agrave;' HH:mm:ss
    */
   public String getDateWasAtMaxInQueue(){
      try {
         if (_dateWasAtMaxInQueue==null)
            return "Info indisponible";
         else
            return _dateFormat.format(_dateWasAtMaxInQueue);
      } catch (Exception e){
         return "Info indisponible";
      }
   }

   /**
    * récupére la durée d'execution du pool de thread en millisecondes
    * @return le temps en millisecondes
    */
   public long getTimeElapsed(){
      try {
         if(_dateLaunched==null) return 0;
         else return (new Date().getTime())-_dateLaunched.getTime();
      } catch (Exception ex) {
         return 0;
      }
   }

   /**
    * Permet de récuperer sous forme formatée la durée d'execution du pool
    * Possiblité d'avoir une version courte : "3 jours, 12h45h12s"
    * ou longue : "3 jours, 12 heures, 45 minutes, 12 secondes"
    * @param shortVersion : passer true si on veut la version courte
    * @return la durée formatée
    */
   public String getTimeElapsedString(boolean shortVersion){
      try {
         long time = getTimeElapsed();
         if (time==0) return "Pas encore demarre";
         long days = Math.abs(time/1000/3600/24);
         time-=days*1000*3600*24;
         long hours = Math.abs(time/1000/3600);
         time-=hours*1000*3600;
         long minutes = Math.abs(time/1000/60);
         time-=minutes*1000*60;
         long seconds = Math.abs(time/1000);
         StringBuffer result = new StringBuffer();
         if (days>0) result.append(days).append(" jours, ");

         if (shortVersion){
            if (hours>0) result.append(hours).append("h");
            if (minutes>0) result.append(minutes).append("m");
            if (seconds>0) result.append(seconds).append("s");
         } else {
            if (hours>0) result.append(hours).append(" heures, ");
            if (minutes>0) result.append(minutes).append(" minutes, ");
            if (seconds>0) result.append(seconds).append(" secondes");
         }

         return result.toString();
      } catch (Exception ex) {
         return "Erreur de calcul !";
      }
   }

   /**
    * Calcul le nombre de mail par heure, ou une extrapolation de ce nombre si le pool n'est pas encore démaré depuis 1 heure
    * @return le nombre de mail par heure
    */
   public long getNbMailPerHours(){
      try {
         if (getTimeElapsed()==0 || getTaskCount()==0)
            return 0;
         else
            return Math.abs((60*60*1000*getTaskCount())/getTimeElapsed());
      } catch (Exception ex){
         return 0;
      }
   }

   /**
    * Permet de retrouver l'état ExecutorState actuel du Pool de thread
    * @return l'état ExecutorState actuel
    */
   public ExecutorState getState(){
      return _state;
   }

   /**
    * Permet de retrouver le libellé de l'état ExecutorState actuel du Pool de thread
    * @return libellé de l'état ExecutorState actuel
    */
   public String getStateLabel(){
      return _state.toString();
   }

   /**
    * Mets en pause le pool de thread
    * En pause, on continue d'instancier des thread tant que l'on a pas atteint le nombre max,
    * mais ces thread attendront que la pause soit enlevée pour lancer l'envoi mail
    */
   public void pause() {
     _pauseLock.lock();
     try {
       _isPaused = true;
     } finally {
       _pauseLock.unlock();
     }
     _state = ExecutorState.PAUSED;
   }

   /**
    * Enlève la pause
    */
   public void resume() {
     _pauseLock.lock();
     try {
       _isPaused = false;
       _unpaused.signalAll();
     } finally {
       _pauseLock.unlock();
     }
     _state = ExecutorState.RUNNING;
   }

   /**
    * Permet de savoir si on est en mode debug
    * @return true si on est en mode debug
    */
   public boolean isDebugEnabled(){
      return _debug;
   }

   /**
    * Permet de forcer le mode Debug (normalement lu dans le mail.properties)
    * @param debugMode : boolean, true si on veut activer le mode debug, false si on veut le désactiver
    */
   public void setDebug(boolean debugMode){
      _debug = debugMode;
   }
   /**
    * l'intéret de cette méthode est peut être limité,
    * voir nul si elle bloque un thread weblogique
    * voir a utiliser des objets condition
    */
   public void shutdownAndUpdateState() {
      shutdown();
      _state = ExecutorState.SHUTDOWN_IN_PROGRESS;
      if (_waitShutdown<=0) _waitShutdown=10;
      try {
         awaitTermination(_waitShutdown,TimeUnit.SECONDS);
      } catch (InterruptedException ex) {
         // l'attente a été interompue ...
      }
      if (!isShutdown()) return;
      // si l'executor à été jusqu'au bout de son shutdown, il sera soit terminating ou terminated
      // si il reste dans l'état terminating cela veut dire que des taches n'ont pas répondu au thread.interrupt
      // l'executor est donc dans un état pas propre ...
      if (isTerminating()) _state=ExecutorState.TERMINATING;
      if (isTerminated()) _state=ExecutorState.TERMINATED;
   }


 }