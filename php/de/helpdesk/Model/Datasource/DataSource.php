<?php
/**
 * Class DataSource
 *
 * Parent class to all database implementations,
 * loads the database based on the configuration and
 * throws an exception if something goes wrong
 */

namespace de\helpdesk\Model\Datasource;
use de\helpdesk\Core as Core;
use de\helpdesk\Error as Error;

abstract class DataSource
{
	private static $database_instance = null;
	
	/**
	 * @var Statement variables
	 */
	protected $st_vars = array();
	
	/**
	 * @var Resultset The last result
	 */
	protected $result;
	
	/**
	 * @var array last prepared statement query and bound info
	 */
	protected $last_prepare;
	
	/**
	 * Returns an instance of the database object
	 */
	public static final function getInstance()
	{
		if(self::$database_instance !== null)
			return self::$database_instance;
		else
			return self::$database_instance = self::loadDB();
	}
	
	/**
	 * This function reads the configuration and
	 * loads the proper database
	 */
	private static final function loadDB()
	{
		$db_type = strtolower(Core\Configure::read('DB.type'));
		$db_class = __NAMESPACE__ . '\\Database\\' . $db_type;
		
		try
		{
			class_exists($db_class);
		}
		catch(\Exception $e)
		{
			throw new Error\DatabaseEngineNotFoundException($db_type);
		}
		
		return new $db_class();
	}
	
	/**
	 * Singleton design pattern used here.
	 * Protect the instanciation from outside.
	 */
	protected function __construct() {}
	
	/**
	 * Magic methods to set custom data
	 */
	public function __set($index, $value)
	{
		if(!is_array($index) && !is_array($value))
			$this->st_vars[$index] = $value;
	}
	
	
	/**
	 * Extracts attributes from a model
	 *
	 * @param $model Model to extract data from
	 * @returns array table_name => value
	 *
	protected function extractData($model)
	{
		$cols = $model::$table_cols;
		$out = array();
		
		foreach($cols as $col)
			$out[$col] = $model->$col;
		
		return $out;
	}*/
	
	/**
	 * Generates an array with named or unnamed placeholders
	 *
	 * @param array $array Containing the placeholder names
	 * @param string $type Way to generate output
	 * 					1: ':placeholder' or '?'
	 *					2: 'value = placeholder' or 'value = ?'
	 * @param boolean $named Generate named or unnamed placeholders
	 */
	public function placeholder_gen($array, $type=1, $named=true)
	{
		$out = array();
		
		foreach($array as $key => $value)
		{
			// $key := name of the placeholder (optional, defaulted to $value if undefined)
			if(is_int($key)) $key = $value;
			
			// prepare key for query
			$key = ($named===true) ? ":$key" : "?";
		
			if($type === 2)
			{
				$out[] = "$value = $key";
			}
			else
			{
				$out[] = $key;
			}
		}
		
		return $out;
	}
	
	/**
	 * Forcing the implementation of various functions needed to work
	 * with the models
	 */
	public abstract function prepare($query);
	public abstract function bind_param($index, $key, $value=null);
	public abstract function execute($data=null);
	public abstract function query($query);
	
	/**
	 * Some methods to work more directly with the database
	 */
	public abstract function insert_id();
	public abstract function num_rows();
	public abstract function fetch_array($arg="both");
	public abstract function fetchAll();
}