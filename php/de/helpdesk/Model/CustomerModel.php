<?php
/**
 * Class CustomerModel
 *
 * Manages customer data
 */

namespace de\helpdesk\Model;
use de\helpdesk\Error as Error;

class CustomerModel extends Model
{
	public static $name = "Customer";
	public static $table_name = 'customer';
	public static $table_cols = array('CID', 'firstName', 'lastName', 'username', 'password', 'telephone', 'email', 'bDeleted');
	public static $table_pk   = 'CID';
	
	/**
	 * Checks login credentials for a user
	 *
	 * @param string $username Username
	 * @param string $password Plain text password
	 */
	public static function login($username, $password)
	{
		$db = Datasource\DataSource::getInstance();
		
		$query = sprintf("SELECT %s FROM %s WHERE username = :user AND password = hash_pw(:pw) AND bDeleted = 0 LIMIT 1", self::$table_pk, self::$table_name);
		$db->prepare($query);
		
		$db->bind_param("user", "user", $username);
		$db->bind_param("pw", "password", $password);
		
		$db->execute();
		
		if($result = $db->fetch_array("num"))
		{
			return (int) $result[0];
		}
		
		return 0;
	}
	
	/**
	 * @Override
	 */
	public function create()
	{
		$not_null = array('firstName', 'lastName', 'username', 'password');
		$exclude  = array('CID', 'bDeleted');
		
		parent::create($exclude, $not_null);
	}
	
	/**
	 * @Override
	 */
	public function save()
	{
		$not_null = array('firstName', 'lastName');
		$exclude  = array('CID', 'username', 'password');
		
		parent::save($exclude, $not_null);
	}
}