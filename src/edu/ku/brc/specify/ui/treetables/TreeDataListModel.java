/* This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package edu.ku.brc.specify.ui.treetables;

import java.awt.FontMetrics;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.AbstractListModel;

import org.apache.log4j.Logger;

import edu.ku.brc.specify.datamodel.TreeDefIface;
import edu.ku.brc.specify.datamodel.TreeDefItemIface;
import edu.ku.brc.specify.datamodel.Treeable;
import edu.ku.brc.util.Pair;

/**
 * 
 * @code_status Beta
 * @author jstewart
 */
@SuppressWarnings("serial")
public class TreeDataListModel<T extends Treeable<T,D,I>,
								D extends TreeDefIface<T,D,I>,
								I extends TreeDefItemIface<T,D,I>>
								extends AbstractListModel
{
    private static final Logger log = Logger.getLogger(TreeDataListModel.class);

    protected Vector<T> visibleNodes;
	protected Hashtable<T,Boolean> childrenWereShowing;
	protected D treeDef;
    protected T root;
    protected T visibleRoot;
    protected Comparator<? super T> comparator;

	public TreeDataListModel( T root )
	{
		visibleNodes = new Vector<T>();
		childrenWereShowing = new Hashtable<T, Boolean>();
		this.root = root;
		this.visibleRoot = root;
		comparator = root.getComparator();
		
		treeDef = root.getDefinition();
		
		makeNodeVisible(root);
		showChildren(root);
	}
	
	public T getRoot()
	{
		return root;
	}

	public T getVisibleRoot()
	{
		return visibleRoot;
	}
	
	public D getTreeDef()
	{
		return this.treeDef;
	}
	
	public boolean allChildrenAreVisible(T t)
	{
		if( t == null )
		{
			return false;
		}
		
		for(T child: t.getChildren())
		{
			if(!visibleNodes.contains(child))
			{
				return false;
			}
		}
		return true;
	}
	
	protected boolean childrenWereShowing(T t)
	{
		Boolean showing = childrenWereShowing.get(t);
		return (showing != null) ? showing : false;
	}
	
	public void setChildrenVisible( T t, boolean visible )
	{
		// if the node is currently invisible, change the status of the children nodes
		// for when the node becomes visible in the future
		if( !visibleNodes.contains(t) )
		{
			childrenWereShowing.put(t, visible);
			return;
		}
		
		if(visible)
		{
			showChildren(t);
		}
		else
		{
			hideChildren(t);
		}
	}
	
	public void showDescendants( T t )
	{
		showChildren(t);
		for( T child: t.getChildren() )
		{
			showDescendants(child);
		}
	}
	
	protected void showChildren(T t)
	{
		if( allChildrenAreVisible(t) )
		{
			return;
		}

		for( T child: t.getChildren() )
		{
			setNodeVisible(child,true);
		}
	}
	
	protected void hideChildren(T t)
	{
		if( !allChildrenAreVisible(t) )
		{
			return;
		}
		
		for( T child: t.getChildren() )
		{
			setNodeVisible(child, false);
		}
	}
	
	protected void setNodeVisible( T t, boolean visible )
	{
		if(visible)
		{
			if( visibleNodes.contains(t) )
			{
				// already visible
				return;
			}

			int origSize = visibleNodes.size();
			int addedIndex = makeNodeVisible(t);
			if( addedIndex == -1 )
			{
				log.error("Code error: Unexpected behavior");
				return;
			}
			int sizeChange = visibleNodes.size() - origSize;
			fireIntervalAdded(this, addedIndex, addedIndex+sizeChange-1);
		}
		else
		{
			if( !visibleNodes.contains(t) )
			{
				// already invisible
				return;
			}

			int startIndex = visibleNodes.indexOf(t);
			int origSize = visibleNodes.size();
			makeNodeInvisible(t);
			fireIntervalRemoved(this, startIndex, startIndex+(origSize-visibleNodes.size()-1));
		}
	}
	
	/**
	 * @param t the node to insert
	 * @return the new index of t in the visible node collection
	 * @throws RuntimeException if the parent of t isn't currently visible
	 */
	protected int makeNodeVisible( T t )
	{
		// if this is the first node to ever be made visible...
		if( visibleNodes.isEmpty() )
		{
			visibleNodes.add(t);
			if( childrenWereShowing(t) )
			{
				showChildren(t);
			}

			return 0;
		}
		
		T parent = t.getParent();

		// if the parent node isn't currently visible, throw an Exception
		int indexOfParent = visibleNodes.indexOf(parent);
		if( indexOfParent == -1 )
		{
			throw new RuntimeException("Parent of argument must already be visible");
		}
		
		// if the parent node is the current last node...
		if( indexOfParent == visibleNodes.size()-1 )
		{
			visibleNodes.add(t);
			if( childrenWereShowing(t) )
			{
				showChildren(t);
			}

			return visibleNodes.size()-1;
		}

		// else...
		// parent is visible and not the last node
		
		int currentIndex = indexOfParent+1;
		T node = visibleNodes.get(currentIndex);
		while( true )
		{
			// if we've moved past the last descendant of 'parent',
			// insert the new node ('t') as the last descendant of 'parent'
			if( !node.isDescendantOf(parent) )
			{
				// we've ventured out of our parent's descendant set
				// we should be added just before 'node'
				visibleNodes.insertElementAt(t, currentIndex);
				if( childrenWereShowing(t) )
				{
					showChildren(t);
				}
				return currentIndex;
			}
			else if( (node.getParent() == parent) && (comparator.compare(t, node) < 0) )
			{
				//else if 'node' is a direct child of 'parent' and is after 't' according to the comparator,
				// the new node ('t') should be inserted before 'node'
				visibleNodes.insertElementAt(t, currentIndex);
				if( childrenWereShowing(t) )
				{
					showChildren(t);
				}
				return currentIndex;
			}
			else if( currentIndex == visibleNodes.size()-1 )
			{
				// if there are no more nodes after this one, insert at the end
				visibleNodes.add(t);
				if( childrenWereShowing(t) )
				{
					showChildren(t);
				}
				return visibleNodes.size()-1;
			}
			
			++currentIndex;
			node = visibleNodes.get(currentIndex);
		}
	}
	
	protected void makeNodeInvisible( T t )
	{
		if( !visibleNodes.contains(t) )
		{
			return;
		}
		
		if( allChildrenAreVisible(t) )
		{
			childrenWereShowing.put(t, true);
			
			for( T child: t.getChildren() )
			{
				makeNodeInvisible(child);
			}
		}
		else
		{
			childrenWereShowing.put(t, false);
		}
		visibleNodes.remove(t);
	}
	
	protected void removeNode( T node )
	{
		if( node == root )
		{
			throw new IllegalArgumentException("Cannot remove root node");
		}
		
		// basic algorithm here...
		// 1. hide the children of the parent node
		// 2. remove the child node
		// 3. reshow the children IF they were previously visible
		
		T parent = node.getParent();
		boolean prevVisible = allChildrenAreVisible(parent);
		hideChildren(parent);
		parent.removeChild(node);
		if( prevVisible )
		{
			showChildren(parent);
		}
	}
	
	protected void insertNode( T node, T parent )
	{
		// basic algorithm here...
		// 1. hide children of new parent
		// 2. insert new child
		// 3. show children of new parent
		
		hideChildren(parent);
		parent.addChild(node);
		showChildren(parent);
	}
	
	public SortedSet<Integer> getVisibleRanks()
	{
		TreeSet<Integer> usedRanks = new TreeSet<Integer>();
		for( T node: visibleNodes )
		{
			Integer rank = node.getRankId();
			usedRanks.add(rank);
		}
		return usedRanks;
	}
	
	public int getVisibleNodeCountForRank(Integer rankId)
	{
		if( rankId == null )
		{
			return 0;
		}
		
		int count = 0;
		for( T node: visibleNodes )
		{
			if( node.getRankId().equals(rankId) )
			{
				++count;
			}
		}
		return count;
	}
	
	public Integer getLongestNamePixelLengthByRank( Integer rank, FontMetrics fm, boolean considerRankName )
	{
		return getLongestNameAndPixelLengthByRank(rank, fm, considerRankName).second;
	}
	
	public String getLongestNameByRank( Integer rank, FontMetrics fm, boolean considerRankName )
	{
		return getLongestNameAndPixelLengthByRank(rank, fm, considerRankName).first;
	}
	
	protected Pair<String,Integer> getLongestNameAndPixelLengthByRank( Integer rank, FontMetrics fm, boolean considerRankName )
	{
		int nodeCount = getVisibleNodeCountForRank(rank);
		if( nodeCount == 0 )
		{
			return null;
		}
		
		I defItem = treeDef.getDefItemByRank(rank);

		// start with the rank name being the longest item
		// this way, it's length gets factored in
		// which is useful when displays show column headers
		String longestName = "";
		if( considerRankName == true )
		{
			longestName = defItem.getName();
		}
		int nodesFound = 0;
		for( T node: visibleNodes )
		{
			if( node.getRankId().equals(rank) )
			{
				if( fm.stringWidth(node.getName()) > fm.stringWidth(longestName) )
				{
					longestName = node.getName();
				}
				nodesFound++;
				if( nodesFound >= nodeCount )
				{
					return new Pair<String,Integer>(longestName,fm.stringWidth(longestName));
				}
			}
		}
		return new Pair<String,Integer>(longestName,fm.stringWidth(longestName));
	}
	
	public Object getElementAt(int arg0)
	{
		return visibleNodes.elementAt(arg0);
	}
	
	public int indexOf(Object o)
	{
		return visibleNodes.indexOf(o);
	}
	
	public boolean parentHasChildrenAfterNode( T parent, T child )
	{
		if( !visibleNodes.contains(child) )
		{
			throw new IllegalArgumentException("Must provide a child node that is visible");
		}
		
		if( child.getParent() != parent )
		{
			throw new IllegalArgumentException("Given nodes are not a parent/child pair");
		}
		
		int indexOfChild = visibleNodes.indexOf(child);
		for( int i = indexOfChild+1; i < visibleNodes.size(); ++i )
		{
			T node = visibleNodes.get(i);
			if( node.getParent() == parent )
			{
				return true;
			}
		}
		
		return false;
	}

	public int getSize()
	{
		return visibleNodes.size();
	}
	
	public boolean reparent( T node, T newParent )
	{
		if( node.getParent() == newParent )
		{
			return false;
		}
		
		this.removeNode(node);
		this.insertNode(node, newParent);
		node.fixFullNameForAllDescendants();
		return true;
	}

	public void addChild( T child, T parent )
	{
		parent.addChild(child);
		child.setParent(parent);
		showChildren(parent);
	}

	public void nodeValuesChanged(T node)
	{
		if( visibleNodes.contains(node) )
		{
			int index = visibleNodes.indexOf(node);
			fireContentsChanged(this, index, index);
		}
	}

	public void setVisibleRoot(T node)
	{
		setNodeVisible(visibleRoot,false);
		visibleRoot = node;
		makeNodeVisible(visibleRoot);
		showChildren(visibleRoot);
	}
}
