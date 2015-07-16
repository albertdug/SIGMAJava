package org.ucla.sigma.daobase;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * The container class of exception caused during data access. 
 *
 */
public class DAOException extends RuntimeException {	

   /**
	 * Holds the reference to the exception or error that caused
	 * this exception to be thrown.
	 */
	private Throwable cause = null;

	/**
	 * Constructs a new <code>DaoException</code> without specified
	 * detail message.
	 */
   public DAOException() {
		super();
	}

	/**
	 * Constructs a new <code>DaoException</code> with specified
	 * detail message.
	 *
	 * @param msg the error message
	 */
   public DAOException(String msg) {
		super( msg );
	}

	/**
	 * Constructs a new <code>DaoException</code> with specified
	 * nested <code>Throwable</code>.
	 *
	 * @param cause the exception or error that caused this exception to be thrown
	 */
	public DAOException(Throwable cause) {
		super();
		this.cause = cause;
	}
   
	/**
	 * Constructs a new <code>DaoException</code> with specified
	 * detail message and nested <code>Throwable</code>.
	 *
	 * @param msg   the error message
	 * @param cause the exception or error that caused this exception to be thrown
	 */
	public DAOException(String msg, Throwable cause) {
		super( msg );
		this.cause = cause;
	}
   
   /**
	 * Get nested <code>Throwable</code>.
    *
	 * @return nested <code>Throwable</code>
	 */
   @Override
public Throwable getCause() {
		return cause;
	}

	/**
	 * Returns the detail message string of this throwable. If it was
	 * created with a null message, returns the following:
	 * ( cause==null ? null : cause.toString().
	 */
	@Override
	public String getMessage() {
		if ( super.getMessage() != null ) {
			return super.getMessage();
		}
		else if ( cause != null ) {
			return cause.toString();
		}
	   else {
			return null;
		}
	}
	
   /** (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace()
	 */
   @Override
public void printStackTrace(){
	   printStackTrace(System.err);        
   }

   /**(non-Javadoc)
    * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
    */
   @Override
public void printStackTrace(PrintStream out){
       synchronized ( out ) {
		    PrintWriter pw = new PrintWriter( out, false );
			 printStackTrace( pw );
			 // Flush the PrintWriter before it's GC'ed.
			 pw.flush();
      }  
    }

   /**(non-Javadoc)
    * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
    */
   @Override
public void printStackTrace(PrintWriter s)
    {
        super.printStackTrace(s);
        if (cause != null)
        {
            s.println("Caused by:");
            cause.printStackTrace(s);
        }
    }

}