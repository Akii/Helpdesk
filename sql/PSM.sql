-- ---------------------------------
-- USER DEFINED FUNCTIONS
-- ---------------------------------
-- 0.	drop functions
DROP FUNCTION IF EXISTS hash_pw;
DROP FUNCTION IF EXISTS newTicket;

DELIMITER ;;

-- 1.	hash function for generating salted md5 hashes
-- 		@param clear_text Password in plain text
--		@return Password hash ready for db insertion
CREATE FUNCTION hash_pw (clear_text VARCHAR(150)) RETURNS VARCHAR(32)
BEGIN
	RETURN MD5(CONCAT('mySecretSalt123456789', clear_text));
END;;

-- 2.	Creates a new ticket and returns a valid insert id if successful
--		@param CategoryID 	Valid CategoryID from table problem_category
--		@param StatusID 	Valid StatusID from table ticket_status
--		@param employee 	Valid EID from table employee
--		@param customer 	Valid CID from table customer
--		@param Topic 		contains a summary/headline of the problem
--		@param Problem 		contains the problem
--		@param Note 		custom note for employees only (NULL)
--		@param Solution 	solution for the problem once finished (NULL)
<<<<<<< HEAD
--		@return				Insert id in case of successful insert, otherwise 0 
=======
--		@return				Insert id in case of successful insert, otherwise triggers SQL error
>>>>>>> b16ec0fed773f4d265d5c010de3870ff91b8a933
CREATE FUNCTION newTicket (CategoryID INTEGER, StatusID INTEGER, 
							employee INTEGER, customer INTEGER,
							Topic VARCHAR(250), Problem VARCHAR(5000),
							Note VARCHAR(5000), Solution VARCHAR(5000)) RETURNS INTEGER
BEGIN
	INSERT INTO ticket (CategoryID, StatusID, employee_EID, customer_CID,
						Topic, Problem, Note, Solution) VALUES
						(CategoryID, StatusID, employee, customer, Topic,
						Problem, Note, Solution);
	RETURN LAST_INSERT_ID();
END;;

DELIMITER ;

-- ---------------------------------
-- TRIGGER
-- ---------------------------------
-- 0.	drop trigger
DROP TRIGGER IF EXISTS ticket_history;
DROP TRIGGER IF EXISTS customer_insert_pwhash;
DROP TRIGGER IF EXISTS employee_insert_pwhash;

DELIMITER ;;

-- 1.	hashes the plain text password upon insert for table customer
CREATE TRIGGER customer_insert_pwhash BEFORE INSERT ON customer FOR EACH ROW
BEGIN
	SET NEW.password = hash_pw(NEW.password);
END;;

-- 2.	hashes the plain text password upon insert for table employee
CREATE TRIGGER employee_insert_pwhash BEFORE INSERT ON employee FOR EACH ROW
BEGIN
	SET NEW.password = hash_pw(NEW.password);
END;;

-- 3.	Ticket history trigger
--		Keeps track of every change done to the ticket table and saves the previous
--		data to the table ticket_history. See documentation for restrictions and details.
--		Also sets the column last_update to the current timestamp.
CREATE TRIGGER ticket_history BEFORE UPDATE ON ticket FOR EACH ROW
BEGIN
	DECLARE ticket_id INT;
	SET ticket_id = NEW.TID;
	
	IF NOT(OLD.customer_CID <=> NEW.customer_CID) THEN
		INSERT INTO ticket_history (ticket_TID, column_name, column_value) VALUES (ticket_id, 'customer_CID', OLD.customer_CID);
	END IF;
	IF NOT(OLD.employee_EID <=> NEW.employee_EID) THEN
		INSERT INTO ticket_history (ticket_TID, column_name, column_value) VALUES (ticket_id, 'employee_EID', OLD.employee_EID);
	END IF;
	IF NOT(OLD.CategoryID <=> NEW.CategoryID) THEN
		INSERT INTO ticket_history (ticket_TID, column_name, column_value) VALUES (ticket_id, 'CategoryID', OLD.CategoryID);
	END IF;
	IF NOT(OLD.StatusID <=> NEW.StatusID) THEN
		INSERT INTO ticket_history (ticket_TID, column_name, column_value) VALUES (ticket_id, 'StatusID', OLD.StatusID);
	END IF;
	IF NOT(OLD.Topic <=> NEW.Topic) THEN
		INSERT INTO ticket_history (ticket_TID, column_name, column_value) VALUES (ticket_id, 'Topic', OLD.Topic);
	END IF;
	IF NOT(OLD.Problem <=> NEW.Problem) THEN
		INSERT INTO ticket_history (ticket_TID, column_name, column_value) VALUES (ticket_id, 'Problem', OLD.Problem);
	END IF;
	IF NOT(OLD.Note <=> NEW.Note) THEN
		INSERT INTO ticket_history (ticket_TID, column_name, column_value) VALUES (ticket_id, 'Note', OLD.Note);
	END IF;
	IF NOT(OLD.Solution <=> NEW.Solution) THEN
		INSERT INTO ticket_history (ticket_TID, column_name, column_value) VALUES (ticket_id, 'Solution', OLD.Solution);
	END IF;

	SET NEW.last_update = NOW();
END;;

DELIMITER ;

-- ---------------------------------
-- STORED PROCEDURES
-- ---------------------------------
-- 0. 	drop procedures
DROP PROCEDURE IF EXISTS test_entries;
DROP PROCEDURE IF EXISTS traceTicket;

DELIMITER ;;

-- 1. 	Test entries
CREATE PROCEDURE test_entries()
BEGIN
	-- Delete all records from the tables
	DELETE FROM ticket;
	DELETE FROM products_involved;
	DELETE FROM problem_category;
	DELETE FROM ticket_status;
	DELETE FROM product;
	DELETE FROM customer;
	DELETE FROM employee;
	
	-- Alter the tables to reset the auto increment value
	ALTER TABLE customer 		AUTO_INCREMENT = 1;
	ALTER TABLE employee 		AUTO_INCREMENT = 1;
	ALTER TABLE ticket 			AUTO_INCREMENT = 1;
	ALTER TABLE ticket_status 	AUTO_INCREMENT = 1;
	ALTER TABLE ticket_history 	AUTO_INCREMENT = 1;
	ALTER TABLE problem_category AUTO_INCREMENT = 1;
	ALTER TABLE product 		AUTO_INCREMENT = 1;
	
	INSERT INTO customer (firstName, lastName, username, password, telephone, email) VALUES 
	('Salms', 'Othrenim', 	'salms', 'salms', 			NULL, 'salms.othrenim@example.com'),
	('Dalam', 'Sarayn', 	'dalam', 'dalam', 			NULL, 'dalam.sarayn@example.com'),
	('Maggothe', 'Theray', 	'maggothe', 'maggothe', 	NULL, 'maggothe.theray@example.com'),
	('Bradasou', 'Girend', 	'bradasou', 'bradasou', 	NULL, 'bradasou.girend@example.com'),
	('Norusi', 'Orethe', 	'norusi', 'norusi', 		NULL, 'norusi.orethe@example.com'),
	('Gidave', 'Helas', 	'gidave', 'gidave', 		NULL, 'gidave.helas@example.com'),
	('Bradasou', 'Irano', 	'irano', 'irano', 			NULL, 'bradasou.irano@example.com'),
	('Golam', 'Sarayn', 	'golam', 'golam', 			NULL, 'golam.sarayn@example.com'),
	('Balamus', 'Favala', 	'balamus', 'balamus', 		NULL, 'balamus.favala@example.com'),
	('Gadave', 'Berend', 	'gadave', 'gadave', 		NULL, 'gadave.berend@example.com'),
	('Tolmse', 'Ienith', 	'tolmse', 'tolmse', 		NULL, 'tolmse.ienith@example.com'),
	('Thalin', 'Arendu', 	'thalin', 'thalin', 		NULL, 'thalin.arendu@example.com'),
	('Llaalave', 'Nethre', 	'llaalave', 'llaalave', 	NULL, 'llaalave.nethre@example.com'),
	('Nadene', 'Girend', 	'nadene', 'nadene', 		NULL, 'nadene.girend@example.com'),
	('Shabael', 'Asserbas', 'shabael', 'shabael', 		NULL, 'shabael.sserbas@examble.com'),
	('Han-Il', 'Tansumiran', 'han-il', 'han-il', 		NULL, 'han-il.tansumiran@examble.com'),
	('Shabael', 'Odimsa', 'shabaelo', 'shabaelo', 		NULL, 'shabael.odimsa@examble.com'),
	('Zebbasi', 'Assunbahanammu', 'zebbasi', 'zebbasi', NULL, 'zebbasi.assunbahanammu@examble.com');
	
	INSERT INTO employee (firstName, lastName, username, password, bIsTroubleshooter) VALUES 
	('Zababa', 'Hairshashishi', 	'zababa', 'zababa', 1),
	('Ashahulu', 'Niladon', 		'niladon', 'niladon', 1),
	('Kummuuuu', 'Sanammasour', 	'kummuuuu', 'kummuuuu', 1),
	('Ohibaa', 'Kaushad', 			'ohibaa', 'ohibaa', 1),
	('Mibdinab', 'Asserb', 			'mibdinab', 'mibdinab', 1),
	('Elibaal', 'Puntar', 			'elibaal', 'elibaal', 1),
	('Ashahulu', 'Assumanallit', 	'ashahulu', 'ashahulu', 1),
	('Zebbaael', 'Dunsamsi', 		'zebbaael', 'zebbaael', 1),
	('Yahahul', 'Asserbas', 		'yahahul', 'yahahul', 1),
	('Lamanu', 'Massitisun', 		'lamanu', 'lamanu', 1);
	
	INSERT INTO product (name, description) VALUES 
	('AnySung HDD 320 GB','320 GB harddrive with 7200 rpm.'),
	('AnySung HDD 500 GB','500 GB harddrive with 7200 rpm.'),
	('AnySung HDD 1 TB','1 TB harddrive with 7200 rpm.'),
	('AnySung HDD 2 TB','2 TB harddrive with 7200 rpm.'),
	('Winmine PX Home','The all new operating system from Bigsoft - home edition.'),
	('Winmine PX Prof.','The all new operating system from Bigsoft - professional edition.');
	
	INSERT INTO problem_category (description) VALUES 
	('Hardware Problem'),
	('Software Problem'),
	('Activation Problems'),
	('Other');
	
	INSERT INTO ticket_status (description) VALUES 
	('Open'),
	('Closed'),
	('In process');
END;;

-- 2. 	Ticket Traceback (/!\WARNING/!\ There be dragons. Only a dragonborn may enter this procedure.)
--		This would probably be better if done on application side, but for the lulz here's the PSM version
--		of patching together the ticket.
--		Flaws: extremely slow; Problems: Can't JOIN the stuff.. at least not dynamically on CALL
--		@param ticket_id 	Valid TID from table ticket (returns 0 records in case of invalid)
--		@param ticket_from 	Look at the ticket the way it was on this timestamp
--		@return The ticket it was on the given time between creation and last update.
CREATE PROCEDURE traceTicket(IN ticket_id INT, IN ticket_from TIMESTAMP)
BEGIN
	-- declaring variables (no dragons yet ^.^)
	DECLARE colname VARCHAR(45);
	DECLARE colval VARCHAR(5000);
	-- guess what this is for :D …
	DECLARE done INT DEFAULT 0;
	DECLARE stmt VARCHAR(120);
	
	-- … right! the cursor (entering the dragons lair)
	DECLARE cur1 CURSOR FOR 
		SELECT `column_name`, `column_value` FROM `ticket_history`
		WHERE `ticket_TID` = ticket_id AND `changed_on` > ticket_from
    	GROUP BY `column_name`;
	
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	
	-- will be dropped after the connection has been lost
	-- placing this here ensures that the procedure can be called even if there
	-- was an error earlier
	DROP TABLE IF EXISTS tmp_ticket;
	CREATE TEMPORARY TABLE tmp_ticket (
		`TID` INT NOT NULL,
		`customer_CID` INT NOT NULL,
		`employee_EID` INT NULL,
		`CategoryID` INT NOT NULL,
		`StatusID` INT NOT NULL,
		`Topic` VARCHAR(250) NOT NULL,
		`Problem` VARCHAR(5000) NOT NULL,
		`Note` VARCHAR(5000) NULL,
		`Solution` VARCHAR(5000) NULL,
		`created_on` TIMESTAMP NOT NULL,
		`last_update` TIMESTAMP NULL
	) ENGINE=MEMORY;
	
	-- get the current ticket state (you've been warned)
	INSERT INTO tmp_ticket (SELECT * FROM ticket WHERE TID = ticket_id);
	
	-- do some testing if we got a valid input
	-- will return 0 in case there has nothing changed between now and ticket_from
	SET @num_updates = (SELECT COUNT(*) FROM ticket_history WHERE `ticket_TID` = ticket_id AND `changed_on` > ticket_from);
	
	IF (@num_updates > 0) THEN
		OPEN cur1;
		
		WHILE (done = 0) DO
			FETCH cur1 INTO colname, colval;
			-- there be dragons, not even shouting helps anymore now
			SET @stmt = CONCAT('UPDATE tmp_ticket SET ', colname, '=?');
			SET @colval = colval;
			-- fus ro dah all you want, not gonna help x.x
			PREPARE updTicketStmt FROM @stmt;
			EXECUTE updTicketStmt USING @colval;
			DEALLOCATE PREPARE updTicketStmt;
		END WHILE;
		
		CLOSE cur1;
	END IF;
	
	-- hand out the ticket the way it was at the time given :)
	SELECT * FROM tmp_ticket;
	-- very important when calling this procedure again
	DROP TABLE IF EXISTS tmp_ticket;
END;;

DELIMITER ;

CALL test_entries();

-- ---------------------------------
-- VIEWS
-- ---------------------------------
-- 0.	drop views
DROP VIEW IF EXISTS full_ticket;
DROP VIEW IF EXISTS ticket_products;

-- 1.	Show entity ticket with filled in values for employee, customer, category and status
CREATE VIEW full_ticket AS
SELECT 
	TID, 
	pc.description problem_category, 
	ts.description ticket_status,
	e.EID EID,
	e.firstName e_fn, 
	e.lastName e_ln,
	c.CID CID,
	c.firstName c_fn, 
	c.lastName c_ln, 
	c.telephone tel, 
	c.email c_email, 
	Topic, 
	Problem, 
	Note, 
	Solution, 
	created_on, 
	last_update 
FROM ticket t 
JOIN customer c ON(t.customer_CID = c.CID) 
LEFT JOIN employee e ON(t.employee_EID = e.EID) 
JOIN problem_category pc ON(t.CategoryID = pc.CategoryID) 
JOIN ticket_status ts ON(t.StatusID = ts.StatusID);

-- 2.	Show all products for a ticket id
CREATE VIEW ticket_products AS
SELECT PID, ticket_TID TID, name, description
FROM product JOIN products_involved ON(PID = product_PID);