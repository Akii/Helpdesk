<?php
use \de\helpdesk\Core as Core;

Core\Configure::write('App.encoding', 'UTF-8');

Core\Configure::write('DB.type', 'MySQL');
Core\Configure::write('DB.name', 'dapro01');
Core\Configure::write('DB.host', 'i-intra-02.informatik.hs-ulm.de');
Core\Configure::write('DB.user', 'dapro01');
Core\Configure::write('DB.pass', 'brot123');

Core\Configure::write('View.script.css', '/public/css/default.css');
Core\Configure::write('App.pagination.per_page', 2);

/**
 * @var Debug.level
 *
 * 0: No debug msgs are displayed.
 * 1: No SQL debug msgs are displayed.
 * 2: Debug + SQL msgs are displayed.
 */
Core\Configure::write('Debug.level', 2);