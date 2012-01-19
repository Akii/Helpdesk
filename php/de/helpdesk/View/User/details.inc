<div id="tabs">
	<ul>
		<?php echo $tabs; ?>
	</ul>
</div>
<div id="content">
	<?php echo $ticket_new; ?>
	<div id="ticket" class="content_form">
		<h1>Ticket Details</h1>
		
		<fieldset id="ticket_head" class="gradient_04 corners_01">
			<legend>General</legend>
			<div class="align_left">
				<span>ID: <b><?php echo $ticket_id; ?></b></span>
				<span>Assigned to: <b><?php echo $assigned_to; ?></b></span>
				<span>Status: <b><?php echo $status; ?></b></span>
			</div>
			<div class="align_right">
				<span>Created on: <b><?php echo $created_on; ?></b></span>
				<span>Last change: <b><?php echo $last_update; ?></b></span>
			</div>
		</fieldset>
		<fieldset class="gradient_04 corners_01">
			<legend>Ticket Information</legend>
			<label for="category">Category:</label>
				<input type="text" name="category" value="<?php echo $category; ?>" disabled/>
			
			<label for="topic">Topic:</label>
				<input type="text" name="topic" value="<?php echo $topic; ?>" disabled/>
			
			<label for="problem">Problem:</label>
				<textarea name="problem" disabled><?php echo $problem; ?></textarea>
		</fieldset>
		<fieldset class="gradient_04 corners_01">
			<legend>Products</legend>
			<ul>
				<?php echo $products; ?>
			</ul>
		</fieldset>
		<fieldset class="gradient_04 corners_01">
			<legend>Solution</legend>
			<?php echo $solution; ?>
		</fieldset>
		<fieldset class="gradient_04 corners_01">
			<legend>Changelog</legend>
			<?php echo $changelog; ?>
		</fieldset>
	</div>
</div>