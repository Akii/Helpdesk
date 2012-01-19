<div id="tabs">
	<ul>
		<?php echo $tabs; ?>
	</ul>
</div>
<div id="content">
	<?php echo $ticket_new; ?>
	<div id="ticket" class="content_form">
		<h1>Ticket Details</h1>
		
		<fieldset class="gradient_04 corners_01">
			<legend>General</legend>
			<?php echo $ticket_id; ?>
			<?php echo $assigned_to; ?>
			<?php echo $status; ?>
			<?php echo $created_on; ?>
			<?php echo $last_update; ?>
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