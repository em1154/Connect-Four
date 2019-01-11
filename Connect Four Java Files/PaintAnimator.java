import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class, designed to be used in conjunction with Paint, makes it possible
 * to create animated drawings.
 * 
 * @author Stina Bridgeman
 */

public class PaintAnimator extends TimerTask {

	/**
	 * The objects to be animated at each time step.
	 */

	private List<Object> animate_ = new ArrayList<Object>();

	/**
	 * The timer that invokes the animation.
	 */

	private Timer timer_;

	private boolean running_ = false;

	private List<Object> toanimate_ = new ArrayList<Object>();

	private List<Object> tounanimate_ = new ArrayList<Object>();

	/**
	 * Create a new animator which will perform one animation step every 'delay'
	 * milliseconds.
	 * 
	 * @param delay
	 *          number of ms between animation steps
	 */

	public PaintAnimator ( long delay ) {
		timer_ = new Timer();
		timer_.schedule(this,delay,delay);
	}

	/**
	 * Start animating an object. The object must have a method of the form
	 * 
	 * <pre>
	 *   public void doAnimateStep ( PaintAnimator animator ) { ... }
	 * </pre>
	 * 
	 * doAnimateStep will be called for each step of the animation, and should
	 * handle updating whatever needs to be updated (e.g. changing the position)
	 * and drawing the new thing. The PaintAnimator parameter is the animator that
	 * triggered the call to doAnimateStep.
	 * 
	 * @param obj
	 *          object to animate
	 */

	public synchronized void animate ( Object obj ) {
		if ( running_ ) {
			toanimate_.add(obj);
		} else {
			animate_.add(obj);
		}
	}

	/**
	 * Stop animating an object. doAnimateStep() will no longer be called for the
	 * specified object.
	 * 
	 * @param obj
	 *          object to stop animating
	 */

	public synchronized void unanimate ( Object obj ) {
		if ( running_ ) {
			tounanimate_.add(obj);
		} else {
			animate_.remove(obj);
		}
	}

	/**
	 * Carry out one animation step for each thing being animated. This method is
	 * used by the Java system, and should not be called directly.
	 */
	@Override
	public synchronized void run () {
		// a hack - don't clear the screen if there's nothing to draw
		if ( animate_.size() == 0 ) {
			return;
		}

		// clear the window
		Paint.clear();

		// animate the objects
		running_ = true;
		boolean repaint = Paint.setAutoRepaint(false);
		for ( Object obj : animate_ ) {
			try {
				Method method =
				    obj.getClass().getMethod("doAnimateStep",
				                             (Class[]) new Class[] { getClass() });
				method.invoke(obj,(Object[]) new Object[] { this });

			} catch ( SecurityException e ) {
				System.out.println("something went wrong with animating object " + obj);
				e.printStackTrace();
			} catch ( NoSuchMethodException e ) {
				System.out.println("cannot animate object " + obj
				    + " because it does not have a doAnimateStep() method");
				e.printStackTrace();
			} catch ( IllegalArgumentException e ) {
				System.out.println("something went wrong with animating object " + obj);
				e.printStackTrace();
			} catch ( IllegalAccessException e ) {
				System.out.println("something went wrong with animating object " + obj);
				e.printStackTrace();
			} catch ( InvocationTargetException e ) {
				System.out.println("something went wrong with animating object " + obj);
				e.printStackTrace();
			}

		}
		Paint.setAutoRepaint(repaint);
		running_ = false;

		for ( Object obj : toanimate_ ) {
			animate_.add(obj);
		}
		for ( Object obj : tounanimate_ ) {
			animate_.remove(obj);
		}
		toanimate_.clear();
		tounanimate_.clear();
	}

}