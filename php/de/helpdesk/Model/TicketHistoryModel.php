<?php
/**
 * Class TicketHistoryModel
 *
 * Manages ticket data
 */

namespace de\helpdesk\Model;
use de\helpdesk\Error as Error;

class TicketHistoryModel extends Model
{
	public static $name = "TicketHistory";
	public static $table_name = 'ticket_history';
	public static $table_cols = array('ticket_TID', 'changed_on', 'column_name', 'column_value');
	public static $table_pk   = '';
	
	public static function getHistory($ticket_id)
	{
		$db = Datasource\DataSource::getInstance();
		$query = sprintf("SELECT * FROM %s WHERE ticket_TID = %u", self::$table_name, $ticket_id);
		
		$db->query($query);
		
		return $db->fetchAll();
	}
}