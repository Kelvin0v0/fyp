<?php
$return = [];

$current = new DateTime();
$current->setTimezone(new DateTimeZone('Hongkong'));
$time =  $current->getTimestamp();
$return['date_str'] = $date_str;
$return['count'] = count($tables);
$return['date'] = $current->format("Y-m-d H:i:s");
$data = [];
foreach ($tables as $table) {
  if ($table->status == "completed") {
    $data[] = '<tr class="record">
      <td>' . $table->id . '</td>
      <td>' . "$table->sender ($table->sender_id)" . '</td>
      <td>' .  "$table->receiver ($table->receiver_id)" . '</td>
      <td>' . $table->amount . '</td>
      <td>' . $table->created_at->setTimezone(new DateTimeZone('Hongkong')) . '</td>
      <td>' . $table->updated_at->setTimezone(new DateTimeZone('Hongkong')) . '</td>
      <td>' . $table->status . '</td>
      <td>' . $table->type . '</td></tr>';
  }
}

$return['data'] = $data;
echo json_encode($return);
