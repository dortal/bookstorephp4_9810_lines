package com.accor.asa.commun.persistance;

/**
 * Levee en cas de difference de timestamp lors de l'ecriture (Lock Optimiste)
 */
public class TimestampException extends PersistanceException
{
    private int codeObjetMaitre = -1;

    public TimestampException(){
      super();
    }

    public TimestampException(String message){
      super(message);
    }

    public TimestampException(Exception e){
      super(e);
    }

    public TimestampException(int codeObjetMaitre){
      super();
      this.codeObjetMaitre = codeObjetMaitre;
    }

    public TimestampException(String message, int codeObjetMaitre){
      super(message);
      this.codeObjetMaitre = codeObjetMaitre;
    }

    public TimestampException(Exception e, int codeObjetMaitre){
      super(e);
      this.codeObjetMaitre = codeObjetMaitre;
    }

    public int getCodeObjetMaitre() {
        return codeObjetMaitre;
    }
}