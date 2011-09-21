package whiteboard.object;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import java.util.Set;

import whiteboard.object.style.*;

/**
 * <b> FreeLine object</b> <br />
 * A freely drawn line on the whiteboard
 * Implemented as a Set of vectors, each of which corresponds to 
 * a point of the line. this set does not support null objects.
 */
public class FreeLine extends AWhiteboardObject
	implements Serializable, Set<Vector> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3400688941464945718L;
	
	
	
	private Content head;
	private StyleLine style;
	
	/**
	 * Creates a freeline object from the 
	 * first point in the line
	 * @param head
	 */
	public FreeLine(Vector head) {
		super(head);
		this.head = new Content(head);
	}
	
	@Override
	public void setStyle(Style newstyle) {
		if(newstyle instanceof StyleLine) {
			this.style = new StyleLine((StyleLine) newstyle);
		} else {
			this.style = new StyleLine(newstyle.getBrush());
		}
	}
	
	@Override
	public Style getStyle() {
		return style;
	}

	@Override
	public boolean add(Vector arg0) 
		throws NullPointerException {
		if(arg0 == null) {
			throw new NullPointerException("Cannot add null element");
		}
		Content temp = head;
		while(temp.getNext() != null) {
			if(temp.getNext().getValue().equals(arg0)) {
				return false;
			}
		}
		temp.setNext(new Content(arg0));
		return true;		
	}

	@Override
	public boolean addAll(Collection<? extends Vector> arg0) 
		throws NullPointerException, ClassCastException {
		if(arg0.equals(null)) {
			throw new NullPointerException("arg0 is null");
		}else if(!(arg0 instanceof FreeLine)) {
			throw new ClassCastException("Argument not another FreeLine");
		}
		
		if(arg0.isEmpty()) {
			return true;
		}
		//Reset the iterator
		Iterator<? extends Vector> argiter = arg0.iterator();
		//Check that we're not adding a duplicate element into the set
		//before beginning to modify the set.
		while(argiter.hasNext()) {
			if(this.contains(argiter.next()));
			return false;
		}
		//reset the iterator
		argiter = arg0.iterator();
		while(argiter.hasNext()) {
			this.add(argiter.next());
		}
		return true;
	}

	@Override
	public void clear() {
		this.head = null;		
	}

	@Override
	public boolean contains(Object arg0) {
		if(!(arg0 instanceof Vector) || arg0 == null) {
			return false;
		}
		Content ref = head;
		while(ref != null) {
			if(ref.getValue().equals(arg0)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) 
		throws NullPointerException {
		if(arg0 == null) {
			throw new NullPointerException("null argument");
		}
		if(arg0.isEmpty()) {
			return true;
		}
		int argsize = arg0.size();
		Iterator<?> argiter = arg0.iterator();
		while(argiter.hasNext()) {
			Object nextarg = argiter.next();
			if(this.contains(nextarg)) {
				argsize--;
			}
		}
		return (argsize == 0);
	}

	@Override
	public boolean isEmpty() {
		return this.head == null;
	}

	@Override
	public Iterator<Vector> iterator() {
		return new LineIterator(this.head);
	}
	

	@Override
	public boolean remove(Object arg0)
		throws NullPointerException, ClassCastException {
		if(arg0 == null) {
			throw new NullPointerException("null argument");
		} else if (!(arg0 instanceof Vector)) {
			throw new ClassCastException("Can only remove vectors");
		}
		if(!this.isEmpty()) {
			Iterator<Vector> iter = this.iterator();
			while(iter.hasNext()) {
				if(iter.next().equals(arg0)) {
					iter.remove();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) 
		throws NullPointerException,ClassCastException{
		if(arg0.contains(null)) {
			throw new NullPointerException("Set does not support null elements");
		}
		if(!(arg0 instanceof FreeLine)) {
			throw new ClassCastException("Argument not a Freeline");
		}
		if(arg0.isEmpty()) {
			return true;
		}		
		//Reset iterator
		Iterator<?> argiter = arg0.iterator();
		while(argiter.hasNext()) {
			this.remove(argiter.next());
		}
		return true;		
	}

	@Override
	public boolean retainAll(Collection<?> arg0) 
		throws NullPointerException, ClassCastException {
		if(arg0 == null) {
			throw new NullPointerException("null argument");
		} else if(!(arg0 instanceof FreeLine)) {
			throw new ClassCastException("Argument not a FreeLine Object");
		} else if (arg0.contains(null)) {
			throw new NullPointerException("This Set does not support the null element");
		}
		Iterator<Vector> iter = this.iterator();
		//Remove all elements which are not in arg0
		while(iter.hasNext()) {
			if(!arg0.contains(iter.next())) {
				iter.remove();
			}
		}		
		return false;
	}

	@Override
	public int size() {
		if(this.isEmpty()) {
			return 0;
		}
		Iterator<Vector> iter = this.iterator();
		int count = 0;
		while(iter.hasNext()) {
			iter.next();
			count++;
		}
		return count;		
	}

	@Override
	public Vector[] toArray() {
		int size = this.size();
		Vector[] arr = new Vector[size];
		Iterator<Vector> iter = this.iterator();
		for(int i=0;i<size;i++) {
			arr[i]=new Vector(iter.next());
		}
		return arr;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		throw new ArrayStoreException();
	}
	
	/**
	 * Class stores content as a forward linked list
	 */
	class Content {
		private Content next;
		private Vector value;
		
		public Content(Vector value) {
			this.value = value;
			this.next = null;
		}
		
		public Content(Content c) {
			this.next = c.getNext();
			this.value = c.getValue();
		}
		
		public Content getNext() {
			return next;
		}
		public Vector getValue() {
			return value;
		}
		
		public void setNext(Content next) {
			this.next = new Content(next);
		}
	}
	
	class LineIterator implements Iterator<Vector> {
		private Content prev;
		private Content curr;
		
		public LineIterator(Content head) {
			this.prev = null;
			this.curr = head;
		}
		@Override
		public boolean hasNext() {
			return (curr == null);
		}

		@Override
		public Vector next() {
			Vector v = curr.getValue();
			this.prev = curr;
			this.curr = curr.getNext();
			return v;
		}

		@Override
		public void remove() {
			this.prev.setNext(curr.getNext());
		}		
	};
	
	protected Content getHead() {
		return this.head;
	}

}
