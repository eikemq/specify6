package edu.ku.brc.specify.datamodel;

import java.util.*;




/**
 *  @hibernate.class
 *             table="geographytreedefitem" 
 */
public class GeographyTreeDefItem  implements TreeDefinitionItemIface,java.io.Serializable {

    // Fields    

     protected Integer treeDefItemId;
     protected String name;
     protected Integer rankId;
     protected GeographyTreeDef treeDef;
     protected GeographyTreeDefItem parent;
     protected Set children;


    // Constructors

    /** default constructor */
    public GeographyTreeDefItem() {
    }
    
    /** constructor with id */
    public GeographyTreeDefItem(Integer treeDefItemId) {
        this.treeDefItemId = treeDefItemId;
    }
   
    
    

    // Property accessors

    /**
     *      *  @hibernate.property column="TreeDefItemID" length="10" 
     */
    public Integer getTreeDefItemId() {
        return this.treeDefItemId;
    }
    
    public void setTreeDefItemId(Integer treeDefItemId) {
        this.treeDefItemId = treeDefItemId;
    }

    /**
     *      *  @hibernate.property column="Name" length="64"
     *             
     */
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    /**
     *      *  @hibernate.property column="RankID" 
     */
    public Integer getRankId() {
        return this.rankId;
    }
    
    public void setRankId(Integer rankId) {
        this.rankId = rankId;
    }

    /**
     *      * A reference back to the tree defintion
     */
    public GeographyTreeDef getTreeDef() {
        return this.treeDef;
    }
    
    public void setTreeDef(GeographyTreeDef treeDef) {
        this.treeDef = treeDef;
    }

    /**
     * 
     */
    public GeographyTreeDefItem getParent() {
        return this.parent;
    }
    
    public void setParent(GeographyTreeDefItem parent) {
        this.parent = parent;
    }

    /**
     * 
     */
    public Set getChildren() {
        return this.children;
    }
    
    public void setChildren(Set children) {
        this.children = children;
    }

  /**
	 * toString
	 * @return String
	 */
  public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("treeDefItemId").append("='").append(getTreeDefItemId()).append("' ");			
      buffer.append("name").append("='").append(getName()).append("' ");			
      buffer.append("treeDef").append("='").append(getTreeDef()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
	}



  // The following is extra code specified in the hbm.xml files

            
            public TreeDefinitionIface getTreeDefinition()
            {
                return getTreeDef();
            }
            
            public void setTreeDefinition(TreeDefinitionIface treeDef)
            {
                if( !(treeDef instanceof GeographyTreeDef) )
                {
                    throw new IllegalArgumentException("Argument must be an instanceof GeographyTreeDef");
                }
                setTreeDef((GeographyTreeDef)treeDef);
            }
            
            public TreeDefinitionItemIface getParentItem()
            {
                return getParent();
            }
            
            public void setParentItem(TreeDefinitionItemIface parent)
            {
                if( !(parent instanceof GeographyTreeDefItem) )
                {
                    throw new IllegalArgumentException("Argument must be an instanceof GeographyTreeDefItem");
                }
                setParent((GeographyTreeDefItem)parent);
            }

            public TreeDefinitionItemIface getChildItem()
            {
                if( getChildren().isEmpty() )
                {
                    return null;
                }
                
                return (TreeDefinitionItemIface)getChildren().iterator().next();
            }
            
            public void setChildItem(TreeDefinitionItemIface child)
            {
                if( !(child instanceof GeographyTreeDefItem) )
                {
                    throw new IllegalArgumentException("Argument must be an instanceof GeographyTreeDefItem");
                }
                Set children = Collections.synchronizedSet(new HashSet());
                children.add(child);
                setChildren(children);
            }
            
        
  // end of extra code specified in the hbm.xml files
}