package edu.ku.brc.specify.datamodel;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;




/**

 */
public class TaxonTreeDefItem  implements TreeDefinitionItemIface,java.io.Serializable {

    // Fields    

     protected Integer treeDefItemId;
     protected String name;
     protected Integer rankId;
     protected Boolean isEnforced;
     protected TaxonTreeDef treeDef;
     protected Set treeEntries;
     protected TaxonTreeDefItem parent;
     protected Set children;


    // Constructors

    /** default constructor */
    public TaxonTreeDefItem() {
    }
    
    /** constructor with id */
    public TaxonTreeDefItem(Integer treeDefItemId) {
        this.treeDefItemId = treeDefItemId;
    }
   
    // Initializer
    /*public void initialize()
    {
        treeDefItemId = null;
        name = null;
        rankId = null;
        isEnforced = null;
        treeDef = null;
        treeEntries = new HashSet<TreeEntrie>();
        parent = null;
        children = new HashSet<Children>();
    }*/
    // End Initializer
    

    // Property accessors

    /**
     * 
     */
    public Integer getTreeDefItemId() {
        return this.treeDefItemId;
    }
    
    public void setTreeDefItemId(Integer treeDefItemId) {
        this.treeDefItemId = treeDefItemId;
    }

    /**
     * 
     */
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     */
    public Integer getRankId() {
        return this.rankId;
    }
    
    public void setRankId(Integer rankId) {
        this.rankId = rankId;
    }

    /**
     * 
     */
    public Boolean getIsEnforced() {
        return this.isEnforced;
    }
    
    public void setIsEnforced(Boolean isEnforced) {
        this.isEnforced = isEnforced;
    }

    /**
     * 
     */
    public TaxonTreeDef getTreeDef() {
        return this.treeDef;
    }
    
    public void setTreeDef(TaxonTreeDef treeDef) {
        this.treeDef = treeDef;
    }

    /**
     * 
     */
    public Set getTreeEntries() {
        return this.treeEntries;
    }
    
    public void setTreeEntries(Set treeEntries) {
        this.treeEntries = treeEntries;
    }

    /**
     * 
     */
    public TaxonTreeDefItem getParent() {
        return this.parent;
    }
    
    public void setParent(TaxonTreeDefItem parent) {
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
                    if( !(treeDef instanceof TaxonTreeDef) )
                    {
                        throw new IllegalArgumentException("Argument must be an instanceof TaxonTreeDef");
                    }
                    setTreeDef((TaxonTreeDef)treeDef);
                }
                
                public TreeDefinitionItemIface getParentItem()
                {
                    return getParent();
                }
                
                public void setParentItem(TreeDefinitionItemIface parent)
                {
                    if( !(parent instanceof TaxonTreeDefItem) )
                    {
                        throw new IllegalArgumentException("Argument must be an instanceof TaxonTreeDefItem");
                    }
                    setParent((TaxonTreeDefItem)parent);
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
                    if( !(child instanceof TaxonTreeDefItem) )
                    {
                        throw new IllegalArgumentException("Argument must be an instanceof TaxonTreeDefItem");
                    }
                    Set children = Collections.synchronizedSet(new HashSet());
                    children.add(child);
                    setChildren(children);
                }
                
            
  // end of extra code specified in the hbm.xml files
}