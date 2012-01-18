<?php
/**
 * Class mysql 
 *
 * MySQL backend for DataSource
 * Uses the PDO framework for connection
 */

namespace de\helpdesk\Model\Datasource\Database;
use de\helpdesk\Model\Datasource as ds;
use de\helpdesk\Core as Core;
use de\helpdesk\Error as Error;
use de\helpdesk\Model as Model;

class mysql extends ds\DataSource
{
	/**
	 * @var Database handle
	 */
	private $DBH;
	
	/**
	 * @var Statement handle
	 */
	private $STH;
	
	/**
	 * Constructor
	 */
	protected function __construct()
	{
		try
		{
			$this->connect();
		}
		catch(\PDOException $e)
		{
			throw new Error\SQLException($e->getCode(), "Unable to connect to the database.");
		}
	}
	
	private function connect()
	{
		$db_vars = Core\Configure::read('DB');
		
		if(!(isset($db_vars["type"]) && isset($db_vars["name"]) && isset($db_vars["host"]) && isset($db_vars["user"]) && isset($db_vars["pass"])))
			throw new Error\SQLException(0, "Could not connect to the database: Please check the settings.");
		
		$url = sprintf("%s:host=%s;dbname=%s", strtolower($db_vars["type"]), $db_vars["host"], $db_vars["name"]);
		$this->DBH = new \PDO($url, $db_vars["user"], $db_vars["pass"]);
		$this->DBH->setAttribute(\PDO::ATTR_ERRMODE, \PDO::ERRMODE_EXCEPTION);
	}
	
	public function insert_id()
	{
		if(!($this->DBH instanceof \PDO))
			return 0;
		else
			return $this->DBH->lastInsertId();
	}
	
	public function num_rows()
	{
		if(!($this->STH instanceof \PDOStatement))
			return 0;
		else
			return $this->STH->rowCount();
	}
	
	public function fetch_array($arg="both")
	{
		if(!($this->STH instanceof \PDOStatement))
			return false;
		
		switch($arg)
		{
			case "assoc":
				$this->STH->setFetchMode(\PDO::FETCH_ASSOC);
				break;
			case "num":
				$this->STH->setFetchMode(\PDO::FETCH_NUM);
				break;
			case "both":
			default:
				$this->STH->setFetchMode(\PDO::FETCH_BOTH);
		}
		
		return $this->STH->fetch();
	}
	
	/**
	 * Prepares a query, resets old variables and creates a new statement handle
	 *
	 * @param string $query Query with or without placeholders
	 */
	public function prepare($query)
	{
		if($this->last_prepare["query"] === $query)
			return false;
		
		try
		{
			$this->STH = $this->DBH->prepare($query);
			$this->last_prepare = array("query" => $query);
			$this->st_vars = array();
			$this->used_keys = array();
		}
		catch(\PDOException $e)
		{
			throw new Error\SQLException($e->getCode(), $e->getMessage());
		}
	}
	
	/**
	 * Binds a parameter to an array
	 *
	 * Example:
	 *
	 * prepare("SELECT * FROM table WHERE id=:id");
	 * bind_param("id", "table_id");
	 *
	 * $this->st_vars["table_id"] = 1;
	 * execute();
	 *
	 * $this->st_vars["table_id"] = 2;
	 * execute();
	 *
	 * @param string $index Placeholder name or number
	 * @param string $key Key for the array of statement values
	 */
	public function bind_param($index, $key, $value=null)
	{
		if($index === "") return;
		
		try
		{
			$this->STH->bindParam($index, $this->st_vars[$key]);
			
			if($value !== null)
				$this->st_vars[$key] = $value;
				
			$this->last_prepare["bound"] = true;
		}
		catch(\PDOException $e)
		{
			throw new Error\SQLException($e->getCode(), $e->getMessage());
		}
	}
	
	/**
	 * Executes a prepared statement
	 *
	 * @param array $data For executing a prepared statement without binding parameters
	 */
	public function execute($data=null)
	{
		if(!($this->STH instanceof \PDOStatement))
			throw new Error\SQLException(0, "Statement not ready.");
		
		try
		{
			if($data !== null && is_array($data))
				$this->STH->execute($data);
			else
				$this->STH->execute();
		}
		catch(\PDOException $e)
		{
			throw new Error\SQLException($e->getCode(), $e->getMessage());
		}
	}
	
	/**
	 * Direct execute a given query without placeholders
	 * 
	 * @param string $query Query to execute
	 */
	public function query($query)
	{
		if(!($this->DBH instanceof \PDO))
			throw new Error\SQLException(0, "Database handle not ready.");
		
		try
		{
			$this->last_prepare = array();
			$this->STH = $this->DBH->query($query);
		}
		catch(\PDOException $e)
		{
			throw new Error\SQLException($e->getCode(), $e->getMessage());
		}
	}
	
	/**
	 * Fetches all rows into an array and returns it
	 */
	public function fetchAll()
	{
		if(!($this->STH instanceof \PDOStatement))
			throw new Error\SQLException(0, "Statement not ready.");
		
		try
		{
			return $this->STH->fetchAll();
		}
		catch(\PDOException $e)
		{
			throw new Error\SQLException($e->getCode(), $e->getMessage());
		}
	}
}