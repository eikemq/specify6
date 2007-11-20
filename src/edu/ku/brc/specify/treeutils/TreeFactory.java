/**
 * Copyright (C) 2007  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */
package edu.ku.brc.specify.treeutils;

import java.util.Set;

import org.apache.log4j.Logger;

import edu.ku.brc.specify.datamodel.Geography;
import edu.ku.brc.specify.datamodel.GeographyTreeDefItem;
import edu.ku.brc.specify.datamodel.GeologicTimePeriod;
import edu.ku.brc.specify.datamodel.GeologicTimePeriodTreeDefItem;
import edu.ku.brc.specify.datamodel.LithoStrat;
import edu.ku.brc.specify.datamodel.LithoStratTreeDefItem;
import edu.ku.brc.specify.datamodel.Location;
import edu.ku.brc.specify.datamodel.LocationTreeDef;
import edu.ku.brc.specify.datamodel.LocationTreeDefItem;
import edu.ku.brc.specify.datamodel.Taxon;
import edu.ku.brc.specify.datamodel.TaxonTreeDef;
import edu.ku.brc.specify.datamodel.TaxonTreeDefItem;
import edu.ku.brc.specify.datamodel.TreeDefIface;
import edu.ku.brc.specify.datamodel.TreeDefItemIface;
import edu.ku.brc.specify.datamodel.Treeable;
import edu.ku.brc.util.Pair;

/**
 * A factory class for creating instances of the {@link Treeable}, {@link TreeDefIface}, and
 * {@link TreeDefItemIface} interfaces.  The class also contains factory methods for creating
 * appropriate comparators for comparing Treeables.  Also included are some methods for
 * determining if a given Treeable instance is deletable based on type-specific business rules.
 * 
 * @author jstewart
 * @code_status Alpha
 */
public class TreeFactory
{
    public static final Logger log = Logger.getLogger(TreeFactory.class);
    
	/**
	 * Creates a new Treeable instance of the given <code>implementingClass</code> having the given parent, name,
	 * and rank.
	 * 
	 * @param implementingClass the class of the node to be created
	 * @param parentWin the parent of the new node
	 * @param name the name of the new node
	 * @param rank the rank of the new node
	 * @return the new Treeable node instance
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Treeable<T,?,?>> T createNewTreeable( Class<? extends T> implementingClass, String name )
	{
		T t = null;
		// big switch statement on implementingClass
		if( implementingClass.equals(Geography.class) )
		{
			t = (T)new Geography();
			((Geography)t).initialize();
		}
		else if( implementingClass.equals(GeologicTimePeriod.class) )
		{
			t = (T)new GeologicTimePeriod();
			((GeologicTimePeriod)t).initialize();
		}
		else if( implementingClass.equals(Location.class) )
		{
			t = (T)new Location();			
			((Location)t).initialize();
		}
        else if( implementingClass.equals(Taxon.class) )
        {
            t = (T)new Taxon();
            ((Taxon)t).initialize();
        }
        else if( implementingClass.equals(LithoStrat.class) )
        {
            t = (T)new LithoStrat();
            ((LithoStrat)t).initialize();
        }
		else
		{
			throw new IllegalArgumentException("Provided class must be one of Geography, GeologicTimePeriod, Location, LithoStrat or Taxon");
		}

		t.setName(name);

		return t;
	}
    
    /**
     * Creates a new {@link Treeable} object having the given name and the same class as the given node.
     * 
     * @param <T> the class of the new node
     * @param nodeOfSameClass a node having the same class as the node to be created
     * @param name the name of the new node
     * @return the new node
     */
    @SuppressWarnings("unchecked")
    public static <T extends Treeable<T,?,?>> T createNewTreeable( T nodeOfSameClass, String name )
    {
        if (nodeOfSameClass instanceof Taxon)
        {
            return (T)createNewTreeable(Taxon.class,name);
        }
        if (nodeOfSameClass instanceof Geography)
        {
            return (T)createNewTreeable(Geography.class,name);
        }
        if (nodeOfSameClass instanceof GeologicTimePeriod)
        {
            return (T)createNewTreeable(GeologicTimePeriod.class,name);
        }
        if (nodeOfSameClass instanceof LithoStrat)
        {
            return (T)createNewTreeable(LithoStrat.class,name);
        }
        if (nodeOfSameClass instanceof Location)
        {
            return (T)createNewTreeable(Location.class,name);
        }
        throw new IllegalArgumentException("Provided node must be instance of Geography, GeologicTimePeriod, Location, LithoStrat or Taxon");
    }
		
	/**
	 * Creates a new <code>TreeDefinitionItemIface</code> instance of the class <code>implementingClass</code>.
	 * The new item has the given parent and name.
	 * 
	 * @param implementingClass the implementation class of the item instance
	 * @param parent the items parent
	 * @param name the name of the item
	 * @return the new def item
	 */
	@SuppressWarnings("unchecked")
	public static <I extends TreeDefItemIface<?, ?, I>> I createNewTreeDefItem( Class<? extends I> implementingClass, I parent, String name )
	{
		I t = null;
		
		// big switch statement on implementingClass
		if( implementingClass.equals(GeographyTreeDefItem.class) )
		{
			t = (I)new GeographyTreeDefItem();
		}
		else if( implementingClass.equals(GeologicTimePeriodTreeDefItem.class) )
		{
			t = (I)new GeologicTimePeriodTreeDefItem();
		}
		else if( implementingClass.equals(LocationTreeDefItem.class) )
		{
			t = (I)new LocationTreeDefItem();			
		}
        else if( implementingClass.equals(TaxonTreeDefItem.class) )
        {
            t = (I)new TaxonTreeDefItem();
        }
        else if( implementingClass.equals(LithoStratTreeDefItem.class) )
        {
            t = (I)new LithoStratTreeDefItem();
        }
		else
		{
			return null;
		}
		t.initialize();
		
		if( parent != null )
		{
			t.setParent(parent);
		}
		if( name != null )
		{
			t.setName(name);
		}
		return t;
	}
    
    /**
     * @param <T>
     * @param parent
     * @return
     */
    public static <T extends Treeable<T,?,?>> String getChildQueryString(T parent)
    {
        if (parent instanceof Taxon)
        {
            return "SELECT n.id, n.name, n.fullName, n.nodeNumber, n.highestChildNodeNumber, n.rankId, n2.id, n2.fullName FROM Taxon n LEFT OUTER JOIN n.acceptedTaxon n2 WHERE n.parent=:PARENT ORDER BY n.rankId, n.name";
        }
        
        if (parent instanceof Geography)
        {
            return "SELECT n.id, n.name, n.fullName, n.nodeNumber, n.highestChildNodeNumber, n.rankId, n2.id, n2.fullName FROM Geography n LEFT OUTER JOIN n.acceptedGeography n2 WHERE n.parent=:PARENT ORDER BY n.rankId, n.name";
        }
        
        if (parent instanceof GeologicTimePeriod)
        {
            return "SELECT n.id, n.name, n.fullName, n.nodeNumber, n.highestChildNodeNumber, n.rankId, n2.id, n2.fullName FROM GeologicTimePeriod n LEFT OUTER JOIN n.acceptedGeologicTimePeriod n2 WHERE n.parent=:PARENT ORDER BY n.startPeriod, n.endPeriod, n.name";
        }
        
        if (parent instanceof Location)
        {
            return "SELECT n.id, n.name, n.fullName, n.nodeNumber, n.highestChildNodeNumber, n.rankId, n2.id, n2.fullName FROM Location n LEFT OUTER JOIN n.acceptedLocation n2 WHERE n.parent=:PARENT ORDER BY n.rankId, n.name";
        }
        
        if (parent instanceof LithoStrat)
        {
            return "SELECT n.id, n.name, n.fullName, n.nodeNumber, n.highestChildNodeNumber, n.rankId, n2.id, n2.fullName FROM LithoStrat n LEFT OUTER JOIN n.acceptedLithoStrat n2 WHERE n.parent=:PARENT ORDER BY n.rankId, n.name";
        }
        
        return null;
    }
    
    public static String getSynonymQueryString(Class<?> clazz)
    {
        if (clazz.equals(Taxon.class))
        {
            return "SELECT n.id, n.fullName FROM " + clazz.getName() + " n WHERE n.acceptedTaxon.id=:NODEID";
        }
        
        if (clazz.equals(Geography.class))
        {
            return "SELECT n.id, n.fullName FROM " + clazz.getName() + " n WHERE n.acceptedGeography.id=:NODEID";
        }
        
        if (clazz.equals(GeologicTimePeriod.class))
        {
            return "SELECT n.id, n.fullName FROM " + clazz.getName() + " n WHERE n.acceptedGeologicTimePeriod.id=:NODEID";
        }
        
        if (clazz.equals(Location.class))
        {
            return "SELECT n.id, n.fullName FROM " + clazz.getName() + " n WHERE n.acceptedLocation.id=:NODEID";
        }
        
        if (clazz.equals(LithoStrat.class))
        {
            return "SELECT n.id, n.fullName FROM " + clazz.getName() + " n WHERE n.acceptedLithoStrat.id=:NODEID";
        }
        
        return null;
    }

	/**
	 * Find and return the names of the formset and view for editing tree nodes of the same class
	 * as the given <code>Treeable</code>.
	 * 
	 * @param node a node of the class to be edited using the returned formset and view
	 * @return a {@link edu.ku.brc.util.Pair<String,String>} containing the formset and view names
	 */
	public static Pair<String,String> getAppropriateFormsetAndViewNames( Object node )
	{
	    log.info("getAppropriateFormsetAndViewNames(Object): Replace this with a call to the table ID manager to get the appropriate form");
		if( node instanceof Geography )
		{
			return new Pair<String,String>("Global","Geography");
		}

		if( node instanceof GeologicTimePeriod )
		{
			return new Pair<String,String>("Global","GeologicTimePeriod");
		}

        if( node instanceof LithoStrat )
        {
            return new Pair<String,String>("Global","LithoStrat");
        }
        
		if( node instanceof Location )
		{
			return new Pair<String,String>("Global","Location");
		}

        if( node instanceof Taxon )
        {
            return new Pair<String,String>("Global","Taxon");
        }
        
		if( node instanceof TreeDefIface<?,?,?>)
		{
			return new Pair<String,String>("Global","TreeDefEditor");
		}
		
        if( node instanceof GeographyTreeDefItem)
        {
            TreeDefItemIface<?,?,?> defItem = (TreeDefItemIface<?,?,?>)node;
            if (defItem.getParent() == null)
            {
                return new Pair<String, String>("Global","RootGeographyTreeDefItem");
            }
            return new Pair<String, String>("Global","GeographyTreeDefItem");
        }
        
        if( node instanceof GeologicTimePeriodTreeDefItem)
        {
            TreeDefItemIface<?,?,?> defItem = (TreeDefItemIface<?,?,?>)node;
            if (defItem.getParent() == null)
            {
                return new Pair<String, String>("Global","RootGeologicTimePeriodTreeDefItem");
            }
            return new Pair<String, String>("Global","GeologicTimePeriodTreeDefItem");
        }
        
        if( node instanceof LithoStratTreeDefItem)
        {
            TreeDefItemIface<?,?,?> defItem = (TreeDefItemIface<?,?,?>)node;
            if (defItem.getParent() == null)
            {
                return new Pair<String, String>("Global","RootLithoStratTreeDefItem");
            }
            return new Pair<String, String>("Global","LithoStratTreeDefItem");
        }
        
        if( node instanceof LocationTreeDefItem)
        {
            TreeDefItemIface<?,?,?> defItem = (TreeDefItemIface<?,?,?>)node;
            if (defItem.getParent() == null)
            {
                return new Pair<String, String>("Global","RootLocationTreeDefItem");
            }
            return new Pair<String, String>("Global","LocationTreeDefItem");
        }
        
        if( node instanceof TaxonTreeDefItem)
        {
            TreeDefItemIface<?,?,?> defItem = (TreeDefItemIface<?,?,?>)node;
            if (defItem.getParent() == null)
            {
                return new Pair<String, String>("Global","RootTaxonTreeDefItem");
            }
            return new Pair<String, String>("Global","TaxonTreeDefItem");
        }
        
		return null;
	}
    
	/**
     * Creates a standard {@link Location} tree.
     * 
	 * @param defName the name of the new tree
	 * @param remarks the remarks about the new tree
	 * @return the new tree
	 */
	public static LocationTreeDef createStdLocationTreeDef(String defName,String remarks)
	{
		LocationTreeDef def = new LocationTreeDef();
		def.initialize();
		def.setName(defName);
		def.setRemarks(remarks);
		
		LocationTreeDefItem defItem = new LocationTreeDefItem();
		defItem.initialize();
		defItem.setName("Root");
		defItem.setRankId(0);
		defItem.setIsEnforced(true);
        defItem.setIsInFullName(false);
		
		Location rootNode = new Location();
		rootNode.initialize();
		rootNode.setName("Root");
        rootNode.setFullName("Root");
		rootNode.setRankId(0);
        rootNode.setNodeNumber(1);
        rootNode.setHighestChildNodeNumber(1);
		
		// tie everything together
		defItem.setTreeDef(def);
		defItem.getTreeEntries().add(rootNode);
		def.getTreeDefItems().add(defItem);
		def.getTreeEntries().add(rootNode);
		rootNode.setDefinitionItem(defItem);
		rootNode.setDefinition(def);
		
		return def;
	}
    
    public static Set<TaxonTreeDefItem> addStandardTaxonDefItems(TaxonTreeDef def)
    {
        Set<TaxonTreeDefItem> items = def.getTreeDefItems();
        
        TaxonTreeDefItem prevItem = null;
        
        for (Object[] itemDesc: stdTaxonItems)
        {
            TaxonTreeDefItem item = new TaxonTreeDefItem();
            item.initialize();
            items.add(item);
            item.setTreeDef(def);
            
            item.setRankId((Integer)itemDesc[0]);
            item.setName((String)itemDesc[1]);
            item.setIsEnforced((Boolean)itemDesc[2]);
            item.setIsInFullName((Boolean)itemDesc[3]);
            item.setFullNameSeparator(" ");
            item.setParent(prevItem);
            prevItem = item;
        }
        
        return items;
    }
    
//	/** An array describing the standard levels of a {@link Location} tree. */
//	@SuppressWarnings("unused")
//	private static Object[][] stdLocItems = {
//			{   0,"Location Root",true},
//            { 200,"Building",false},
//         	{ 400,"Floor",false},
//         	{ 600,"Room",true},
//         	{ 800,"Shelf/Freezer",true}, };
//	
//    /** An array describing the standard levels of a {@link Geography} tree. */
//    @SuppressWarnings("unused")
//	private static Object[][] stdGeoItems = {
//    		{ 0, "Geography Root", true },
//			{ 200, "Continent/Ocean", true },
//			{ 400, "Country", false },
//			{ 600, "State", true },
//			{ 800, "County", false }, };
//
//    /** An array describing the standard levels of a {@link GeologicTimePeriod} tree. */
//    @SuppressWarnings("unused")
//    private static Object[][] stdGtpItems = {
//            { 0, "Time Root", true },
//            { 200, "Erathem", false },
//            { 400, "Period", false },
//            { 600, "Epoch", false },
//            { 800, "Age", false }, };
//
//    /** An array describing the standard levels of a {@link LithoStrat} tree. */
//    @SuppressWarnings("unused")
//    private static Object[][] stdLithoStratItems = {
//            { 0, "Litho Root", true },
//            { 100, "SuperGroup", false },
//            { 200, "LithoGroup", false },
//            { 300, "Formation", false },
//            { 400, "Member", false },
//            { 500, "Bed", false }, };

    /** An array describing the standard levels of a {@link Taxon} tree. */
    // format is rankID, name, isEnforced, isInFullname
	@SuppressWarnings("unused")
	private static Object[][] stdTaxonItems = {
            { TaxonTreeDef.TAXONOMY_ROOT,   "Taxonomy Root", true,  false },
            { TaxonTreeDef.KINGDOM,         "Kingdom",       true,  false },
            { TaxonTreeDef.SUBKINGDOM,      "Subkingdom",    false, false },
            { TaxonTreeDef.PHYLUM,          "Phylum",        true,  false },
            //{ TaxonTreeDef.DIVISION,        "Division",      true,  false}, // botanical collections
            { TaxonTreeDef.SUBPHYLUM,       "Subphylum",     false, false },
            //{ TaxonTreeDef.SUBDIVISION,     "Subdivision",   false, false}, // botanical collections
            { TaxonTreeDef.SUPERCLASS,      "Superclass",    false, false },
            { TaxonTreeDef.CLASS,           "Class",         true,  false },
            { TaxonTreeDef.SUBCLASS,        "Subclass",      false, false },
            { TaxonTreeDef.INFRACLASS,      "Infraclass",    false, false },
            { TaxonTreeDef.SUPERORDER,      "Superorder",    false, false },
            { TaxonTreeDef.ORDER,           "Order",         true,  false },
            { TaxonTreeDef.SUBORDER,        "Suborder",      false, false },
            { TaxonTreeDef.INFRAORDER,      "Infraorder",    false, false },
            { TaxonTreeDef.SUPERFAMILY,     "Superfamily",   false, false },
            { TaxonTreeDef.FAMILY,          "Family",        false, false },
            { TaxonTreeDef.SUBFAMILY,       "Subfamily",     false, false },
            { TaxonTreeDef.TRIBE,           "Tribe",         false, false },
            { TaxonTreeDef.SUBTRIBE,        "Subtribe",      false, false },
            { TaxonTreeDef.GENUS,           "Genus",         true,  true },
            { TaxonTreeDef.SUBGENUS,        "Subgenus",      false, true },
            { TaxonTreeDef.SECTION,         "Section",       false, true },
            { TaxonTreeDef.SUBSECTION,      "Subsection",    false, true },
            { TaxonTreeDef.SPECIES,         "Species",       false, true },
            { TaxonTreeDef.SUBSPECIES,      "Subspecies",    false, true },
            { TaxonTreeDef.VARIETY,         "Variety",       false, true },
            { TaxonTreeDef.SUBVARIETY,      "Subvariety",    false, true },
            { TaxonTreeDef.FORMA,           "Forma",         false, true },
            { TaxonTreeDef.SUBFORMA,        "Subforma",      false, true }
            };
}
