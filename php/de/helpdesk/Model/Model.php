<?php
/**
 * Class Model
 *
 * Parent class for all models
 */
namespace de\helpdesk\Model;
use de\helpdesk\Utils as Utils;
use de\helpdesk\Error as Error;

abstract class Model
{
	/**
	 * @var DataSource Connection to the database
	 */
	protected $db = null;
	
	public static $name = "";
	
	/**
	 * @var string $table_name Name of the table this model is stored in
	 * @var array $table_cols Columns of the table (no need to fetch those automatically just yet)
	 * @var string $table_pk Primary key for the table
	 */
	public static $table_name = '';
	public static $table_cols = array();
	public static $table_pk   = '';
	
	/**
	 * Constructor
	 */
	public function __construct()
	{
		$this->db = Datasource\DataSource::getInstance();
		
		// initiate all table columns with null values!
		if(isset(static::$table_cols) && is_array(static::$table_cols))
			foreach(static::$table_cols as $cols)
				$this->$cols = null;
	}
	
	/**
	 * Magic methods for setting and getting attributes
	 */
	public function __get($index)
	{
		if(property_exists($this, $index) && in_array($index, static::$table_cols))
			return $this->$index;
		else
			return false;
	}
	
	public function __set($index, $value)
	{
		if(is_object($value) || is_array($value) || !in_array($index, static::$table_cols))
			return false;
		
		$this->$index = $value;
		return true;
	}
	
	public function fillModel($array=array())
	{
		if(!is_array($array))
			throw new Error\Exception("Parameter for fillModel must be an array!");
		
		foreach($array as $property => $value)
		{
			if(in_array($property, $this::$table_cols))
				$this->$property = $value;
		}
	}
	
	/**
	 * Gets all entries for a model
	 *
	 * @param $paginateWith Used to limit the output and sort the result
	 * @param $renderWith Callback function passed to the database
	 */
	public static function getAll($renderWith=null)
	{
		if(!isset(static::$table_name) || static::$table_name == "")
			throw new Error\Exception("Cannot perform getAll: Missing table name.");
			
		$db = Datasource\DataSource::getInstance();
		
		$query = sprintf("SELECT * FROM %s", static::$table_name);
		
		if(isset(static::$table_cols) && in_array("bDeleted", static::$table_cols))
			$query .= " WHERE bDeleted = 0";
		
		$db->query($query);
		
		if($renderWith !== null)
		{
			return $renderWith($db);
		}
		else
		{
			$out = array();
			while($row = $db->fetch_array("assoc"))
			{
				$out[] = $row;
			}
			return $out;
		}
	}
	
	/**
	 * Returns the number of records for the model
	 */
	public static function countAll()
	{
		if(!isset(static::$table_name) || static::$table_name == "")
			throw new Error\Exception("Cannot perform countAll: Missing table name.");
			
		$db = Datasource\DataSource::getInstance();
		
		$query = sprintf("SELECT COUNT(*) FROM %s", static::$table_name);
		
		if(isset(static::$table_cols) && in_array("bDeleted", static::$table_cols))
			$query .= " WHERE bDeleted = 0";
		
		$db->query($query);
		
		if($result = $db->fetch_array("num"))
			return $result[0];
		else
			return 0;
	}
	
	/**
	 * Returns a model based on the primary key id
	 */
	public static function getByID($id=0)
	{
		if(!isset(static::$table_name) || !isset(static::$table_pk))
			throw new Error\Exception("Cannot perform getByID: Missing table name or primary key.");
			
		$db = Datasource\DataSource::getInstance();
		
		$query = sprintf("SELECT * FROM %s WHERE %s = :id", static::$table_name, static::$table_pk);
		
		$db->prepare($query);
		$db->bind_param("id", "table_pk", $id);
		$db->execute();
		
		if($result = $db->fetch_array("assoc"))
		{
			$model = new static();
			$model->fillModel($result);
			return $model;
		}
		return null;
	}
	
	/**
	 * Creates a new model in the database based on the object
	 */
	public function create($exclude=array(), $not_null=array())
	{
		if(!isset(static::$table_name) || !isset(static::$table_cols) || !isset(static::$table_pk))
			throw new Error\Exception("Cannot perform create: Missing attributes.");
			
		$db = $this->db;
		$pk = static::$table_pk;
		
		$this->check_null("create", $not_null);
		
		$table_cols = array_diff(static::$table_cols, $exclude);
		
		$query = "INSERT INTO %s (%s) VALUES (%s)";
		$columns = implode(', ', $table_cols);
		$placeholders = implode(', ', $db->placeholder_gen($table_cols));
		
		$query = sprintf($query, static::$table_name, $columns, $placeholders);
		
		$db->prepare($query);
		
		foreach($table_cols as $value)
		{
			$db->bind_param($value, $value, $this->$value);
		}
		
		$db->execute();
		
		$insert_id = $db->insert_id();
		
		if($insert_id <= 0)
			throw new Error\ModelInteractionException("create", static::$name);
		
		$this->$pk = $insert_id;
		
		$this->refresh();
		
		return true;
	}
	
	/**
	 * Saves changes to the database
	 */
	public function save($exclude=array(), $not_null=array())
	{
		if(!isset(static::$table_name) || !isset(static::$table_cols) || !isset(static::$table_pk))
			throw new Error\Exception("Cannot perform save: Missing attributes.");
		
		$pk = static::$table_pk;
		$this->check_null("save", $not_null);
		$table_cols = array_diff(static::$table_cols, $exclude);
		
		$query = "UPDATE %s SET %s WHERE %s = :id";
		
		$placeholders = implode(', ', $this->db->placeholder_gen($table_cols, 2));
		
		$query = sprintf($query, static::$table_name, $placeholders, $pk);
		
		$this->db->prepare($query);
		
		foreach($table_cols as $value)
		{
			$this->db->bind_param($value, $value, $this->$value);
		}
		
		// bind the id
		$this->db->bind_param("id", "id", $this->$pk);
		
		$this->db->execute();
		
		return true;
	}
	
	/**
	 * Deletes a record from the database either by setting bDisabled = 1
	 * or, if no such column exists, by deleting the record.
	 */
	public function delete()
	{
		if(!isset(static::$table_name) || !isset(static::$table_cols) || !isset(static::$table_pk))
			throw new Error\Exception("Cannot perform save: Missing attributes.");
		
		$pk = static::$table_pk;
		if($this->$pk <= 0)
			return false;
		
		if(in_array("bDeleted", static::$table_cols))
		{
			$this->bDeleted = 1;
			return $this->save();
		}
		else
		{
			$query = sprintf("DELETE FROM %s WHERE %s = :id", static::$table_name, $pk);
			$this->db->prepare($query);
			$this->db->execute(array("id" => $this->$pk));
		}
		
		return true;
	}
	
	/**
	 * Fetches the current state of the attributes from the database
	 * and updates $this model
	 */
	public function refresh()
	{
		if(!isset(static::$table_name) || !isset(static::$table_pk))
			throw new Error\Exception("Cannot perform refresh: Missing attributes.");
			
		$pk = static::$table_pk;
		if($this->$pk < 1)
			return false;
		
		$query = sprintf("SELECT * FROM %s WHERE %s = :id", static::$table_name, $pk);
		
		$this->db->prepare($query);
		$this->db->execute(array("id" => $this->$pk));
		
		if($result = $this->db->fetch_array("assoc"))
		{
			foreach($result as $attribute => $value)
				$this->$attribute = $value;
				
			return true;
		}
		return false;
	}
	
	/**
	 * Check for insert and update if a column is null or not
	 */
	protected function check_null($action, $not_null)
	{
		foreach($not_null as $value)
		{
			if(!isset($this->$value) || is_null($this->$value) || $this->$value == "")
				throw new Error\ModelInteractionException($action, $this::$name);
		}
	}
}